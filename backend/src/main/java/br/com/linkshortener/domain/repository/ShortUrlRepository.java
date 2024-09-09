package br.com.linkshortener.domain.repository;

import br.com.linkshortener.domain.model.ShortUrl;
import java.util.Optional;

public interface ShortUrlRepository {

    Optional<ShortUrl> findByShortUrl(String shortUrl);

}
