package com.aoede.modules.music.repository;

import org.springframework.stereotype.Repository;

import com.aoede.commons.base.repository.AbstractJpaRepository;
import com.aoede.modules.music.entity.NoteEntity;

@Repository
public interface NoteRepository extends AbstractJpaRepository <Long, NoteEntity> {
}



