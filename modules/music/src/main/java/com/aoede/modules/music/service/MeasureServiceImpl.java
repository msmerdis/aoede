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
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.repository.MeasureRepository;

@Service
public class MeasureServiceImpl extends AbstractServiceDomainImpl <Long, Measure, MeasureEntity, MeasureRepository> implements MeasureService {

	@Autowired
	private SectionService sectionService;

	@Autowired
	private NoteService noteService;

	public MeasureServiceImpl(MeasureRepository repository, EntityManagerFactory entityManagerFactory) {
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
		domain.setId(entity.getId());

		if (includeParent) {
			domain.setSection(
				sectionService.createDomain(entity.getSection(), true, false)
			);
		}

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

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Measure> findBySectionId(Long id) {
		return repository.findBySectionId(id).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

}



