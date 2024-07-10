package com.example.updatedb.dto;


import lombok.*;


/**
 * 캐릭터 이름, 이미지, 최신 EXP, 일주일간 평균 exp 성장률 정보를 담는 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class CharacterExpDTO {

    private String characterName;

    private String characterImage;

    private String characterExp;

    private String growth_rate;
}
