package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.domain.SectionKey;
import com.aoede.modules.music.domain.TrackKey;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.entity.SectionId;

public interface SectionService extends AbstractServiceDomain <SectionKey, Section, SectionId, SectionEntity> {

	void updateMeasureEntity(MeasureEntity entity, SectionKey key);

	List<Section> findByTrackId(TrackKey trackKey);

}



