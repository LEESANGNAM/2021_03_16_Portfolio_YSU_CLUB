package kr.ac.yeonsung.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import kr.ac.yeonsung.demo.controller.NoticeBoardForm;
import kr.ac.yeonsung.demo.domain.NoticeBoard;
import kr.ac.yeonsung.demo.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;

    //게시글작성
    @Transactional
    public Long write(NoticeBoardForm form){
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setTitle(form.getTitle());
        noticeBoard.setContent(form.getContent());
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        noticeBoard.setWriteDate(dateTime);
        noticeBoardRepository.save(noticeBoard);
        return noticeBoard.getId();
    }

    //게시글 단건 조회
    public NoticeBoard findOne(Long boardId){
        return noticeBoardRepository.findOne(boardId);
    }
   
    //게시글 삭제
    @Transactional
    public void delete(Long noticeId){
        NoticeBoard notice = noticeBoardRepository.findOne(noticeId);//게시글의 id값으로 해당 게시물을 가지고옴
        noticeBoardRepository.delete(notice);
    }

    //게시글 수정
    @Transactional
    public void update(Long id, NoticeBoardForm form){
        NoticeBoard updateNotice = findOne(id);//id로 이전 작성한 게시물을 가져옴
        updateNotice.setTitle(form.getTitle());//값을 update
        updateNotice.setContent(form.getContent());
        //수정한 날짜를 저장
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        updateNotice.setUpdateDate(dateTime);
        noticeBoardRepository.update(updateNotice);
    }

    //게시글 전체 조회
    public List<NoticeBoard> findAll(){ return noticeBoardRepository.findAll();}
}