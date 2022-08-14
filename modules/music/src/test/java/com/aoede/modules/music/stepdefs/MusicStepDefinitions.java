package com.aoede.modules.music.stepdefs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.aoede.commons.cucumber.BaseStepDefinition;
import com.aoede.commons.cucumber.ResponseResults;
import com.aoede.commons.cucumber.service.AbstractTestService;
import com.aoede.commons.cucumber.service.AbstractTestServiceDiscoveryService;
import com.aoede.commons.cucumber.service.DataTableService;
import com.aoede.commons.cucumber.service.HttpService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class MusicStepDefinitions extends BaseStepDefinition {
	private Map<String, Integer> scales = Map.of(
		"C", 60,
		"D", 62,
		"E", 64,
		"F", 65,
		"G", 67,
		"A", 69,
		"B", 71
	);

	public MusicStepDefinitions (
		ServerProperties serverProperties,
		AbstractTestServiceDiscoveryService services,
		TestCaseIdTrackerService testCaseIdTrackerService,
		JsonService jsonService,
		DataTableService dataTableService,
		HttpService httpService
	) {
		super (
			serverProperties,
			services,
			testCaseIdTrackerService,
			jsonService,
			dataTableService,
			httpService
		);
	}

	@Given("prepare {string} scale as {string}")
	public void prepareScale(String scale, String name) {
		prepareScale(scale, name, false, false);
	}

	@Given("prepare {string} scale as {string} with tags")
	public void prepareScaleTags(String scale, String name) {
		prepareScale(scale, name, true, false);
	}

	@Given("prepare {string} scale as {string} with flags")
	public void prepareScaleFlags(String scale, String name) {
		prepareScale(scale, name, false, true);
	}

	@Given("prepare {string} scale as {string} with tags and flags")
	public void prepareScaleAll(String scale, String name) {
		prepareScale(scale, name, true, true);
	}

	private void prepareScale(String scale, String name, boolean tags, boolean flags) {
		logger.info("generate a music sheet for " + scale + " scale");
		JsonObject sheet = new JsonObject();
		JsonArray tracks = new JsonArray();

		assertTrue("unknown scale " + scale, scales.containsKey(scale));

		sheet.add("name", new JsonPrimitive(name));
		sheet.add("tracks", tracks);

		tracks.add(prepareScaleTrack(scales.get(scale), tags, flags));

		jsonService.put(name, sheet);
	}

	private JsonObject prepareScaleTrack (int pitch, boolean tags, boolean flags) {
		JsonObject track = new JsonObject();
		JsonArray measures = new JsonArray();

		track.add("clef", new JsonPrimitive("Treble"));
		track.add("tempo", new JsonPrimitive(120));
		track.add("keySignature", new JsonPrimitive(0));
		track.add("timeSignature", prepareFraction(4,4));
		track.add("measures", measures);

		attachTagsAndFlags(track, tags, flags, "track");

		measures.add(prepareScaleMeasure(1, pitch +  0, pitch + 2, pitch +  4, pitch +  5, tags, flags));
		measures.add(prepareScaleMeasure(2, pitch +  7, pitch + 9, pitch + 11, pitch + 12, tags, flags));
		measures.add(prepareScaleMeasure(3, pitch + 11, pitch + 9, pitch +  7, pitch +  4, tags, flags));
		measures.add(prepareScaleMeasure(4, pitch +  4, pitch + 2, pitch +  0,         -1, tags, flags));

		return track;
	}

	private JsonObject prepareScaleMeasure (int order, int n1, int n2, int n3, int n4, boolean tags, boolean flags) {
		JsonObject measure = new JsonObject();
		JsonArray notes = new JsonArray();

		measure.add("notes", notes);

		attachTagsAndFlags(measure, tags, flags, "measure");

		notes.add(prepareScaleNote(1, n1, tags, flags));
		notes.add(prepareScaleNote(2, n2, tags, flags));
		notes.add(prepareScaleNote(3, n3, tags, flags));
		notes.add(prepareScaleNote(4, n4, tags, flags));

		return measure;
	}

	private JsonObject prepareScaleNote (int order, int pitch, boolean tags, boolean flags) {
		JsonObject note = new JsonObject();

		note.add("order", new JsonPrimitive(order));
		note.add("pitch", new JsonPrimitive(pitch));
		note.add("value", prepareFraction(1,4));

		attachTagsAndFlags(note, tags, flags, "note");

		return note;
	}

	private JsonObject prepareFraction (int num, int den) {
		JsonObject fraction = new JsonObject();

		fraction.add(  "numerator", new JsonPrimitive(num));
		fraction.add("denominator", new JsonPrimitive(den));

		return fraction;
	}

	private void attachTagsAndFlags (JsonObject object, boolean tags, boolean flags, String type) {
		if (tags) {
			JsonArray arr = new JsonArray();
			object.add("tags", arr);
			arr.add("tag");
		}

		if (flags) {
			JsonObject obj = new JsonObject();
			object.add("flags", obj);
			obj.add("flag", new JsonPrimitive("1"));
			obj.add("type", new JsonPrimitive(type));
		}
	}

	@When("request a key signature with major {string}")
	public void getMajor(String id) {
		getKeySignature("major", id);
	}

	@When("request a key signature with minor {string}")
	public void getMinor(String id) {
		getKeySignature("minor", id);
	}

	private void getKeySignature(String type, String id) {
		logger.info("retrieve key signature with " + type + " " + id);
		AbstractTestService service = services.getLatestService("keySignature");

		assertNotNull("keySignature service was not found", service);

		ResponseResults results = executeGet (service.getPath() + "/" + id + "/" + type);

		// update test controller with data
		service.accessResults(results);
	}

}



