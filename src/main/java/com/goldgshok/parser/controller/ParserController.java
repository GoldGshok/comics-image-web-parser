package com.goldgshok.parser.controller;

import com.goldgshok.parser.request.ParameterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.goldgshok.parser.service.Parser;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parser")
@Tag(name = "Контролер парсера", description = "API для скачивания картинок комиксов с сайтов")
public class ParserController {

    private final Parser parser;

    @PostMapping("/download")
    @Operation(summary = "Загрузка картинок с сайта")
    public void downloadImages(@RequestBody ParameterRequest request) {
        Objects.requireNonNull(request, "Request is empty!");
        Objects.requireNonNull(request.getUrl(), "URL is empty!");
        Objects.requireNonNull(request.getStartPageNumber(), "Start page number is empty!");
        Objects.requireNonNull(request.getEndPageNumber(), "End page number is empty!");
        Objects.requireNonNull(request.getDivName(), "Div name block is empty!");
        parser.downloadImages(request);
    }

}
