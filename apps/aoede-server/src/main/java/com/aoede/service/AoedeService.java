package com.aoede.service;

import java.time.LocalDate;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.transfer.AoedePreload;

public interface AoedeService {
	public AoedePreload buildPreloadData () throws GenericException;
	public void         clearPreloadData ();
	public LocalDate    buildPreloadDate ();
}



