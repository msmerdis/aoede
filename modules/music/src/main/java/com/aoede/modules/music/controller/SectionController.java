package com.aoede.modules.music.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractResourceController;
import com.aoede.modules.music.domain.KeySignature;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.service.SectionService;
import com.aoede.modules.music.transfer.section.CreateSection;
import com.aoede.modules.music.transfer.section.DetailSectionResponse;
import com.aoede.modules.music.transfer.section.SimpleSectionResponse;
import com.aoede.modules.music.transfer.section.UpdateSection;

@RestController
@RequestMapping ("/api/section")
public class SectionController extends AbstractResourceController<
	Long,
	Section,
	Long,
	CreateSection,
	UpdateSection,
	SimpleSectionResponse,
	DetailSectionResponse,
	SectionService
> {
	MeasureController measureController;

	public SectionController(SectionService service, MeasureController measureController) {
		super(service);

		this.measureController = measureController;
	}

	@Override
	public SimpleSectionResponse simpleResponse(Section entity, boolean includeParent, boolean cascade) {
		SimpleSectionResponse response = new SimpleSectionResponse ();

		response.setSectionId(entity.getId());
		response.setTempo(entity.getTempo());
		response.setTimeSignature(entity.getTimeSignature());
		response.setKeySignature(entity.getKeySignature().getId());

		return response;
	}

	@Override
	public DetailSectionResponse detailResponse(Section entity, boolean includeParent, boolean cascade) {
		DetailSectionResponse response = new DetailSectionResponse ();

		response.setSectionId(entity.getId());
		response.setTempo(entity.getTempo());
		response.setTimeSignature(entity.getTimeSignature());
		response.setKeySignature(entity.getKeySignature().getId());

		if (includeParent) {
			response.setTrackId(entity.getTrack().getId());
			response.setSheetId(entity.getTrack().getSheet().getId());
		}

		if (cascade) {
			response.setMeasures(
				entity.getMeasures().stream().map(d -> measureController.simpleResponse(d, false, true)).collect(Collectors.toList())
			);
		}

		return response;
	}

	@Override
	public Section createDomain(CreateSection request) {
		Section section = updateDomain (request);
		Track track = new Track ();

		track.setId(request.getTrackId());
		section.setTrack(track);

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

		return section;
	}

	@GetMapping("/track/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleSectionResponse> findAllByTrack(@PathVariable("id") final Long id) throws Exception {
		return service.findByTrackId(id).stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public Long createDomainId(Long data) {
		return data;
	}

}



