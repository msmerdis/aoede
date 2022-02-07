package com.aoede.modules.music.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.domain.TrackKey;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.entity.TrackEntity;
import com.aoede.modules.music.entity.TrackId;
import com.aoede.modules.music.repository.TrackRepository;
import com.aoede.modules.user.service.UserService;

@Service
public class TrackServiceImpl extends AbstractServiceDomainImpl <TrackKey, Track, TrackId, TrackEntity, TrackRepository> implements TrackService {

	private ClefService clefService;
	private SheetService sheetService;
	private SectionService sectionService;
	private UserService userService;

	public TrackServiceImpl(
		TrackRepository repository,
		EntityManagerFactory entityManagerFactory,
		ClefService clefService,
		SectionService sectionService,
		UserService userService
	) {
		super(repository, entityManagerFactory);

		this.clefService = clefService;
		this.sectionService = sectionService;
		this.sectionService.updateTrackService(this);
		this.userService = userService;
	}

	@Override
	public void updateSheetService (SheetService sheetService) {
		this.sheetService = sheetService;
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
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Track> findAll() throws GenericException {
		return repository.findBySheetUserId(
			userService.currentUserId()
		).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public TrackEntity createEntity(Track domain, boolean includeParent, boolean cascade) throws GenericException {
		TrackEntity entity = new TrackEntity ();
		Long sheetId = domain.getSheet().getId();

		updateEntity (domain, entity, includeParent, cascade);

		// update parent entities
		sheetService.updateTrackEntity(entity, sheetId);

		entity.setId(new TrackId(sheetId, (short) (repository.countBySheetId(sheetId) + 1)));

		return entity;
	}

	@Override
	public void updateEntity(Track domain, TrackEntity entity, boolean includeParent, boolean cascade) throws GenericException {
		if (entity.getSheet() != null)
			authenticationChecks(entity);

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
		authenticationChecks(entity);

		try {
			domain.setId(createDomainKey(entity.getId()));
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

	@Override
	public boolean verifyDelete(TrackEntity entity) {
		authenticationChecks (entity);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Track> findBySheetId(Long id) {
		return repository.findBySheetId(id).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	public void updateSectionEntity(SectionEntity entity, TrackKey key) {
		entity.setTrack(repository.getById(createEntityKey(key)));
	}

	@Override
	public TrackId createEntityKey(TrackKey key) {
		return new TrackId(key.getSheetId(), key.getTrackId());
	}

	public TrackKey createDomainKey(TrackId id) {
		TrackKey key = new TrackKey ();

		key.setSheetId(id.getSheetId());
		key.setTrackId(id.getTrackId());

		return key;
	}

	private void authenticationChecks (TrackEntity entity) {
		if (!entity.getSheet().getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot access tracks created by a different user")
			);
		}
	}

}



