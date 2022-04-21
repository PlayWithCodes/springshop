package myshop.springshop.service;

import lombok.RequiredArgsConstructor;
import myshop.springshop.domain.Member;
import myshop.springshop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원 이름입니다.");
        }
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}