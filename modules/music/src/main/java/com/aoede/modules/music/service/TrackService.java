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
	protected TrackEntity createEntity(Track domain) throws GenericException {
		TrackEntity entity = new TrackEntity ();

		sheetService.updateTrack(entity, domain.getSheet().getId());
		entity.setClef(domain.getClef().getId());

		return entity;
	}

	@Override
	protected void updateEntity(Track domain, TrackEntity entity) throws GenericException {
		entity.setClef(domain.getClef().getId());
	}

	@Override
	protected Track createDomain(TrackEntity entity) {
		Track track = new Track ();

		updateDomain (entity, track);

		return track;
	}

	@Override
	protected void updateDomain(TrackEntity entity, Track domain) {
		try {
			domain.setId(entity.getId());
			domain.setSheet(sheetService.createDomain(entity.getSheet()));
			domain.setClef(clefService.find(entity.getClef()));
		} catch (GenericException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}



