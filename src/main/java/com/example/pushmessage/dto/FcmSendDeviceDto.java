package com.example.pushmessage.dto;

import lombok.*;

/**
 * DB에서 FCM Token 값을 조회할 수 있는 DTO
 */

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDeviceDto {
    private String dvcTkn;

    @Builder
    public FcmSendDeviceDto(String dvcTkn) {
        this.dvcTkn = dvcTkn;
    }
}
