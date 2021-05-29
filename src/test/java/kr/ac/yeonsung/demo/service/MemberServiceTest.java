package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.Member;
import kr.ac.yeonsung.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;



//@SpringBootTest
//@Transactional
//public class MemberServiceTest {
//
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    EntityManager em; // db의 쿼리를 보기 위해 생성
//
//    @Test
//    // @Rollback(false) // Transctional이 기본적으로 Rollback을 해버리기 때문에 db의 쿼리를 보기 위해 이용함
//    public void 회원가입() throws Exception {
//        //given
//        Member member = new Member();
//        member.setName("kim");
//
//        //when
//        Long savedId = memberService.join(member);
//
//        //then
//        em.flush(); // db에 쿼리를 날림 @Rollback(false)와 같음
////        assertEquals(member, memberRepository.findById(savedId));
//    }
//
//    @Test
//    public void 중복_회원_예외() throws Exception {
//        //given
//        Member member1 = new Member();
//        member1.setName("kim");
//
//        Member member2 = new Member();
//        member2.setName("kim");
//
//        //when
//        memberService.join(member1);
//
//        //then
////        assertThrows(IllegalStateException.class,()->memberService.join(member2));
//    }
//}