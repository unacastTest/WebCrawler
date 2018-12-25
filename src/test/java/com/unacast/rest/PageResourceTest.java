package com.unacast.rest;

import com.google.gson.JsonParser;
import com.unacast.WebCrawlerApplication;
import com.unacast.tracker.JobTracker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = WebCrawlerApplication.class)
public class PageResourceTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JobTracker jobTracker;
    private String jobId;

    @Test
    public void pageResourceTest() throws Exception {
        feedEndpointTest();
        fetchEndpointTest();
    }

    private void feedEndpointTest() throws Exception {
        // given
        String url = "https://www.baeldung.com";

        // when
        ResultActions perform = mvc.perform(get("/page/feed?url=" + url));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("request has been sent")));

        String response = perform
                .andReturn()
                .getResponse()
                .getContentAsString();

        jobId = new JsonParser()
                .parse(response)
                .getAsJsonObject()
                .get("jobId")
                .getAsString();
    }

    private void fetchEndpointTest() throws Exception {
        // given
        jobTracker.get(UUID.fromString(jobId)).get();

        // when
        ResultActions perform = mvc.perform(get("/page/fetch/" + jobId));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("job has finished with success")));
    }

}
