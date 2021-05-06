package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.repository.ClubRepository;
import kr.ac.yeonsung.demo.repository.JoinClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {
    private  final ClubRepository clubRepository;
    private final JoinClubRepository joinClubRepository;

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
    public void deleteClub(Club club){ clubRepository.deleteOne(club);}

    @Transactional
    public void updateClub(Long clubId, String name, int totalNumber, String author, String isbn) {
        Book book =  (Book) clubRepository.findOne(clubId);
        book.setName(name);
        book.setTotalNumber(totalNumber);
        book.setAuthor(author);
        book.setIsbn(isbn);
    }
}
