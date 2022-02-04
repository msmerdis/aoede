package com.aoede.commons.base.compositekey;

import com.aoede.commons.base.transfer.CompositeKey;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CompositeKeyAccessData implements CompositeKey {
	private Integer parentId;
	private Integer childId;
}



