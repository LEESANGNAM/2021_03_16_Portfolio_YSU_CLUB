package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private  final ClubRepository clubRepository;

    @Transactional
    public void saveClub(Club club){
        clubRepository.save(club);
    }

    public List<Club> findClub(){
        return  clubRepository.findAll();
    }

    public  Club findOne(Long clubId){
        return clubRepository.findOne(clubId);
    }



    @Transactional
    public void deleteClub(Long id){
        Club club = clubRepository.findOne(id);
        clubRepository.deleteOne(club);
    }

    /**
     * 변경
     * @param id 업데이트할 객체의 id 변하지 않음
     */
    @Transactional
    public void updateClub(Long id,String name,int totalNumber,String autor,String isbn){
        Book book = (Book) clubRepository.findOne(id);
        book.setName(name);
        book.setTotalNumber(totalNumber);
        book.setAuthor(autor);
        book.setIsbn(isbn);
    }


}
