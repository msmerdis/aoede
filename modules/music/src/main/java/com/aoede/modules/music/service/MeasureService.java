package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Measure;
import com.aoede.modules.music.domain.MeasureKey;
import com.aoede.modules.music.domain.SectionKey;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.MeasureId;
import com.aoede.modules.music.entity.NoteEntity;

public interface MeasureService extends AbstractServiceDomain <MeasureKey, Measure, MeasureId, MeasureEntity> {

	void updateSectionService(SectionService sectionService);

	void updateNoteEntity(NoteEntity entity, MeasureKey key);

	List<Measure> findBySectionId(SectionKey id);

}



