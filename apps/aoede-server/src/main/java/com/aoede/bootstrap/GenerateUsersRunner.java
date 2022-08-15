package com.aoede.bootstrap;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.modules.music.domain.Clef;
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.domain.TimeSignature;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.service.ClefService;
import com.aoede.modules.music.service.SheetService;
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
	private SheetService sheetService;

	private int startingNote = 0;
	private String [] notes  = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	private short  [] keys   = { 0 ,  7  ,  2 ,  -3 ,  4 ,  -1,  6 ,   1 ,  -4 ,  3 ,  -2 ,  5 };

	public GenerateUsersRunner (
		RoleService roleService,
		UserService userService,
		ClefService clefService,
		SheetService sheetService
	) {
		this.roleService = roleService;
		this.userService = userService;
		this.clefService = clefService;
		this.sheetService = sheetService;
	}

	@Override
	public void run(String... args) throws Exception {
		generateRoles();
		generateUsers();

		try {
			generateSheets(userService.login("boss", "test").getId(), 12);
			generateSheets(userService.login("pleb", "test").getId(), 4);
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
		userService.create(makeUser("null", "test"));
	}

	private Sheet makeScaleSheet (int startingNote, short key, String name) throws GenericException {
		Sheet sheet = new Sheet();

		sheet.setName(name);
		sheet.setTracks(new LinkedList<Track>());

		for (Clef clef : clefService.findAll()) {
			if (clef.getType() == 'F') {
				sheet.getTracks().add(makeScaleTrack(48 + startingNote, key, clef.getId()));
			} else {
				sheet.getTracks().add(makeScaleTrack(60 + startingNote, key, clef.getId()));
			}
		}

		return sheet;
	}

	private Sheet makeCromaticSheet (short key, String name) throws GenericException {
		Sheet sheet = new Sheet();

		sheet.setName(name);
		sheet.setTracks(new LinkedList<Track>());

		for (Clef clef : clefService.findAll()) {
			if (clef.getType() == 'F') {
				sheet.getTracks().add(makeCromaticTrack(48, key, clef.getId()));
			} else {
				sheet.getTracks().add(makeCromaticTrack(60, key, clef.getId()));
			}
		}

		return sheet;
	}

	private Track makeScaleTrack (int startingNote, short key, String clef) {
		Track track = new Track();

		track.setClef(clef);
		track.setName(clef);
		track.setKeySignature(key);
		track.setTempo((short)120);
		track.setTimeSignature(new TimeSignature(4, 4));
		track.setMeasures(new LinkedList<Measure>());

		track.getMeasures().add(makeMeasure((short)1, startingNote +  0, startingNote + 2, startingNote +  4, startingNote +  5));
		track.getMeasures().add(makeMeasure((short)2, startingNote +  7, startingNote + 9, startingNote + 11, startingNote + 12));
		track.getMeasures().add(makeMeasure((short)3, startingNote + 11, startingNote + 9, startingNote +  7, startingNote +  5));
		track.getMeasures().add(makeMeasure((short)4, startingNote +  4, startingNote + 2, startingNote +  0, -1));

		return track;
	}

	private Track makeCromaticTrack (int startingNote, short key, String clef) {
		Track track = new Track();

		track.setClef(clef);
		track.setName(clef);
		track.setKeySignature(key);
		track.setTempo((short)120);
		track.setTimeSignature(new TimeSignature(4, 4));
		track.setMeasures(new LinkedList<Measure>());

		track.getMeasures().add(makeMeasure((short)1, startingNote +  0, startingNote +  1, startingNote +  2, startingNote +  3));
		track.getMeasures().add(makeMeasure((short)2, startingNote +  4, startingNote +  5, startingNote +  6, startingNote +  7));
		track.getMeasures().add(makeMeasure((short)3, startingNote +  8, startingNote +  9, startingNote + 10, startingNote + 11));
		track.getMeasures().add(makeMeasure((short)4, startingNote + 11, startingNote + 10, startingNote +  9, startingNote +  8));
		track.getMeasures().add(makeMeasure((short)5, startingNote +  7, startingNote +  6, startingNote +  5, startingNote +  4));
		track.getMeasures().add(makeMeasure((short)6, startingNote +  3, startingNote +  2, startingNote +  1, startingNote +  0));

		return track;
	}

	private Measure makeMeasure (short order, int n1, int n2, int n3, int n4) {
		Measure measure = new Measure();

		measure.setNotes(new LinkedList<Note>());

		measure.getNotes().add(makeNote((short)1, n1));
		measure.getNotes().add(makeNote((short)2, n2));
		measure.getNotes().add(makeNote((short)3, n3));
		measure.getNotes().add(makeNote((short)4, n4));

		return measure;
	}

	private Note makeNote (short order, int pitch) {
		Note note = new Note();

		note.setPitch(pitch);
		note.setValue(4);

		return note;
	}

	private void generateSheets (long userId, long count) throws GenericException, Exception {
		// "authenticate" the user
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(userId, null, List.of())
		);

		generateValueSheet();

		for (int i = 0; i < count; i += 1, this.startingNote = (this.startingNote + 1) % 12) {
			generateScaleSheet(userId, this.startingNote);
		}
		for (int j = 0; j < 12; j += 1) {
			generateCromaticSheet (userId, j);
		}

		// clear authentication
		SecurityContextHolder.clearContext();
	}

	private void generateValueSheet () throws GenericException, Exception {
		Track track = new Track();

		track.setClef("Treble");
		track.setKeySignature((short)0);
		track.setTempo((short)120);
		track.setTimeSignature(new TimeSignature(4, 4));
		track.setMeasures(new LinkedList<Measure>());

		for (int i = 1; i < 100; i *= 2) {
			Measure measure = new Measure();

			measure.setNotes(new LinkedList<Note>());

			for (int j = 0; j < i; j += 1) {
				Note note = new Note ();

				note.setPitch(67);
				note.setValue(i);

				measure.getNotes().add(note);
			}

			track.getMeasures().add(measure);
		}

		Sheet sheet = new Sheet();

		sheet.setName("All values track");
		sheet.setTracks(List.of(track));

		this.sheetService.create(sheet);
	}

	private void generateScaleSheet (long userId, int root) throws GenericException, Exception {
		String note = this.notes[root % 12];
		short key = this.keys[root % 12];

		this.sheetService.create(makeScaleSheet(root, key, note + " scale"));
	}

	private void generateCromaticSheet (long userId, int root) throws GenericException, Exception {
		String note = this.notes[root % 12];
		short key = this.keys[root % 12];

		this.sheetService.create(makeCromaticSheet(key, note + " chromatic scale"));
	}

}



