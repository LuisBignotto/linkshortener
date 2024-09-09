package br.com.linkshortener.application.service;

import br.com.linkshortener.application.dto.ShortUrlResponse;
import br.com.linkshortener.domain.model.ShortUrl;
import br.com.linkshortener.infrastructure.repository.JpaShortUrlRepository;
import br.com.linkshortener.infrastructure.repository.JpaUrlStatisticsRepository;
import br.com.linkshortener.interfaces.mapper.UrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class LinkShortenerService {

    @Autowired
    private JpaShortUrlRepository shortUrlRepository;

    @Autowired
    private JpaUrlStatisticsRepository urlStatisticsRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UrlMapper urlMapper;

    public final String URL_PATTERN = "^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$";

    public int URL_SIZE = 10;

    public String getOriginalUrl(String shortUrl){

        String key = "url_" + shortUrl;

        Object isCached = redisService.getData(key);

        if(isCached != null){
            ShortUrl cachedUrl = (ShortUrl) isCached;
            return cachedUrl.getOriginalUrl();
        }

        ShortUrl url = shortUrlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new IllegalArgumentException("URL not found."));

        redisService.saveData(key, url);

        return url.getOriginalUrl();
    }

    public ShortUrlResponse createShortUrl(String url) {
        validateUrl(url);

        String newUrl = generateRandomString(1);

        ShortUrl shortUrl = createAndSaveNewShortUrl(url, newUrl);

        return urlMapper.toResponse(shortUrl);
    }

    private void validateUrl(String url){
        Pattern compiledPattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = compiledPattern.matcher(url);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("This is not an URL.");
        }

        if (url.length() <= 10) {
            throw new IllegalArgumentException("URL is too short.");
        }
    }

    private String generateRandomString(int attempts) {
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

        if(attempts >= 5) {
            URL_SIZE++;
        }

        StringBuilder sb = new StringBuilder(URL_SIZE);

        for(int i = 0; i < URL_SIZE; i++){
            int random = (int) Math.floor(Math.random() * AlphaNumericStr.length());
            sb.append(AlphaNumericStr.charAt(random));
        }

        String randomString = sb.toString();

        if(existOnDatabase(randomString)){
            return generateRandomString(++attempts);
        }

        return randomString;
    }

    private boolean existOnDatabase(String generatedString){
        Optional<ShortUrl> shortUrl = shortUrlRepository.findByShortUrl(generatedString);
        return shortUrl.isPresent();
    }

    private ShortUrl createAndSaveNewShortUrl(String originalUrl, String newUrl){
        ShortUrl shortUrl = new ShortUrl();

        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortUrl(newUrl);

        shortUrlRepository.save(shortUrl);

        return shortUrl;
    }
}
