package com.aoede.modules.music.service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.transfer.combination.FullSheet;

public interface CombinationService {
	public FullSheet getSheet(Long id) throws GenericException;
}



