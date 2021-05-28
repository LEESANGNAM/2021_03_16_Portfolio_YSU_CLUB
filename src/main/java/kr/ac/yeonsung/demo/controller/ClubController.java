package kr.ac.yeonsung.demo.controller;


import kr.ac.yeonsung.demo.domain.Member;
import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.form.BookForm;
import kr.ac.yeonsung.demo.service.ClubService;
import kr.ac.yeonsung.demo.service.JoinClubService;
import kr.ac.yeonsung.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClubController {
    private final ClubService clubService;
    private final JoinClubService joinClubService;
    private final MemberService memberService;
    private Object Null;


    @GetMapping("/clubs/new")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        try {
            members.get(0).getId();
        }catch (Exception e) {
            return "clubs/clubListError";
        }
        model.addAttribute("bookForm", new BookForm());
        model.addAttribute("members",members);
        return "clubs/createClubForm";
    }

    @PostMapping("/clubs/new")
    public String create(@Valid BookForm bookForm,BindingResult result, @RequestParam("memberId") Long memberId,Model model) {//BindingResult form에서 오류가 있을시 오류가 result에 담김
        if(result.hasErrors()){
            List<Member> members = memberService.findMembers();
            model.addAttribute("members",members);
            return "clubs/createClubForm";
        }
        Book book = new Book();
        Member member = memberService.findOne(memberId);
        book.setName(bookForm.getName());
        book.setTotalNumber(bookForm.getTotalNumber());
        book.setClubJang(member.getName());
        try {
            clubService.saveClub(book);
        }catch (IllegalStateException e){
            String message = e.getMessage();
            log.info(message);
            model.addAttribute("message",message);
            List<Member> members = memberService.findMembers();
            model.addAttribute("members",members);

            return "clubs/createClubForm";
        }


        return "redirect:/clubs";
    }

    // 페이징
    @GetMapping("/clubs")
    public String list(@PageableDefault Pageable pageable, Model model){
        Page<Club> clubList = clubService.findAll(pageable);
        model.addAttribute("clubList", clubList);

        List<Club> getClubList = clubList.getContent();
        model.addAttribute("getClubList",getClubList);//list size가져옴, list size확인용
        return "/clubs/clubList";
    }

    @PostMapping("clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") Long clubId){
        Club club = clubService.findOne(clubId);
        joinClubService.chageStatus(club);
        clubService.deleteClub(clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/change")
    public String changeForm(Model model,@PathVariable("clubId") Long clubId){
        List<Member> members = memberService.findMembers();

        Club club = clubService.findOne(clubId);
        model.addAttribute("bookForm",club);
        model.addAttribute("members",members);
        return "clubs/clubInfoChange";
    }

    @PostMapping("/clubs/{clubId}/change")
    public String change(@Valid BookForm bookForm, BindingResult result,@PathVariable("clubId") Long clubId,@RequestParam("memberId") Long memberId,Model model){
        if(result.hasErrors()){
            List<Member> members = memberService.findMembers();
            model.addAttribute("members",members);
            return "clubs/clubInfoChange";
        }

       try {
           Member member = memberService.findOne(memberId);
           clubService.updateClub(clubId, bookForm.getName(), bookForm.getTotalNumber(),
                   member.getName());
       }catch (IllegalStateException e){
           String message = e.getMessage();
           log.info(message);
           model.addAttribute("message",message);
           List<Member> members = memberService.findMembers();
           model.addAttribute("members",members);

           return "clubs/clubInfoChange";
       }
        return "redirect:/clubs";
    }


}
