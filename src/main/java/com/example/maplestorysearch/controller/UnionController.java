package com.example.maplestorysearch.controller;

import com.example.maplestorysearch.dto.character.CharacterDTO;
import com.example.maplestorysearch.dto.union.UnionDTO;
import com.example.maplestorysearch.service.UnionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UnionController {

    private final UnionService unionService;

    @GetMapping("/union/{name}")
    public UnionDTO getUnion(@PathVariable String name) {
        return unionService.getUnionByName(name);
    }
}
