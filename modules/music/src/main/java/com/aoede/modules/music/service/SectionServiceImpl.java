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
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.repository.SectionRepository;
import com.aoede.modules.music.transfer.Fraction;

@Service
public class SectionServiceImpl extends AbstractServiceDomainImpl <Long, Section, Long, SectionEntity, SectionRepository> implements SectionService {

	@Autowired
	private TrackService trackService;

	@Autowired
	private MeasureService measureService;

	public SectionServiceImpl(SectionRepository repository, EntityManagerFactory entityManagerFactory) {
		super(repository, entityManagerFactory);
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
	public SectionEntity createEntity(Section domain, boolean includeParent, boolean cascade) throws GenericException {
		SectionEntity entity = new SectionEntity ();

		updateEntity(domain, entity, includeParent, cascade);
		if (includeParent)
			trackService.updateSectionEntity(entity, domain.getTrack().getId());

		return entity;
	}

	@Override
	public void updateEntity(Section domain, SectionEntity entity, boolean includeParent, boolean cascade) throws GenericException {
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
		KeySignature keySignature = new KeySignature ();
		keySignature.setId(entity.getKeySignature());

		domain.setId(entity.getId());
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

	public void updateMeasureEntity(MeasureEntity entity, Long id) {
		entity.setSection(repository.getById(id));
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Section> findByTrackId(Long id) {
		return repository.findByTrackId(id).stream().map(e -> createDomain(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public Long createEntityKey(Long key) {
		return key;
	}

}



