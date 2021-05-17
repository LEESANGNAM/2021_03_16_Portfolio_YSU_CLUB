package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.*;
import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.repository.ClubRepository;
import kr.ac.yeonsung.demo.repository.JoinClubRepository;
import kr.ac.yeonsung.demo.repository.JoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ClubServiceTest {

    @Autowired ClubService clubService;
    @Autowired JoinClubService joinClubService;
    @Autowired JoinService joinService;
    @Autowired ClubRepository clubRepository;
    @Autowired
    private EntityManager em;
    private JoinRepository joinRepository;
    private JoinClubRepository joinClubRepository;

    @Test
     public void 클럽생성() throws Exception{
         //given
          Book book =new Book();
          book.setName("살려주세요");
         //when
        clubService.saveClub(book);
         em.flush();
        Book book2= (Book) clubService.findOne(book.getId());
         //then
        assertEquals(book, book2);
     }

      @Test
      public void 삭제() throws Exception{
          //given
          Member member = createMember();

          Book book =new Book();
          book.setName("살려주세요111");
          book.setTotalNumber(10);
          clubService.saveClub(book);

//          int joinCount = 1;
//          joinService.Join(member.getId(),book.getId(),joinCount);
//          //when
//          joinClubService.chageStatus(book);
//
//          List<JoinclubMapping> byClub = joinClubRepository.findByClub(book);

          clubService.deleteClub(book.getId());
          Book book2= (Book) clubService.findOne(book.getId());

          //then
//          Join getjoin = joinRepository.findById(byClub.get(0).getJoin_Id()).orElse(null);
//          assertEquals("동아리 탈퇴 확인", JoinStatus.cancel,getjoin.getStatus());//탈퇴후 Join의 status가 cancel인지 확인
//          assertEquals("Member 동아리 탈퇴 확인",JoinStatus.cancel,member.getStatus());//탈퇴후 Member의 status가 cancel인지 확인
          assertNull(book2);
      }

       @Test
        public void 조회() throws Exception{
        //given
           Book book =new Book();
           book.setName("살려주세요112323231");
           clubService.saveClub(book);
        //when
        clubService.findClub();
        //then
        }
    private Member createMember() {
        Member member = new Member();
        member.setName("회원테스트1");
        em.persist(member);
        return member;
    }
}