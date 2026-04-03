package com.sonnesen.customerservice.domain;

import java.util.Objects;

public abstract class Entity<T extends Identifier<?>> {

    protected final T id;

    protected Entity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Entity))
            return false;
        Entity<?> other = (Entity<?>) obj;
        return Objects.equals(id, other.id);
    }

}
