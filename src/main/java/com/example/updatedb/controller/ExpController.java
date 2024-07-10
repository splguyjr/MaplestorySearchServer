package com.example.updatedb.controller;

import com.example.updatedb.dto.SubscribeCharacterListDTO;
import com.example.updatedb.dto.SubscribeCharaterRequestDTO;
import com.example.updatedb.service.CharacterExpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "4.캐릭터 exp 구독 관련 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ExpController {

    private final CharacterExpService characterExpService;

    //캐릭터 이름과 fcmToken정보를 통해 캐릭터 새로 구독
    @PostMapping("/subscribe/{name}")
    @Operation(summary = "새로운 캐릭터 구독", description = "fcmToken과 캐릭터 이름을 기반으로 unique한 구독 정보를 생성하여 db에 저장, 일주일간 경험치 평균 변화량 계산 로직 수행")
    public ResponseEntity<String> subscribeCharacter(@PathVariable String name,
                                                         @RequestBody SubscribeCharaterRequestDTO subscribeCharaterRequestDTO) {
        String fcmToken = subscribeCharaterRequestDTO.fcmToken();
        boolean subscribed = characterExpService.subscribeCharacter(name, fcmToken);

        if(subscribed) return new ResponseEntity<>("이미 구독한 캐릭터입니다", HttpStatus.CONFLICT);
        return new ResponseEntity<>("성공적으로 등록되었습니다", HttpStatus.CREATED);
    }

    //fcmToken을 통해 해당 디바이스에서 구독하고 있는 캐릭터 정보들을 불러옴
    @GetMapping("/subscribe")
    @Operation(summary = "캐릭터 구독 정보 조회", description = "fcmToken을 통해 해당 디바이스에서 구독한 캐릭터 정보들을 조회하여 반환")
    public SubscribeCharacterListDTO subscribeCharacter(@RequestParam String fcmToken) {
        SubscribeCharacterListDTO subscribeCharacterList = characterExpService.getSubscribeCharacterList(fcmToken);
        return subscribeCharacterList;
    }

    //캐릭터 이름과 fcmToken정보를 통해 캐릭터 구독 삭제
    @DeleteMapping("/subscribe/{name}")
    @Operation(summary = "캐릭터 구독 취소", description = "fcmToken과 캐릭터 이름을 기반으로 unique한 구독 정보를 식별, db에서 해당 데이터 삭제")
    public ResponseEntity<HttpStatus> subscribeCharacter(@PathVariable String name,
                                                     @RequestParam String fcmToken) {
        characterExpService.deleteSubscription(name, fcmToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


