package com.aoede.commons.base.converter;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.http.HttpStatus;

import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.transfer.CompositeKey;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompositeKeyDecoderFactory implements ConverterFactory<String, CompositeKey> {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T extends CompositeKey> Converter<String, T> getConverter(Class<T> targetType) {
		return new AccessKeyDecoder<>(targetType, mapper);
	}

	private static class AccessKeyDecoder<K extends CompositeKey> implements Converter<String, K> {

		private Class<K> target;
		private ObjectMapper mapper;

		public AccessKeyDecoder (Class<K> target, ObjectMapper mapper) {
			this.target = target;
			this.mapper = mapper;
		}

		@Override
		public K convert(String source) {
			try {
				byte[] decode = Base64.decodeBase64(source.getBytes());
				return mapper.readerFor(target).readValue(decode);
			} catch (IOException e) {
				throw new GenericExceptionContainer (HttpStatus.BAD_REQUEST, "could not decode " + target.getName());
			}
		}

	}
}



