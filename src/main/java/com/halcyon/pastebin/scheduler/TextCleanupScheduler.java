package com.halcyon.pastebin.scheduler;

import com.halcyon.pastebin.repository.ITextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TextCleanupScheduler {
    private final ITextRepository textRepository;

    @Scheduled(fixedRate = 1000)
    public void cleanupExpiredTexts() {
        Instant currentTime = Instant.now();
        textRepository.deleteByExpirationTime(currentTime);
    }
}
