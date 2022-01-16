package com.aoede.commons.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.aoede.AbstractTestServiceImplTestCaseSetup;
import com.aoede.commons.base.ResponseResults;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TestAbstractTestServiceImpl extends AbstractTestServiceImplTestCaseSetup {

	@Test
	public void verifySetup() throws Exception {
		AbstractTestServiceImpl uut = uut ();

		// setup data
		setField (uut, "success", true);
		setField (uut, "latestKey", null);
		setField (uut, "latestObj", null);
		setField (uut, "latestArr", null);
		setField (uut, "latestResults", null);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertNull ("latestResults field was not setup correctly", getField(uut, "latestResults"));

		// execute function
		Method setup = AbstractTestServiceImpl.class.getDeclaredMethod("setup");
		setup.setAccessible(true);
		setup.invoke(uut);

		assertEquals ("success field was not setup correctly", false, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertNull ("latestResults field was not setup correctly", getField(uut, "latestResults"));
	}

	@Test
	public void verifySetupWithData() throws Exception {
		AbstractTestServiceImpl uut = uut ();

		// setup data
		setField (uut, "success", true);
		setField (uut, "latestKey", new JsonPrimitive("key"));
		setField (uut, "latestObj", new JsonObject());
		setField (uut, "latestArr", new JsonArray());
		setField (uut, "latestResults", new ResponseResults());

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNotNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNotNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertNotNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertNotNull ("latestResults field was not setup correctly", getField(uut, "latestResults"));

		// execute function
		Method setup = AbstractTestServiceImpl.class.getDeclaredMethod("setup");
		setup.setAccessible(true);
		setup.invoke(uut);

		assertEquals ("success field was not setup correctly", false, getField(uut, "success"));
		assertNotNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNotNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertNotNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertNotNull ("latestResults field was not setup correctly", getField(uut, "latestResults"));
	}

	// Search Results

	@Test
	public void verifySearchResultsSuccess() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "[]");

		// execute function
		uut.searchResults(results);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));

		assertNotNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifySearchResultsIncorrectStatus() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.CREATED, "[]");

		// execute function
		try {
			uut.searchResults(results);
			assertFalse ("searchResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	@Test
	public void verifySearchResultsIncorrectBody() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "{}");

		// execute function
		try {
			uut.searchResults(results);
			assertFalse ("searchResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	// Access Results

	@Test
	public void verifyAccessResultsSuccess() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "{}");

		// execute function
		uut.accessResults(results);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));

		assertNotNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyAccessResultsIncorrectStatus() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.CREATED, "{}");

		// execute function
		uut.accessResults(results);

		assertEquals ("success field was not setup correctly", false, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));

		assertNotNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyAccessResultsIncorrectBody() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "[]");

		// execute function
		try {
			uut.accessResults(results);
			assertFalse ("accessResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	// Find All Results

	@Test
	public void verifyFindAllResultsSuccess() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "[]");

		// execute function
		uut.findAllResults(results);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));

		assertNotNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyFindAllResultsIncorrectStatus() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.BAD_REQUEST, "[]");

		// execute function
		try {
			uut.findAllResults(results);
			assertFalse ("findAllResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	@Test
	public void verifyFindAllResultsIncorrectBody() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "{}");

		// execute function
		try {
			uut.findAllResults(results);
			assertFalse ("findAllResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	// Create Results

	@Test
	public void verifyCreateResultsSuccess() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.CREATED, "{}");

		// execute function
		uut.createResults(results);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));

		assertNotNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyCreateResultsWithKey() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.CREATED, "{\"the key\": 1}");

		// execute function
		uut.createResults(results);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));

		assertEquals ("latestKey field was not setup correctly", 1, ((JsonPrimitive)getField(uut, "latestKey")).getAsInt());
		assertNotNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyCreateResultsIncorrectStatus() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "{}");

		// execute function
		uut.createResults(results);

		assertEquals ("success field was not setup correctly", false, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));

		assertNotNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));
		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyCreateResultsIncorrectBody() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.CREATED, "[]");

		// execute function
		try {
			uut.createResults(results);
			assertFalse ("createResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	// Update Results

	@Test
	public void verifyUpdateResultsSuccess() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.NO_CONTENT, "");

		// execute function
		uut.updateResults(results);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));

		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyUpdateResultsIncorrectStatus() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "");

		// execute function
		try {
			uut.updateResults(results);
			assertFalse ("updateResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	@Test
	public void verifyUpdateResultsIncorrectData() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.NO_CONTENT, "{}");

		// execute function
		try {
			uut.updateResults(results);
			assertFalse ("updateResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	// Delete Results

	@Test
	public void verifyDeleteResultsSuccess() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.NO_CONTENT, "");

		// execute function
		uut.deleteResults(results);

		assertEquals ("success field was not setup correctly", true, getField(uut, "success"));
		assertNull ("latestKey field was not setup correctly", getField(uut, "latestKey"));
		assertNull ("latestArr field was not setup correctly", getField(uut, "latestArr"));
		assertNull ("latestObj field was not setup correctly", getField(uut, "latestObj"));

		assertEquals ("latestResults field was not setup correctly", results, getField(uut, "latestResults"));
	}

	@Test
	public void verifyDeleteResultsIncorrectStatus() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.OK, "");

		// execute function
		try {
			uut.deleteResults(results);
			assertFalse ("deleteResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	@Test
	public void verifyDeleteResultsIncorrectBody() throws Exception {
		AbstractTestServiceImpl uut = uut ();
		ResponseResults results = setupVerifyResults (uut, HttpStatus.NO_CONTENT, "{}");

		// execute function
		try {
			uut.deleteResults(results);
			assertFalse ("deleteResults did not perform the assertion", true);
		} catch (AssertionError e) {
			assertTrue ("invocation must fail due to an assertion", true);
		}
	}

	// help functions

	private ResponseResults setupVerifyResults(AbstractTestServiceImpl uut, HttpStatus status, String body) throws Exception {
		ResponseResults results = new ResponseResults();

		results.status  = status;
		results.headers = new HttpHeaders ();
		results.body    = body;

		// setup data
		setField (uut, "success", false);
		setField (uut, "latestKey", null);
		setField (uut, "latestObj", null);
		setField (uut, "latestArr", null);
		setField (uut, "latestResults", null);

		assertEquals ("success field was not setup correctly", false, getField(uut, "success"));
		assertEquals ("latestKey field was not setup correctly", null, getField(uut, "latestKey"));
		assertEquals ("latestObj field was not setup correctly", null, getField(uut, "latestObj"));
		assertEquals ("latestArr field was not setup correctly", null, getField(uut, "latestArr"));
		assertEquals ("latestResults field was not setup correctly", null, getField(uut, "latestResults"));

		return results;
	}

}



