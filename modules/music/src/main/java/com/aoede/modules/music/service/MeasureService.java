package com.aoede.modules.music.service;

import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.repository.MeasureRepository;

@Service
public class MeasureService extends AbstractServiceDomainImpl <Long, Measure, MeasureEntity, MeasureRepository> {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private NoteService noteService;

	public MeasureService(MeasureRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
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
	public MeasureEntity createEntity(Measure domain, boolean includeParent, boolean cascade) throws GenericException {
		MeasureEntity entity = new MeasureEntity ();

		updateEntity(domain, entity, includeParent, cascade);
		if (includeParent)
			sectionService.updateMeasureEntity(entity, domain.getSection().getId());

		return entity;
	}

	@Override
	public void updateEntity(Measure domain, MeasureEntity entity, boolean includeParent, boolean cascade) throws GenericException {
	}

	@Override
	public Measure createDomain(MeasureEntity entity, boolean includeParent, boolean cascade) {
		Measure measure = new Measure ();

		updateDomain (entity, measure, includeParent, cascade);

		return measure;
	}

	@Override
	public void updateDomain(MeasureEntity entity, Measure domain, boolean includeParent, boolean cascade) {
		if (cascade) {
			domain.setNotes(
				entity.getNotes().stream()
					.map(e -> noteService.createDomain(e, false, true))
					.peek(d -> d.setMeasure(domain))
					.collect(Collectors.toList())
			);
		}
	}

	public void updateNoteEntity(NoteEntity entity, Long id) {
		entity.setMeasure(repository.getById(id));
	}

}



