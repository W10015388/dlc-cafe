package com.tableorder.sse.adapter.in.web;

import com.tableorder.sse.application.SseEventService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/events")
public class SseController {

    private final SseEventService sseEventService;

    public SseController(SseEventService sseEventService) {
        this.sseEventService = sseEventService;
    }

    @GetMapping(value = "/store/{storeId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> subscribeStore(@PathVariable Long storeId) {
        return sseEventService.subscribeStore(storeId);
    }

    @GetMapping(value = "/table/{tableId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> subscribeTable(@PathVariable Long tableId) {
        return sseEventService.subscribeTable(tableId);
    }
}
