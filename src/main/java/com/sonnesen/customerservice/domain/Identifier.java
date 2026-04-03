package com.sonnesen.customerservice.domain;

public abstract class Identifier<T> extends ValueObject {

    public abstract T getValue();
}
