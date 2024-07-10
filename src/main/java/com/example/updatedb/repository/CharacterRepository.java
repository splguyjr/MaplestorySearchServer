package com.example.updatedb.repository;

import com.example.updatedb.entity.Character;
import com.example.updatedb.entity.CharacterExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByFcmToken(String fcmToken);

    @Query("SELECT DISTINCT c.fcmToken FROM Character c")
    List<String> findDistinctFcmTokens();

    Boolean existsByCharacterNameAndFcmToken(String characterName, String fcmToken);

    Character findByCharacterNameAndFcmToken(String characterName, String fcmToken);
}
