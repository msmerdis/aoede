package com.aoede.commons.base.controller;

import java.util.LinkedList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.ValidationFailure;

/**
 * Define a controller in the test scope to generate the errors
 */
@RestController
@RequestMapping ("/test/error/validation")
public class ExceptionValidationTestController {

	// force validation messages

	@GetMapping("/username")
	@ResponseStatus(HttpStatus.OK)
	public void username() throws GenericException {
		BadRequestException e = new BadRequestException("username");

		e.validations = new LinkedList<ValidationFailure>();
		e.validations.add(new ValidationFailure("user", "username", "", "cannot be empty"));

		throw e;
	}

	@GetMapping("/password")
	@ResponseStatus(HttpStatus.OK)
	public void password() throws GenericException {
		BadRequestException e = new BadRequestException("password");

		e.validations = new LinkedList<ValidationFailure>();
		e.validations.add(new ValidationFailure("user", "password", "abcde", "try harder"));

		throw e;
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public void all() throws GenericException {
		BadRequestException e = new BadRequestException("all");

		e.validations = new LinkedList<ValidationFailure>();
		e.validations.add(new ValidationFailure("user", "username", "", "cannot be empty"));
		e.validations.add(new ValidationFailure("user", "password", "abcde", "try harder"));

		throw e;
	}
}



