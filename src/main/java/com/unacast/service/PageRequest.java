package com.unacast.service;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Service;

@Service
public class PageRequest {

    private static final AsyncHttpClient client = Dsl.asyncHttpClient();

    public ListenableFuture<Response> get(final String url) {
        return client.prepareGet(url).execute();
    }

}
