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
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.domain.SectionKey;
import com.aoede.modules.music.domain.TrackKey;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.entity.SectionId;
import com.aoede.modules.music.repository.SectionRepository;
import com.aoede.modules.music.transfer.Fraction;
import com.aoede.modules.user.service.UserService;

@Service
public class SectionServiceImpl extends AbstractServiceDomainImpl <SectionKey, Section, SectionId, SectionEntity, SectionRepository> implements SectionService {

	private TrackService trackService;
	private MeasureService measureService;
	private UserService userService;

	public SectionServiceImpl(
		SectionRepository repository,
		EntityManagerFactory entityManagerFactory,
		MeasureService measureService,
		UserService userService
	) {
		super(repository, entityManagerFactory);

		this.measureService = measureService;
		this.measureService.updateSectionService(this);
		this.userService = userService;
	}

	@Override
	public void updateTrackService (TrackService trackService) {
		this.trackService = trackService;
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Section";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Section> findAll() throws GenericException {
		return repository.findBySheetUserId(
			userService.currentUserId()
		).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public SectionEntity createEntity(Section domain, boolean includeParent, boolean cascade) throws GenericException {
		SectionEntity entity = new SectionEntity ();
		TrackKey trackId = domain.getTrack().getId();

		updateEntity(domain, entity, includeParent, cascade);

		trackService.updateSectionEntity(entity, trackId);
		entity.setId(new SectionId(trackId.getSheetId(), trackId.getTrackId(),
			(short) (repository.countByTrackId(trackService.createEntityKey(trackId)) + 1)
		));

		return entity;
	}

	@Override
	public void updateEntity(Section domain, SectionEntity entity, boolean includeParent, boolean cascade) throws GenericException {
		authenticationChecks(entity);

		entity.setKeySignature(domain.getKeySignature().getId());
		entity.setTempo(domain.getTempo());
		entity.setTimeSignatureNumerator(domain.getTimeSignature().getNumerator());
		entity.setTimeSignatureDenominator(domain.getTimeSignature().getDenominator());
	}

	@Override
	public Section createDomain(SectionEntity entity, boolean includeParent, boolean cascade) {
		Section section = new Section ();

		updateDomain (entity, section, includeParent, cascade);

		return section;
	}

	@Override
	public void updateDomain(SectionEntity entity, Section domain, boolean includeParent, boolean cascade) {
		authenticationChecks(entity);

		KeySignature keySignature = new KeySignature ();
		keySignature.setId(entity.getKeySignature());

		domain.setId(createDomainKey(entity.getId()));
		domain.setKeySignature(keySignature);
		domain.setTempo(entity.getTempo());
		domain.setTimeSignature(
			new Fraction (
				entity.getTimeSignatureNumerator(),
				entity.getTimeSignatureDenominator()
			)
		);

		if (includeParent) {
			domain.setTrack(
				trackService.createDomain(entity.getTrack(), true, false)
			);
		}

		if (cascade) {
			domain.setMeasures(
				entity.getMeasures().stream()
					.map(e -> measureService.createDomain(e, false, true))
					.peek(d -> d.setSection(domain))
					.collect(Collectors.toList())
			);
		}
	}

	@Override
	public boolean verifyDelete(SectionEntity entity) {
		authenticationChecks (entity);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Section> findByTrackId(TrackKey trackKey) {
		return repository.findByTrackId(
			trackService.createEntityKey(trackKey)
		).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public SectionId createEntityKey(SectionKey key) {
		return new SectionId(key.getSheetId(), key.getTrackId(), key.getSectionId());
	}

	public void updateMeasureEntity(MeasureEntity entity, SectionKey key) {
		entity.setSection(repository.getById(createEntityKey(key)));
	}

	public SectionKey createDomainKey(SectionId id) {
		SectionKey key = new SectionKey ();

		key.setSheetId(id.getSheetId());
		key.setTrackId(id.getTrackId());

		key.setSectionId(id.getSectionId());

		return key;
	}

	private void authenticationChecks (SectionEntity entity) {
		if (entity.getSheet() != null && !entity.getSheet().getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot access sections created by a different user")
			);
		}
	}

}



