package com.aoede.commons.base.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.http.HttpStatus;

import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.transfer.CompositeKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompositeKeyConverterFactory implements ConverterFactory<String, CompositeKey> {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T extends CompositeKey> Converter<String, T> getConverter(Class<T> targetType) {
		return new AccessKeyConverter<>(targetType, mapper);
	}

	private static class AccessKeyConverter<AccessKey extends CompositeKey> implements Converter<String, AccessKey> {

		private Class<AccessKey> targetType;
		private ObjectMapper mapper;

		public AccessKeyConverter (Class<AccessKey> targetType, ObjectMapper mapper) {
			this.targetType = targetType;
		}

		@Override
		public AccessKey convert(String source) {
			try {
				return mapper.readerFor(targetType).readValue(source);
			} catch (JsonProcessingException e) {
				throw new GenericExceptionContainer (HttpStatus.BAD_REQUEST, "could not decode " + targetType.getName());
			}
		}

	}
}



