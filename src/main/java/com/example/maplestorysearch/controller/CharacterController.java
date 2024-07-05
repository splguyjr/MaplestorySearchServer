package com.example.maplestorysearch.controller;


import com.example.maplestorysearch.TimeFormatter;
import com.example.maplestorysearch.dto.*;
import com.example.maplestorysearch.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/character/{name}")
    public CharacterDTO getCharacter(@PathVariable String name) {
        return characterService.getCharacter(name);
    }

    //현재 날짜로부터 딱 1달 전 날짜까지만 오류가 나지 않는 것으로 판단됨, 이유는 모르겠음..
    @GetMapping("/character/basic")
    public CharacterBasicDTO getCharacterBasic(@RequestParam String ocid, @RequestParam(required = false) String date) {
        if(date != null) {
            LocalDateTime localDateTime = TimeFormatter.toLocalDateTime(date);
            return characterService.getCharacterBasic(ocid, localDateTime);
        }
        else {
            return characterService.getCharacterBasic(ocid);
        }
    }

    @GetMapping("/character/basic/{name}")
    public CharacterBasicDTO getCharacterBasicByName(@PathVariable String name) {
        return characterService.getCharacterBasicByName(name);
    }

    @GetMapping("/character/stat/{name}")
    public CharacterStatDTO getCharacterStatByName(@PathVariable String name) {
        return characterService.getCharacterStatByName(name);
    }

    @GetMapping("/character/hyperstat/{name}")
    public CharacterHyperStatDTO getCharacterHyperStatByName(@PathVariable String name) {
        return characterService.getCharacterHyperStatByName(name);
    }

    @GetMapping("/character/ability/{name}")
    public CharacterAbilityDTO getCharacterAbilityByName(@PathVariable String name) {
        return characterService.getCharacterAbilityByName(name);
    }

    @GetMapping("/character/hexamatrix/{name}")
    public CharacterHexaMatrixDTO getCharacterHexaMatrixByName(@PathVariable String name) {
        return characterService.getCharacterHexaMatrixByName(name);
    }

    @GetMapping("/character/hexamatrix-stat/{name}")
    public CharacterHexaMatrixStatDTO getCharacterHexaStatByName(@PathVariable String name) {
        return characterService.getCharacterHexaStatByName(name);
    }

}
