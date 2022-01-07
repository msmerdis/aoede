package com.aoede.modules.music.transfer.measure;

import java.util.List;

import com.aoede.commons.base.transfer.AbstractResponse;
import com.aoede.modules.music.transfer.note.SimpleNoteResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SimpleMeasureResponse implements AbstractResponse <Long> {
	private Long measureId;
	private List<SimpleNoteResponse> notes;
}



