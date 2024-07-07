package com.example.updatedb.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;

@Entity
@Data
@Table(name = "characterExp")
public class CharacterExp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fcm_token")
    @Nullable
    private String fcmToken;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "exp1")
    @Nullable
    private double exp1;

    @Column(name = "exp2")
    @Nullable
    private double exp2;

    @Column(name = "exp3")
    @Nullable
    private double exp3;

    @Column(name = "exp4")
    @Nullable
    private double exp4;

    @Column(name = "exp5")
    @Nullable
    private double exp5;

    @Column(name = "exp6")
    @Nullable
    private double exp6;

    @Column(name = "exp7")
    @Nullable
    private double exp7;

    @Column(name = "average_growth_rate")
    @ColumnDefault("0")
    private double averageGrowthRate;

    @Column(name = "level_up")
    @ColumnDefault("false")
    private boolean levelUp;

    @PreUpdate
    public void calculateAverageGrowthRate() {
        double[] exps = {exp7, exp6, exp5, exp4, exp3, exp2, exp1};
        double totalGrowthRate = 0.0;
        int count = 0;

        for (int i = 0; i < exps.length - 1; i++) {
            if (exps[i] > exps[i+1]) {//중간에 레벨업이 일어나서 0%로 줄어든 경우 그 뒤 경험치는 +100%로 취급
                for (int j = i+1; j < exps.length; j++) {
                    exps[j] += 100;
                }
            }
                totalGrowthRate += (double)(exps[i + 1] - exps[i]);
                count++;

        }
        setAverageGrowthRate(count > 0 ? totalGrowthRate / count : 0.0);
        System.out.println(averageGrowthRate);
    }
}
