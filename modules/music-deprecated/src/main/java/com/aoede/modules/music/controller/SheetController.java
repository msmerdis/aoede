package com.aoede.modules.music.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractResourceController;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.service.SheetService;
import com.aoede.modules.music.transfer.sheet.CreateSheet;
import com.aoede.modules.music.transfer.sheet.DetailSheetResponse;
import com.aoede.modules.music.transfer.sheet.SimpleSheetResponse;
import com.aoede.modules.music.transfer.sheet.UpdateSheet;

@RestController
@RequestMapping ("/api/sheet")
public class SheetController extends AbstractResourceController<
	Long,
	Sheet,
	Long,
	CreateSheet,
	UpdateSheet,
	SimpleSheetResponse,
	DetailSheetResponse,
	SheetService
> {

	public SheetController(SheetService sheetService) {
		super(sheetService);
	}

	@Override
	public SimpleSheetResponse simpleResponse(Sheet entity) {
		SimpleSheetResponse response = new SimpleSheetResponse ();

		response.setId(entity.getId());
		response.setName(entity.getName());

		return response;
	}

	@Override
	public DetailSheetResponse detailResponse(Sheet entity) {
		DetailSheetResponse response = new DetailSheetResponse ();

		response.setId(entity.getId());
		response.setName(entity.getName());

		return response;
	}

	@Override
	public Sheet createDomain(CreateSheet request) {
		return updateDomain (request);
	}

	@Override
	public Sheet updateDomain(UpdateSheet request) {
		Sheet sheet = new Sheet ();

		sheet.setName(request.getName());

		return sheet;
	}

	@Override
	public Long createDomainKey(Long data) {
		return data;
	}

}



