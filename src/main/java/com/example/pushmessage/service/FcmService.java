package com.example.pushmessage.service;

import com.example.pushmessage.dto.FcmSendDeviceDto;
import com.example.pushmessage.dto.FcmSendDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface FcmService {
    void sendMessageTo(FcmSendDto fcmSendDto) throws IOException;

    List<FcmSendDeviceDto> selectFcmSendList();
}
