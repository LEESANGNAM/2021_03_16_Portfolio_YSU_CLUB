package kr.ac.yeonsung.demo.repository;

import kr.ac.yeonsung.demo.domain.Join;
import kr.ac.yeonsung.demo.domain.JoinClub;
import kr.ac.yeonsung.demo.domain.club.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JoinRepository {

    private final EntityManager em;
    private JoinClub joinClub;

    //동아리 신청
    public void save(Join join){
        em.persist(join);
    }

    //동아리 조회
    public Join findOne(Long id){
        return em.find(Join.class,id);
    }
    // 모든 동아리 신청 현황 찾기(쿼리)
    public List<JoinClub> findAll(){return em.createQuery("select j from JoinClub j",JoinClub.class).getResultList();}


    public void deleteOne(Join join) {
        em.remove(join);
    }
}