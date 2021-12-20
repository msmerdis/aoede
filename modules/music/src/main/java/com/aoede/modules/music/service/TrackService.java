package com.aoede.modules.music.service;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.entity.TrackEntity;
import com.aoede.modules.music.repository.TrackRepository;

@Service
public class TrackService extends AbstractServiceDomainImpl <Long, Track, TrackEntity, TrackRepository> {

	@Autowired
	private ClefService clefService;

	@Autowired
	private SheetService sheetService;

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

		entity.setClef(domain.getClef().getId());
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
	}

}



