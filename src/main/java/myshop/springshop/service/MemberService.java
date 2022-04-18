package myshop.springshop.service;

import myshop.springshop.repository.Member;

public interface MemberService {
    Long join(Member member);

    Member findMember(Long id);
}
