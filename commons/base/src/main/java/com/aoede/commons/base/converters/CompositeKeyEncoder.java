package com.aoede.commons.base.converters;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;

import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.transfer.CompositeKey;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CompositeKeyEncoder extends JsonSerializer<CompositeKey> implements Converter<CompositeKey, String> {

	private ObjectMapper mapper = new ObjectMapper();

	public CompositeKeyEncoder () {
		mapper.configure(MapperFeature.USE_ANNOTATIONS, false);
	}

	@Override
	public String convert(CompositeKey source) {
		try {
			String encode = mapper.writerFor(Object.class).writeValueAsString(source);

			// pad string to end up with a base 64 representation not containing =
			switch (encode.length() % 3) {
				case 1: encode = encode + "  "; break;
				case 2: encode = encode + " ";  break;
				default: // no padding is needed
			}

			return Base64.encodeBase64String(encode.getBytes());
		} catch (Exception e) {
			throw new GenericExceptionContainer (HttpStatus.BAD_REQUEST, "could not encode " + source.getClass().getName());
		}
	}

	@Override
	public void serialize(CompositeKey value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(convert(value));
	}

}



