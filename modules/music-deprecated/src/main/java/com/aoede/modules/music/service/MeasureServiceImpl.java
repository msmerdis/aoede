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
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.MeasureKey;
import com.aoede.modules.music.domain.SectionKey;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.MeasureId;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.entity.SectionId;
import com.aoede.modules.music.repository.MeasureRepository;
import com.aoede.modules.user.service.UserService;

@Service
public class MeasureServiceImpl extends AbstractServiceDomainImpl <MeasureKey, Measure, MeasureId, MeasureEntity, MeasureRepository> implements MeasureService {

	private SheetService sheetService;
	private SectionService sectionService;
	private UserService userService;

	public MeasureServiceImpl(
		MeasureRepository repository,
		EntityManagerFactory entityManagerFactory,
		SheetService sheetService,
		SectionService sectionService,
		UserService userService
	) {
		super(repository, entityManagerFactory);

		this.sheetService = sheetService;
		this.sectionService = sectionService;
		this.userService = userService;
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Measure";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Measure> findAll() throws GenericException {
		return repository.findBySheetUserId(
			userService.currentUserId()
		).stream().map(e -> createDomain(e)).collect(Collectors.toList());
	}

	@Override
	public MeasureEntity createEntity(Measure domain) throws GenericException {
		MeasureEntity entity = new MeasureEntity ();
		SectionKey sectionId = domain.getId();

		sectionService.updateMeasureEntity(entity, sectionId);
		sheetService.updateSheetableEntity(entity, sectionId.getSheetId());

		updateEntity(domain, entity);

		entity.setId(new MeasureId(sectionId.getSheetId(), sectionId.getTrackId(), sectionId.getSectionId(),
			(short)(repository.countBySectionId(sectionService.createEntityKey(sectionId)) + 1)
		));

		return entity;
	}

	@Override
	public void updateEntity(Measure domain, MeasureEntity entity) throws GenericException {
		if (entity.getSheet() != null)
			authenticationChecks(entity);
	}

	@Override
	public Measure createDomain(MeasureEntity entity) {
		Measure measure = new Measure ();

		updateDomain (entity, measure);

		return measure;
	}

	@Override
	public void updateDomain(MeasureEntity entity, Measure domain) {
		authenticationChecks(entity);

		domain.setId(createDomainKey(entity.getId()));
	}

	@Override
	public boolean verifyDelete(MeasureEntity entity) {
		authenticationChecks (entity);
		return true;
	}

	@Override
	public void updateNoteEntity(NoteEntity entity, MeasureKey key) {
		entity.setMeasure(repository.getById(createEntityKey(key)));
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Measure> findBySectionId(SectionKey id) {
		SectionId key = sectionService.createEntityKey(id);

		return repository.findBySectionId(key).stream().map(e -> createDomain(e)).collect(Collectors.toList());
	}

	@Override
	public MeasureId createEntityKey(MeasureKey key) {
		return new MeasureId(key.getSheetId(), key.getTrackId(), key.getSectionId(), key.getMeasureId());
	}

	public MeasureKey createDomainKey(MeasureId id) {
		MeasureKey key = new MeasureKey ();

		key.setSheetId(id.getSheetId());
		key.setTrackId(id.getTrackId());

		key.setSectionId(id.getSectionId());
		key.setMeasureId(id.getMeasureId());

		return key;
	}

	private void authenticationChecks (MeasureEntity entity) {
		if (!entity.getSheet().getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot access measures created by a different user")
			);
		}
	}

}



