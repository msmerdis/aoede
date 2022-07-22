package com.aoede.service;

import java.time.LocalDate;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.service.ClefService;
import com.aoede.modules.music.service.KeySignatureService;
import com.aoede.modules.music.service.OctaveService;
import com.aoede.modules.music.service.TempoService;
import com.aoede.modules.music.service.TimeSignatureService;
import com.aoede.transfer.AoedePreload;

@Service
public class AoedeServiceImpl extends BaseComponent implements AoedeService {

	private ClefService          clefService;
	private TempoService         tempoService;
	private KeySignatureService  keySignatureService;
	private OctaveService        octaveService;
	private TimeSignatureService timeSignatureService;

	public AoedeServiceImpl (
		ClefService          clefService,
		TempoService         tempoService,
		KeySignatureService  keySignatureService,
		OctaveService        octaveService,
		TimeSignatureService timeSignatureService
	) {
		this.clefService          = clefService;
		this.tempoService         = tempoService;
		this.keySignatureService  = keySignatureService;
		this.octaveService        = octaveService;
		this.timeSignatureService = timeSignatureService;
	}

	@Cacheable(value = "aoede", key = "#root.methodName")
	public AoedePreload buildPreloadData () throws GenericException {
		return new AoedePreload (
			clefService.findAll(),
			tempoService.findAll(),
			keySignatureService.findAll(),
			octaveService.findAll(),
			timeSignatureService.findAll(),
			this.buildPreloadDate()
		);
	}

	@CacheEvict(value = "aoede", allEntries = true)
	public void clearPreloadData () {}

	public LocalDate buildPreloadDate () {
		return LocalDate.now();
	}
}



