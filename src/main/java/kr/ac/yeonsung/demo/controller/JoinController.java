package kr.ac.yeonsung.demo.controller;


import kr.ac.yeonsung.demo.domain.Join;
import kr.ac.yeonsung.demo.domain.JoinClub;
import kr.ac.yeonsung.demo.domain.Member;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.repository.JoinClubRepository;
import kr.ac.yeonsung.demo.service.ClubService;
import kr.ac.yeonsung.demo.service.JoinService;
import kr.ac.yeonsung.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;
    private final MemberService memberService;
    private final ClubService clubService;


    @GetMapping("/join")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Club> clubs = clubService.findClub();

        model.addAttribute("members",members);
        model.addAttribute("clubs",clubs);
        return "join/joinForm";
    }

    @PostMapping("/join")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("clubId") Long clubId,
                        @RequestParam("count") int count){
        joinService.Join(memberId,clubId,count);

        return "redirect:/joins";
    }

    @GetMapping("/joins")
    public String orderList(Model model){
        List<JoinClub> joinList = joinService.findAll();
        model.addAttribute("joins",joinList);
        return  "join/joinList";
    }

    @PostMapping("joins/{joinId}/cancel")
    public String cancelOrder(@PathVariable("joinId") Long joinId){
        joinService.cancelClub(joinId);
        return "redirect:/joins";
    }

}
