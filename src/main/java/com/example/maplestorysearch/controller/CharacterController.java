package com.example.maplestorysearch.controller;


import com.example.maplestorysearch.dto.CharacterDTO;
import com.example.maplestorysearch.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/character/{name}")
    public CharacterDTO getCharacter(@PathVariable String name) {
        return characterService.getCharacter(name);
    }
}
