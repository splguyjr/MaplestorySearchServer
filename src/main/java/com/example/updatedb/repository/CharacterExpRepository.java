package com.example.updatedb.repository;

import com.example.updatedb.entity.CharacterExp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterExpRepository extends JpaRepository<CharacterExp, Long> {
    CharacterExp findByCharacterName(String CharacterName);
    CharacterExp findByCharacterNameAndFcmToken(String characterName, String fcmToken);

    Boolean existsByCharacterName(String characterName);
}
