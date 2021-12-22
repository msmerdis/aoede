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
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.entity.TrackEntity;
import com.aoede.modules.music.repository.TrackRepository;

@Service
public class TrackService extends AbstractServiceDomainImpl <Long, Track, TrackEntity, TrackRepository> {

	@Autowired
	private ClefService clefService;

	@Autowired
	private SheetService sheetService;

	@Autowired
	private SectionService sectionService;

	public TrackService(TrackRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Track";
	}

	@Override
	public TrackEntity createEntity(Track domain, boolean includeParent, boolean cascade) throws GenericException {
		TrackEntity entity = new TrackEntity ();

		updateEntity (domain, entity, includeParent, cascade);

		if (includeParent)
			sheetService.updateTrackEntity(entity, domain.getSheet().getId());

		return entity;
	}

	@Override
	public void updateEntity(Track domain, TrackEntity entity, boolean includeParent, boolean cascade) throws GenericException {
		entity.setClef(domain.getClef().getId());
	}

	@Override
	public Track createDomain(TrackEntity entity, boolean includeParent, boolean cascade) {
		Track track = new Track ();

		updateDomain (entity, track, includeParent, cascade);

		return track;
	}

	@Override
	public void updateDomain(TrackEntity entity, Track domain, boolean includeParent, boolean cascade) {
		try {
			domain.setId(entity.getId());
			domain.setClef(clefService.find(entity.getClef()));
		} catch (GenericException e) {
			throw new RuntimeException(e.getMessage());
		}

		if (includeParent)
			domain.setSheet(sheetService.createDomain(entity.getSheet(), true, false));

		if (cascade) {
			domain.setSections (
				entity.getSections().stream()
					.map(e -> sectionService.createDomain(e, false, true))
					.peek(d -> d.setTrack(domain))
					.collect(Collectors.toList())
			);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Track> findBySheetId(Long id) throws GenericException {
		return repository.findBySheetId(id).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	public void updateSectionEntity(SectionEntity entity, Long id) {
		entity.setTrack(repository.getById(id));
	}

}



