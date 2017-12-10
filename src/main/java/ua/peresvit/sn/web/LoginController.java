package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.security.RoleEnum;
import ua.peresvit.sn.service.UserService;

import java.util.Locale;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages; // TODO Use tymeleaf messaging on html

    @RequestMapping(value = "login/success")
    public String loginSuccess() {
        if (userService.getCurrentUser().getRoles().get(0).getRoleName().equals(RoleEnum.ADMIN.getCode())) { // TODO Refactor to milty roles and use Spring sec Auth
            return "redirect:/admin";
        }
        return "redirect:/home/workField";
    }

    @RequestMapping(value = "/login/failure")
    public String loginFailure(RedirectAttributes model, Locale locale) {
        model.addFlashAttribute("message", messages.getMessage("message.loginFailure", null, locale));
        model.addAttribute(new User());
        return "redirect:/home";
    }
}
