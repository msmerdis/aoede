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
import com.aoede.modules.music.domain.Fraction;
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

	private int startingNote = 60;
	private String[] notes   = {"F", "G", "A", "B", "C", "D", "E"};

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
		userService.create(makeUser("null", "test"));
	}

	private Sheet makeSheet (int startingNote, short key, String name) throws GenericException {
		Sheet sheet = new Sheet();

		sheet.setName(name);
		sheet.setTracks(new LinkedList<Track>());

		for (Clef clef : clefService.findAll())
			sheet.getTracks().add(makeTrack(startingNote, (short)1, key, clef.getId()));

		return sheet;
	}

	private Track makeTrack (int startingNote, short order, short key, String clef) {
		Track track = new Track();

		track.setClef(clef);
		track.setName(clef);
		track.setKeySignature(key);
		track.setTempo((short)120);
		track.setTimeSignature(new TimeSignature(4, 4));
		track.setMeasures(new LinkedList<Measure>());

		track.getMeasures().add(makeMeasure((short)1, startingNote +  0, startingNote + 2, startingNote +  4, startingNote +  5));
		track.getMeasures().add(makeMeasure((short)2, startingNote +  7, startingNote + 9, startingNote + 11, startingNote + 12));
		track.getMeasures().add(makeMeasure((short)3, startingNote + 11, startingNote + 9, startingNote +  7, startingNote +  4));
		track.getMeasures().add(makeMeasure((short)4, startingNote +  4, startingNote + 2, startingNote +  0, -1));

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

		note.setOrder(order);
		note.setPitch(pitch);
		note.setValue(new Fraction(1, 4));

		return note;
	}

	private void generateSheets (long userId, long count) throws GenericException, Exception {
		// "authenticate" the user
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(userId, null, List.of())
		);

		for (int i = 0; i < count; i += 1, this.startingNote += 1) {
			generateSheet(userId, (short)-1, this.startingNote);
			generateSheet(userId, (short) 0, this.startingNote);
			generateSheet(userId, (short) 1, this.startingNote);
		}

		// clear authentication
		SecurityContextHolder.clearContext();
	}

	private void generateSheet (long userId, short key, int root) throws GenericException, Exception {
		String note = this.notes[root % 7];
		this.sheetService.create(makeSheet(root, key, note + " scale"));
	}

}



