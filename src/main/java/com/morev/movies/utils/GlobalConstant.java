package com.morev.movies.utils;

import lombok.Getter;

public enum GlobalConstant {
    BASE_URL("http://localhost:8080/api/v1");
    @Getter
    private final String url;
    GlobalConstant(String envUrl) {
        this.url = envUrl;
    }
}
