package com.example.updatedb.mapper;

import com.example.updatedb.entity.CharacterExp;
import org.springframework.stereotype.Component;

@Component
public class ExpMapper {

    public CharacterExp createNewCharacterExp(CharacterExp characterExp, String fcmToken) {
        return CharacterExp.builder()
                .fcmToken(fcmToken)
                .characterName(characterExp.getCharacterName())
                .characterImage(characterExp.getCharacterImage())
                .exp1(characterExp.getExp1())
                .exp2(characterExp.getExp2())
                .exp3(characterExp.getExp3())
                .exp4(characterExp.getExp4())
                .exp5(characterExp.getExp5())
                .exp6(characterExp.getExp6())
                .exp7(characterExp.getExp7())
                .averageGrowthRate(characterExp.getAverageGrowthRate())
                .levelUp(characterExp.isLevelUp())
                .build();
    }
}
