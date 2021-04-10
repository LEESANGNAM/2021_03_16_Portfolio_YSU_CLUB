package kr.ac.yeonsung.demo.repository;

import kr.ac.yeonsung.demo.domain.Join;
import kr.ac.yeonsung.demo.domain.JoinClub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class JoinClubRepository {

    private final EntityManager em;
    private JoinClub joinClub;

    public void save(JoinClub joinClub){
        em.persist(joinClub);
    }

    //동아리 조회
    public JoinClub findOne(Long id){
        return em.find(JoinClub.class,id);
    }

    public void deleteOne(JoinClub joinClub) {
        em.remove(joinClub);
    }
}

