package com.aoede.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.service.ClefService;
import com.aoede.modules.music.service.KeySignatureService;
import com.aoede.modules.music.service.OctaveService;
import com.aoede.modules.music.service.TempoService;
import com.aoede.transfer.AoedePreload;

@RestController
@RequestMapping ("/api/aoede")
public class AoedeController {

	private ClefService         clefService;
	private TempoService        tempoService;
	private KeySignatureService keySignatureService;
	private OctaveService       octaveService;

	public AoedeController (
		ClefService         clefService,
		TempoService        tempoService,
		KeySignatureService keySignatureService,
		OctaveService       octaveService
	) {
		this.clefService         = clefService;
		this.tempoService        = tempoService;
		this.keySignatureService = keySignatureService;
		this.octaveService       = octaveService;
	}

	@GetMapping("/preload")
	@ResponseStatus(HttpStatus.OK)
	public AoedePreload preload() throws Exception {
		return buildPreloadData ();
	}

	private AoedePreload buildPreloadData () throws GenericException {
		return new AoedePreload (
			clefService.findAll(),
			tempoService.findAll(),
			keySignatureService.findAll(),
			octaveService.findAll()
		);
	}

}



