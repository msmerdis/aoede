package com.aoede.commons.base.transfer;

import com.aoede.commons.base.converters.CompositeKeyEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CompositeKeyEncoder.class)
public interface CompositeKey {
}



