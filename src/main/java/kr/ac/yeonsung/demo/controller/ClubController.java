package kr.ac.yeonsung.demo.controller;


import kr.ac.yeonsung.demo.domain.EventBoard;
import kr.ac.yeonsung.demo.domain.Member;
import kr.ac.yeonsung.demo.domain.NoticeBoard;
import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.service.ClubService;
import kr.ac.yeonsung.demo.service.JoinClubService;
import kr.ac.yeonsung.demo.service.JoinService;
import kr.ac.yeonsung.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;
    private final JoinClubService joinClubService;
    private final MemberService memberService;


    @GetMapping("/clubs/new")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();

        model.addAttribute("form", new BookForm());
        model.addAttribute("members",members);
        return "clubs/createClubForm";
    }

    @PostMapping("/clubs/new")
    public String create(BookForm form, @RequestParam("memberId") Long memberId) {
        Book book = new Book();
        Member member = memberService.findOne(memberId);
        book.setName(form.getName());
        book.setTotalNumber(form.getTotalNumber());
        book.setClubJang(member.getName());

        clubService.saveClub(book);

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
        model.addAttribute("form",club);
        model.addAttribute("members",members);
        return "clubs/clubInfoChange";
    }

    @PostMapping("/clubs/{clubId}/change")
    public String change(BookForm form,@PathVariable("clubId") Long clubId,@RequestParam("memberId") Long memberId){
        Member member = memberService.findOne(memberId);
        clubService.updateClub(clubId,form.getName(),form.getTotalNumber(),
                member.getName());
        return "redirect:/clubs";
    }


}
