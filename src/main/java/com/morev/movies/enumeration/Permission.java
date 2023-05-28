package com.morev.movies.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    REVIEWER_CREATE("reviewer:create"),
    REVIEWER_UPDATE("reviewer:update"),
    REVIEWER_DELETE("reviewer:delete"),
    REVIEWER_READ("reviewer:create");
    @Getter
    private final String permission;
}
