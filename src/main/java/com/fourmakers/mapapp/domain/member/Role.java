package com.fourmakers.mapapp.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    MEMBER("ROLE_MEMBER", "일반 사용자");

    private final String key;
    private final String title;

}
