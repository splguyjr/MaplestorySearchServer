package com.example.pushmessage.service.impl;

import com.example.pushmessage.dto.FcmMessageDto;
import com.example.pushmessage.dto.FcmSendDeviceDto;
import com.example.pushmessage.dto.FcmSendDto;
import com.example.pushmessage.mapper.FcmMapper;
import com.example.pushmessage.service.FcmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import org.apache.ibatis.session.SqlSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * FCM 서비스를 처리하는 구현체
 */

@Service
public class FcmServiceImpl implements FcmService {

    private final SqlSession sqlSession;

    public FcmServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    //sendMessageTo   메시지를 구성하고 토큰을 받아서 FCM으로 메시지 처리를 수행하는 비즈니스 로직
    @Override
    public void sendMessageTo(FcmSendDto fcmSendDto) throws IOException {

        String message = makeMessage(fcmSendDto);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity entity = new HttpEntity<>(message, headers);

        String API_URL = "<https://fcm.googleapis.com/v1/projects/fir-cloud-messaging-a7145/messages:send>";
        try {
            restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            System.out.println("[-] FCM 전송 오류 :: " + e.getMessage());
        }
    }

    //FCM 전송 디바이스 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<FcmSendDeviceDto> selectFcmSendList() {
        FcmMapper udm = sqlSession.getMapper(FcmMapper.class);
        return udm.selectFcmSendList();
    }


    //getAccessToken   Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급 받습니다.
    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "splguyjr-dev-firebase-key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("<https://www.googleapis.com/auth/cloud-platform>"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    //makeMessage   FCM 전송 정보를 기반으로 메시지를 구성합니다. (Object -> String)
    private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(fcmSendDto.getToken())
                        .notification(FcmMessageDto.Notification.builder()
                                .title(fcmSendDto.getTitle())
                                .body(fcmSendDto.getBody())
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();

        return om.writeValueAsString(fcmMessageDto);
    }
}
