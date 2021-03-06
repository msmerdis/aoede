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
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.NoteKey;
import com.aoede.modules.music.service.NoteService;
import com.aoede.modules.music.transfer.measure.AccessMeasure;
import com.aoede.modules.music.transfer.note.AccessNote;
import com.aoede.modules.music.transfer.note.CreateNote;
import com.aoede.modules.music.transfer.note.DetailNoteResponse;
import com.aoede.modules.music.transfer.note.SimpleNoteResponse;
import com.aoede.modules.music.transfer.note.UpdateNote;

@RestController
@RequestMapping ("/api/note")
public class NoteController extends AbstractCompositeResourceController<
	NoteKey,
	Note,
	AccessNote,
	CreateNote,
	UpdateNote,
	SimpleNoteResponse,
	DetailNoteResponse,
	NoteService
> {
	private ConversionService conversionService;
	private MeasureController measureController;

	public NoteController(
		NoteService service,
		MeasureController measureController,
		ConversionService conversionService
	) {
		super(service);

		this.measureController = measureController;
		this.conversionService = conversionService;
	}

	@Override
	public SimpleNoteResponse simpleResponse(Note entity) {
		SimpleNoteResponse response = new SimpleNoteResponse ();

		updateSimpleNoteResponse (response, entity, entity.getId());

		return response;
	}

	@Override
	public DetailNoteResponse detailResponse(Note entity) {
		DetailNoteResponse response = new DetailNoteResponse ();

		updateSimpleNoteResponse (response, entity, entity.getId());

		return response;
	}

	private void updateSimpleNoteResponse (SimpleNoteResponse response, Note entity, NoteKey key) {
		response.setId(new AccessNote(key.getSheetId(), key.getTrackId(), key.getSectionId(), key.getMeasureId(), key.getNoteId()));
		response.setNote(entity.getNote());
		response.setValue(entity.getValue());
	}

	@Override
	public Note createDomain(CreateNote request) {
		Note note = updateDomain (request);
		AccessMeasure key = conversionService.convert(request.getMeasureId(), AccessMeasure.class);

		note.getId().setSheetId(key.getSheetId());
		note.getId().setTrackId(key.getTrackId());
		note.getId().setSectionId(key.getSectionId());
		note.getId().setMeasureId(key.getMeasureId());

		return note;
	}

	@Override
	public Note updateDomain(UpdateNote request) {
		Note note = new Note ();

		note.setNote(request.getNote());
		note.setValue(request.getValue());
		note.setId(new NoteKey());

		return note;
	}

	@GetMapping("/measure/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleNoteResponse> findAllByMeasure(@PathVariable("id") final AccessMeasure id) throws Exception {
		return service.findByMeasureId(
			measureController.createDomainKey(id)
		).stream().map(e -> simpleResponse(e)).collect(Collectors.toList());
	}

	@Override
	public NoteKey createDomainKey(AccessNote data) {
		NoteKey key = new NoteKey ();

		key.setSheetId(data.getSheetId());
		key.setTrackId(data.getTrackId());

		key.setSectionId(data.getSectionId());
		key.setMeasureId(data.getMeasureId());

		key.setNoteId(data.getNoteId());

		return key;
	}

}



