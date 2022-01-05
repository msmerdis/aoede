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
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.service.MeasureService;
import com.aoede.modules.music.transfer.measure.CreateMeasure;
import com.aoede.modules.music.transfer.measure.DetailMeasureResponse;
import com.aoede.modules.music.transfer.measure.SimpleMeasureResponse;
import com.aoede.modules.music.transfer.measure.UpdateMeasure;

@RestController
@RequestMapping ("/api/measure")
public class MeasureController extends AbstractResourceController<
	Long,
	Measure,
	CreateMeasure,
	UpdateMeasure,
	SimpleMeasureResponse,
	DetailMeasureResponse,
	MeasureService
> {
	NoteController noteController;

	public MeasureController(MeasureService service, NoteController noteController) {
		super(service);

		this.noteController = noteController;
	}

	@Override
	public SimpleMeasureResponse simpleResponse(Measure entity, boolean includeParent, boolean cascade) {
		SimpleMeasureResponse response = new SimpleMeasureResponse ();

		response.setMeasureId(entity.getId());

		return response;
	}

	@Override
	public DetailMeasureResponse detailResponse(Measure entity, boolean includeParent, boolean cascade) {
		DetailMeasureResponse response = new DetailMeasureResponse ();

		response.setMeasureId(entity.getId());

		if (includeParent) {
			response.setSectionId(entity.getSection().getId());
			response.setTrackId(entity.getSection().getTrack().getId());
			response.setSheetId(entity.getSection().getTrack().getSheet().getId());
		}

		if (cascade) {
			response.setNotes(
				entity.getNotes().stream().map(d -> noteController.simpleResponse(d, false, true)).collect(Collectors.toList())
			);
		}

		return response;
	}

	@Override
	public Measure createRequest(CreateMeasure request) {
		Measure measure = updateRequest (request);
		Section section = new Section ();

		section.setId(request.getSectionId());
		measure.setSection(section);

		return measure;
	}

	@Override
	public Measure updateRequest(UpdateMeasure request) {
		Measure measure = new Measure ();

		return measure;
	}

	@GetMapping("/section/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleMeasureResponse> findAllBySection(@PathVariable("id") final Long id) throws Exception {
		return service.findBySectionId(id).stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

}



