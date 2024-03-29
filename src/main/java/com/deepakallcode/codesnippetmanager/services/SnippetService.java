package com.deepakallcode.codesnippetmanager.services;

import com.deepakallcode.codesnippetmanager.entities.Snippet;
import com.deepakallcode.codesnippetmanager.models.SnippetAPIModel.SnippetAPIResponseDTO;
import com.deepakallcode.codesnippetmanager.models.SnippetResponseDTO;
import com.deepakallcode.codesnippetmanager.repositories.SnippetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SnippetService {

    private final RestTemplate restTemplate;
    private final SnippetRepository snippetRepository;
    private final String apiKey = "AIzaSyAsC__l64bMX62gDGYvtpdUXQlFQ5wAYP0";
    private final ObjectMapper objectMapper;
    private static final String TITLE_INDENTIFIER = "TITLEOFTHISCODE=";
    private static final String DESCRIPTION_IDENTIFIER = "SNIPPETDESCRIPTION=";

    public SnippetService(RestTemplate restTemplate,
                          ObjectMapper objectMapper,
                          SnippetRepository snippetRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.snippetRepository = snippetRepository;
    }

    private SnippetResponseDTO getSnippetDetailsFromChatGpt(SnippetResponseDTO snippet) throws Exception {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("I will give you a code snippet in next line.")
                .append("Please give me a explanatory tile for this by ")
                .append("writing " + TITLE_INDENTIFIER + "the title you come up with")
                .append("Then after the period give brief description as " + DESCRIPTION_IDENTIFIER + "the description you come up with on what this code does in 200 characters")
                .append("\n")
                .append(snippet.getCode());
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> parts = new HashMap<>();
        parts.put("text", promptBuilder.toString());

        Map<String, Object> partsObj = new HashMap<>();
        partsObj.put("parts", parts);

        List<Map<String, Object>> contentParts = new ArrayList<>();
        contentParts.add(partsObj);

        requestBody.put("contents", contentParts);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(requestBody);

        } catch (Exception e) {
            throw e;
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity;
        String description;
        String title;
        try {
            if (!requestBody.isEmpty()) {
                responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
                SnippetAPIResponseDTO apiResponseDTO = objectMapper.readValue(responseEntity.getBody(), SnippetAPIResponseDTO.class);
                String text = apiResponseDTO.getCandidates().get(0).getContent().getParts().get(0).getText();
                description = text.substring(text.indexOf(DESCRIPTION_IDENTIFIER) + DESCRIPTION_IDENTIFIER.length());
                title = text.substring(text.indexOf(TITLE_INDENTIFIER) + TITLE_INDENTIFIER.length(), text.indexOf(DESCRIPTION_IDENTIFIER));
                snippet.setDescription(description);
                snippet.setTitle(title);
            } else {
                throw new Exception("The request body is empty");
            }
        } catch (Exception e) {
            throw e;
        }
        return snippet;
    }

    public List<SnippetResponseDTO> getSnippets(String userid) throws Exception {
        List<SnippetResponseDTO> snippets = null;
        List<Snippet> snippetList = null;
        snippetList = snippetRepository.findSnippetsByUserId(Long.valueOf(userid));
        if (snippetList != null) {
            snippets = mapToSnippetDTO(snippetList);
        }
        return snippets;
    }

    public List<SnippetResponseDTO> getSnippetsByType(String type, String userId) throws Exception {
        List<SnippetResponseDTO> snippets = null;
        List<Snippet> snippetList = null;

        snippetList = snippetRepository.findByType(type, Long.valueOf(userId));
        if (snippetList != null) {
            snippets = mapToSnippetDTO(snippetList);
        }
        return snippets;
    }

    public SnippetResponseDTO saveSnippet(SnippetResponseDTO snippetResponseDTO) throws Exception {
        snippetResponseDTO = getSnippetDetailsFromChatGpt(snippetResponseDTO);

        Snippet snippetToSave = new Snippet();
        snippetToSave.setCode(snippetResponseDTO.getCode());
        snippetToSave.setUserId(Long.valueOf(snippetResponseDTO.getUserId()));
        snippetToSave.setType(snippetResponseDTO.getType());
        snippetToSave.setDescription(snippetResponseDTO.getDescription());
        snippetToSave.setTitle(snippetResponseDTO.getTitle());

        try {
            snippetRepository.save(snippetToSave);
        } catch (Exception e) {
            throw e;
        }

        return snippetResponseDTO;
    }

    public String deleteSnippetById(String id) throws Exception {
        snippetRepository.deleteById(Long.parseLong(id));
        return "SUCCESS";
    }

    public SnippetResponseDTO editSnippet(SnippetResponseDTO editedSnippet) throws Exception {
        snippetRepository.deleteById(Long.valueOf(editedSnippet.getId()));

        Snippet snippetToSave = new Snippet();
        snippetToSave.setCode(editedSnippet.getCode());
        snippetToSave.setUserId(Long.valueOf(editedSnippet.getUserId()));
        snippetToSave.setType(editedSnippet.getType());
        snippetToSave.setDescription(editedSnippet.getDescription());
        snippetToSave.setTitle(editedSnippet.getTitle());
        snippetRepository.save(snippetToSave);

        return editedSnippet;
    }

    private List<SnippetResponseDTO> mapToSnippetDTO(List<Snippet> snippets) {
        List<SnippetResponseDTO> snippetResponseDTOS = new ArrayList<>();

        for (Snippet snippet : snippets) {
            SnippetResponseDTO tempSnippet = new SnippetResponseDTO();

            tempSnippet.setCode(snippet.getCode());
            tempSnippet.setId(snippet.getId().toString());
            tempSnippet.setUserId(snippet.getUserId().toString());
            tempSnippet.setType(snippet.getType());
            tempSnippet.setDescription(snippet.getDescription());
            tempSnippet.setTitle(snippet.getTitle());

            snippetResponseDTOS.add(tempSnippet);
        }
        return snippetResponseDTOS;
    }


}
