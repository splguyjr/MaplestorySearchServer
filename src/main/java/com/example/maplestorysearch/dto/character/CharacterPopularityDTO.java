package com.example.maplestorysearch.dto.character;

import com.example.maplestorysearch.TimeFormatter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 캐릭터 인기도 정보
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CharacterPopularityDTO {

    /**
     * 조회 기준일 (KST, 일 단위 데이터로 시, 분은 일괄 0으로 표기)
     */
    @JsonProperty("date")
    private String date;

    /**
     * 캐릭터 인기도
     */
    @JsonProperty("popularity")
    private long popularity;

    public LocalDateTime getDate() {
        return date != null
                ? TimeFormatter.toLocalDateTime(this.date)
                : null;
    }
}
