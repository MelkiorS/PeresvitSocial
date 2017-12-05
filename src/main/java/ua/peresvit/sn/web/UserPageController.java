package ua.peresvit.sn.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.config.Constant;
import ua.peresvit.sn.domain.dto.UserDto;
import ua.peresvit.sn.domain.entity.*;
import ua.peresvit.sn.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping(value = "/home")
public class UserPageController {

    private final UserService userService;

    private final UserGroupService userGroupService;

    private final EventService eventService;

    private final ResourceGroupTypeService rgtService;

    private final ResourceGroupTypeChapterService rgtcService;

    private final ArticleService aService;

    private final CityService cityService;

    private final ClubService clubService;

    private final CombatArtService combatArtService;

    private final RoleService roleService;

    private final AchievementService achievementService;

    private final PostService postService;

    private final MessageService messageService;

    private final MarkService markservice;

    @Autowired
    public UserPageController(ArticleService aService, UserService userService, UserGroupService userGroupService, EventService eventService, ResourceGroupTypeService rgtService, ResourceGroupTypeChapterService rgtcService, CityService cityService, ClubService clubService, CombatArtService combatArtService, PostService postService, RoleService roleService, AchievementService achievementService, MessageService messageService, MarkService markservice) {
        this.aService = aService;
        this.userService = userService;
        this.userGroupService = userGroupService;
        this.eventService = eventService;
        this.rgtService = rgtService;
        this.rgtcService = rgtcService;
        this.cityService = cityService;
        this.clubService = clubService;
        this.combatArtService = combatArtService;
        this.postService = postService;
        this.roleService = roleService;
        this.achievementService = achievementService;
        this.messageService = messageService;
        this.markservice = markservice;
    }

    @GetMapping
    public String goToReg(Model model) {
        model.addAttribute("user", (userService.getCurrentUser() == null ? new User() : userService.getCurrentUser()) );
//        return "home"; // настроить поля
        return "test";
    }

    @RequestMapping(value = "/workField", method = RequestMethod.GET)
    public String showStartOffice(Model model) {
        User loggedUser = userService.getCurrentUser();
        String imagePath = loggedUser.getAvatarURL();
        model.addAttribute("imageAvatar", null);
        if (imagePath != null) {
            try {
                model.addAttribute("imageAvatar", Constant.encodeFileToBase64Binary(imagePath));
            } catch (IOException ex) {
            }
        }
//        Set<Mark> marks = loggedUser.getMarks();
//
//        for (Mark mark : marks) {
//            try {
//                mark.setImageURL(Constant.encodeFileToBase64Binary(mark.getImageURL()));
//            } catch (IOException ignored) {}
//        }
//        loggedUser.setMarks(marks);

        // Achievements
        Map<Long, String> achiveList = new HashMap<>();
        List<Achievement> achievements = achievementService.findByUser(loggedUser);
        for (Achievement achievement:achievements) {
            try {
//                achievement.setImageURL(Constant.encodeFileToBase64Binary(achievement.getImageURL()));
                achiveList.put(achievement.getAchievementId(), Constant.encodeFileToBase64Binary(achievement.getImageURL()));
            } catch (IOException ex){achiveList.put(achievement.getAchievementId(), null);}
        }
        model.addAttribute("achieveList", achiveList);

        List<City> cities = cityService.findAll();
        model.addAttribute("cityList", cities);          // adding list of city for select

        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubList", clubs);           // adding list of club for select

        List<CombatArt> combatArts = combatArtService.findAll();
        model.addAttribute("combatArtList", combatArts); // adding list of combatArt for select

        model.addAttribute("user", loggedUser);
        model.addAttribute("groups", rgtService.findAll());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/workField";
    }

    @RequestMapping(value = "/workField/{userId}", method = RequestMethod.GET)
    public String showUserPage(@PathVariable("userId") long userId,  Model model, Principal principal) {
        User loggedUser = userService.findOne(userId);
        Set<Mark> marks = loggedUser.getMarks();
        for (Mark mark : marks) {
            try {
                mark.setImageURL(Constant.encodeFileToBase64Binary(mark.getImageURL()));
            } catch (IOException ignored) {}
        }
        loggedUser.setMarks(marks);
        Map<Long, String> achiveList = new HashMap<>();
        List<Achievement> achievements = achievementService.findByUser(loggedUser);
        for (Achievement achievement:achievements) {
            try {
//                achievement.setImageURL(Constant.encodeFileToBase64Binary(achievement.getImageURL()));
                achiveList.put(achievement.getAchievementId(), Constant.encodeFileToBase64Binary(achievement.getImageURL()));
            } catch (IOException ex){achiveList.put(achievement.getAchievementId(), null);}
        }
        model.addAttribute("achieveList", achiveList);
        String imagePath = loggedUser.getAvatarURL();
        model.addAttribute("imageAvatar", null);
        if (imagePath != null) {
            try {
                model.addAttribute("imageAvatar", Constant.encodeFileToBase64Binary(imagePath));
            } catch (IOException ex) {
            }
        }
        model.addAttribute("user", loggedUser);
        model.addAttribute("isGuest", true);
//        model.addAttribute("achieveList", achiveList);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/workField";
    }

     // go to Edit user form
    @RequestMapping(value = "/profileEdit/{userId}", method = RequestMethod.GET)
    public String editUser(@PathVariable("userId")  long userId, Model model) {
        if (!userService.getCurrentUser().getUserId().equals(userId)) {
            return "redirect:/home/workField";
        }
        User user = userService.findOne(userId);

        List<City> cities = cityService.findAll();
        model.addAttribute("cityList", cities);          // adding list of city for select

        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubList", clubs);           // adding list of club for select

        List<CombatArt> combatArts = combatArtService.findAll();
        model.addAttribute("combatArtList", combatArts); // adding list of combatArt for select

        List<User> mentors = userService.findByRole( roleService.findOne(4l));
        model.addAttribute("mentorList", mentors);       // adding list of mentor for select

        List<Role> roleList = roleService.findAll();
        model.addAttribute("rangList", roleList);       // adding list of rang for select

        List<Mark> markList = markservice.findAll();
        model.addAttribute("markList", markList);       // adding list of marks for select

        model.addAttribute(user);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/profileEdit";
    }

    // Edit User
    @RequestMapping(value = "/profileEdit", method = RequestMethod.POST)
    public String editUser(@Valid User user, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("unreadMessages", messageService.countUnreadChats());
            return "home/profileEdit";
        }

        user.setAvatarURL(userService.saveFile(user, file));

        // check fields
        // TODO use java 8 Optional .orNull
        if (user.getCity().getCityId() == null)
            user.setCity(null);
        if (user.getClub().getClubId() == null)
            user.setClub(null);
        if (user.getCombatArt().getCombatArtId() == null)
            user.setCombatArt(null);
        if (user.getMentor().getUserId() == null)
            user.setMentor(null);

        userService.save(user);

        model.addAttribute("user", user);
        return "redirect:/home/workField";
    }

    //TODO: front - сделать авто пересчет в случае успеха - автоматом отражать новое состояние, в случае неудачи - показывать сообщение о фейле
    //TODO: back - добавить функцию удаления unassign
    //TODO: сделать рекфакторинг EventService isAssignedToMe - переделать на обращение к методу сущности. дописать тесты
    @RequestMapping(value = "/myWay/assignToMe", method = RequestMethod.POST)
    public boolean assignToMe(Model model, @RequestParam(value = "eventId") long eventid) {
        Event ev = eventService.findById(eventid);
        return eventService.assignToMe(ev);
    }

    @RequestMapping(value = "/myWay/isAssignedToMe", method = RequestMethod.GET)
    public boolean isAssignedToMe(Model model, @RequestParam(value = "eventId") long eventid) {
        Event ev = eventService.findById(eventid);
        return eventService.isAssignedToMe(ev);
    }

    @RequestMapping(value = {"/we/{groupId}", "/we"}, method = RequestMethod.GET)
    public String getWe(@PathVariable("groupId") Optional<Long> groupId, Model model){

        List<UserGroup> ug = userService.getUserGroups(userService.getCurrentUser());
        UserGroup[] uga;
        if (groupId.isPresent()) {
            uga = new UserGroup[1];
            uga[0] = userGroupService.findById(groupId.get().longValue());
        } else {
            uga = new UserGroup[ug.size()];
            uga = ug.toArray(uga);
        }

        model.addAttribute("groups", ug);
        //TODO how to do it in more correct way?
        model.addAttribute("userList", uga.length==0 ? new ArrayList<User>() : userService.getGroupsUsersWithoutCurrent(uga));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/workField_we";
    }

    // NEWS all
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String getPosts(Model model, @PageableDefault(value=3, direction = Sort.Direction.DESC, sort = "createDate") Pageable pageable){
        model.addAttribute("page", postService.findAll(pageable) );
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/workField_allPosts";
    }

    // NEWS show by userId
    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String showPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.findOne(id));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/workField_post";
    }

}
