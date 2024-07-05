package com.example.maplestorysearch.service;

import com.example.maplestorysearch.dto.character.CharacterBasicDTO;
import com.example.maplestorysearch.dto.character.CharacterDTO;
import com.example.maplestorysearch.dto.union.UnionDTO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UnionService {

    private final RestClient restClient;
    private final CharacterService characterService;

    @Autowired
    public UnionService(RestClient restClient, CharacterService characterService) {
        this.restClient = restClient;
        this.characterService = characterService;
    }

    public UnionDTO getUnion(@NonNull String ocid) {
        return this.getUnion(ocid, null);
    }


    public UnionDTO getUnion(@NonNull String ocid, LocalDateTime localDateTime) {
        String url = "/maplestory/v1/user/union";
        // LocalDateTime이 null이 아닌 경우 date 파라미터 추가
        if (localDateTime != null) {
            //2023년 12월 21 이후 정보만 api 조회 가능
            System.out.println(localDateTime);
            String date = toDateString(minDate(2023, 12, 21), localDateTime);
            System.out.println(date);
            url = UriComponentsBuilder.fromPath(url)
                    .queryParam("ocid", ocid)
                    .queryParam("date", date)
                    .build()
                    .toString();
        }

        else {
            url = String.format("%s?ocid=%s", url, ocid);
        }

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(UnionDTO.class);
    }

    public UnionDTO getUnionByName(@NonNull String characterName) {
        CharacterDTO character = characterService.getCharacter(characterName);
        String ocid = character.getOcid();
        UnionDTO unionDTO = getUnion(ocid);
        return unionDTO;
    }

    private static LocalDateTime minDate(int year, int month, int day) {
        return LocalDateTime.of(year, month, day, 0, 0, 0, 0);
    }

    private static String toDateString(@NonNull LocalDateTime minDate, @NonNull LocalDateTime date) {
        System.out.println(minDate);
        final int minYear = minDate.getYear();
        final int minMonth = minDate.getMonthValue();
        final int minDay = minDate.getDayOfMonth();

        final int year = date.getYear();
        final int month = date.getMonthValue();
        final int day = date.getDayOfMonth();

        if (year < minYear || (year == minYear && month < minMonth) || (year == minYear && month == minMonth && day < minDay)) {
            throw new IllegalArgumentException(String.format("You can only retrieve data after %d-%02d-%02d.", minYear, minMonth, minDay));
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final String yyyyMMdd = date.format(formatter);

        return yyyyMMdd;
    }
}
