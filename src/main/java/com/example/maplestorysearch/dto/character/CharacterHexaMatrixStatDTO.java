package com.example.maplestorysearch.dto.character;

import com.example.maplestorysearch.TimeFormatter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CharacterHexaMatrixStatDTO {

    /**
     * 조회 기준일 (KST)
     */
    @JsonProperty("date")
    private String date;

    /**
     * 캐릭터 직업
     */
    @JsonProperty("character_class")
    private String characterClass;

    /**
     * HEXA 스탯 코어 정보
     */
    @JsonProperty("character_hexa_stat_core")
    private List<CharacterHexaMatrixStatCoreDTO> characterHexaStatCore;

    /**
     * 프리셋 HEXA 스탯 코어 정보
     */
    @JsonProperty("preset_hexa_stat_core")
    private List<CharacterHexaMatrixStatCoreDTO> presetHexaStatCore;

    public LocalDateTime getDate() {
        return date != null
                ? TimeFormatter.toLocalDateTime(this.date)
                : null;
    }
}