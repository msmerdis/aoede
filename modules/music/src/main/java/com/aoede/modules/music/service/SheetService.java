package com.aoede.modules.music.service;

import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.entity.SheetEntity;
import com.aoede.modules.music.entity.TrackEntity;
import com.aoede.modules.music.repository.SheetRepository;

@Service
public class SheetService extends AbstractServiceDomainImpl <Long, Sheet, SheetEntity, SheetRepository> {

	@Autowired
	private TrackService trackService;

	public SheetService(SheetRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Sheet";
	}

	@Override
	public SheetEntity createEntity(Sheet domain, boolean includeParent, boolean cascade) {
		SheetEntity entity = new SheetEntity ();

		updateEntity (domain, entity, includeParent, cascade);

		return entity;
	}

	@Override
	public void updateEntity(Sheet domain, SheetEntity entity, boolean includeParent, boolean cascade) {
		entity.setName(domain.getName());
	}

	@Override
	public Sheet createDomain(SheetEntity entity, boolean includeParent, boolean cascade) {
		Sheet sheet = new Sheet ();

		updateDomain (entity, sheet, includeParent, cascade);

		return sheet;
	}

	@Override
	public void updateDomain(SheetEntity entity, Sheet domain, boolean includeParent, boolean cascade) {
		domain.setId(entity.getId());
		domain.setName(entity.getName());

		if (cascade) {
			domain.setTracks(
				entity.getTracks().stream()
					.map(e -> trackService.createDomain(e, false, true))
					.peek(d -> d.setSheet(domain))
					.collect(Collectors.toList())
			);
		}
	}

	public void updateTrackEntity(TrackEntity entity, Long id) {
		entity.setSheet(repository.getById(id));
	}

}



