package com.example.updatedb.dto;

import lombok.Builder;

@Builder
public record SubscribeCharaterRequestDTO(String fcmToken) {
}
