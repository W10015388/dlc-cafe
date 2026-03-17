package com.tableorder.sse.application;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEventService {

    private final Map<Long, Sinks.Many<ServerSentEvent<Object>>> storeSinks = new ConcurrentHashMap<>();
    private final Map<Long, Sinks.Many<ServerSentEvent<Object>>> tableSinks = new ConcurrentHashMap<>();

    public Flux<ServerSentEvent<Object>> subscribeStore(Long storeId) {
        Sinks.Many<ServerSentEvent<Object>> sink = storeSinks.computeIfAbsent(storeId,
                k -> Sinks.many().multicast().onBackpressureBuffer());
        return sink.asFlux().doOnCancel(() -> storeSinks.remove(storeId));
    }

    public Flux<ServerSentEvent<Object>> subscribeTable(Long tableId) {
        Sinks.Many<ServerSentEvent<Object>> sink = tableSinks.computeIfAbsent(tableId,
                k -> Sinks.many().multicast().onBackpressureBuffer());
        return sink.asFlux().doOnCancel(() -> tableSinks.remove(tableId));
    }

    public void publishToStore(Long storeId, String eventType, Object data) {
        if (storeId == null) return;
        Sinks.Many<ServerSentEvent<Object>> sink = storeSinks.get(storeId);
        if (sink != null) {
            sink.tryEmitNext(ServerSentEvent.builder().event(eventType).data(data).build());
        }
    }

    public void publishToTable(Long tableId, String eventType, Object data) {
        if (tableId == null) return;
        Sinks.Many<ServerSentEvent<Object>> sink = tableSinks.get(tableId);
        if (sink != null) {
            sink.tryEmitNext(ServerSentEvent.builder().event(eventType).data(data).build());
        }
    }
}
