package com.deepakallcode.codesnippetmanager.models.SnippetAPIModel;

public class SnippetAPIResponseSafetyRating {
    private String category;
    private String probability;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }
}
