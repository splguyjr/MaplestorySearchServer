package com.example.maplestorysearch.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO {
    /**
     * 캐릭터 식별자 ocid
     */

    @JsonProperty("ocid")
    private String ocid;
}
