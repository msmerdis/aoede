package com.aoede.modules.music.service;

import java.util.LinkedList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.transfer.combination.FullMeasure;
import com.aoede.modules.music.transfer.combination.FullNote;
import com.aoede.modules.music.transfer.combination.FullSection;
import com.aoede.modules.music.transfer.combination.FullSheet;
import com.aoede.modules.music.transfer.combination.FullTrack;
import com.aoede.modules.music.transfer.measure.AccessMeasure;
import com.aoede.modules.music.transfer.note.AccessNote;
import com.aoede.modules.music.transfer.section.AccessSection;
import com.aoede.modules.music.transfer.track.AccessTrack;

@Service
public class CombinationServiceImpl implements CombinationService {

	private SheetService sheetService;
	private TrackService trackService;
	private SectionService sectionService;
	private MeasureService measureService;
	private NoteService noteService;

	public CombinationServiceImpl(
		SheetService sheetService,
		TrackService trackService,
		SectionService sectionService,
		MeasureService measureService,
		NoteService noteService
	) {
		this.sheetService = sheetService;
		this.trackService = trackService;
		this.sectionService = sectionService;
		this.measureService = measureService;
		this.noteService = noteService;
	}

	@Override
	public FullSheet getSheet(Long id) throws GenericException {
		FullSheet fullSheet = new FullSheet();
		Sheet sheet = sheetService.find(id);

		fullSheet.setId(sheet.getId());
		fullSheet.setName(sheet.getName());
		fullSheet.setTracks(
			trackService.findBySheetId(id).stream()
				.map((track) -> updateTrack(track))
				.collect(Collectors.toList())
		);

		return fullSheet;
	}

	private FullTrack updateTrack(Track track) {
		FullTrack fullTrack = new FullTrack();

		fullTrack.setId(new AccessTrack(track.getId().getSheetId(), track.getId().getTrackId()));
		fullTrack.setClef(track.getClef());
		fullTrack.setSections(
			sectionService.findByTrackId(track.getId()).stream()
				.map((section) -> updateSection(section))
				.collect(Collectors.toList())
		);

		return fullTrack;
	}

	private FullSection updateSection(Section section) {
		FullSection fullSection = new FullSection();

		fullSection.setId(new AccessSection(section.getId().getSheetId(), section.getId().getTrackId(), section.getId().getSectionId()));
		fullSection.setKeySignature(section.getKeySignature().getId());
		fullSection.setTempo(section.getTempo());
		fullSection.setTimeSignature(section.getTimeSignature());
		fullSection.setMeasures(new LinkedList<FullMeasure>());
		fullSection.setMeasures(
			measureService.findBySectionId(section.getId()).stream()
				.map((measure) -> updateMeasure(measure))
				.collect(Collectors.toList())
		);
		return fullSection;
	}

	private FullMeasure updateMeasure(Measure measure) {
		FullMeasure fullMeasure = new FullMeasure();

		fullMeasure.setId(new AccessMeasure(
			measure.getId().getSheetId(),
			measure.getId().getTrackId(),
			measure.getId().getSectionId(),
			measure.getId().getMeasureId()
		));
		fullMeasure.setNotes(
			noteService.findByMeasureId(measure.getId()).stream()
				.map((note) -> updateNote(note))
				.collect(Collectors.toList())
		);
		return fullMeasure;
	}

	private FullNote updateNote(Note note) {
		FullNote fullNote = new FullNote();

		fullNote.setId(new AccessNote(
			note.getId().getSheetId(),
			note.getId().getTrackId(),
			note.getId().getSectionId(),
			note.getId().getMeasureId(),
			note.getId().getNoteId()
		));
		fullNote.setNote(note.getNote());
		fullNote.setValue(note.getValue());

		return fullNote;
	}
}



