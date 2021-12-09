package com.aoede.modules.music.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.modules.music.domain.Clef;
import com.aoede.modules.music.service.ClefService;

@RestController
@RequestMapping ("/api/clef")
public class ClefController extends AbstractDomainController<String, Clef, ClefService> {

	public ClefController(ClefService service) {
		super(service);
	}

}



