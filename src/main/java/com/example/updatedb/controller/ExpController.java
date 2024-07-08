package com.example.updatedb.controller;

import com.example.updatedb.dto.SubscribeCharacterListDTO;
import com.example.updatedb.service.CharacterExpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ExpController {

    private final CharacterExpService characterExpService;

    @GetMapping("/subscribe/{fcmToken}")
    public SubscribeCharacterListDTO subscribeCharacter(@PathVariable String fcmToken) {
        SubscribeCharacterListDTO subscribeCharacterList = characterExpService.getSubscribeCharacterList(fcmToken);
        return subscribeCharacterList;
    }
}
