package ru.mai.news_classifier.controllers;

import ru.mai.news_classifier.forms.ClassifierForm;
import ru.mai.news_classifier.services.ClassifierService;
import ru.mai.news_classifier.services.Category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClassifierController {
    
    private ClassifierService classifier;
    
    public ClassifierController() throws Exception {
        classifier = new ClassifierService();
    }
    
    @RequestMapping(value = "/classifier", method = RequestMethod.GET)
    public String classifyForm(Model model) {
        model.addAttribute("classifierForm", new ClassifierForm());
        return "classifierForm";
    }
    
    @RequestMapping(value = "/classifier", method = RequestMethod.POST)
    public String classifySubmit(@ModelAttribute ClassifierForm classifierForm, Model model) throws Exception {
        model.addAttribute("category", Category.getCategoryName(classifier.getCategory(classifierForm.getText())));
        return "classifierCategoryForm";
    }
}
