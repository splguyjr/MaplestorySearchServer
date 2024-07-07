package com.example.pushmessage.service;

import com.example.pushmessage.dto.FcmSendDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FcmService {
    int sendMessageTo(FcmSendDto fcmSendDto) throws IOException;
}
