package com.unacast.rest;

import com.unacast.tracker.JobTracker;
import lombok.AllArgsConstructor;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.unacast.util.PageConverter.fromRequest;
import static org.springframework.http.HttpStatus.*;

@RequestMapping("/page")
@RestController
@AllArgsConstructor
public class PageResource {

    private JobTracker jobTracker;

    @GetMapping("/feed")
    public PageResponse feed(@RequestParam String url) {
        return new PageResponse(jobTracker.put(url), "request has been sent", OK);
    }

    @GetMapping("/fetch/{jobId}")
    public PageResponse fetch(@PathVariable String jobId) throws ExecutionException, InterruptedException {
        final UUID uuId = UUID.fromString(jobId);
        ListenableFuture<Response> futureResult = jobTracker.get(uuId);
        if (futureResult == null) {
            return new PageResponse(uuId, "no running job for given jobId", NOT_FOUND);
        }
        if (futureResult.isDone()) {
            return new PageResponse(uuId, "job has finished with success", OK, fromRequest(futureResult.get()));
        }
        return new PageResponse(uuId, "job in progress", PROCESSING);
    }

}
