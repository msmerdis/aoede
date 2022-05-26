package com.aoede.modules.music.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.service.KeySignatureService;

public class KeySignatureValidator extends BaseComponent implements ConstraintValidator<KeySignatureConstraint, Short> {

	private KeySignatureService keySignatureService;

	public KeySignatureValidator (KeySignatureService keySignatureService) {
		this.keySignatureService = keySignatureService;
	}

	@Override
	public boolean isValid(Short value, ConstraintValidatorContext context) {
		try {
			return this.keySignatureService.exists(value);
		} catch (GenericException e) {
			return false;
		}
	}

}



