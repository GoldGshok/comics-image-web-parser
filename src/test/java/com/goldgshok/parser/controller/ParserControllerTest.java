package com.goldgshok.parser.controller;

import com.goldgshok.parser.BaseControllerTest;
import com.goldgshok.parser.request.ParameterRequest;
import com.goldgshok.parser.service.Parser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ParserControllerTest extends BaseControllerTest {

    @MockBean
    private Parser parser;

    @Test
    void convert_baseCase_success() {
        var request = new ParameterRequest();
        request.setUrl("123");
        request.setStartPageNumber(1);
        request.setEndPageNumber(2);
        request.setDivName("div");

        makeRequest("/api/parser/download", request);

        verify(parser).downloadImages(any());
    }

    @Test
    void convert_emptyRequest_throwException() throws Exception {
        var result = makeRequest("/api/parser/download", null);
        result.andExpect(status().is4xxClientError());

        verifyNoInteractions(parser);
    }

    @Test
    void convert_emptyUrl_throwException() throws Exception {
        var request = new ParameterRequest();
        request.setStartPageNumber(1);
        request.setEndPageNumber(2);
        request.setDivName("div");

        var result = makeRequest("/api/parser/download", null);
        result.andExpect(status().is4xxClientError());

        verifyNoInteractions(parser);
    }

    @Test
    void convert_emptyStartPage_throwException() throws Exception {
        var request = new ParameterRequest();
        request.setUrl("123");
        request.setEndPageNumber(2);
        request.setDivName("div");

        var result = makeRequest("/api/parser/download", null);
        result.andExpect(status().is4xxClientError());

        verifyNoInteractions(parser);
    }

}