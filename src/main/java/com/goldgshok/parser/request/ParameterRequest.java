package com.goldgshok.parser.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ParameterRequest {

    @Schema(description = "URL сайта", example = "https://mangapoisk.ru/manga/attack-on-titan-2/chapter/%s",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String url;
    @Schema(description = "Начальная страница для поиска картинок", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer startPageNumber;
    @Schema(description = "Конечная страница для поиска картинок", example = "139", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer endPageNumber;
    @Schema(description = "div-блок в котором находятся картинки", example = "mt-1 d-flex flex-column align-items-center chapter-images", requiredMode = Schema.RequiredMode.REQUIRED)
    private String divName;

    @Schema(description = "Шаблон листания страниц, добавляется к url сайта", example = "%s-%s")
    private String templateURI;
    @Schema(description = "Начало дополнительного номера страниц, когда шаблон состоит из нескольких параметров", example = "25")
    private Integer startAdditionalNumber;
    @Schema(description = "Окончание дополнительного номера страниц, когда шаблон состоит из нескольких параметров", example = "35")
    private Integer endAdditionalNumber;

}
