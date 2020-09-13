package com.fourmakers.mapapp.member;

import com.fourmakers.mapapp.domain.member.SocialMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialMemberRepository extends JpaRepository<SocialMember, Long> {
    Optional<SocialMember> findByKakaoAccountId(String email);
}
