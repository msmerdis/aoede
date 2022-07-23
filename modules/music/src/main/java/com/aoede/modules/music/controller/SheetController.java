package com.aoede.modules.music.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.ValidationFailure;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.service.SheetService;
import com.aoede.modules.music.transfer.GenerateSheet;

@RestController
@RequestMapping ("/api/sheet")
public class SheetController extends AbstractDomainController<Long, Sheet, SheetService> {

	private Validator validator;

	public SheetController(
		SheetService sheetService,
		Validator validator
	) {
		super(sheetService);

		this.validator = validator;
	}

	@PostMapping("/generate")
	@ResponseStatus(HttpStatus.CREATED)
	public Sheet generate(@Valid @RequestBody final GenerateSheet data) throws Exception {
		Sheet sheet = service.generate (data);
		Set<ConstraintViolation<Sheet>> validations = validator.validate(sheet);

		if (validations.size() > 0) {
			GenericException e =
				new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "validation failed");

			e.validations = validations.stream().map((v) -> new ValidationFailure(
				"internal",
				v.getPropertyPath().toString(),
				v.getInvalidValue(),
				v.getMessage()
			)).collect(Collectors.toList());

			throw e;
		}

		return this.create(sheet);
	}

}



