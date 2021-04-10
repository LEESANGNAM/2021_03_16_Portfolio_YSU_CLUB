package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.repository.ClubRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ClubServiceTest {

    @Autowired ClubService clubService;
    @Autowired ClubRepository clubRepository;

     @Test
     public void 클럽생성() throws Exception{
         //given
          Book book =new Book();
          book.setName("살려주세요");
          book.setAuthor("Lee");
         //when
        clubService.saveClub(book);
         //then
        assertEquals(book, clubRepository.findOne(book.getId()));
     }

      @Test
      public void 삭제() throws Exception{
          //given
          Book book =new Book();
          book.setName("살려주세요111");
          book.setAuthor("Lee111");
          clubService.saveClub(book);
          //when
          clubService.deleteClub(book);
          //then
          assertNotEquals(book,clubRepository.findOne(book.getId()));
      }

       @Test
        public void 조회() throws Exception{
        //given
           Book book =new Book();
           book.setName("살려주세요112323231");
           book.setAuthor("Lee1112323");
           clubService.saveClub(book);
        //when
        clubService.findClub();
        //then
        }
}