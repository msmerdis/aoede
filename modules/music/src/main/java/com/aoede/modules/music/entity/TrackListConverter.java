package com.aoede.modules.music.entity;

import java.io.IOException;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.modules.music.domain.Track;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class TrackListConverter extends BaseComponent implements AttributeConverter<List<Track>, String> {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Track> attribute) {
		if (attribute == null) return null;

		try {
			return mapper
				.writerFor(List.class)
				.writeValueAsString(attribute);
		} catch (final JsonProcessingException e) {
			throw new GenericExceptionContainer(
				// TODO replace with a message
				new BadRequestException(e.getMessage())
			);
		}
	}

	@Override
	public List<Track> convertToEntityAttribute(String data) {
		if (data == null) return null;

		try {
			return mapper
				.readerFor(List.class)
				.readValue(data);
		} catch (final IOException e) {
			logger.error("got read error : " + e.getMessage());
			throw new GenericExceptionContainer(
				// TODO replace with a message
				new BadRequestException(e.getMessage())
			);
		}
	}

}



