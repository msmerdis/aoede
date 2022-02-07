package com.aoede.modules.music.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.music.entity.MeasureId;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.entity.NoteId;

@Repository
public interface NoteRepository extends AbstractJpaRepository <NoteId, NoteEntity> {

	List<NoteEntity> findByMeasureId(MeasureId id);
	List<NoteEntity> findBySheetUserId(Long currentUserId);

	Short countByMeasureId(MeasureId id);

}



