package com.unacast.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Page {

    private String title;
    private LocalDate date; // TODO: not sure what do you mean by scraped date
    private String url;
    private String contents;
}
