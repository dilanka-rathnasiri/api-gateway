package com.example.sse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
public class SseController {
    @GetMapping("/sse")
    public SseEmitter streamRandomNumbers() {
        SseEmitter emitter = new SseEmitter(30_000L);
        Thread.ofVirtual().start(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    emitter.send(i);
                    Thread.sleep(1000L);
                }
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
}
