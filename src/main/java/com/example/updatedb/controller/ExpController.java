package com.example.updatedb.controller;

import com.example.updatedb.dto.SubscribeCharacterListDTO;
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
    public ResponseEntity<HttpStatus> subscribeCharacter(@PathVariable String name,
                                                         @RequestParam String fcmToken) {
        characterExpService.subscribeCharacter(name, fcmToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //fcmToken을 통해 해당 디바이스에서 구독하고 있는 캐릭터 정보들을 불러옴
    @GetMapping("/subscribe")
    public SubscribeCharacterListDTO subscribeCharacter(@RequestParam String fcmToken) {
        SubscribeCharacterListDTO subscribeCharacterList = characterExpService.getSubscribeCharacterList(fcmToken);
        return subscribeCharacterList;
    }
}
