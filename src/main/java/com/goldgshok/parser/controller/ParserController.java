package com.goldgshok.parser.controller;

import com.goldgshok.parser.request.ParameterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.goldgshok.parser.service.Parser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parser")
@Tag(name = "Контролер парсера", description = "API для скачивания картинок комиксов с сайтов")
public class ParserController {

    private final Parser parser;

    @PostMapping("/download")
    @Operation(summary = "Загрузка картинок с сайта")
    public void downloadImages(@RequestBody ParameterRequest request) {
        parser.downloadImages(request);
    }

}
