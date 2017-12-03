package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.*;
import ua.peresvit.sn.service.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController {

	@Autowired
	private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

	@Autowired
	private RoleService rangService;

    @Autowired
    private CombatArtService combatArtService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private MarkService markService;

    @Autowired
    private MessageService messageService;

    //go to manage page
    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String goToManagement(Model model) {
        return "admin/user/userManagement";
    }
    
    //go to addForm
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String goToAddForm(Model model) {
    	User user = new User();

//        userService.initializeUserInfo(user);

        initializeModelLists(model);
        model.addAttribute(user);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        
        return "admin/user/addUser";
    }

    // create user
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createUser(User user, RedirectAttributes model, @RequestParam("file") MultipartFile file) {
    	   
    	user.setAvatarURL(userService.saveFile(user, file));

        // check fields
        // TODO use java 8 advantage - Optional .orNull
        if (user.getCity().getCityId() == null)
            user.setCity(null);
        if (user.getClub().getClubId() == null)
            user.setClub(null);
        if (user.getCombatArt().getCombatArtId() == null)
            user.setCombatArt(null);
        if (user.getMentor().getUserId() == null)
            user.setMentor(null);

        userService.save(user);

        model.addAttribute("userId", user.getUserId());
        model.addFlashAttribute("user", user);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "redirect:/admin/user/";
    }

    // show user by userId
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String getUser(@PathVariable("userId") long userId, Model model) {
        if (!model.containsAttribute("user"))
            model.addAttribute("user", userService.findOne(userId));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/user/userProfile";
    }

    // show all users
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("userList", users);
        model.addAttribute("user", new User());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/user/allUsers";
    }

    // deactivate user
    // need to solve issue when its FK to smth !!!!
    @RequestMapping(value = "/deactivate/{userId}", method = RequestMethod.GET)
    public String deactivateUser(@PathVariable("userId") long userId,
                                 Model model) {
        User user = userService.findOne(userId);
        if (user == null) {
            // custom exception
            return listAllUsers(model);
        }
        userService.deactivateUser(user);
        return listAllUsers(model);
    }

    // activate user
    // need to solve issue when its FK to smth !!!!
    @RequestMapping(value = "/activate/{userId}", method = RequestMethod.GET)
    public String activateUser(@PathVariable("userId") long userId,
                             Model model) {
        User user = userService.findOne(userId);
        if (user == null) {
            // custom exception
            return listAllUsers(model);
        }
        userService.activateUser(user);
        return listAllUsers(model);
    }

    // edit user
    @RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
    public String editUser(@PathVariable("userId")  long userId,
                           Model model) {
        User user = userService.findOne(userId);
        if (user == null) {
            // custom exception
        }

        initializeModelLists(model);
        model.addAttribute("user", user);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/user/addUser";
    }

    // init Lists of entities for model
    private void initializeModelLists(Model model){

        List<City> cities = cityService.findAll();
        model.addAttribute("cityList", cities);          // adding list of city for select

        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubList", clubs);           // adding list of club for select

        List<Mark> marks = markService.findAll();
        model.addAttribute("markList", marks);           // adding list of mark for select

        List<CombatArt> combatArts = combatArtService.findAll();
        model.addAttribute("combatArtList", combatArts); // adding list of combatArt for select

        List<User> mentors = userService.findByRole(rangService.findOne(4l));
        model.addAttribute("mentorList", mentors);       // adding list of mentor for select

        List<Role> rangTypes = rangService.findAll();
        model.addAttribute("rangList", rangTypes);       // adding list of rang for select
    }


    @RequestMapping(value = "/groupsUsers", method = RequestMethod.GET)
    public List<User> getGroupsUsers(String[] sug){
        UserGroup[] ug = new UserGroup[sug.length];
        for (int i=0;i<sug.length;i++) ug[i] = userGroupService.findById(Long.parseLong(sug[i]));
        return userService.getGroupsUsers(ug);
    }

    @RequestMapping(value = "/userGroups", method = RequestMethod.GET)
    public List<UserGroup> getUserGroups(@RequestParam("userId") Long userId){
        return userService.getUserGroups(userService.findOne(userId));
    }

}