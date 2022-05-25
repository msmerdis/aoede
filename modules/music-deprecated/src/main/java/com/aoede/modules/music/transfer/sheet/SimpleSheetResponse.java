package com.aoede.modules.music.transfer.sheet;

import com.aoede.commons.base.transfer.AbstractResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SimpleSheetResponse extends UpdateSheet implements AbstractResponse <Long> {
	private Long id;
}



