package br.com.linkshortener.domain.repository;

import br.com.linkshortener.domain.model.ShortUrl;
import br.com.linkshortener.domain.model.UrlStatistics;

import java.util.Optional;

public interface UrlStatisticsRepository {

    Optional<UrlStatistics> findByShortUrl(ShortUrl shortUrl);

}
