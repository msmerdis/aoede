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
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.service.MeasureService;
import com.aoede.modules.music.service.NoteService;
import com.aoede.modules.music.transfer.note.CreateNote;
import com.aoede.modules.music.transfer.note.DetailNoteResponse;
import com.aoede.modules.music.transfer.note.SimpleNoteResponse;
import com.aoede.modules.music.transfer.note.UpdateNote;

@RestController
@RequestMapping ("/api/note")
public class NoteController extends AbstractResourceController<
	Long,
	Note,
	Long,
	CreateNote,
	UpdateNote,
	SimpleNoteResponse,
	DetailNoteResponse,
	NoteService
> {
	MeasureService measureService;

	public NoteController(NoteService service, MeasureService measureService) {
		super(service);

		this.measureService = measureService;
	}

	@Override
	public SimpleNoteResponse simpleResponse(Note entity, boolean includeParent, boolean cascade) {
		SimpleNoteResponse response = new SimpleNoteResponse ();

		response.setNote(entity.getNote());
		response.setNoteId(entity.getId());
		response.setValue(entity.getValue());

		return response;
	}

	@Override
	public DetailNoteResponse detailResponse(Note entity, boolean includeParent, boolean cascade) {
		DetailNoteResponse response = new DetailNoteResponse ();

		response.setNote(entity.getNote());
		response.setNoteId(entity.getId());
		response.setValue(entity.getValue());

		if (includeParent)
			response.setMeasureId(entity.getMeasure().getId());

		return response;
	}

	@Override
	public Note createDomain(CreateNote request) {
		Measure measure = new Measure();
		Note note = updateDomain (request);

		measure.setId(request.getMeasureId());

		note.setMeasure(measure);

		return note;
	}

	@Override
	public Note updateDomain(UpdateNote request) {
		Note note = new Note ();

		note.setNote(request.getNote());
		note.setValue(request.getValue());

		return note;
	}

	@GetMapping("/measure/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleNoteResponse> findAllBySheet(@PathVariable("id") final Long id) throws Exception {
		return service.findByMeasureId(id).stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public Long createDomainKey(Long data) {
		return data;
	}

}



