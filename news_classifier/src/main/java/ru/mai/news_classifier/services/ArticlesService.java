package ru.mai.news_classifier.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ArticlesService {

    public Map<String, String> getCategories() {
        Map<String, String> categories = new HashMap<>();

        for (Category category : Category.values()) {
            categories.put(category.toString(), Category.getCategoryName(category));
        }
        return categories;
    }

    public int getAllArticlesCount() throws IOException {
        return (int) Files.walk(Paths.get("News"))
                .parallel()
                .filter(p -> !p.toFile().isDirectory())
                .count();
    }

    public ArrayList<String> getArticlesByCategory(String category) {
        File categoryArticles = new File("News" + "//" + category);
        File[] articles = categoryArticles.listFiles();

        ArrayList<String> fileNames = new ArrayList<>();
        if (articles != null) {
            for (File article : articles) {
                fileNames.add(article.getName());
            }
        }
        return fileNames;
    }

    public File getArticleFileByCategoryAndFilename(String category, String filename) {
        return new File("News" + "//" + category + "//" + filename);
    }
}
