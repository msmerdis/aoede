package com.aoede.modules.music.validation.constraints;

import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.modules.music.domain.TimeSignature;

public class BeatsValidator extends BaseComponent implements ConstraintValidator<BeatsConstraint, TimeSignature> {

	@Override
	public boolean isValid(TimeSignature value, ConstraintValidatorContext context) {
		List<Integer> beats = value.getBeats();

		if (beats == null)
			return true;

		if (beats.size() == 0)
			return false;

		return check(beats.iterator(), value.getNumerator());
	}

	private boolean check (Iterator<Integer> beats, int numerator) {
		int prev = -1;
		int curr;

		// check that beats
		// 1. are in order
		// 2. have no duplicates
		// 3. are above or equal to 0
		// 4. do not exceed numerator
		while (beats.hasNext()) {
			curr = beats.next();
			if (curr <= prev)
				return false;
			prev = curr;
		}

		return prev < numerator;
	}
}



