package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.EnumResourceType;
import ua.peresvit.sn.domain.entity.Resource;
import ua.peresvit.sn.domain.entity.ResourceType;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.ResourceGroupService;
import ua.peresvit.sn.service.ResourceService;
import ua.peresvit.sn.service.ResourceTypeService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/resource")
@MultipartConfig(fileSizeThreshold = 1024*1024*20)
public class ResourceController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ResourceGroupService resourceGroupService;
	@Autowired
	private ResourceTypeService resourceTypeService;
	@Autowired
	private MessageService messageService;

	//go to manage page
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public String goToManagement(Model model) {
		return "admin/resource/resourceManagement";
	}

	//go to addForm
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String goToAddForm(Model model) {
		model.addAttribute("resource", new Resource());
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resource/addResource";
	}
	
	//go to TEXT PHOTO addForm
	@RequestMapping(value = "/add/group/{resourceGroupId}", method = RequestMethod.GET)
	public String goToAddForm(@PathVariable("resourceGroupId") long resourceGroupId, Model model) {
		Resource resource = new Resource();
		resource.setResourceGroup(resourceGroupService.findOne(resourceGroupId));
		model.addAttribute("resource", resource);
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resource/addResource";
	}

	//go to  VIDEO addForm
	@RequestMapping(value = "/addVideo/group/{resourceGroupId}", method = RequestMethod.GET)
	public String goToAddVideoForm(@PathVariable("resourceGroupId") long resourceGroupId, Model model) {
		Resource resource = new Resource();
		resource.setResourceGroup(resourceGroupService.findOne(resourceGroupId));
		model.addAttribute("resource", resource);
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resource/addVideoResource";
	}
	
	 // create resource
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String createResource(Resource resource, RedirectAttributes model, @RequestParam("file") MultipartFile file) {
		resource.setUrl(resourceService.saveFile(resource, file));
		resourceService.save(resource);
		model.addAttribute("resourceId", resource.getResourceId());
		model.addFlashAttribute("resource", resource);
		return "redirect:/admin/resource/";
	}

	// create video resource
	@RequestMapping(value = "/video", method = RequestMethod.POST)
	public String createVideoResource(Resource resource, RedirectAttributes model) {
		ResourceType resourceType = resourceTypeService.findOne(EnumResourceType.VIDEO.getValue());
		resource.setResourceType(resourceType);
		resourceService.save(resource);
		model.addAttribute("resourceId", resource.getResourceId());
		model.addFlashAttribute("resource", resource);
		return "redirect:/admin/resource/{resourceId}";
	}
	
	// show resource by id
	@RequestMapping(value = "/{resourceId}", method = RequestMethod.GET)
	public String getResource(@PathVariable("resourceId") long resourceId, Model model) {
		if (!model.containsAttribute("resource")) 
			model.addAttribute("resource", resourceService.findOne(resourceId));
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resource/resourceProfile";
	}

	// show all resources
	@RequestMapping(value = "/group/{resourceGroupId}", method = RequestMethod.GET)
	public String listAllResources(@PathVariable("resourceGroupId") long resourceGroupId, Model model) {
		Collection<Resource> resources = resourceGroupService.findOne(resourceGroupId).getResourceCollection();
		model.addAttribute("resourceList", resources);
		model.addAttribute("resource", new Resource());
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resource/allResources";
	}
	
	// show all resources
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listAllResources(Model model) {
		List<Resource> resources = resourceService.findAll();
		model.addAttribute("resourceList", resources);
		model.addAttribute("resource", new Resource());
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "admin/resource/allResources";
	}
	
	// delete resource
	// need to solve issue when its FK to smth !!!!
	@RequestMapping(value = "/delete/{resourceId}", method = RequestMethod.GET)
    public String deleteResource(@PathVariable("resourceId") long resourceId,
    		Model model) {
        Resource resource = resourceService.findOne(resourceId);
        long resourceGroupId = resource.getResourceGroup().getResourceGroupId();
        if (resource == null) {
           // custom exception
        }
        resourceService.delete(resource);
        return listAllResources(resourceGroupId,model);
    }
	
	// edit resource
	@RequestMapping(value = "/edit/{resourceId}", method = RequestMethod.GET)
    public String editResource(@PathVariable("resourceId")  long resourceId,
    		Model model) {
        Resource resource = resourceService.findOne(resourceId);
        if (resource == null) {
           // custom exception
        }
        model.addAttribute("resource", resource);
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/resource/addResource";
    }	
	
	 // download resource file
	@RequestMapping(value = "/download/{resourceId}", method = RequestMethod.GET)
	public void downloadResourceFile(@PathVariable("resourceId") long resourceId, HttpServletResponse response, Model model) {
		Resource resource = resourceService.findOne(resourceId);

		if (resource.getUrl() != null) {
			File downloadingFile = new File(resource.getUrl());
			Path filePath = Paths.get(downloadingFile.getPath());
			if (downloadingFile.exists()) {
				try {
					response.setContentType(Files.probeContentType(filePath));
					response.addHeader("Content-Disposition", "attachment; filename=" + downloadingFile.getName());

					Files.copy(filePath.toAbsolutePath(), response.getOutputStream());
					response.getOutputStream().flush();
				} catch (IOException ex) {
					// TODO use custom exception !!!  RuntimeException only for dummy
					throw new RuntimeException("Увага! Помилка запису файла!");
				}
			}
			else
				throw new RuntimeException("Увага! Неможливо завантажити файл. Файл не існує!");
		}
		else
			throw new RuntimeException("Увага! URL пустий");
	}

	// TODO use common (for all application) exception handler
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("type", "warning");
		model.addAttribute("unreadMessages", messageService.countUnreadChats());
		return "error/general";
	}

}
