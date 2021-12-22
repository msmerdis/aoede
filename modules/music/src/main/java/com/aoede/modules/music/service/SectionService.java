package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Section;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.SectionEntity;

public interface SectionService extends AbstractServiceDomain <Long, Section, SectionEntity> {

	void updateMeasureEntity(MeasureEntity entity, Long id);

	List<Section> findByTrackId(Long id);

}



