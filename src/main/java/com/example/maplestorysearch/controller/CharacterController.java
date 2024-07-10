package com.example.maplestorysearch.controller;


import com.example.maplestorysearch.TimeFormatter;
import com.example.maplestorysearch.dto.character.*;
import com.example.maplestorysearch.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "1.캐릭터 정보 관련 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/character/{name}")
    @Operation(summary = "캐릭터 OCID 조회", description = "캐릭터 이름을 통해 캐릭터 OCID를 조회")
    public CharacterDTO getCharacter(@PathVariable String name) {
        return characterService.getCharacter(name);
    }

    //현재 날짜로부터 딱 1달 전 날짜까지만 오류가 나지 않는 것으로 판단됨, 이유는 모르겠음..
    @GetMapping("/character/basic")
    @Operation(summary = "ocid로 캐릭터 기본정보 조회", description = "캐릭터 ocid와 date(선택)을 통해 캐릭터 기본정보를 조회")
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
    @Operation(summary = "이름으로 캐릭터 기본정보 조회", description = "캐릭터 이름을 통해 캐릭터 기본정보 조회")
    public CharacterBasicDTO getCharacterBasicByName(@PathVariable String name) {
        return characterService.getCharacterBasicByName(name);
    }

    @GetMapping("/character/stat/{name}")
    @Operation(summary = "이름으로 캐릭터 스텟 정보 조회", description = "캐릭터 이름을 통해 캐릭터 스텟정보를 조회")
    public CharacterStatDTO getCharacterStatByName(@PathVariable String name) {
        return characterService.getCharacterStatByName(name);
    }

    @GetMapping("/character/hyperstat/{name}")
    @Operation(summary = "이름으로 캐릭터 하이퍼 스텟 정보 조회", description = "캐릭터 이름을 통해 캐릭터 하이퍼 스텟정보를 조회")
    public CharacterHyperStatDTO getCharacterHyperStatByName(@PathVariable String name) {
        return characterService.getCharacterHyperStatByName(name);
    }

    @GetMapping("/character/ability/{name}")
    @Operation(summary = "이름으로 캐릭터 어빌리티 정보 조회", description = "캐릭터 이름을 통해 캐릭터 어빌리티 정보를 조회")
    public CharacterAbilityDTO getCharacterAbilityByName(@PathVariable String name) {
        return characterService.getCharacterAbilityByName(name);
    }

    @GetMapping("/character/hexamatrix/{name}")
    @Operation(summary = "이름으로 캐릭터 헥사 메트릭스 정보 조회", description = "캐릭터 이름을 통해 캐릭터 헥사 메트릭스 정보를 조회")
    public CharacterHexaMatrixDTO getCharacterHexaMatrixByName(@PathVariable String name) {
        return characterService.getCharacterHexaMatrixByName(name);
    }

    @GetMapping("/character/hexamatrix-stat/{name}")
    @Operation(summary = "이름으로 캐릭터 헥사 스텟 정보 조회", description = "캐릭터 이름을 통해 캐릭터 헥사 스텟 정보를 조회")
    public CharacterHexaMatrixStatDTO getCharacterHexaStatByName(@PathVariable String name) {
        return characterService.getCharacterHexaStatByName(name);
    }

    @GetMapping("/character/popularity/{name}")
    @Operation(summary = "이름으로 캐릭터 인기도 정보 조회", description = "캐릭터 이름을 통해 인기도 정보를 조회")
    public CharacterPopularityDTO getCharacterPopularityByName(@PathVariable String name) {
        return characterService.getCharacterPopularityByName(name);
    }

}
