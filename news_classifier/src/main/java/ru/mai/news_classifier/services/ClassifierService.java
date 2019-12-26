package ru.mai.news_classifier.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import org.springframework.stereotype.Service;

@Service
public class ClassifierService {
    private Classifier classifier;
    private HashMap<String, Integer> dictionaryWords;

    public ClassifierService() throws Exception {
        classifier = (Classifier) SerializationHelper.read(new FileInputStream("data/oka_fm_data.model"));
        fillWords();
    }

    public Category getCategory(String text) throws Exception {
        return Category.values()[(int) classifier.classifyInstance(getInstancesFromText(getWordsArrayFromText(text)).firstInstance())];
    }

    private void fillWords() throws Exception {
        dictionaryWords = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/oka_fm_dictionary.txt"), StandardCharsets.UTF_8));
        int wordCounter = 0;
        String line = reader.readLine();
        while (line != null) {
            dictionaryWords.put(line, wordCounter);
            ++wordCounter;
            line = reader.readLine();
        }
    }

    private Instances getInstancesFromText(ArrayList<String> words) {
        double[] vector = getVectorFromWordsArray(words);

        ArrayList<Attribute> attributes = getAttributesFromDictionary();
        Attribute category = getCategoryAttribute();
        attributes.add(category);

        Instances dateModel;
        dateModel = new Instances("text", attributes, 0);
        dateModel.setClass(category);
        dateModel.add(new DenseInstance(1.0, vector));
        dateModel.instance(0).setClassMissing();
        return dateModel;
    }

    private ArrayList<String> getWordsArrayFromText(String text) {
        String[] uncheckedWords = text.split("\\s+");

        ArrayList<String> words = new ArrayList<>();
        for (String uncheckedWord : uncheckedWords) {
            String checkedWord = uncheckedWord.toLowerCase().replaceAll("[.,/#!?$%^&*;:{}=_`~()]", "");
            if (!checkedWord.isEmpty()) {
                words.add(checkedWord);
            }
        }
        return words;
    }

    private ArrayList<Attribute> getAttributesFromDictionary() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < dictionaryWords.size(); ++i) {
            attributes.add(new Attribute("attribute" + (i + 1)));
        }
        return attributes;
    }

    private Attribute getCategoryAttribute() {
        ArrayList<String> categories = new ArrayList<>();
        for (int i = 0; i < Category.values().length; ++i) {
            categories.add(Category.values()[i].toString());
        }
        return new Attribute("category", categories);
    }

    private double[] getVectorFromWordsArray(ArrayList<String> words) {
        double[] vector = new double[dictionaryWords.size() + 1];

        Arrays.fill(vector, 0);

        for (String word : words) {
            Integer attrIndex = dictionaryWords.get(word);
            if (attrIndex != null) {
                vector[attrIndex] = vector[attrIndex] + 1;
            }
        }
        return vector;
    }
}
