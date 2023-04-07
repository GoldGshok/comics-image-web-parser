package com.goldgshok.parser.service;

import com.goldgshok.parser.request.ParameterRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;

@Slf4j
@Service
public class Parser {

    public void downloadImages(ParameterRequest request) {
        log.info("Start load comics");
        var additionalPage = request.getStartAdditionalNumber();
        for (var page = request.getStartPageNumber(); page < request.getEndPageNumber(); page++) {
            var url = getUrl(request, page, additionalPage);
            try {
                var doc = Jsoup.connect(url).get();
                var elements = doc.getElementsByClass(request.getDivName());
                if (elements.isEmpty()) {
                    throw new RuntimeException("No elements");
                }
                var folder = createAndGetComicsDirectory(page);
                for (var element : elements) {
                    var images = element.getElementsByTag("img");
                    for (Element image : images) {
                        var path = image.attr("data-alternative");
                        var fileName = image.attr("id");
                        saveImage(path, folder, fileName);
                        log.info("path: {}, page: {}", path, fileName);
                    }
                }
            } catch (Exception e) {
                log.info("Can't load for page {}. {}", page, e.getMessage());
                if (additionalPage != null && additionalPage < request.getEndAdditionalNumber()) {
                    additionalPage++;
                    --page;
                }
            }
            log.info("End loads");
        }
    }

    private String getUrl(ParameterRequest request, Integer page, Integer additionalPage) {
        var url = request.getUrl();
        if (request.getTemplateURI().isEmpty()) {
            url = String.format(url, page);
        } else {
            if (additionalPage != null) {
                var template = String.format(request.getTemplateURI(), additionalPage, page);
                url = String.format(url, template);
            }
        }
        return url;
    }

    private String createAndGetComicsDirectory(int page) {
        var folder = String.format("%d", page);
        var file = new File(folder);
        if (file.mkdir()) {
            log.info("Folder {} is created", folder);
        }
        return folder;
    }

    private void saveImage(String url, String folder, String fileName) {
        fileName = String.format("%s/%s.%s", folder, fileName, url.substring(url.length() - 3));

        try (var readableByteChannel = Channels.newChannel(new URL(url).openStream());
                var fileOutputStream = new FileOutputStream(fileName)) {
            var fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            log.error("Can't save file: {}", fileName, e);
        }
    }

}
