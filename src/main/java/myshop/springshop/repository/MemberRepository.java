package myshop.springshop.repository;

import myshop.springshop.domain.Member;

import java.util.List;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    List<Member> findByName(String name);
}
