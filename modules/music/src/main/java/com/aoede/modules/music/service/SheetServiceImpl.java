package com.aoede.modules.music.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.commons.base.exceptions.ValidationException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Fraction;
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.entity.SheetEntity;
import com.aoede.modules.music.repository.SheetRepository;
import com.aoede.modules.music.transfer.GenerateSheet;
import com.aoede.modules.user.service.UserService;

@Service
public class SheetServiceImpl extends AbstractServiceDomainImpl <Long, Sheet, Long, SheetEntity, SheetRepository> implements SheetService {

	private ClefService clefService;
	private UserService userService;

	public SheetServiceImpl(
		ClefService clefService,
		UserService userService,
		SheetRepository repository
	) {
		super(repository);

		this.clefService = clefService;
		this.userService = userService;
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Sheet";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Sheet> findAll() throws GenericException {
		return repository.findByUserId(
			userService.currentUserId()
		).stream().map(e -> createDomain(e)).collect(Collectors.toList());
	}

	@Override
	public SheetEntity createEntity(Sheet domain) {
		SheetEntity entity = new SheetEntity ();

		if (domain.getId() != null) {
			throw new GenericExceptionContainer(
				new ValidationException(
					"sheet",
					"id",
					domain.getId().toString(),
					"Sheet cannot provide an id during creation"
				)
			);
		}

		entity.setUserId(userService.currentUserId());
		entity.setName(domain.getName());
		entity.setTracks(domain.getTracks());

		return entity;
	}

	@Override
	public void updateEntity(Sheet domain, SheetEntity entity) {

		if (domain.getId() == null) {
			throw new GenericExceptionContainer(
				new ValidationException(
					"sheet",
					"id",
					null,
					"Sheet's id cannot be null"
				)
			);
		}

		if (!domain.getId().equals(entity.getId())) {
			throw new GenericExceptionContainer(
				new ValidationException(
					"sheet",
					"id",
					domain.getId().toString(),
					"Sheet's id does not match updating record"
				)
			);
		}

		if (!entity.getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot update sheets created by a different user")
			);
		}

		entity.setName(domain.getName());
		entity.setTracks(domain.getTracks());
	}

	@Override
	public Sheet createDomain(SheetEntity entity) {
		Sheet sheet = new Sheet ();

		if (!entity.getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot access sheets created by a different user")
			);
		}

		updateDomain (entity, sheet);

		return sheet;
	}

	@Override
	public void updateDomain(SheetEntity entity, Sheet domain) {
		domain.setId(entity.getId());
		domain.setName(entity.getName());
		domain.setTracks(entity.getTracks());
	}

	@Override
	public boolean verifyDelete(SheetEntity entity) {
		return entity.getUserId().equals(userService.currentUserId());
	}

	@Override
	public Long createEntityKey(Long key) {
		return key;
	}

	@Override
	public Sheet generate(GenerateSheet data) throws GenericException {
		// Generate note
		List<Note> notes = new LinkedList<Note>();
		Note note = new Note();
		notes.add(note);

		int pitch = clefService.find(data.getClef()).getNote();

		note.setPitch(pitch);

		Fraction fraction = new Fraction (
			data.getTimeSignature().getNumerator(),
			data.getTimeSignature().getDenominator()
		);
		fraction.simplify();

		if (fraction.getNumerator()   % 31 == 0 &&
			fraction.getDenominator() % 16 == 0) {
			fraction.setNumerator  (fraction.getNumerator()   / 31);
			fraction.setDenominator(fraction.getDenominator() / 16);
			note.setFlags(Map.of("DOTTED", Integer.toString(4)));
		}

		if (fraction.getNumerator()   % 15 == 0 &&
			fraction.getDenominator() %  8 == 0) {
			fraction.setNumerator  (fraction.getNumerator()   / 15);
			fraction.setDenominator(fraction.getDenominator() /  8);
			note.setFlags(Map.of("DOTTED", Integer.toString(3)));
		}

		if (fraction.getNumerator()   % 7 == 0 &&
			fraction.getDenominator() % 4 == 0) {
			fraction.setNumerator  (fraction.getNumerator()   / 7);
			fraction.setDenominator(fraction.getDenominator() / 4);
			note.setFlags(Map.of("DOTTED", Integer.toString(2)));
		}

		if (fraction.getNumerator()   % 3 == 0 &&
			fraction.getDenominator() % 2 == 0) {
			fraction.setNumerator  (fraction.getNumerator()   / 3);
			fraction.setDenominator(fraction.getDenominator() / 2);
			note.setFlags(Map.of("DOTTED", Integer.toString(1)));
		}

		note.setValue(fraction.getDenominator());

		for (int i = 1; i < fraction.getNumerator(); i += 1) {
			notes.add(note = new Note());
			note.setPitch(pitch);
			note.setValue(fraction.getDenominator());
		}

		// generate measure
		Measure measure = new Measure();

		measure.setNotes(notes);

		// generate track
		Track track = new Track();

		track.setClef(data.getClef());
		track.setKeySignature(data.getKeySignature());
		track.setTimeSignature(data.getTimeSignature());
		track.setTempo(data.getTempo());
		track.setMeasures(List.of(measure));

		// generate sheet
		Sheet sheet = new Sheet();

		sheet.setName(data.getName());
		sheet.setTracks(List.of(track));

		return sheet;
	}

}



