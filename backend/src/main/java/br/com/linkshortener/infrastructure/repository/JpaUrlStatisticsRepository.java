package br.com.linkshortener.infrastructure.repository;

import br.com.linkshortener.domain.model.UrlStatistics;
import br.com.linkshortener.domain.repository.UrlStatisticsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUrlStatisticsRepository extends JpaRepository<UrlStatistics, Long>, UrlStatisticsRepository {
}
