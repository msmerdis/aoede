package com.aoede.modules.music.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractDomainController;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.service.SheetService;

@RestController
@RequestMapping ("/api/sheet")
public class SheetController extends AbstractDomainController<Long, Sheet, SheetService> {

	public SheetController(SheetService sheetService) {
		super(sheetService);
	}

}



