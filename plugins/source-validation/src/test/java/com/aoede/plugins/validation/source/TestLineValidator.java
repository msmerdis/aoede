package com.aoede.plugins.validation.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.aoede.LineValidatorTestCaseSetup;

@RunWith(JUnit4.class)
public class TestLineValidator extends LineValidatorTestCaseSetup {

	private void validateEmptyLine (String ext) {
		LineValidator uut = uut (ext);

		uut.accept("");

		assertFalse(uut.hasErrors());
		assertEquals(1, uut.getConsecutiveEmptyLines());

		uut.accept("");

		assertFalse(uut.hasErrors());
		assertEquals(2, uut.getConsecutiveEmptyLines());

		uut.accept("");

		assertFalse(uut.hasErrors());
		assertEquals(3, uut.getConsecutiveEmptyLines());
	}

	@Test public void validateEmptyLine_java () { validateEmptyLine ("java"); }
	@Test public void validateEmptyLine_xml  () { validateEmptyLine ( "xml"); }
	@Test public void validateEmptyLine_yml  () { validateEmptyLine ( "yml"); }
	@Test public void validateEmptyLine_yaml () { validateEmptyLine ("yaml"); }
	@Test public void validateEmptyLine_txt  () { validateEmptyLine ( "txt"); }
	@Test public void validateEmptyLine_none () { validateEmptyLine (    ""); }

	private void validateNonEmptyLine (String ext) {
		LineValidator uut = uut (ext);

		uut.accept("non");

		assertFalse(uut.hasErrors());
		assertEquals(0, uut.getConsecutiveEmptyLines());

		uut.accept("\tempty");

		assertFalse(uut.hasErrors());
		assertEquals(0, uut.getConsecutiveEmptyLines());

		uut.accept("\t\tline");

		assertFalse(uut.hasErrors());
		assertEquals(0, uut.getConsecutiveEmptyLines());
	}

	@Test public void validateNonEmptyLine_java () { validateNonEmptyLine ("java"); }
	@Test public void validateNonEmptyLine_xml  () { validateNonEmptyLine ( "xml"); }
	@Test public void validateNonEmptyLine_yml  () { validateNonEmptyLine ( "yml"); }
	@Test public void validateNonEmptyLine_yaml () { validateNonEmptyLine ("yaml"); }
	@Test public void validateNonEmptyLine_txt  () { validateNonEmptyLine ( "txt"); }
	@Test public void validateNonEmptyLine_none () { validateNonEmptyLine (    ""); }

	private void validateTrailingSpace (String ext, boolean trailingSpaceCheck) {
		LineValidator uut = uut (ext);

		uut.accept("trailing ");

		if (trailingSpaceCheck) {
			assertTrue(uut.hasErrors());
			assertEquals(1, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}

		uut.accept("\tspace\t");

		if (trailingSpaceCheck) {
			assertEquals(2, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}

		uut.accept("\t\tcheck \t");

		if (trailingSpaceCheck) {
			assertEquals(3, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}

		uut.accept("\t\t\t");

		if (trailingSpaceCheck) {
			assertEquals(4, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}
	}

	// file types that do enforce trailing space checks
	@Test public void validateTrailingSpace_java () { validateTrailingSpace ("java",  true); }
	@Test public void validateTrailingSpace_xml  () { validateTrailingSpace ( "xml",  true); }
	@Test public void validateTrailingSpace_yml  () { validateTrailingSpace ( "yml",  true); }
	@Test public void validateTrailingSpace_yaml () { validateTrailingSpace ("yaml",  true); }

	// file types that do not enforce trailing space checks
	@Test public void validateTrailingSpace_txt  () { validateTrailingSpace ( "txt", false); }
	@Test public void validateTrailingSpace_none () { validateTrailingSpace (    "", false); }

	private void validateLeadingSpace (String ext, boolean leadingSpaceCheck) {
		LineValidator uut = uut (ext);

		uut.accept(" leading");

		if (leadingSpaceCheck) {
			assertTrue(uut.hasErrors());
			assertEquals(1, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}

		uut.accept("\t space");

		if (leadingSpaceCheck) {
			assertEquals(2, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}

		uut.accept("\t\t check");

		if (leadingSpaceCheck) {
			assertEquals(3, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}
	}

	// file types that do enforce leading space checks
	@Test public void validateLeadingSpace_java () { validateLeadingSpace ("java",  true); }
	@Test public void validateLeadingSpace_xml  () { validateLeadingSpace ( "xml",  true); }

	// file types that do not enforce leading space checks
	@Test public void validateLeadingSpace_yml  () { validateLeadingSpace ( "yml", false); }
	@Test public void validateLeadingSpace_yaml () { validateLeadingSpace ("yaml", false); }
	@Test public void validateLeadingSpace_txt  () { validateLeadingSpace ( "txt", false); }
	@Test public void validateLeadingSpace_none () { validateLeadingSpace (    "", false); }

	private void validateMiddleTabs (String ext) {
		LineValidator uut = uut (ext);

		uut.accept("\tmiddle\ttab");

		assertTrue(uut.hasErrors());
		assertEquals(1, uut.getErrors().size());
	}

	@Test public void validateMiddleTabs_java () { validateMiddleTabs ("java"); }
	@Test public void validateMiddleTabs_xml  () { validateMiddleTabs ( "xml"); }
	@Test public void validateMiddleTabs_yml  () { validateMiddleTabs ( "yml"); }
	@Test public void validateMiddleTabs_yaml () { validateMiddleTabs ("yaml"); }
	@Test public void validateMiddleTabs_txt  () { validateMiddleTabs ( "txt"); }
	@Test public void validateMiddleTabs_none () { validateMiddleTabs (    ""); }

	private void validateWhiteSpaceLine (String ext, boolean checkLeading, boolean checkTrailing) {
		LineValidator uut = uut (ext);

		uut.accept("\t \t");

		if (checkLeading && checkTrailing) {
			assertTrue(uut.hasErrors());
			assertEquals(2, uut.getErrors().size());
		} else if (checkLeading || checkTrailing) {
			assertTrue(uut.hasErrors());
			assertEquals(1, uut.getErrors().size());
		} else {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		}
	}

	// file types that do enforce leading and trailing space checks
	@Test public void validateWhiteSpaceLine_java () { validateWhiteSpaceLine ("java",  true,  true); }
	@Test public void validateWhiteSpaceLine_xml  () { validateWhiteSpaceLine ( "xml",  true,  true); }

	// file types that do enforce trailing space checks
	@Test public void validateWhiteSpaceLine_yml  () { validateWhiteSpaceLine ( "yml", false,  true); }
	@Test public void validateWhiteSpaceLine_yaml () { validateWhiteSpaceLine ("yaml", false,  true); }

	// file types that do not enforce trailing or leading space checks
	@Test public void validateWhiteSpaceLine_txt  () { validateWhiteSpaceLine ( "txt", false, false); }
	@Test public void validateWhiteSpaceLine_none () { validateWhiteSpaceLine (    "", false, false); }

	private void validateJavaDocLine (String ext, boolean pass) {
		LineValidator uut = uut (ext);

		uut.accept("\t\t/************");
		uut.accept("\t\t *");
		uut.accept("\t\t * java doc");
		uut.accept("\t\t *");
		uut.accept("\t\t */");

		if (pass) {
			assertFalse(uut.hasErrors());
			assertEquals(0, uut.getErrors().size());
		} else {
			assertTrue(uut.hasErrors());
			assertEquals(4, uut.getErrors().size());
		}
	}

	// file types that do enforce leading and trailing space checks
	@Test public void validateJavaDocLine_xml  () { validateJavaDocLine ( "xml", false); }

	// file types that do not enforce leading space checks
	@Test public void validateJavaDocLine_yml  () { validateJavaDocLine ( "yml",  true); }
	@Test public void validateJavaDocLine_yaml () { validateJavaDocLine ("yaml",  true); }
	@Test public void validateJavaDocLine_txt  () { validateJavaDocLine ( "txt",  true); }
	@Test public void validateJavaDocLine_none () { validateJavaDocLine (    "",  true); }

	// java file type, passes this check
	@Test public void validateJavaDocLine_java () { validateJavaDocLine ("java",  true); }

}



