package com.aoede.modules.music.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractCompositeResourceController;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.domain.SectionKey;
import com.aoede.modules.music.service.SectionService;
import com.aoede.modules.music.transfer.section.AccessSection;
import com.aoede.modules.music.transfer.section.CreateSection;
import com.aoede.modules.music.transfer.section.DetailSectionResponse;
import com.aoede.modules.music.transfer.section.SimpleSectionResponse;
import com.aoede.modules.music.transfer.section.UpdateSection;
import com.aoede.modules.music.transfer.track.AccessTrack;

@RestController
@RequestMapping ("/api/section")
public class SectionController extends AbstractCompositeResourceController<
	SectionKey,
	Section,
	AccessSection,
	CreateSection,
	UpdateSection,
	SimpleSectionResponse,
	DetailSectionResponse,
	SectionService
> {
	private ConversionService conversionService;
	private TrackController trackController;

	public SectionController(
		SectionService service,
		ConversionService conversionService,
		TrackController trackController
	) {
		super(service);

		this.conversionService = conversionService;
		this.trackController = trackController;
	}

	@Override
	public SimpleSectionResponse simpleResponse(Section entity) {
		SimpleSectionResponse response = new SimpleSectionResponse ();

		updateSimpleSectionResponse (response, entity, entity.getId());

		return response;
	}

	@Override
	public DetailSectionResponse detailResponse(Section entity) {
		DetailSectionResponse response = new DetailSectionResponse ();
		SectionKey key = entity.getId();

		updateSimpleSectionResponse (response, entity, key);

		return response;
	}

	private void updateSimpleSectionResponse (SimpleSectionResponse response, Section entity, SectionKey key) {
		response.setId(new AccessSection(key.getSheetId(), key.getTrackId(), key.getSectionId()));
		response.setTempo(entity.getTempo());
		response.setTimeSignature(entity.getTimeSignature());
		response.setKeySignature(entity.getKeySignature().getId());
	}

	@Override
	public Section createDomain(CreateSection request) {
		Section section = updateDomain (request);
		AccessTrack key = conversionService.convert(request.getTrackId(), AccessTrack.class);

		section.getId().setSheetId(key.getSheetId());
		section.getId().setTrackId(key.getTrackId());

		return section;
	}

	@Override
	public Section updateDomain(UpdateSection request) {
		Section section = new Section ();
		KeySignature keySignature = new KeySignature();

		keySignature.setId(request.getKeySignature());

		section.setKeySignature(keySignature);
		section.setTempo(request.getTempo());
		section.setTimeSignature(request.getTimeSignature());
		section.setId(new SectionKey());

		return section;
	}

	@GetMapping("/track/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleSectionResponse> findAllByTrack(@PathVariable("id") final AccessTrack id) throws Exception {
		return service.findByTrackId(
			trackController.createDomainKey(id)
		).stream().map(e -> simpleResponse(e)).collect(Collectors.toList());
	}

	@Override
	public SectionKey createDomainKey(AccessSection data) {
		SectionKey key = new SectionKey ();

		key.setSheetId(data.getSheetId());
		key.setTrackId(data.getTrackId());

		key.setSectionId(data.getSectionId());

		return key;
	}

}



