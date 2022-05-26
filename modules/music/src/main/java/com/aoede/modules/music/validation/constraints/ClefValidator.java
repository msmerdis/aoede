package com.aoede.modules.music.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.service.ClefService;

@Component
public class ClefValidator extends BaseComponent implements ConstraintValidator<ClefConstraint, String> {

	private ClefService clefService;

	public ClefValidator (ClefService clefService) {
		this.clefService = clefService;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			return this.clefService.exists(value);
		} catch (GenericException e) {
			return false;
		}
	}

}



