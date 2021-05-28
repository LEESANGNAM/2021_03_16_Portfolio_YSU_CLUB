package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.Join;
import kr.ac.yeonsung.demo.domain.JoinClub;
import kr.ac.yeonsung.demo.domain.JoinStatus;
import kr.ac.yeonsung.demo.domain.Member;
import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.repository.ClubRepository;
import kr.ac.yeonsung.demo.repository.JoinClubRepository;
import kr.ac.yeonsung.demo.repository.JoinRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ClubServiceTest {

    @Autowired ClubService clubService;
    @Autowired JoinClubService joinClubService;
    @Autowired JoinService joinService;
    @Autowired ClubRepository clubRepository;
    @Autowired JoinClubRepository joinClubRepository;
    @PersistenceContext
    private EntityManager em;

    @AfterEach//테스트 메소드 실행후 호출
    public void reset(){
        clubRepository.deleteAll();//전체 data 삭제
        this.em.createNativeQuery("ALTER TABLE club ALTER COLUMN `club_id` RESTART WITH 41").executeUpdate();//시작값을 41로 초기화
    }

    @Test
    public void 클럽생성() throws Exception{
        //given
        Member member = createMember();
        Book book =createBook("testClub",10, member.getName());
        //when
        em.flush();
        Book book2= (Book) clubService.findOne(book.getId());
        //then
        assertEquals(book, book2);
    }

    @Test
    public void 삭제() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("TestName",10, member.getName());

        int joinCount = 1;
        Long join = joinService.Join(member.getId(), book.getId(), joinCount);
        Join getjoin = joinService.findOne(join);
        //when
        joinClubService.chageStatus(book); // 멤버상태변경
        joinClubRepository.deleteById(1l);
        clubService.deleteClub(book.getId()); // 동아리삭제
        Club one = clubService.findOne(book.getId());// 동아리아이디로 찾기 (삭제되었으면 null)

        //then

        assertEquals("동아리 탈퇴 확인", JoinStatus.cancel,getjoin.getStatus());//삭제후 Join의 status가 cancel인지 확인
        assertEquals("Member 동아리 탈퇴 확인",JoinStatus.cancel,member.getStatus());//삭제후 Member의 status가 cancel인지 확인
        assertNull(one); //삭제후 동아리가 null이 맞는지 확인
    }

    @Test
    public void 조회() throws Exception{
        //given
        Member member = createMember();

        Club club = createBook("테스트2",10,member.getName());
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

     @Test
      public void 동아리명_null() throws Exception{
         //given
         Book book = new Book();
         Member member = createMember();
         book.setClubJang(member.getName());
         book.setTotalNumber(10);
         clubService.saveClub(book);
          //when
         Club one = clubService.findOne(41l);

         //then
         assertEquals(book.getName(), one.getName());
         assertNull(one.getName());

     }
     @Test
      public void 동아리명_not_null() throws Exception{
         //given
         Book book = new Book();
         book.setName("testName");
         clubService.saveClub(book);
         //when
         Club one = clubService.findOne(41l);

         //then
         assertEquals(book.getName(), one.getName());
         assertNotNull(one.getName());
     }
      @Test
       public void 동아리명10초과() throws Exception{
          //given
           Book book = new Book();
           book.setName("일이삼사오육칠팔구십초과");
           clubService.saveClub(book);
           //when
          Club one = clubService.findOne(41l);
          int length = one.getName().length();
          //then
          assertEquals(book.getName(), one.getName());
          assertTrue(length>10);
      }
      @Test
       public void 동아리명10이하() throws Exception{
          //given
           Book book = new Book();
           book.setName("일이삼사오육칠팔구");
           clubService.saveClub(book);
           //when
          Club one = clubService.findOne(41l);
          int length = one.getName().length();
          //then
          assertEquals(book.getName(), one.getName());
          assertTrue(length<10);
      }
       @Test
        public void 가입인원1미만() throws Exception{
           //given
            Book book = new Book();
            book.setTotalNumber(0);
            clubService.saveClub(book);
            //when
           Club one = clubService.findOne(41l);
           int totalNumber = one.getTotalNumber();
           //then
           assertEquals(book.getId(),one.getId());
           assertTrue(totalNumber<1);
       }
       @Test
        public void 가입인원20초과() throws Exception{
           //given
            Book book = new Book();
            book.setTotalNumber(21);
            clubService.saveClub(book);
            //when
           Club one = clubService.findOne(41l);
           int totalNumber = one.getTotalNumber();
           //then
           assertEquals(book.getId(),one.getId());
           assertTrue(totalNumber>20);
       }
       @Test
        public void 동아리명공백() throws Exception{
           //정규식
           String pattern = "^[\\s]*$"; //공백만체크

            //given
            Book book = new Book();
            book.setName("    name");
            clubService.saveClub(book);
            //when
           Club one = clubService.findOne(41l);
           //then
           assertFalse(Pattern.matches(pattern,one.getName()));//title이 공백만있는게 아니라면 True
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