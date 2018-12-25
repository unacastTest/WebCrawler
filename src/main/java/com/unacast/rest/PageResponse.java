package com.unacast.rest;

import com.unacast.model.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
class PageResponse {

    private UUID jobId;
    private String message;
    private HttpStatus httpStatus;
    private Page page;

    PageResponse(UUID jobId, String message, HttpStatus httpStatus) {
        this(jobId, message, httpStatus, null);
    }

}
