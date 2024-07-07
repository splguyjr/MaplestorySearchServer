package com.example.maplestorysearch.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 연결된 HEXA 스킬 정보
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CharacterHexaMatrixEquipmentLinkedSkillDTO {

    /**
     * HEXA 스킬 명
     */
    @JsonProperty("hexa_skill_id")
    private String hexaSkillId;
}
