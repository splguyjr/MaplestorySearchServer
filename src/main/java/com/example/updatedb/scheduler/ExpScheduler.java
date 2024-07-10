package com.example.updatedb.scheduler;

import com.example.updatedb.service.CharacterExpService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpScheduler {

    private CharacterExpService characterExpService;

    public ExpScheduler(CharacterExpService characterExpService) {
        this.characterExpService = characterExpService;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void updateDailyExp() {
        List<String> fcmTokenList = characterExpService.getFcmTokenList();
        for(String fcmToken : fcmTokenList) {
            characterExpService.updateExp(fcmToken);
        }
    }
}
