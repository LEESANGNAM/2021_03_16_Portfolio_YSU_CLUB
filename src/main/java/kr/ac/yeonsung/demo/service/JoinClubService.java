package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.Join;
import kr.ac.yeonsung.demo.domain.JoinClub;
import kr.ac.yeonsung.demo.repository.ClubRepository;
import kr.ac.yeonsung.demo.repository.JoinClubRepository;
import kr.ac.yeonsung.demo.repository.JoinRepository;
import kr.ac.yeonsung.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinClubService {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private JoinRepository joinRepository;
    @Autowired
    private JoinClubRepository joinClubRepository;

    @Transactional
    public JoinClub findOne(Long clubId){  return joinClubRepository.findOne(clubId);}
    public void deleteOne(JoinClub joinClub){ joinClubRepository.deleteOne(joinClub);}
}
