package com.example.updatedb.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.lang.Nullable;

@Entity
@Data
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fcm_token")
    @Nullable
    private String fcmToken;

    @Column(name = "character_name")
    private String characterName;

}
