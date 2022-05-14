package com.aoede.modules.music.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.MeasureKey;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.NoteKey;
import com.aoede.modules.music.entity.MeasureId;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.entity.NoteId;
import com.aoede.modules.music.repository.NoteRepository;
import com.aoede.modules.music.transfer.Fraction;
import com.aoede.modules.user.service.UserService;

@Service
public class NoteServiceImpl extends AbstractServiceDomainImpl <NoteKey, Note, NoteId, NoteEntity, NoteRepository> implements NoteService {

	private SheetService sheetService;
	private MeasureService measureService;
	private UserService userService;

	public NoteServiceImpl(
		NoteRepository repository,
		EntityManagerFactory entityManagerFactory,
		SheetService sheetService,
		MeasureService measureService,
		UserService userService
	) {
		super(repository, entityManagerFactory);

		this.sheetService = sheetService;
		this.measureService = measureService;
		this.userService = userService;
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Note";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Note> findAll() throws GenericException {
		return repository.findBySheetUserId(
			userService.currentUserId()
		).stream().map(e -> createDomain(e)).collect(Collectors.toList());
	}

	@Override
	public NoteEntity createEntity(Note domain) throws GenericException {
		NoteEntity entity = new NoteEntity ();
		MeasureKey measureId = domain.getId();

		measureService.updateNoteEntity(entity, measureId);
		sheetService.updateSheetableEntity(entity, measureId.getSheetId());

		updateEntity (domain, entity);

		entity.setId(new NoteId(
			measureId.getSheetId(),
			measureId.getTrackId(),
			measureId.getSectionId(),
			measureId.getMeasureId(),
			(short) (repository.countByMeasureId(
				measureService.createEntityKey(measureId)
			) + 1)
		));

		return entity;
	}

	@Override
	public void updateEntity(Note domain, NoteEntity entity) throws GenericException {
		authenticationChecks(entity);

		entity.setNote(domain.getNote());
		entity.setValueNum(domain.getValue().getNumerator());
		entity.setValueDen(domain.getValue().getDenominator());
	}

	@Override
	public Note createDomain(NoteEntity entity) {
		Note note = new Note ();

		updateDomain(entity, note);

		return note;
	}

	@Override
	public void updateDomain(NoteEntity entity, Note domain) {
		authenticationChecks(entity);

		domain.setId(createDomainKey(entity.getId()));
		domain.setNote(entity.getNote());
		domain.setValue(
			new Fraction (entity.getValueNum(), entity.getValueDen())
		);
	}

	@Override
	public boolean verifyDelete(NoteEntity entity) {
		authenticationChecks (entity);
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Note> findByMeasureId(MeasureKey id) {
		MeasureId key = measureService.createEntityKey(id);

		return repository.findByMeasureId(key).stream().map(e -> createDomain(e)).collect(Collectors.toList());
	}

	@Override
	public NoteId createEntityKey(NoteKey key) {
		return new NoteId(key.getSheetId(), key.getTrackId(), key.getSectionId(), key.getMeasureId(), key.getNoteId());
	}

	public NoteKey createDomainKey(NoteId id) {
		NoteKey key = new NoteKey ();

		key.setSheetId(id.getSheetId());
		key.setTrackId(id.getTrackId());

		key.setSectionId(id.getSectionId());
		key.setMeasureId(id.getMeasureId());

		key.setNoteId(id.getNoteId());

		return key;
	}

	private void authenticationChecks (NoteEntity entity) {
		if (entity.getSheet() != null && !entity.getSheet().getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot access notes created by a different user")
			);
		}
	}

}



