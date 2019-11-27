package ru.mai.news_classifier.services;

public enum Category {
    ACCIDENTS,
    ADVERTISEMENTS,
    BEAUTIFICATION,
    CULTURE,
    INCIDENTS,
    POLITICS,
    SOCIETY;
    
    public static String getCategoryName(Category category) {
        switch (category) {
            case ACCIDENTS:
                return "ДТП";
            case ADVERTISEMENTS:
                return "Объявления";
            case BEAUTIFICATION:
                return "Благоустройство";
            case CULTURE:
                return "Культура";
            case INCIDENTS:
                return "Происшествия";
            case POLITICS:
                return "Политика";
            case SOCIETY:
                return "Общество";
            default:
                return "";
        }
    }
}
