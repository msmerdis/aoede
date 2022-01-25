package com.aoede;

import com.aoede.plugins.validation.source.LineValidator;

public class LineValidatorTestCaseSetup {

	protected LineValidator uut (String ext) {
		return new LineValidator(ext);
	}
}



