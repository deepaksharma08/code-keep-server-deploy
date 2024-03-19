package com.deepakallcode.codesnippetmanager.models.SnippetAPIModel;

import java.util.ArrayList;

public class SnippetAPIResponseContent {
    private ArrayList<SnippetAPIResponsePart> parts;
    private String role;

    public ArrayList<SnippetAPIResponsePart> getParts() {
        return parts;
    }

    public void setParts(ArrayList<SnippetAPIResponsePart> parts) {
        this.parts = parts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
