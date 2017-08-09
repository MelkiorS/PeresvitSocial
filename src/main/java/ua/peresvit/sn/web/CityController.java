package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.City;
import ua.peresvit.sn.service.CityService;
import ua.peresvit.sn.service.MessageService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/city")
public class CityController {

    @Autowired
    private CityService cityService;
    @Autowired
    private MessageService messageService;

    //go to manage page
    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String goToManagement(Model model) {
        return "admin/city/cityManagement";
    }

    //go to addForm
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String goToAddForm(Model model) {
        City city = new City();

        model.addAttribute(city);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());

        return "admin/city/addCity";
    }

    // create city
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createCity(City city, RedirectAttributes model) {

        cityService.save(city);

        model.addAttribute("cityId", city.getCityId());
        model.addFlashAttribute("city", city);

        return "redirect:/admin/city/";
    }

    // show City by id
    @RequestMapping(value = "/{cityId}", method = RequestMethod.GET)
    public String geCity(@PathVariable("cityId") long cityId, Model model) {
        if (!model.containsAttribute("cityId"))
            model.addAttribute("cityId", cityService.findOne(cityId));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/city/cityProfile";
    }

    // show all citys
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllUsers(Model model) {
        List<City> cities = cityService.findAll();
        model.addAttribute("cityList", cities);
        model.addAttribute("city", new City());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/city/allCities";
    }

    // edit city
    @RequestMapping(value = "/edit/{cityId}", method = RequestMethod.GET)
    public String editCity(@PathVariable("cityId")  long cityId,
                           Model model) {
        City city = cityService.findOne(cityId);
        model.addAttribute("city", city);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/city/addCity";
    }

}
