package com.example.maplestorysearch.service;

import com.example.maplestorysearch.dto.CharacterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CharacterService {

    private final RestClient restClient;

    @Autowired
    public CharacterService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CharacterDTO getCharacter(String characterName) {
        String url = String.format("/maplestory/v1/id?character_name=%s", characterName);
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(CharacterDTO.class);
    }
}
