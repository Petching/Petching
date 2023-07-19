package com.Petching.petching.login.oauth;

import java.util.Map;
// 추상 클래스로 상속을 받아야 사용가능.
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getNickname();

    public abstract String getImageUrl();

    public abstract String getEmail();
}
