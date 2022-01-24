package com.aoede.modules.user.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

import com.aoede.modules.user.transfer.user.UserStatus;

public class UserStatusConverter implements AttributeConverter<UserStatus, Character> {

	@Override
	public Character convertToDatabaseColumn(UserStatus attribute) {
		return attribute.toCharacter();
	}

	@Override
	public UserStatus convertToEntityAttribute(Character dbData) {
		return Stream.of(UserStatus.values())
			.filter(s -> s.toCharacter().equals(dbData))
			.findFirst()
			.orElseThrow();
	}

}



