package com.example.updatedb.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@ToString
public class SubscribeCharacterListDTO {
    private List<CharacterExpDTO> characterList;
}
