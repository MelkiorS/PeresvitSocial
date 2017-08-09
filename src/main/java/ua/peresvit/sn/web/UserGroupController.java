package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.domain.entity.UserGroup;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.UserGroupService;
import ua.peresvit.sn.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin/userGroup")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "users", new CustomCollectionEditor(Set.class) {

            protected Object convertElement(Object element) {
                if (element instanceof String) {
                    User user = userService.findOne(Long.parseLong((String)element));
                    return user;
                }
                return null;
            }
        });
    }

    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String goToManagement(Model model) {
        return "admin/userGroup/userGroupManagement";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllUsers(Model model) {
        List<UserGroup> userGroups = userGroupService.findAll();
        model.addAttribute("userGroupList", userGroups);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/userGroup/allUserGroups";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String goToAddForm(Model model) {
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("userGroup", new UserGroup());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/userGroup/addUserGroup";
    }

    @RequestMapping(value="/edit/{userGroupId}", method = RequestMethod.GET)
    public String getUserGroup(@PathVariable(value="userGroupId") long userGroupId, Model model) {
        model.addAttribute("userList", userService.findAll());
        UserGroup ug = userGroupService.findById(userGroupId);
        model.addAttribute("userGroup", ug);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/userGroup/addUserGroup";
    }

    //TODO: rename add to edit
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createUser(UserGroup userGroup, Model model) {
        userGroupService.create(userGroup);
        return "redirect:/admin/userGroup/";
    }

    //TODO: Make DELETE METHOD
    @RequestMapping(value = "/delete/{userGroupId}", method = RequestMethod.GET)
    public String createUser(@PathVariable(value="userGroupId") long userGroupId, Model model) {
        userGroupService.delete(userGroupService.findById(userGroupId));
        return "redirect:/admin/userGroup/";
    }

}
