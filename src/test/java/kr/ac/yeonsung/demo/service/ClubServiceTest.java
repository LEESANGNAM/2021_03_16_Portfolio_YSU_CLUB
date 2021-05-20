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

          Club club = createBook("테스트1",10,member.getName());
          clubService.saveClub(club);

          int joinCount = 1;
          Long join = joinService.Join(member.getId(), club.getId(), joinCount);
          Join getjoin = joinService.findOne(join);
          //when
          joinClubService.chageStatus(club); // 멤버상태변경
          clubService.deleteClub(club.getId()); // 동아리삭제
          Book book2= (Book) clubService.findOne(club.getId()); // 동아리아이디로 찾기 (삭제되었으면 null)

          //then

          assertEquals("동아리 탈퇴 확인", JoinStatus.cancel,getjoin.getStatus());//삭제후 Join의 status가 cancel인지 확인
          assertEquals("Member 동아리 탈퇴 확인",JoinStatus.cancel,member.getStatus());//삭제후 Member의 status가 cancel인지 확인
          assertNull(book2); //삭제후 동아리가 null이 맞는지 확인
      }

       @Test
        public void 조회() throws Exception{
        //given
           Member member = createMember();

           Club club = createBook("테스트1",10,member.getName());
           clubService.saveClub(club);
        //when
        clubService.findClub();
        //then
        }

         @Test
          public void 수정() throws Exception{
             //given
             Member member = createMember();
             Book book = createBook("동아리테스트",10,member.getName());
             Club one = clubService.findOne(book.getId()); //생성하고 해당 객체를 찾아놓는다.
             //when
                clubService.updateClub(book.getId(),"수정테스트동아리",10,"수정테스트 멤버이름"); //값을 변경한다.
              //then
             assertEquals("동아리 번호확인",one.getId(),book.getId()); //수정전 객체와 수정후 객체의 번호가 같은지 확인
             assertEquals("동아리 이름수정확인","수정테스트동아리",book.getName());
             assertEquals("동아리 가입인원 확인",10,book.getTotalNumber());
             assertEquals("동아리 회장이름 확인","수정테스트 멤버이름",book.getClubJang());
         }

    private Book createBook(String name,int totalCount,String memberName) {
        Book book = new Book();
        book.setName(name);
        book.setTotalNumber(totalCount);
        book.setClubJang(memberName);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원테스트1");
        em.persist(member);
        return member;
    }
}