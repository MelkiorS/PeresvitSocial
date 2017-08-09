package ua.peresvit.sn.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.domain.entity.ResourceGroupTypeChapter;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.ResourceGroupTypeChapterService;
import ua.peresvit.sn.service.ResourceGroupTypeService;
import ua.peresvit.sn.service.RoleService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/chapter")
public class ResourceGroupTypeChapterController {
    @Autowired
    private ResourceGroupTypeChapterService resourceGroupTypeChapterService;
    @Autowired
    private ResourceGroupTypeService resourceGroupTypeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MessageService messageService;

    //go to manage pagel
    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String goToManagement(Model model) {
        return "admin/chapter/chapterManagement";
    }
    //go to addForm
    @RequestMapping(value = "/addChapter", method = RequestMethod.GET)
    public String goToAddForm(Model model) {
        List<ResourceGroupType> resourceGroupTypes = resourceGroupTypeService.findAll();
        ResourceGroupTypeChapter chapter = new ResourceGroupTypeChapter();
        model.addAttribute("chapter", chapter);   // addig empty object for post form
        model.addAttribute("resourceGroupTypeList", resourceGroupTypes); // adding types for select
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/chapter/addChapter";
    }

    // create chapter )
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createChapter(ResourceGroupTypeChapter chapter, RedirectAttributes model) {
        resourceGroupTypeChapterService.save(chapter);
        model.addFlashAttribute("chapter", chapter); // adding attribute that will be alive in two requests
        return "redirect:/admin/chapter/";
    }

    // show all chapters
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllChapters(Model model) {
        List<ResourceGroupTypeChapter> chapters = resourceGroupTypeChapterService.findAll();
        model.addAttribute("chapterList", chapters);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/chapter/allChapters";
    }

    // delete chapter
    // need to solve issue when its FK to smth !!!!
    @RequestMapping(value = "/delete/{chapterId}", method = RequestMethod.GET)
    public String deleteChapter(@PathVariable("chapterId") long chapterId,
                                Model model) {
        ResourceGroupTypeChapter chapter = resourceGroupTypeChapterService.findOne(chapterId);
        if (chapter == null) {
            // custom exception
        }
        resourceGroupTypeChapterService.delete(chapter);
        return listAllChapters(model); // after deleting show all
    }



    // edit chapter
    @RequestMapping(value = "/edit/{chapterId}", method = RequestMethod.GET)
    public String editChapter(@PathVariable("chapterId")  long chapterId,
                              Model model) {
        ResourceGroupTypeChapter chapter = resourceGroupTypeChapterService.findOne(chapterId); //taking current chapter
        List<ResourceGroupType> resourceGroupTypes = resourceGroupTypeService.findAll();
        List<Role> rangTypes = roleService.findAll();
        if (chapter == null) {
            // custom exception
        }
        model.addAttribute("chapter", chapter); // object is not empty
        model.addAttribute("rangList", rangTypes);
        model.addAttribute("resourceGroupTypeList", resourceGroupTypes);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/chapter/addChapter"; // sending to addForm
    }
}
