package com.unacast.util;

import com.unacast.model.Page;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.asynchttpclient.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageConverter {

    private static final String TITLE_REGEX = "<head>.*?<title>(.*?)</title>.*?</head>";

    public static Page fromRequest(final Response response) {
        String responseBody = response.getResponseBody();
        return Page.builder()
                .title(getTitlePage(responseBody))
                .url(response.getUri().toUrl())
                .contents(responseBody)
                .build();
    }

    private static String getTitlePage(String responseBody) {
        Matcher m = compile(TITLE_REGEX, Pattern.DOTALL).matcher(responseBody);
        return m.find() ? m.group(1) : "";
    }

}
