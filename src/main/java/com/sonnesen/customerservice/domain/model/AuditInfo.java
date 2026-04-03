package com.sonnesen.customerservice.domain.model;

import java.time.Instant;

import com.sonnesen.customerservice.domain.ValueObject;

public class AuditInfo extends ValueObject {
    private final Instant createdAt;
    private final Instant updatedAt;

    private AuditInfo(final Instant createdAt, final Instant updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AuditInfo of(final Instant createdAt, final Instant updatedAt) {
        return new AuditInfo(createdAt, updatedAt);
    }

    public AuditInfo withUpdatedAt(final Instant now) {
        return new AuditInfo(this.createdAt, now);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
