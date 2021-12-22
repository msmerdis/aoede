package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.repository.NoteRepository;
import com.aoede.modules.music.transfer.Fraction;

@Service
public class NoteService extends AbstractServiceDomainImpl <Long, Note, NoteEntity, NoteRepository> {

	@Autowired
	private MeasureService measureService;

	public NoteService(NoteRepository repository, EntityManagerFactory entityManagerFactory) {
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
		domain.setNote(entity.getNote());
		domain.setValue(
			new Fraction (entity.getValueNum(), entity.getValueDen())
		);
	}

}



