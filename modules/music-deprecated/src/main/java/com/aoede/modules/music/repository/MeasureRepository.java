package com.aoede.modules.music.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.music.entity.MeasureEntity;
import com.aoede.modules.music.entity.MeasureId;
import com.aoede.modules.music.entity.SectionId;

@Repository
public interface MeasureRepository extends AbstractJpaRepository <MeasureId, MeasureEntity> {

	List<MeasureEntity> findBySectionId(SectionId id);
	List<MeasureEntity> findBySheetUserId(Long currentUserId);

	Short countBySectionId(SectionId sectionId);

}



