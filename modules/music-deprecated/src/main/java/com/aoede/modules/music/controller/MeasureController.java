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
import com.aoede.modules.music.service.MeasureService;
import com.aoede.modules.music.transfer.measure.AccessMeasure;
import com.aoede.modules.music.transfer.measure.CreateMeasure;
import com.aoede.modules.music.transfer.measure.DetailMeasureResponse;
import com.aoede.modules.music.transfer.measure.SimpleMeasureResponse;
import com.aoede.modules.music.transfer.measure.UpdateMeasure;
import com.aoede.modules.music.transfer.section.AccessSection;

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
	private ConversionService conversionService;
	private SectionController sectionController;

	public MeasureController(
		MeasureService service,
		SectionController sectionController,
		ConversionService conversionService
	) {
		super(service);

		this.sectionController = sectionController;
		this.conversionService = conversionService;
	}

	@Override
	public SimpleMeasureResponse simpleResponse(Measure entity) {
		SimpleMeasureResponse response = new SimpleMeasureResponse ();

		updateSimpleMeasureResponse (response, entity.getId());

		return response;
	}

	@Override
	public DetailMeasureResponse detailResponse(Measure entity) {
		DetailMeasureResponse response = new DetailMeasureResponse ();
		MeasureKey key = entity.getId();

		updateSimpleMeasureResponse (response, key);

		return response;
	}

	private void updateSimpleMeasureResponse (SimpleMeasureResponse response, MeasureKey key) {
		response.setId(new AccessMeasure(key.getSheetId(), key.getTrackId(), key.getSectionId(), key.getMeasureId()));
	}

	@Override
	public Measure createDomain(CreateMeasure request) {
		Measure measure = updateDomain (request);
		AccessSection key = conversionService.convert(request.getSectionId(), AccessSection.class);

		measure.getId().setSheetId(key.getSheetId());
		measure.getId().setTrackId(key.getTrackId());
		measure.getId().setSectionId(key.getSectionId());

		return measure;
	}

	@Override
	public Measure updateDomain(UpdateMeasure request) {
		Measure measure = new Measure ();

		measure.setId(new MeasureKey());

		return measure;
	}

	@GetMapping("/section/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleMeasureResponse> findAllBySection(@PathVariable("id") final AccessSection id) throws Exception {
		return service.findBySectionId(
			sectionController.createDomainKey(id)
		).stream().map(e -> simpleResponse(e)).collect(Collectors.toList());
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



