package com.Petching.petching.login.oauth;

import com.Petching.petching.login.oauth.userInfo.GoogleOAuth2UserInfo;
import com.Petching.petching.login.oauth.userInfo.KakaoOAuth2UserInfo;
import com.Petching.petching.user.entity.SocialType;
import com.Petching.petching.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@Getter
public class OAuthAttributes {
    private String nameAttribute;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttribute, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttribute = nameAttribute;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of (SocialType socialType, String userNameAttribute,
                                      Map<String, Object> attributes) {
        if (socialType == SocialType.KAKAO) {
            return ofKakao (userNameAttribute, attributes);
        }
        return ofGoogle (userNameAttribute, attributes);
    }
    public static OAuthAttributes ofKakao (String userNameAttribute, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttribute(userNameAttribute)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }
    public static OAuthAttributes ofGoogle (String userNameAttribute, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttribute(userNameAttribute)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        return User.builder()
                .socialType(socialType)
                .socialId(oAuth2UserInfo.getId())
                .email(oAuth2UserInfo.getEmail())
                .nickName(oAuth2UserInfo.getNickname())
                .profileImgUrl(oAuth2UserInfo.getImageUrl())
                .roles(List.of("USER"))
                .build();
    }

}
