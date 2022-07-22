package com.aoede.modules.music.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.modules.music.domain.Fraction;

public class FractionDecoderFactory extends BaseComponent implements ConverterFactory<String, Fraction> {

	@Override
	public <T extends Fraction> Converter<String, T> getConverter(Class<T> targetType) {
		return new FractionDecoder<>(targetType);
	}

	private static class FractionDecoder<T extends Fraction> extends BaseComponent implements Converter<String, T> {

		private Class<T> target;

		public FractionDecoder (Class<T> target) {
			this.target = target;
		}

		@Override
		public T convert(String source) {
			T dec = null;

			if (StringUtils.containsWhitespace(source)) {
				throwError("fraction must not contain whitespaces");
			}

			String[] parts = source.split("-");

			if (parts.length != 2) {
				throwError("fraction must contain two parts separated by -");
			}

			int num = Integer.valueOf(parts[0]);
			int den = Integer.valueOf(parts[1]);

			try {
				dec = target.getConstructor().newInstance();
				dec.setNumerator(num);
				dec.setDenominator(den);
			} catch (Exception e) {}

			return dec;
		}

		private void throwError (String msg) {
			throw new GenericExceptionContainer(
				new BadRequestException(msg)
			);
		}
	}
}



