package com.example.maplestorysearch.service;

import com.example.maplestorysearch.dto.CharacterBasicDTO;
import com.example.maplestorysearch.dto.CharacterDTO;
import com.example.maplestorysearch.dto.CharacterFinalStatDTO;
import com.example.maplestorysearch.dto.CharacterStatDTO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class CharacterService {

    private final RestClient restClient;

    @Autowired
    public CharacterService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CharacterDTO getCharacter(@NonNull String characterName){
        String url = String.format("/maplestory/v1/id?character_name=%s", characterName);
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(CharacterDTO.class);
    }

    public CharacterBasicDTO getCharacterBasic(@NonNull String ocid) {
        return this.getCharacterBasic(ocid, null);
    }


    public CharacterBasicDTO getCharacterBasic(@NonNull String ocid, LocalDateTime localDateTime) {
        String url = "/maplestory/v1/character/basic";
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
                .body(CharacterBasicDTO.class);
    }

    public CharacterBasicDTO getCharacterBasicByName(@NonNull String characterName) {
        CharacterDTO character = getCharacter(characterName);
        String ocid = character.getOcid();
        CharacterBasicDTO characterBasicDTO = getCharacterBasic(ocid);
        return characterBasicDTO;
    }

    public CharacterStatDTO getCharacterStat(@NonNull String ocid) {
        return this.getCharacterStat(ocid, null);
    }

    public CharacterStatDTO getCharacterStat(@NonNull String ocid, LocalDateTime localDateTime) {
        String url = "/maplestory/v1/character/stat";
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
                .body(CharacterStatDTO.class);
    }

    public CharacterStatDTO getCharacterStatByName(@NonNull String characterName) {
        CharacterDTO character = getCharacter(characterName);
        String ocid = character.getOcid();
        CharacterStatDTO characterStatDTO = getCharacterStat(ocid);
        return characterStatDTO;
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
