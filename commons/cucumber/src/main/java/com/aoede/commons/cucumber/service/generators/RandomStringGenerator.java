package com.aoede.commons.cucumber.service.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.regex.Pattern;

import com.aoede.commons.cucumber.service.JsonElementGenerator;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class RandomStringGenerator implements JsonElementGenerator {
	private final static String STRING_REGEX = "\\{[^{}]*\\}";

	private Random random;
	private Pattern stringPattern = Pattern.compile(STRING_REGEX, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	public RandomStringGenerator (Random random) {
		this.random = random;
	}

	@Override
	public JsonElement generate(String value) {
		return new JsonPrimitive(randomString(value));
	}

	public String randomString (int length) {
		return random.ints(48, 123)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	public String randomString (String template) {
		var matcher = stringPattern.matcher(template);

		// find and process all matches
		while (matcher.find()) {
			String block = matcher.group();
			String[] parts = block.substring(1, block.length()-1).split(":");

			switch (parts[0]) {
				case "string":
					assertEquals("string template block must have two parts", 2, parts.length);
					int length = Integer.parseInt(parts[1]);
					assertTrue("string template block must define a positive number as the second part", length > 0);
					template = template.replace(block, randomString(length));
					break;
				default:
					assertNotNull("unknown template block for random string: " + parts[0], null);
			}
		}

		return template;
	}
}



