package kr.ac.yeonsung.demo.controller;


import kr.ac.yeonsung.demo.domain.club.Book;
import kr.ac.yeonsung.demo.domain.club.Club;
import kr.ac.yeonsung.demo.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/clubs/new")
    public String createForm(Model model){
       model.addAttribute("form",new BookForm());
       return "clubs/createClubForm";
    }

    @PostMapping("/clubs/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setTotalNumber(form.getTotalNumber());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        clubService.saveClub(book);

        return "redirect:/";
    }

    @GetMapping("/clubs")
    public String list(Model model){
        List<Club> clubs = clubService.findClub();
        model.addAttribute("clubs",clubs);
        return "clubs/clubList";
    }

    }


    @PostMapping("clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") Long clubId){
        Club club = clubService.findOne(clubId);
        clubService.deleteClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/change")
    public String changeForm(Model model,@PathVariable("clubId") Long clubId){
        Club club = clubService.findOne(clubId);
        model.addAttribute("form",club);
        return "clubs/clubInfoChange";
    }

    @PostMapping("/clubs/{clubId}/change")
    public String change(BookForm form,@PathVariable("clubId") Long clubId){
        clubService.updateClub(clubId,form.getName(),form.getTotalNumber(),
                                form.getAuthor(),form.getIsbn());
        return "redirect:/clubs";
    }
}
