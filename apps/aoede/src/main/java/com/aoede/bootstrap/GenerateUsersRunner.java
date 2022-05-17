package com.aoede.bootstrap;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.MeasureKey;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.NoteKey;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.domain.SectionKey;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.domain.TrackKey;
import com.aoede.modules.music.service.ClefService;
import com.aoede.modules.music.service.KeySignatureService;
import com.aoede.modules.music.service.MeasureService;
import com.aoede.modules.music.service.NoteService;
import com.aoede.modules.music.service.SectionService;
import com.aoede.modules.music.service.SheetService;
import com.aoede.modules.music.service.TrackService;
import com.aoede.modules.music.transfer.Fraction;
import com.aoede.modules.user.domain.Role;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.service.RoleService;
import com.aoede.modules.user.service.UserService;
import com.aoede.modules.user.transfer.user.UserStatus;

@Component
public class GenerateUsersRunner extends BaseComponent implements CommandLineRunner {
	private RoleService roleService;
	private UserService userService;
	private ClefService clefService;
	private KeySignatureService keySignatureService;
	private SheetService sheetService;
	private TrackService trackService;
	private SectionService sectionService;
	private MeasureService measureService;
	private NoteService noteService;

	private int startingNote = 60;
	private String[] notes   = {"F", "G", "A", "B", "C", "D", "E"};

	public GenerateUsersRunner (
		RoleService roleService,
		UserService userService,
		ClefService clefService,
		KeySignatureService keySignatureService,
		SheetService sheetService,
		TrackService trackService,
		SectionService sectionService,
		MeasureService measureService,
		NoteService noteService
	) {
		this.roleService = roleService;
		this.userService = userService;
		this.clefService = clefService;
		this.keySignatureService = keySignatureService;
		this.sheetService = sheetService;
		this.trackService = trackService;
		this.sectionService = sectionService;
		this.measureService = measureService;
		this.noteService = noteService;
	}

	@Override
	public void run(String... args) throws Exception {
		generateRoles();
		generateUsers();

		try {
			generateSheets(userService.login("pleb", "test").getId(), 1);
			generateSheets(userService.login("boss", "test").getId(), 2);
		} catch (GenericExceptionContainer e) {
			logger.error(e.toString());
			throw e;
		}
	}

	private Role makeRole(String role, String desc) {
		Role rtn = new Role();

		rtn.setRole(role);
		rtn.setDesc(desc);

		return rtn;
	}

	private User makeUser(String username, String password) {
		User rtn = new User();

		rtn.setStatus(UserStatus.ACTIVE);
		rtn.setUsername(username);
		rtn.setPassword(password);

		return rtn;
	}

	private void generateRoles () throws Exception {
		roleService.create(makeRole("pleb", "just a pleb, nothing to see here"));
		roleService.create(makeRole("boss", "how we talking"));
	}

	private void generateUsers () throws Exception {
		userService.create(makeUser("pleb", "test"));
		userService.create(makeUser("boss", "test"));
	}

	private Sheet makeSheet (int startingNote, String name) {
		Sheet sheet = new Sheet();

		sheet.setName(name);

		return sheet;
	}

	private Track makeTrack (long id) throws GenericException {
		Track track = new Track();

		track.setId(new TrackKey());
		track.getId().setSheetId(id);
		track.setClef(clefService.find("Treble"));

		return track;
	}

	private Section makeSection (TrackKey id, String major) {
		Section section = new Section();

		section.setId(new SectionKey());
		section.getId().setSheetId(id.getSheetId());
		section.getId().setTrackId(id.getTrackId());
		section.setTempo((short)120);
		section.setTimeSignature(new Fraction(4, 4));
		section.setKeySignature(this.keySignatureService.findByMajor(major));

		return section;
	}

	private Measure makeMeasure (SectionKey id) {
		Measure measure = new Measure();

		measure.setId(new MeasureKey());
		measure.getId().setSheetId(id.getSheetId());
		measure.getId().setTrackId(id.getTrackId());

		measure.getId().setSectionId(id.getSectionId());

		return measure;
	}

	private Note makeNote (MeasureKey id, int theNote) {
		Note note = new Note();

		note.setId(new NoteKey());
		note.getId().setSheetId(id.getSheetId());
		note.getId().setTrackId(id.getTrackId());

		note.getId().setSectionId(id.getSectionId());
		note.getId().setMeasureId(id.getMeasureId());

		note.setNote(theNote);
		note.setValue(new Fraction(1, 4));

		return note;
	}

	private void generateSheets (long userId, long count) throws GenericException, Exception {
		// "authenticate" the user
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(userId, null, List.of())
		);

		for (int i = 0; i < count; i += 1, this.startingNote += 1) {
			generateSheet(userId);
		}

		// clear authentication
		SecurityContextHolder.clearContext();
	}

	private void generateSheet (long userId) throws GenericException, Exception {
		String note = this.notes[this.startingNote % 7];
		Sheet sheet = this.sheetService.create(makeSheet(this.startingNote, note + " scale"));

		generateTrack(sheet.getId(), note);
	}

	private void generateTrack (long sheetId, String note) throws GenericException, Exception {
		Track track = this.trackService.create(makeTrack(sheetId));

		generateSection (track.getId(), note);
	}

	private void generateSection (TrackKey trackId, String note) throws GenericException, Exception {
		Section section = this.sectionService.create(makeSection(trackId, note));

		// generate the notes for a major scale
		generateMeasure (section.getId(), this.startingNote +  0, this.startingNote +  2, this.startingNote +  4, this.startingNote +  5);
		generateMeasure (section.getId(), this.startingNote +  7, this.startingNote +  9, this.startingNote + 11, this.startingNote + 12);
		generateMeasure (section.getId(), this.startingNote + 11, this.startingNote +  9, this.startingNote +  7, this.startingNote +  5);
		generateMeasure (section.getId(), this.startingNote +  4, this.startingNote +  2, this.startingNote +  0, -1);
	}

	private void generateMeasure (SectionKey sectionId, int note1, int note2, int note3, int note4) throws GenericException, Exception {
		Measure measure = this.measureService.create(makeMeasure(sectionId));

		this.noteService.create(makeNote(measure.getId(), note1));
		this.noteService.create(makeNote(measure.getId(), note2));
		this.noteService.create(makeNote(measure.getId(), note3));
		this.noteService.create(makeNote(measure.getId(), note4));
	}

}



