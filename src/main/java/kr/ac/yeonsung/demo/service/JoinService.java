package kr.ac.yeonsung.demo.service;

import kr.ac.yeonsung.demo.domain.Join;
import kr.ac.yeonsung.demo.domain.JoinClub;
import kr.ac.yeonsung.demo.domain.Member;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.repository.ClubRepository;
import kr.ac.yeonsung.demo.repository.JoinClubRepository;
import kr.ac.yeonsung.demo.repository.JoinRepository;
import kr.ac.yeonsung.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinService {
    @Autowired
    private  ClubRepository clubRepository;
    @Autowired
    private  MemberRepository memberRepository;
    @Autowired
    private  JoinRepository joinRepository;
    @Autowired
    private JoinClubRepository joinClubRepository;


    //가입
    @Transactional
    public Long Join(Long memberId,Long clubId,int count){
        //System.out.println("=====ServiceLine=====");
        //엔티티 생성
        Member member = memberRepository.findOne(memberId);
        Club club = clubRepository.findOne(clubId);

        //동아리 생성
        JoinClub joinClub = JoinClub.addJoinClub(club, count);//OK
        //System.out.println("넘어온인원수"+joinClub.getClub().getTotalNumber());//ok

        //동아리 가입
        Join join = Join.addJoin(member,joinClub);
        //System.out.println("설정된 이름 : " + join.getMember().getName() + " 설정된 동아리 이름 : " + join.getJoinClubs().get(0).getClub().getName()+" 수용가능 인원수 감소확인 : "+join.getJoinClubs().get(0).getClub().getTotalNumber());
        //System.out.println("Ststus확인 : " + join.getStatus());
        //저장
        joinRepository.save(join);
        joinClubRepository.save(joinClub);
        //System.out.println("===============");
        return join.getId();
    }
    
    //탈퇴
    @Transactional
    public void cancelClub(Long clubId){
        //엔티티 조회
        Join join = joinRepository.findOne(clubId);
        //동아리 탈퇴
        join.cancel();
    }
    // 모든 동아리 신청 현황 찾기
    public List<JoinClub> findJoinClub() {
        return joinRepository.findAll();
    }


    @Transactional
    public Join findOne(Long clubId){  return joinRepository.findOne(clubId);}
    public void deleteOne(Join join){ joinRepository.deleteOne(join);}
}
