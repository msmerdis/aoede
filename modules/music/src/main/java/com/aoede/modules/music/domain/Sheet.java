package com.aoede.modules.music.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aoede.commons.base.domain.AbstractDomain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Sheet implements AbstractDomain<Long> {
	private Long id;

	@NotEmpty (message = "Sheet must define a name")
	private String name;

	@Valid
	@NotNull (message = "Sheet must define tracks")
	private List<Track> tracks;
}



