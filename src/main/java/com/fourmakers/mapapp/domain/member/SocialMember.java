package com.fourmakers.mapapp.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialMember {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "kakao_account_id", unique = true)
    private String kakaoAccountId;

    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public SocialMember(String name, String kakaoAccountId, String email, String picture, Role role) {
        this.kakaoAccountId = kakaoAccountId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public SocialMember update(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

}
