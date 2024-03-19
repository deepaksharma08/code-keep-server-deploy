package com.deepakallcode.codesnippetmanager.models.SnippetAPIModel;

import java.util.ArrayList;

public class SnippetAPIResponseDTO {
    private ArrayList<SnippetAPIResponseCandidate> candidates;
    private SnippetAPIResponsePromptFeedback promptFeedback;

    public ArrayList<SnippetAPIResponseCandidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<SnippetAPIResponseCandidate> candidates) {
        this.candidates = candidates;
    }

    public SnippetAPIResponsePromptFeedback getPromptFeedback() {
        return promptFeedback;
    }

    public void setPromptFeedback(SnippetAPIResponsePromptFeedback promptFeedback) {
        this.promptFeedback = promptFeedback;
    }
}


