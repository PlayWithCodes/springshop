package myshop.springshop.service;

import myshop.springshop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired MemberService memberService;

    @Test
    @Rollback(false)
    @DisplayName("회원가입")
    public void save() {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("member1");
        member2.setName("member2");
        //when
        Long savedId1 = memberService.join(member1);
        Long savedId2 = memberService.join(member2);
        Member savedMember1 = memberService.findMember(savedId1);
        Member savedMember2 = memberService.findMember(savedId2);
        //then
        assertThat(savedMember1).isEqualTo(member1);
        assertThat(savedMember2).isEqualTo(member2);
    }

    @Test
    @DisplayName("중복회원가입")
    public void validateDuplicateMember() {
        //given
        Member member1 = new Member();
        member1.setName("duplicateName");
        Member member2 = new Member();
        member2.setName("duplicateName");
        //when
        memberService.join(member1);
        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}