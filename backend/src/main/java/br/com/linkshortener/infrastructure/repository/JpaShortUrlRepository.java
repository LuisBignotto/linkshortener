package br.com.linkshortener.infrastructure.repository;

import br.com.linkshortener.domain.model.ShortUrl;
import br.com.linkshortener.domain.repository.ShortUrlRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaShortUrlRepository extends JpaRepository<ShortUrl, Long>, ShortUrlRepository {
}
