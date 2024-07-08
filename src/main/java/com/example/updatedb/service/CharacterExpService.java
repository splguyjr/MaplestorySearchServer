package com.example.updatedb.service;

import com.example.maplestorysearch.dto.character.CharacterBasicDTO;
import com.example.maplestorysearch.service.CharacterService;
import com.example.updatedb.dto.CharacterExpDTO;
import com.example.updatedb.dto.CharacterWeekExpDTO;
import com.example.updatedb.dto.SubscribeCharacterListDTO;
import com.example.updatedb.entity.Character;
import com.example.updatedb.entity.CharacterExp;
import com.example.updatedb.mapper.ExpMapper;
import com.example.updatedb.repository.CharacterExpRepository;
import com.example.updatedb.repository.CharacterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterExpService {

    private final CharacterExpRepository characterExpRepository;
    private final CharacterRepository characterRepository;
    private final CharacterService characterService;
    private final ExpMapper expMapper;

    public CharacterExpService(CharacterExpRepository characterExpRepository, RestClient restClient, CharacterRepository characterRepository, CharacterService characterService, ExpMapper expMapper) {
        this.characterExpRepository = characterExpRepository;
        this.characterService = characterService;
        this.characterRepository = characterRepository;
        this.expMapper = expMapper;
    }


    //fcmToken으로 character 구독 정보 조회 이후, character마다 각각 api호출 통해 expRate 업데이트
    @Transactional
    public void updateExp(String fcmToken) {
        List<Character> characters = characterRepository.findByFcmToken(fcmToken);

        for(Character character : characters) {
            String characterName = character.getCharacterName();
            CharacterExp characterExp = characterExpRepository.findByCharacterName(characterName);

            double todayExpRate = ExpApiCall(characterName);
            String imageUrl = ImageApiCall(characterName);

            if (characterExp != null) {
                characterExp.setExp7(characterExp.getExp6());
                characterExp.setExp6(characterExp.getExp5());
                characterExp.setExp5(characterExp.getExp4());
                characterExp.setExp4(characterExp.getExp3());
                characterExp.setExp3(characterExp.getExp2());
                characterExp.setExp2(characterExp.getExp1());
                characterExp.setExp1(todayExpRate);
                characterExp.setCharacterImage(imageUrl);
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

    public String ImageApiCall(String characterName) {
        CharacterBasicDTO characterBasicDTO = characterService.getCharacterBasicByName(characterName);
        String characterImage = characterBasicDTO.getCharacterImage();
        return characterImage;
    }

    //특정 fcmToken을 입력으로 받아 해당 디바이스가 구독한 character들의 기본정보(이미지, 닉네임, 경험치) 반환
    public SubscribeCharacterListDTO getSubscribeCharacterList(String fcmToken) {
        List<Character> characters = characterRepository.findByFcmToken(fcmToken);

        List<CharacterExpDTO> characterExpDTOList = new ArrayList<>();

        for(Character character : characters) {
            String characterName = character.getCharacterName();
            CharacterExp characterExp = characterExpRepository.findByCharacterName(characterName);

            double exp = characterExp.getExp7();
            String exps = Double.toString(exp);
            String characterImage = characterExp.getCharacterImage();

            CharacterExpDTO characterExpDTO = CharacterExpDTO.builder()
                    .characterName(characterName)
                    .characterImage(characterImage)
                    .characterExp(exps)
                    .build();

            characterExpDTOList.add(characterExpDTO);
        }

        return SubscribeCharacterListDTO.builder()
                .characterList(characterExpDTOList)
                .build();
    }

    //특정 디바이스에서 특정 캐릭터 구독시 db에 저장하고, 최근 일주일 간 exp api호출을 통해 긁어옴
    public void subscribeCharacter(String characterName, String fcmToken) {
        Character character = new Character();
        character.setCharacterName(characterName);
        character.setFcmToken(fcmToken);

        characterRepository.save(character);

        //updateNewCharacterExp메소드를 호출하여 characterExp 객체내 필드 채워넣음
        CharacterExp characterExp = updateNewCharacterExp(characterName, fcmToken);
        characterExpRepository.save(characterExp);
    }

    public CharacterExp updateNewCharacterExp(String characterName, String fcmToken) {
        Boolean flag = characterRepository.existsCharacterByCharacterName(characterName);

        //이전에 이미 다른 디바이스에서 등록한적이 있는 캐릭터의 경우 api 호출을 줄이기 위해 기존 정보 이용
        if(flag) {
            CharacterExp characterExp = characterExpRepository.findByCharacterName(characterName);
            CharacterExp newCharacterExp = expMapper.createNewCharacterExp(characterExp, fcmToken);
            return newCharacterExp;
        }

        //해당 캐릭터를 등록하는 것이 모든 디바이스 통틀어 처음인 경우 api 호출 7번하여 정보 구성
        else {
            //일주일전부터 현재까지 순서로 경험치 정보가 저장되어있음
            CharacterWeekExpDTO characterWeekExpDTO = characterService.getCharacterBasicInWeek(characterName);
            List<Double> characterExpList = characterWeekExpDTO.expList();//일주일치 경험치
            String characterImage = characterWeekExpDTO.characterImage();//캐릭터 제일 최근일자 이미지

            CharacterExp characterExp = new CharacterExp();

            characterExp.setCharacterName(characterName);
            characterExp.setFcmToken(fcmToken);
            characterExp.setCharacterImage(characterImage);
            characterExp.setExp7(characterExpList.get(6));
            characterExp.setExp6(characterExpList.get(5));
            characterExp.setExp5(characterExpList.get(4));
            characterExp.setExp4(characterExpList.get(3));
            characterExp.setExp3(characterExpList.get(2));
            characterExp.setExp2(characterExpList.get(1));
            characterExp.setExp1(characterExpList.get(0));

            return characterExp;
        }
    }

    public double ExpApiCallByDate(String characterName) {
        CharacterBasicDTO characterBasicDTO = characterService.getCharacterBasicByName(characterName);
        String characterExpRate = characterBasicDTO.getCharacterExpRate();
        return Double.parseDouble(characterExpRate);
    }
}
