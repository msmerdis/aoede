package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.domain.TrackKey;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.entity.TrackEntity;
import com.aoede.modules.music.entity.TrackId;

public interface TrackService extends AbstractServiceDomain <TrackKey, Track, TrackId, TrackEntity> {

	void updateSectionEntity(SectionEntity entity, TrackKey key);

	List<Track> findBySheetId(Long id);

}



