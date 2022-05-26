package com.aoede.modules.music.service;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.AbstractServiceEntity;
import com.aoede.modules.music.domain.KeySignature;

public interface KeySignatureService extends AbstractServiceEntity <Short, KeySignature> {
	public KeySignature findByMajor (String major) throws GenericException;
	public KeySignature findByMinor (String minor) throws GenericException;
}



