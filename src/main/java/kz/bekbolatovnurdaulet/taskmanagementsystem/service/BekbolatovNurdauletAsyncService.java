package kz.bekbolatovnurdaulet.taskmanagementsystem.service;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BekbolatovNurdauletAsyncService {
    @Async
    public CompletableFuture<Void> sendTaskCreatedNotification(String title, String email) {
        log.info("Async notification: task '{}' was assigned to {}", title, email);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<String> generateTaskReport(Long taskId) {
        String report = "Async report generated for task id " + taskId;
        log.info(report);
        return CompletableFuture.completedFuture(report);
    }
}
