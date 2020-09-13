package com.fourmakers.mapapp.config.auth;

import com.fourmakers.mapapp.config.auth.dto.SessionMember;
import com.fourmakers.mapapp.domain.member.SocialMember;
import com.fourmakers.mapapp.domain.member.dto.auth.OAuthAttributes;
import com.fourmakers.mapapp.member.SocialMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SocialMemberRepository socialMemberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        SocialMember socialMember = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new SessionMember(socialMember));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(socialMember.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private SocialMember saveOrUpdate(OAuthAttributes attributes) {

        SocialMember socialMember = socialMemberRepository.findByKakaoAccountId(attributes.getAccountId())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return socialMemberRepository.save(socialMember);
    }
}
