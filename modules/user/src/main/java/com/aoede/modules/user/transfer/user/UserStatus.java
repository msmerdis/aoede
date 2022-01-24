package com.aoede.modules.user.transfer.user;

import com.aoede.commons.base.transfer.EnumEntityField;

public enum UserStatus implements EnumEntityField {
	PENDING('P'),
	ACTIVE('A'),
	LOCKED('L'),
	SUSPENDED('S');

	private char status;

	private UserStatus(char c) {
		this.status = c;
	}

	@Override
	public Character toCharacter() {
		return status;
	}
}



