package com.aoede.commons.base.controller;

import java.util.LinkedList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericInfo;
import com.aoede.commons.base.exceptions.GenericInfoLevel;

/**
 * Define a controller in the test scope to generate the errors
 */
@RestController
@RequestMapping ("/test/error/info")
public class ExceptionInfoTestController {

	// force info messages

	@GetMapping("/fatal")
	@ResponseStatus(HttpStatus.OK)
	public void fatal() throws GenericException {
		BadRequestException e = new BadRequestException("fatal");

		e.info = new LinkedList<GenericInfo>();
		e.info.add(makeGenericInfo(GenericInfoLevel.FATAL, "fatal error"));

		throw e;
	}

	@GetMapping("/error")
	@ResponseStatus(HttpStatus.OK)
	public void error() throws GenericException {
		BadRequestException e = new BadRequestException("error");

		e.info = new LinkedList<GenericInfo>();
		e.info.add(makeGenericInfo(GenericInfoLevel.ERROR, "error error"));

		throw e;
	}

	@GetMapping("/warning")
	@ResponseStatus(HttpStatus.OK)
	public void warning() throws GenericException {
		BadRequestException e = new BadRequestException("warning");

		e.info = new LinkedList<GenericInfo>();
		e.info.add(makeGenericInfo(GenericInfoLevel.WARN, "warning error"));

		throw e;
	}

	@GetMapping("/info")
	@ResponseStatus(HttpStatus.OK)
	public void info() throws GenericException {
		BadRequestException e = new BadRequestException("info");

		e.info = new LinkedList<GenericInfo>();
		e.info.add(makeGenericInfo(GenericInfoLevel.INFO, "info error"));

		throw e;
	}

	@GetMapping("/debug")
	@ResponseStatus(HttpStatus.OK)
	public void debug() throws GenericException {
		BadRequestException e = new BadRequestException("debug");

		e.info = new LinkedList<GenericInfo>();
		e.info.add(makeGenericInfo(GenericInfoLevel.DEBUG, "debug error"));

		throw e;
	}

	@GetMapping("/trace")
	@ResponseStatus(HttpStatus.OK)
	public void trace() throws GenericException {
		BadRequestException e = new BadRequestException("trace");

		e.info = new LinkedList<GenericInfo>();
		e.info.add(makeGenericInfo(GenericInfoLevel.TRACE, "trace error"));

		throw e;
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public void all() throws GenericException {
		BadRequestException e = new BadRequestException("all");

		e.info = new LinkedList<GenericInfo>();
		e.info.add(makeGenericInfo(GenericInfoLevel.FATAL, "fatal error"));
		e.info.add(makeGenericInfo(GenericInfoLevel.ERROR, "error error"));
		e.info.add(makeGenericInfo(GenericInfoLevel.WARN, "warning error"));
		e.info.add(makeGenericInfo(GenericInfoLevel.INFO, "info error"));
		e.info.add(makeGenericInfo(GenericInfoLevel.DEBUG, "debug error"));
		e.info.add(makeGenericInfo(GenericInfoLevel.TRACE, "trace error"));

		throw e;
	}

	// help functions

	private GenericInfo makeGenericInfo (GenericInfoLevel type, String text) {
		GenericInfo info = new GenericInfo();

		info.type = type;
		info.text = text;

		return info;
	}
}



