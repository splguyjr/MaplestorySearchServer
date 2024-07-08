package com.example.updatedb.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record CharacterWeekExpDTO(String characterImage,
                                  List<Double> expList) {
}
