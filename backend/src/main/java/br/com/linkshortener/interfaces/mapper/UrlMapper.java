package br.com.linkshortener.interfaces.mapper;

import br.com.linkshortener.application.dto.CreateShortUrlRequest;
import br.com.linkshortener.application.dto.ShortUrlResponse;
import br.com.linkshortener.domain.model.ShortUrl;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    public ShortUrl toEntity(CreateShortUrlRequest originalUrl){
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl.originalUrl());
        return shortUrl;
    }

    public ShortUrlResponse toResponse(ShortUrl shortUrl) {
        String url = shortUrl.getShortUrl();
        String pattern = "^(https?:\\/\\/).*";

        if (!url.matches(pattern)) {
            url = "http://localhost/." + url;
        }

        return new ShortUrlResponse(url);
    }
}
