package com.halcyon.pastebin.scheduler;

import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.repository.ITextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TextCleanupScheduler {
    private final ITextRepository textRepository;
    private final ApplicationContext applicationContext;

    @Scheduled(fixedRate = 30000)
    public void cleanupExpiredTexts() {
        Instant currentTime = Instant.now();
        Text deletedText = textRepository.deleteByExpirationTime(currentTime);

        getStringProxy().deleteText(deletedText);
    }

    private TextCleanupScheduler getStringProxy() {
        return applicationContext.getBean(TextCleanupScheduler.class);
    }

    @CacheEvict(value = "text", key = "#text.hash")
    public void deleteText(Text text) {}
}
