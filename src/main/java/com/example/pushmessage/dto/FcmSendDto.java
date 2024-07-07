package com.example.pushmessage.dto;

import lombok.*;

/**
 * 모바일에서 전달받은 객체를 매핑하는 DTO
 */

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSendDto {
    private String token;

    private String title;

    private String body;

    @Builder(toBuilder = true)
    public FcmSendDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
