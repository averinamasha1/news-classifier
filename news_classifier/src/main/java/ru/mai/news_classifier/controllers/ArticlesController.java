package ru.mai.news_classifier.controllers;

import ru.mai.news_classifier.services.ArticlesService;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticlesController {

    private ArticlesService articlesService;

    public ArticlesController() {
        this.articlesService = new ArticlesService();
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String getArticlesCategories(Model model) {
        try {
            model.addAttribute("categories", articlesService.getCategories());
            model.addAttribute("articlesCount", articlesService.getAllArticlesCount());
            return "articlesCategoriesForm";
        } catch (Exception ex) {
            System.out.println("Произошла ошибка при подсчете всех статей");
            return "serviceErrorForm";
        }
    }

    @RequestMapping(value = "/articles/{categoryKey}", method = RequestMethod.GET)
    public String getArticlesByCategory(Model model, @PathVariable("categoryKey") String categoryKey) {
        model.addAttribute("categoryKey", categoryKey);
        model.addAttribute("categoryValue", articlesService.getCategories().get(categoryKey));
        model.addAttribute("articles", articlesService.getArticlesByCategory(categoryKey));
        return "articlesByCategoryForm";
    }

    @RequestMapping(value="/articles/{categoryKey}/download/{filename}", method=RequestMethod.GET)
    @ResponseBody
    public FileSystemResource downloadFile(
            @PathVariable("categoryKey") String categoryKey,
            @PathVariable("filename") String filename) {
        return new FileSystemResource(articlesService.getArticleFileByCategoryAndFilename(categoryKey, filename));
    }
}
