package com.aoede.commons.base.transfer;

import com.aoede.commons.base.converter.CompositeKeyEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CompositeKeyEncoder.class)
public interface CompositeKey {
}



