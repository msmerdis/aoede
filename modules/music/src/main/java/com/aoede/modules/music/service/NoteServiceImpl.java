package com.aoede.modules.music.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.repository.NoteRepository;
import com.aoede.modules.music.transfer.Fraction;

@Service
public class NoteServiceImpl extends AbstractServiceDomainImpl <Long, Note, Long, NoteEntity, NoteRepository> implements NoteService {

	@Autowired
	private MeasureService measureService;

	public NoteServiceImpl(NoteRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
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

		updateEntity (domain, entity, includeParent, cascade);
		if (includeParent)
			measureService.updateNoteEntity(entity, domain.getMeasure().getId());

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
		domain.setId(entity.getId());
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

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Note> findByMeasureId(Long id) {
		return repository.findByMeasureId(id).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public Long createEntityKey(Long key) {
		return key;
	}

}



