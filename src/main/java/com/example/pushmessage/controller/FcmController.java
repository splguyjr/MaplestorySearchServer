package com.example.pushmessage.controller;


import com.example.pushmessage.dto.FcmSendDeviceDto;
import com.example.pushmessage.dto.FcmSendDto;
import com.example.pushmessage.service.FcmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "3.FireBase Cloud Messaging api(미완성)")
@Slf4j
@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    private final FcmService fcmService;

    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/test")
    @Operation(summary = "db상에 저장되어있는 fcm 토큰 리스트 조회", description = "일괄 푸시 알림을 위한 디바이스 토큰 정보를 불러옴")
    public ResponseEntity<Object> push2Message(@RequestBody @Validated FcmSendDto fcmSendDto) throws IOException {

        List<FcmSendDeviceDto> fcmSendDeviceDtoList = fcmService.selectFcmSendList();

        return new ResponseEntity<>(fcmSendDeviceDtoList, HttpStatus.OK);
    }

    @PostMapping("/send")
    @Operation(summary = "메시지 구성 및 FCM 발송", description = "필요한 형식(bearer token)등을 담아 메시지를 구성하고 FCM 서버에게 디바이스로의 전송을 요청")
    public ResponseEntity<Object> pushMessage(@RequestBody @Validated FcmSendDto fcmSendDto) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다. ");
        fcmService.sendMessageTo(fcmSendDto);

        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
