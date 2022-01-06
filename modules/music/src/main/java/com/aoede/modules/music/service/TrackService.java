package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.entity.TrackEntity;

public interface TrackService extends AbstractServiceDomain <Long, Track, Long, TrackEntity> {

	void updateSectionEntity(SectionEntity entity, Long id);

	List<Track> findBySheetId(Long id);

}



