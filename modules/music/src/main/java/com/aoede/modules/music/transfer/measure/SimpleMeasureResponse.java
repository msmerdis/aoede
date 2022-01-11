package com.aoede.modules.music.transfer.measure;

import java.util.List;

import com.aoede.commons.base.transfer.AbstractCompositeResponse;
import com.aoede.modules.music.transfer.note.SimpleNoteResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SimpleMeasureResponse implements AbstractCompositeResponse <AccessMeasure> {
	private AccessMeasure id;
	private List<SimpleNoteResponse> notes;
}



