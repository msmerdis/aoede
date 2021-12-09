package com.aoede.commons.base.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.ToString;

/**
 * This class a base entity for non composite keys
 */
@Getter
@ToString
@MappedSuperclass
public abstract class BaseEntity<Key> implements AbstractEntity <Key> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
	protected Key id;

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass())
			return false;

		@SuppressWarnings("unchecked")
		BaseEntity<Key> other = (BaseEntity<Key>) obj;

		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}



