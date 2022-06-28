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
import com.aoede.commons.cucumber.service.HeadersService;
import com.aoede.commons.cucumber.service.JsonService;
import com.aoede.commons.cucumber.service.TestCaseIdTrackerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class MusicStepDefinitions extends BaseStepDefinition {
	private Map<String, Integer> scales = Map.of(
		"C", 57,
		"D", 59,
		"E", 61,
		"F", 62,
		"G", 64,
		"A", 66,
		"B", 68
	);

	public MusicStepDefinitions (
		ServerProperties serverProperties,
		AbstractTestServiceDiscoveryService services,
		TestCaseIdTrackerService testCaseIdTrackerService,
		JsonService jsonService,
		DataTableService dataTableService,
		HeadersService headersService
	) {
		super (
			serverProperties,
			services,
			testCaseIdTrackerService,
			jsonService,
			dataTableService,
			headersService
		);
	}

	@Given("prepare {string} scale as {string}")
	public void prepareScale(String scale, String name) {
		logger.info("generate a music sheet for " + scale + " scale");
		JsonObject sheet = new JsonObject();
		JsonArray tracks = new JsonArray();

		assertTrue("unknown scale " + scale, scales.containsKey(scale));

		sheet.add("name", new JsonPrimitive(name));
		sheet.add("tracks", tracks);

		tracks.add(prepareScaleTrack(scales.get(scale)));

		jsonService.put(name, sheet);
	}

	private JsonObject prepareScaleTrack (int pitch) {
		JsonObject track = new JsonObject();
		JsonArray measures = new JsonArray();

		track.add("clef", new JsonPrimitive("Treble"));
		track.add("tempo", new JsonPrimitive(120));
		track.add("keySignature", new JsonPrimitive(0));
		track.add("timeSignature", prepareFraction(4,4));
		track.add("measures", measures);

		measures.add(prepareScaleMeasure(1, pitch +  0, pitch + 2, pitch +  4, pitch +  5));
		measures.add(prepareScaleMeasure(2, pitch +  7, pitch + 9, pitch + 11, pitch + 12));
		measures.add(prepareScaleMeasure(3, pitch + 11, pitch + 9, pitch +  7, pitch +  4));
		measures.add(prepareScaleMeasure(4, pitch +  4, pitch + 2, pitch +  0, -1));

		return track;
	}

	private JsonObject prepareScaleMeasure (int order, int n1, int n2, int n3, int n4) {
		JsonObject measure = new JsonObject();
		JsonArray notes = new JsonArray();

		measure.add("notes", notes);

		notes.add(prepareScaleNote(1, n1));
		notes.add(prepareScaleNote(2, n2));
		notes.add(prepareScaleNote(3, n3));
		notes.add(prepareScaleNote(4, n4));

		return measure;
	}

	private JsonObject prepareScaleNote (int order, int pitch) {
		JsonObject note = new JsonObject();

		note.add("order", new JsonPrimitive(order));
		note.add("pitch", new JsonPrimitive(pitch));
		note.add("value", prepareFraction(1,4));

		return note;
	}

	private JsonObject prepareFraction (int num, int den) {
		JsonObject fraction = new JsonObject();

		fraction.add(  "numerator", new JsonPrimitive(num));
		fraction.add("denominator", new JsonPrimitive(den));

		return fraction;
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



