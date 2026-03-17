package com.tableorder.sse.application;

import org.junit.jupiter.api.Test;
import org.springframework.http.codec.ServerSentEvent;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SseEventServiceTest {

    @Test
    void subscribeAndPublish_table() {
        SseEventService service = new SseEventService();
        var flux = service.subscribeTable(1L);

        service.publishToTable(1L, "NEW_ORDER", Map.of("orderId", 1));

        StepVerifier.create(flux.take(1))
                .assertNext(sse -> {
                    assertThat(sse.event()).isEqualTo("NEW_ORDER");
                })
                .verifyComplete();
    }

    @Test
    void publishToNullTableId_doesNothing() {
        SseEventService service = new SseEventService();
        // should not throw
        service.publishToTable(null, "TEST", Map.of());
    }
}
