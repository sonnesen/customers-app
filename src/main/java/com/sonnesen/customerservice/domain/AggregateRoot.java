package com.sonnesen.customerservice.domain;

public abstract class AggregateRoot<T extends Identifier<?>> extends Entity<T> {

    protected AggregateRoot(T id) {
        super(id);
    }

}
