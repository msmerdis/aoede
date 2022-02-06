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
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.MeasureKey;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.service.MeasureService;
import com.aoede.modules.music.transfer.measure.AccessMeasure;
import com.aoede.modules.music.transfer.measure.CreateMeasure;
import com.aoede.modules.music.transfer.measure.DetailMeasureResponse;
import com.aoede.modules.music.transfer.measure.SimpleMeasureResponse;
import com.aoede.modules.music.transfer.measure.UpdateMeasure;
import com.aoede.modules.music.transfer.section.AccessSection;
import com.aoede.modules.music.transfer.track.AccessTrack;

@RestController
@RequestMapping ("/api/measure")
public class MeasureController extends AbstractCompositeResourceController<
	MeasureKey,
	Measure,
	AccessMeasure,
	CreateMeasure,
	UpdateMeasure,
	SimpleMeasureResponse,
	DetailMeasureResponse,
	MeasureService
> {
	private NoteController noteController;
	private ConversionService conversionService;
	private SectionController sectionController;

	public MeasureController(MeasureService service, NoteController noteController, ConversionService conversionService) {
		super(service);

		this.conversionService = conversionService;
		this.noteController    = noteController;
		this.noteController.updateMeasureController(this);
	}

	public void updateSectionController (SectionController sectionController) {
		this.sectionController = sectionController;
	}

	@Override
	public SimpleMeasureResponse simpleResponse(Measure entity, boolean includeParent, boolean cascade) {
		SimpleMeasureResponse response = new SimpleMeasureResponse ();

		updateSimpleMeasureResponse (response, entity.getId());

		return response;
	}

	@Override
	public DetailMeasureResponse detailResponse(Measure entity, boolean includeParent, boolean cascade) {
		DetailMeasureResponse response = new DetailMeasureResponse ();
		MeasureKey key = entity.getId();

		updateSimpleMeasureResponse (response, key);

		if (includeParent) {
			response.setSheetId(key.getSheetId());
			response.setTrackId(new AccessTrack(key.getSheetId(), key.getTrackId()));
			response.setSectionId(new AccessSection(key.getSheetId(), key.getTrackId(), key.getSectionId()));
		}

		if (cascade) {
			response.setNotes(
				entity.getNotes().stream().map(d -> noteController.simpleResponse(d, false, true)).collect(Collectors.toList())
			);
		}

		return response;
	}

	private void updateSimpleMeasureResponse (SimpleMeasureResponse response, MeasureKey key) {
		response.setId(new AccessMeasure(key.getSheetId(), key.getTrackId(), key.getSectionId(), key.getMeasureId()));
	}

	@Override
	public Measure createDomain(CreateMeasure request) {
		Measure measure = updateDomain (request);
		Section section = new Section ();

		section.setId(sectionController.createDomainKey(
				conversionService.convert(request.getSectionId(), AccessSection.class)
		));
		measure.setSection(section);

		return measure;
	}

	@Override
	public Measure updateDomain(UpdateMeasure request) {
		Measure measure = new Measure ();

		return measure;
	}

	@GetMapping("/section/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleMeasureResponse> findAllBySection(@PathVariable("id") final AccessSection id) throws Exception {
		return service.findBySectionId(
			sectionController.createDomainKey(id)
		).stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public MeasureKey createDomainKey(AccessMeasure data) {
		MeasureKey key = new MeasureKey ();

		key.setSheetId(data.getSheetId());
		key.setTrackId(data.getTrackId());

		key.setSectionId(data.getSectionId());
		key.setMeasureId(data.getMeasureId());

		return key;
	}

}



