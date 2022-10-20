package com.smart.project.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    COMPANY("업체","업체"),
    PLANNER("웨딩플래너","웨딩플래너"),
    USER("일반회원","일반 회원");

    private final String key;
    private final String title;
}

