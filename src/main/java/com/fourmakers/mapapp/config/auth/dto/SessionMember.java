package com.fourmakers.mapapp.config.auth.dto;

import com.fourmakers.mapapp.domain.member.SocialMember;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionMember(SocialMember socialMember) {
        this.name = socialMember.getName();
        this.email = socialMember.getEmail();
        this.picture = socialMember.getPicture();
    }
}
