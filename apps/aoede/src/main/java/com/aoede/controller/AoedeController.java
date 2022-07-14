package com.aoede.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.service.AoedeService;
import com.aoede.transfer.AoedePreload;

@RestController
@RequestMapping ("/api/aoede")
public class AoedeController extends BaseComponent {

	private AoedeService aoedeService;

	public AoedeController (
		AoedeService aoedeService
	) {
		this.aoedeService = aoedeService;
	}

	@GetMapping("/preload")
	@ResponseStatus(HttpStatus.OK)
	public AoedePreload preload() throws Exception {
		AoedePreload data = aoedeService.buildPreloadData ();

		if (!aoedeService.buildPreloadDate().equals(data.getCreated())) {
			aoedeService.clearPreloadData();
			data = aoedeService.buildPreloadData ();
		}

		return data;
	}

}



