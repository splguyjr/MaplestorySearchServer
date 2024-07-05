package com.example.maplestorysearch.dto;

import com.example.maplestorysearch.TimeFormatter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 캐릭터 HEXA 코어 정보
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CharacterHexaMatrixDTO {

    /**
     * 조회 기준일 (KST)
     */
    @JsonProperty("date")
    private String date;

    /**
     * HEXA 코어 정보
     */
    @JsonProperty("character_hexa_core_equipment")
    private List<CharacterHexaMatrixEquipmentDTO> characterHexaCoreEquipment;

    public LocalDateTime getDate() {
        return date != null
                ? TimeFormatter.toLocalDateTime(this.date)
                : null;
    }
}