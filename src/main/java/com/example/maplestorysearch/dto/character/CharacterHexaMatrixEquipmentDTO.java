package com.example.maplestorysearch.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 캐릭터 HEXA 코어 정보
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CharacterHexaMatrixEquipmentDTO {

    /**
     * 코어 명
     */
    @JsonProperty("hexa_core_name")
    private String hexaCoreName;

    /**
     * 코어 레벨
     */
    @JsonProperty("hexa_core_level")
    private Long hexaCoreLevel;

    /**
     * 코어 타입
     */
    @JsonProperty("hexa_core_type")
    private String hexaCoreType;

    /**
     * 연결된 스킬
     */
    @JsonProperty("linked_skill")
    private List<CharacterHexaMatrixEquipmentLinkedSkillDTO> linkedSkill;
}
