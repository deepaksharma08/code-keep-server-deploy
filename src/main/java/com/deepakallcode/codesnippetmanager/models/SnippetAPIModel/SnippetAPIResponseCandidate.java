package com.deepakallcode.codesnippetmanager.models.SnippetAPIModel;

import java.util.ArrayList;

public class SnippetAPIResponseCandidate {
    private SnippetAPIResponseContent content;
    private String finishReason;
    private int index;
    private ArrayList<SnippetAPIResponseSafetyRating> safetyRatings;

    public SnippetAPIResponseContent getContent() {
        return content;
    }

    public void setContent(SnippetAPIResponseContent content) {
        this.content = content;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<SnippetAPIResponseSafetyRating> getSafetyRatings() {
        return safetyRatings;
    }

    public void setSafetyRatings(ArrayList<SnippetAPIResponseSafetyRating> safetyRatings) {
        this.safetyRatings = safetyRatings;
    }
}
