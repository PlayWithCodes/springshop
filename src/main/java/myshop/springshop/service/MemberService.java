package myshop.springshop.service;

import myshop.springshop.domain.Member;

import java.util.List;

public interface MemberService {
    Long join(Member member);

    Member findMember(Long id);

    List<Member> findMembers();

    void update(Long id, String name);
}
