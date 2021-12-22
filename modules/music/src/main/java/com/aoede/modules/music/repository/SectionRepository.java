package com.aoede.modules.music.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.music.entity.SectionEntity;

@Repository
public interface SectionRepository extends AbstractJpaRepository <Long, SectionEntity> {
	List<SectionEntity> findByTrackId(Long id);
}



