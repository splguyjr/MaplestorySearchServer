package com.example.updatedb.controller;

import com.example.updatedb.dto.SubscribeCharacterListDTO;
import com.example.updatedb.dto.SubscribeCharaterRequestDTO;
import com.example.updatedb.service.CharacterExpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ExpController {

    private final CharacterExpService characterExpService;

    //캐릭터 이름과 fcmToken정보를 통해 캐릭터 새로 구독
    @PostMapping("/subscribe/{name}")
    public ResponseEntity<String> subscribeCharacter(@PathVariable String name,
                                                         @RequestBody SubscribeCharaterRequestDTO subscribeCharaterRequestDTO) {
        String fcmToken = subscribeCharaterRequestDTO.fcmToken();
        boolean subscribed = characterExpService.subscribeCharacter(name, fcmToken);

        if(subscribed) return new ResponseEntity<>("이미 구독한 캐릭터입니다", HttpStatus.CONFLICT);
        return new ResponseEntity<>("성공적으로 등록되었습니다", HttpStatus.CREATED);
    }

    //fcmToken을 통해 해당 디바이스에서 구독하고 있는 캐릭터 정보들을 불러옴
    @GetMapping("/subscribe")
    public SubscribeCharacterListDTO subscribeCharacter(@RequestParam String fcmToken) {
        SubscribeCharacterListDTO subscribeCharacterList = characterExpService.getSubscribeCharacterList(fcmToken);
        return subscribeCharacterList;
    }

    //캐릭터 이름과 fcmToken정보를 통해 캐릭터 구독 삭제
    @DeleteMapping("/subscribe/{name}")
    public ResponseEntity<HttpStatus> subscribeCharacter(@PathVariable String name,
                                                     @RequestParam String fcmToken) {
        characterExpService.deleteSubscription(name, fcmToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


