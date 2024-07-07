package com.example.pushmessage.controller;


import com.example.pushmessage.dto.FcmSendDeviceDto;
import com.example.pushmessage.dto.FcmSendDto;
import com.example.pushmessage.service.FcmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    private final FcmService fcmService;

    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/test")
    public ResponseEntity<Object> push2Message(@RequestBody @Validated FcmSendDto fcmSendDto) throws IOException {

        List<FcmSendDeviceDto> fcmSendDeviceDtoList = fcmService.selectFcmSendList();

        return new ResponseEntity<>(fcmSendDeviceDtoList, HttpStatus.OK);
    }

    @PostMapping("/send")
    public ResponseEntity<Object> pushMessage(@RequestBody @Validated FcmSendDto fcmSendDto) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다. ");
        fcmService.sendMessageTo(fcmSendDto);

        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
