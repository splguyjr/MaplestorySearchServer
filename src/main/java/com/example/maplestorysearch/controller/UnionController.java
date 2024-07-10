package com.example.maplestorysearch.controller;

import com.example.maplestorysearch.dto.character.CharacterDTO;
import com.example.maplestorysearch.dto.union.UnionDTO;
import com.example.maplestorysearch.service.UnionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2.유니온 정보 관련 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UnionController {

    private final UnionService unionService;

    @GetMapping("/union/{name}")
    @Operation(summary = "이름으로 유니온 기본 정보 조회", description = "캐릭터 이름을 통해 유니온 기본 정보를 조회")
    public UnionDTO getUnion(@PathVariable String name) {
        return unionService.getUnionByName(name);
    }
}
