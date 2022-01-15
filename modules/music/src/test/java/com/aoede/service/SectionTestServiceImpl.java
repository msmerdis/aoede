package com.aoede.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoede.commons.service.AbstractTestServiceImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Component
public class SectionTestServiceImpl extends AbstractTestServiceImpl implements SectionTestService {
	@Autowired
	TrackTestService trackTestService;

	@Override
	public String getName() {
		return "section";
	}

	@Override
	public String getPath() {
		return "/api/section";
	}

	@Override
	public String getKeyName() {
		return "id";
	}

	@Override
	protected boolean objectMatches (JsonObject obj, String key, String value) {
		switch (key) {
			case "timeSignature":
				JsonElement inner = obj.get(key);

				if (!inner.isJsonObject())
					return false;

				obj = inner.getAsJsonObject();

				String signature =
					obj.get( "numerator" ).getAsString() + "/" +
					obj.get("denominator").getAsString();

				return signature.equals(value);
			default:
				return super.objectMatches(obj, key, value);
		}
	}

}



