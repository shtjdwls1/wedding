package com.smart.project.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    LOCAL("LOCAL","로컬"),
    KAKAO("KAKAO","카카오"),
    NAVER("NAVER","네이버"),
    GOOGLE("GOOGLE","구글");

    private final String key;
    private final String title;
}
