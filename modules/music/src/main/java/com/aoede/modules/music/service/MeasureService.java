package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.NoteEntity;

public interface MeasureService extends AbstractServiceDomain <Long, Measure, MeasureEntity> {

	void updateNoteEntity(NoteEntity entity, Long id);

	List<Measure> findBySectionId(Long id);

}



