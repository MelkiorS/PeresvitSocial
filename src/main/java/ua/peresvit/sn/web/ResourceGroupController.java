package ua.peresvit.sn.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.ResourceGroup;
import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.ResourceGroupService;
import ua.peresvit.sn.service.ResourceGroupTypeService;
import ua.peresvit.sn.service.RoleService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/resourceGroup")
public class ResourceGroupController {
	
	@Autowired
	private ResourceGroupService resourceGroupService;
	@Autowired
	private ResourceGroupTypeService resourceGroupTypeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MessageService messageService;

	//go to manage page
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public String goToManagement(Model model) {
		return "admin/resourceGroup/resourceGroupManagement";
	}
	//go to addForm
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String goToAddForm(Model model) {
		List<ResourceGroupType> resourceGroupTypes = resourceGroupTypeService.findAll();
		List<Role> rangTypes = roleService.findAll();
		model.addAttribute(new ResourceGroup());   // addig empty object for post form
		model.addAttribute("rangList", rangTypes); // adding list of rang for select
		model.addAttribute("resourceGroupTypeList", resourceGroupTypes); // adding types for select
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resourceGroup/addResourceGroup";
	}
	
	 // create resourceGroup )
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String createResourceGroup(ResourceGroup resourceGroup, RedirectAttributes model) {
		resourceGroupService.save(resourceGroup);
		model.addAttribute("resourceGroupId", resourceGroup.getResourceGroupId());
		model.addFlashAttribute("resourceGroup", resourceGroup); // adding attribute that will be alive in two requests
		return "redirect:/admin/resourceGroup/"; // just not to redownload it again
	}
	
	// show resourceGroup by userId
	@RequestMapping(value = "/{resourceGroupId}", method = RequestMethod.GET)
	public String getResourceGroup(@PathVariable("resourceGroupId") long resourceGroupId, Model model) {
		if (!model.containsAttribute("resourceGroup"))  // if it's not redirect add
			model.addAttribute("resourceGroup", resourceGroupService.findOne(resourceGroupId));
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resourceGroup/resourceGroupProfile";
	}

	// show all resourceGroups
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listAllResourceGroups(Model model) {
		List<ResourceGroup> resourceGroups = resourceGroupService.findAll();
		model.addAttribute("resourceGroupList", resourceGroups);
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resourceGroup/allResourceGroups";
	}
	
	// delete resourceGroup
	// need to solve issue when its FK to smth !!!!
	@RequestMapping(value = "/delete/{resourceGroupId}", method = RequestMethod.GET)
    public String deleteResourceGroup(@PathVariable("resourceGroupId") long resourceGroupId,
    		Model model) {
        ResourceGroup resourceGroup = resourceGroupService.findOne(resourceGroupId);
        if (resourceGroup == null) {
           // custom exception
        }
        resourceGroupService.delete(resourceGroup);
        return listAllResourceGroups(model); // after deleting show all
    }
	
	// edit resourceGroup
	@RequestMapping(value = "/edit/{resourceGroupId}", method = RequestMethod.GET)
    public String editResourceGroup(@PathVariable("resourceGroupId")  long resourceGroupId,
    		Model model) {
        ResourceGroup resourceGroup = resourceGroupService.findOne(resourceGroupId); //taking current group
        List<ResourceGroupType> resourceGroupTypes = resourceGroupTypeService.findAll();
        List<Role> rangTypes = roleService.findAll();
        if (resourceGroup == null) {
           // custom exception
        }
        model.addAttribute("resourceGroup", resourceGroup); // object is not empty
        model.addAttribute("rangList", rangTypes);
		model.addAttribute("resourceGroupTypeList", resourceGroupTypes);
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/resourceGroup/addResourceGroup"; // sending to addForm
    }
}
