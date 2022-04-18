package myshop.springshop.repository;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long id);
}
