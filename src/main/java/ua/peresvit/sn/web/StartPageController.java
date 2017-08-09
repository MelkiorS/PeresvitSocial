package ua.peresvit.sn.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartPageController {
        @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "redirect:/home";
    }

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String goToAdminPage() {
		return "redirect:admin/user/";
	}

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String redirectRequestToRegistrationPage() {
        return "redirect:/registration";
    }
}
