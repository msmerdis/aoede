package com.aoede.commons.base.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.aoede.commons.base.exceptions.AcceptedStatus;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.ConflictException;
import com.aoede.commons.base.exceptions.CreatedStatus;
import com.aoede.commons.base.exceptions.ForbiddenException;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.MethodNotAllowedException;
import com.aoede.commons.base.exceptions.NoContentStatus;
import com.aoede.commons.base.exceptions.NotFoundException;
import com.aoede.commons.base.exceptions.NotImplementedException;
import com.aoede.commons.base.exceptions.RequestTimeoutException;
import com.aoede.commons.base.exceptions.SuccessStatus;
import com.aoede.commons.base.exceptions.UnauthorizedException;

/**
 * Define a controller in the test scope to generate the errors
 */
@RestController
@RequestMapping ("/test/error")
public class ExceptionTestController {

	// force status response

	@GetMapping("/accepted")
	@ResponseStatus(HttpStatus.OK)
	public void accepted() throws GenericException {
		throw new AcceptedStatus ();
	}

	@GetMapping("/created")
	@ResponseStatus(HttpStatus.OK)
	public void created() throws GenericException {
		throw new CreatedStatus ();
	}

	@GetMapping("/noContent")
	@ResponseStatus(HttpStatus.OK)
	public void noContent() throws GenericException {
		throw new NoContentStatus();
	}

	@GetMapping("/success")
	@ResponseStatus(HttpStatus.OK)
	public void success() throws GenericException {
		throw new SuccessStatus();
	}

	// actual exceptions

	@GetMapping("/badRequest")
	@ResponseStatus(HttpStatus.OK)
	public void badRequest() throws GenericException {
		throw new BadRequestException ("testing bad request exceptions");
	}

	@GetMapping("/confict")
	@ResponseStatus(HttpStatus.OK)
	public void confict() throws GenericException {
		throw new ConflictException ("testing conflict exceptions");
	}

	@GetMapping("/forbidden")
	@ResponseStatus(HttpStatus.OK)
	public void forbidden() throws GenericException {
		throw new ForbiddenException ("testing forbidden exceptions");
	}

	@GetMapping("/methodNotAllowed")
	@ResponseStatus(HttpStatus.OK)
	public void methodNotAllowed() throws GenericException {
		throw new MethodNotAllowedException ("testing method not allowed exceptions");
	}

	@GetMapping("/notFound")
	@ResponseStatus(HttpStatus.OK)
	public void notFound() throws GenericException {
		throw new NotFoundException ("testing not found exceptions");
	}

	@GetMapping("/notImplemented")
	@ResponseStatus(HttpStatus.OK)
	public void notImplemented() throws GenericException {
		throw new NotImplementedException ("testing not implemented exceptions");
	}

	@GetMapping("/timeout")
	@ResponseStatus(HttpStatus.OK)
	public void timeout() throws GenericException {
		throw new RequestTimeoutException ("testing timeout exceptions");
	}

	@GetMapping("/unauthorized")
	@ResponseStatus(HttpStatus.OK)
	public void unauthorized() throws GenericException {
		throw new UnauthorizedException ("testing unauthorized exceptions");
	}

	@GetMapping("/httpMediaTypeNotAcceptable")
	@ResponseStatus(HttpStatus.OK)
	public void httpMediaTypeNotAcceptable() throws Exception {
		throw new HttpMediaTypeNotAcceptableException (MediaType.APPLICATION_JSON_VALUE);
	}

	@GetMapping("/methodArgumentTypeMismatch")
	@ResponseStatus(HttpStatus.OK)
	public void methodArgumentTypeMismatch() throws Exception {
		throw new MethodArgumentTypeMismatchException (null, null, null, null, null);
	}

}



