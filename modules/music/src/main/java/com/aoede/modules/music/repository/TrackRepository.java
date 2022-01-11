package com.aoede.modules.music.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.music.entity.TrackEntity;
import com.aoede.modules.music.entity.TrackId;

@Repository
public interface TrackRepository extends AbstractJpaRepository <TrackId, TrackEntity> {
	public List<TrackEntity> findBySheetId(Long id);

	public Short countBySheetId(Long id);
}



