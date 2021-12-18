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

	public TrackService(TrackRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return null;
	}

	@Override
	protected TrackEntity createEntity(Track domain) {
		TrackEntity entity = new TrackEntity ();

		updateEntity (domain, entity);

		return entity;
	}

	@Override
	protected void updateEntity(Track domain, TrackEntity entity) {
		entity.setClef(domain.getClef().getId());
	}

	@Override
	protected Track createDomain(TrackEntity entity) {
		Track sheet = new Track ();

		updateDomain (entity, sheet);

		return sheet;
	}

	@Override
	protected void updateDomain(TrackEntity entity, Track domain) {
		try {
			domain.setId(entity.getId());
			domain.setClef(clefService.find(entity.getClef()));
		} catch (GenericException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}



