package myshop.springshop.repository;

import myshop.springshop.domain.Member;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long id);
}
