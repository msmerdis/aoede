package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.entity.NoteEntity;

public interface NoteService extends AbstractServiceDomain <Long, Note, NoteEntity> {

	List<Note> findByMeasureId(Long id);

}



