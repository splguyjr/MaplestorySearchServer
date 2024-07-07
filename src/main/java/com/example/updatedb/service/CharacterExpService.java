package com.example.updatedb.service;

import com.example.maplestorysearch.dto.character.CharacterBasicDTO;
import com.example.maplestorysearch.dto.character.CharacterStatDTO;
import com.example.maplestorysearch.service.CharacterService;
import com.example.updatedb.entity.Character;
import com.example.updatedb.entity.CharacterExp;
import com.example.updatedb.repository.CharacterExpRepository;
import com.example.updatedb.repository.CharacterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CharacterExpService {

    private final CharacterExpRepository characterExpRepository;
    private final CharacterRepository characterRepository;
    private final CharacterService characterService;


    public CharacterExpService(CharacterExpRepository characterExpRepository, RestClient restClient, CharacterRepository characterRepository, CharacterService characterService) {
        this.characterExpRepository = characterExpRepository;
        this.characterService = characterService;
        this.characterRepository = characterRepository;
    }


    //fcmToken으로 character 구독 정보 조회 이후, character마다 각각 api호출 통해 expRate 업데이트
    @Transactional
    public void updateExp(String fcmToken) {
        List<Character> characters = characterRepository.findByFcmToken(fcmToken);

        for(Character character : characters) {
            String characterName = character.getCharacterName();
            CharacterExp characterExp = characterExpRepository.findByCharacterName(characterName);

            double todayExpRate = ExpApiCall(characterName);

            if (characterExp != null) {
                characterExp.setExp7(characterExp.getExp6());
                characterExp.setExp6(characterExp.getExp5());
                characterExp.setExp5(characterExp.getExp4());
                characterExp.setExp4(characterExp.getExp3());
                characterExp.setExp3(characterExp.getExp2());
                characterExp.setExp2(characterExp.getExp1());
                characterExp.setExp1(todayExpRate);

                // save 메서드 호출 시 @PreUpdate 어노테이션에 의해 calculateAverageGrowthRate 메서드가 자동으로 호출됨
                characterExpRepository.save(characterExp);
            }

            double exp7 = characterExp.getExp7();
            double averageGrowthRate = characterExp.getAverageGrowthRate();
            System.out.println(exp7);
            System.out.println(averageGrowthRate);

            if(exp7 + averageGrowthRate >= 100) {
                characterExp.setLevelUp(true);
            }
        }
    }

    @Transactional
    public List<String> getFcmTokenList() {
        List<String> distinctFcmTokens = characterRepository.findDistinctFcmTokens();
        System.out.println(distinctFcmTokens);
        return distinctFcmTokens;
    }

    public double ExpApiCall(String characterName) {
        CharacterBasicDTO characterBasicDTO = characterService.getCharacterBasicByName(characterName);
        String characterExpRate = characterBasicDTO.getCharacterExpRate();
        return Double.parseDouble(characterExpRate);
    }


}
