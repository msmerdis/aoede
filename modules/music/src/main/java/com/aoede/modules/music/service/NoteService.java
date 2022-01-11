package com.aoede.modules.music.service;

import java.util.List;

import com.aoede.commons.base.service.AbstractServiceDomain;
import com.aoede.modules.music.domain.MeasureKey;
import com.aoede.modules.music.domain.Note;
import com.aoede.modules.music.domain.NoteKey;
import com.aoede.modules.music.entity.NoteEntity;
import com.aoede.modules.music.entity.NoteId;

public interface NoteService extends AbstractServiceDomain <NoteKey, Note, NoteId, NoteEntity> {

	List<Note> findByMeasureId(MeasureKey id);

}



