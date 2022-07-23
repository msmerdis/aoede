package com.aoede.modules.music.service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.entity.SheetEntity;
import com.aoede.modules.music.transfer.GenerateSheet;

public interface SheetService extends AbstractServiceDomain <Long, Sheet, Long, SheetEntity> {
	Sheet generate(GenerateSheet data) throws GenericException;
}



