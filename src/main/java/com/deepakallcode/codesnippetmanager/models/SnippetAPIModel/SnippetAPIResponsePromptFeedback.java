package com.deepakallcode.codesnippetmanager.models.SnippetAPIModel;

import java.util.ArrayList;

public class SnippetAPIResponsePromptFeedback {
    private ArrayList<SnippetAPIResponseSafetyRating> safetyRatings;

    public ArrayList<SnippetAPIResponseSafetyRating> getSafetyRatings() {
        return safetyRatings;
    }

    public void setSafetyRatings(ArrayList<SnippetAPIResponseSafetyRating> safetyRatings) {
        this.safetyRatings = safetyRatings;
    }
}
