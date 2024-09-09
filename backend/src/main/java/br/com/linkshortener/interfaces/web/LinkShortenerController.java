package br.com.linkshortener.interfaces.web;


import br.com.linkshortener.application.dto.CreateShortUrlRequest;
import br.com.linkshortener.application.dto.ShortUrlResponse;
import br.com.linkshortener.application.service.LinkShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("")
public class LinkShortenerController {

    @Autowired
    private LinkShortenerService linkShortenerService;

    @PostMapping("/shortlink")
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody CreateShortUrlRequest originalURl){
        ShortUrlResponse shortUrl = linkShortenerService.createShortUrl(originalURl.originalUrl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{url}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String url) throws URISyntaxException {
        String shortUrlResponse = linkShortenerService.getOriginalUrl(url);
        URI uri = new URI(shortUrlResponse);
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

}
