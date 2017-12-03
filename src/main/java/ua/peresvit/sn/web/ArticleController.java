package ua.peresvit.sn.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.peresvit.sn.domain.entity.Article;
import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.service.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping(value = "/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ResourceGroupTypeService resourceGroupTypeService;
    @Autowired
    private ResourceGroupTypeChapterService chapterService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MessageService messageService;

    //go to addForm
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String goToAddForm(Model model) {
        List<ResourceGroupType> resourceGroupTypes = resourceGroupTypeService.findAll();
        List<Role> roleTypes = roleService.findAll();
        resourceGroupTypes.stream().forEach(p->p.
               setChapterList(chapterService.findAllByResourceGroupType(p)));
        model.addAttribute(new Article());   // addig empty object for post form
        model.addAttribute("roleList", roleTypes); // adding list of rang for select
        model.addAttribute("resourceGroupTypeList", resourceGroupTypes); // adding types for select
        model.addAttribute("chapters", chapterService.findAll());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/article/addArticle";
    }

    // create article )
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createArticle(@Valid Article article, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("article", article); // object is not empty
            model.addAttribute("resourceGroupTypeList", resourceGroupTypeService.findAll());
            model.addAttribute("unreadMessages", messageService.countUnreadChats());
            return "admin/article/addArticle";
        }

        long id  = article.getResourceGroupType().getResourceGroupTypeId();
        article.setResourceGroupType(resourceGroupTypeService.findOne(id));
        articleService.save(article);
        model.addAttribute("articleId", article.getArticleId());
        model.addAttribute("article", article); // adding attribute that will be alive in two requests
        return "redirect:/admin/article/"; // just not to redownload it again
    }

    // show article by userId
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public String getArticle(@PathVariable("articleId") long articleId, Model model) {
        if (!model.containsAttribute("article"))  // if it's not redirect add
            model.addAttribute("article", articleService.findOne(articleId));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/article/articleProfile";
    }

    // show all articles
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllArticles(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articleList", articles);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/article/allArticles";
    }

    // delete article
    // need to solve issue when its FK to smth !!!!
    @RequestMapping(value = "/delete/{articleId}", method = RequestMethod.GET)
    public String deleteArticle(@PathVariable("articleId") long articleId,
                                      Model model) {
        Article article = articleService.findOne(articleId);
        if (article == null) {
            // custom exception
        }
        articleService.delete(article);
        return listAllArticles(model); // after deleting show all
    }



    // edit article
    @RequestMapping(value = "/edit/{articleId}", method = RequestMethod.GET)
    public String editArticle(@PathVariable("articleId")  long articleId,
                                    Model model) {
        Article article = articleService.findOne(articleId); //taking current article
        List<ResourceGroupType> resourceGroupTypes = resourceGroupTypeService.findAll();
        List<Role> roleTypes = roleService.findAll();
        if (article == null) {
            // custom exception
        }
        model.addAttribute("article", article); // object is not empty
        model.addAttribute("roleList", roleTypes);
        model.addAttribute("resourceGroupTypeList", resourceGroupTypes);
        model.addAttribute("chapters", chapterService.findAll());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/article/addArticle";
    }
}
