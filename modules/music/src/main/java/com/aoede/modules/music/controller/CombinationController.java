package com.aoede.modules.music.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.service.CombinationService;
import com.aoede.modules.music.transfer.combination.FullSheet;

@RestController
@RequestMapping ("/api/sheet")
public class CombinationController {

	private CombinationService combinationService;

	public CombinationController(
		CombinationService combinationService
	) {
		this.combinationService = combinationService;
	}

	@GetMapping("/{id}/full")
	@ResponseStatus(HttpStatus.OK)
	public FullSheet get(@PathVariable("id") final Long id) throws GenericException {
		return combinationService.getSheet(id);
	}
}



