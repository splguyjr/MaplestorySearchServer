package com.example.pushmessage.mapper;

import com.example.pushmessage.dto.FcmSendDeviceDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FcmMapper {
    List<FcmSendDeviceDto> selectFcmSendList();

}
