package com.aoede.modules.music.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.music.entity.SectionEntity;
import com.aoede.modules.music.entity.SectionId;
import com.aoede.modules.music.entity.TrackId;

@Repository
public interface SectionRepository extends AbstractJpaRepository <SectionId, SectionEntity> {

	List<SectionEntity> findByTrackId(TrackId id);
	List<SectionEntity> findBySheetUserId(Long currentUserId);

	Short countByTrackId(TrackId trackId);

}



