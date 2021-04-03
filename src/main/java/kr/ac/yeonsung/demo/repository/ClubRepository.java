package kr.ac.yeonsung.demo.repository;

import kr.ac.yeonsung.demo.domain.club.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClubRepository {

    private final EntityManager em;

    /**
     * 생성
     */
    public void save(Club club){
        if (club.getId() ==null){
            em.persist(club);
        }else {
            em.merge(club);
        }
    }
    /**
     * 하나조회
     */
    public Club findOne(Long id){
        return em.find(Club.class,id);
    }
    /**
     * 모두조회
     */

    public List<Club> findAll(){
        return em.createQuery("select c from Club c",Club.class).getResultList();
    }

    /**
     * 삭제
     */

    public void deleteOne(Club club){
        em.remove(club);
    }



}
