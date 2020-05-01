package com.mobile_app.themovie.presentation.util;

public enum MovieConst {

    MOVIE_ID("movie_id"),
    CATALOG_ID("catalog_id"),
    CATALOG_NAME("catalog_name"),
    VIDEO_CODE("video_code"),
    TYPE("type"),
    YOUTUBE("YouTube");

    private String constValue;

    MovieConst(String constValue) {
        this.constValue = constValue;
    }

    @Override
    public String toString() {
        return constValue;
    }
}
