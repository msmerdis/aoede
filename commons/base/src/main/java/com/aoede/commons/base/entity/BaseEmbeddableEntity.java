package com.aoede.commons.base.entity;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.ToString;

/**
 * This class a base entity for composite keys
 */
@Getter
@ToString
@MappedSuperclass
public abstract class BaseEmbeddableEntity<Key extends Embeddable> extends AbstractJpaEntity<Key> implements AbstractEntity <Key> {
	@EmbeddedId
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
		BaseEmbeddableEntity<Key> other = (BaseEmbeddableEntity<Key>) obj;

		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}



