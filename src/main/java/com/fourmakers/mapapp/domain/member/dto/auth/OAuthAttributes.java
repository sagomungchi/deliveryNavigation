package com.fourmakers.mapapp.domain.member.dto.auth;

import com.fourmakers.mapapp.domain.member.SocialMember;
import com.fourmakers.mapapp.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String accountId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String accountId, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

   public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofKakao(userNameAttributeName, attributes);
   }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .accountId(String.valueOf(attributes.get("id")))
                .name((String) ((LinkedHashMap) attributes.get("properties")).get("nickname"))
                .picture((String) ((LinkedHashMap) attributes.get("properties")).get("profile_image"))
                .email((String) ((LinkedHashMap) attributes.get("kakao_account")).get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public SocialMember toEntity() {
        return SocialMember.builder()
                .kakaoAccountId(accountId)
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.MEMBER)
                .build();
    }
}
