package com.unacast.tracker;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.unacast.service.PageRequest;
import lombok.AllArgsConstructor;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class JobTracker {

    private static final Cache<UUID, ListenableFuture<Response>> tracker = Caffeine.newBuilder()
            .maximumSize(10_000)
            .build();

    private PageRequest pageRequest;

    public UUID put(final String url) {
        UUID randomId = UUID.randomUUID();
        tracker.put(randomId, pageRequest.get(url));
        return randomId;
    }

    public ListenableFuture<Response> get(final UUID id) {
        return tracker.getIfPresent(id);
    }

}
