package kr.ac.yeonsung.demo.service;

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

    public void deleteClub(Club club){ clubRepository.deleteOne(club);}

}
