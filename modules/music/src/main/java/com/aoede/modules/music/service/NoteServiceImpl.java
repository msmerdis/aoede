package com.aoede.modules.music.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.MeasureKey;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.NoteKey;
import com.aoede.modules.music.entity.MeasureId;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.entity.NoteId;
import com.aoede.modules.music.repository.NoteRepository;
import com.aoede.modules.music.transfer.Fraction;

@Service
public class NoteServiceImpl extends AbstractServiceDomainImpl <NoteKey, Note, NoteId, NoteEntity, NoteRepository> implements NoteService {

	private MeasureService measureService;

	public NoteServiceImpl(NoteRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public void updateMeasureService (MeasureService measureService) {
		this.measureService = measureService;
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
	public NoteEntity createEntity(Note domain, boolean includeParent, boolean cascade) throws GenericException {
		NoteEntity entity = new NoteEntity ();
		MeasureKey measureId = domain.getMeasure().getId();

		updateEntity (domain, entity, includeParent, cascade);

		measureService.updateNoteEntity(entity, measureId);
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
	public void updateEntity(Note domain, NoteEntity entity, boolean includeParent, boolean cascade) throws GenericException {
		entity.setNote(domain.getNote());
		entity.setValueNum(domain.getValue().getNumerator());
		entity.setValueDen(domain.getValue().getDenominator());
	}

	@Override
	public Note createDomain(NoteEntity entity, boolean includeParent, boolean cascade) {
		Note note = new Note ();

		updateDomain(entity, note, includeParent, cascade);

		return note;
	}

	@Override
	public void updateDomain(NoteEntity entity, Note domain, boolean includeParent, boolean cascade) {
		domain.setId(createDomainKey(entity.getId()));
		domain.setNote(entity.getNote());
		domain.setValue(
			new Fraction (entity.getValueNum(), entity.getValueDen())
		);

		if (includeParent) {
			domain.setMeasure(
				measureService.createDomain(entity.getMeasure(), true, false)
			);
		}
	}

	@Override
	public boolean verifyDelete(NoteEntity entity) {
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Note> findByMeasureId(MeasureKey id) {
		MeasureId key = measureService.createEntityKey(id);

		return repository.findByMeasureId(key).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
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

}



