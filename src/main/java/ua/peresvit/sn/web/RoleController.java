package ua.peresvit.sn.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.RoleService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private MessageService messageService;

	//go to manage page
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public String goToManagement(Model model) {
		return "admin/role/roleManagement";
	}
	//go to addForm
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String goToAddForm(Model model) {
		model.addAttribute(new Role());
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/role/addRole";
	}
	
	 // create role
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String createRole(Role role, RedirectAttributes model) {
		roleService.save(role);
		model.addAttribute("RoleId", role.getRoleId());
		model.addFlashAttribute("role", role);
		return "redirect:/admin/role/";
	}
	
	// show role by id
	@RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
	public String getRole(@PathVariable("roleId") long RoleId, Model model) {
		if (!model.containsAttribute("role"))
			model.addAttribute("role", roleService.findOne(RoleId));
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/role/roleProfile";
	}

	// show all Roles
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listAllRoles(Model model) {
		List<Role> roles = roleService.findAll();
		model.addAttribute("roleList", roles);
		model.addAttribute("role", new Role());
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/role/allRoles";
	}
	
	// delete role
	// need to solve issue when its FK to smth !!!!
	@RequestMapping(value = "/delete/{roleId}", method = RequestMethod.GET)
    public String deleteRole(@PathVariable("roleId") long RoleId,
    		Model model) {
        Role role = roleService.findOne(RoleId);
        if (role == null) {
           // custom exception
        }
        roleService.delete(role);
        return listAllRoles(model);
    }
	
	// edit role
	@RequestMapping(value = "/edit/{roleId}", method = RequestMethod.GET)
    public String editRole(@PathVariable("roleId")  long RoleId,
    		Model model) {
        Role role = roleService.findOne(RoleId);
        if (role == null) {
           // custom exception
        }
        model.addAttribute("role", role);
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/role/addRole";
    }
    //show users
	@RequestMapping(value="/users/{roleId}", method = RequestMethod.GET)
	public String showUsers(@PathVariable("roleId") long roleId, Model model) {

		Role role = roleService.findOne(roleId);
		model.addAttribute("role", role);
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/role/showUsers";
	}
	
}
