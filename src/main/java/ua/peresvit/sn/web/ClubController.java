package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.Club;
import ua.peresvit.sn.service.ClubService;
import ua.peresvit.sn.service.MessageService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/club")
public class ClubController {

    @Autowired
    private ClubService clubService;
    @Autowired
    private MessageService messageService;

    //go to manage page
    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String goToManagement(Model model) {
        return "admin/club/clubManagement";
    }

    //go to addForm
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String goToAddForm(Model model) {
        Club club = new Club();

        model.addAttribute(club);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());

        return "admin/club/addClub";
    }

    // create club
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createClub(Club club, RedirectAttributes model) {

        clubService.save(club);

        model.addAttribute("clubId", club.getClubId());
        model.addFlashAttribute("club", club);

        return "redirect:/admin/club/";
    }

    // show Club by id
    @RequestMapping(value = "/{clubId}", method = RequestMethod.GET)
    public String geClub(@PathVariable("clubId") long clubId, Model model) {
        if (!model.containsAttribute("clubId"))
            model.addAttribute("clubId", clubService.findOne(clubId));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/club/clubProfile";
    }

    // show all clubs
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllUsers(Model model) {
        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubList", clubs);
        model.addAttribute("club", new Club());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/club/allClubs";
    }

    // edit club
    @RequestMapping(value = "/edit/{clubId}", method = RequestMethod.GET)
    public String editClub(@PathVariable("clubId")  long clubId,
                           Model model) {
        Club club = clubService.findOne(clubId);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        model.addAttribute("club", club);
        return "admin/club/addClub";
    }

}
