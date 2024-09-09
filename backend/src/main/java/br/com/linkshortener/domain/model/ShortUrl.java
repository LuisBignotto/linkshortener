package br.com.linkshortener.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "short_url")
public class ShortUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;

    private String shortUrl;

    @OneToOne(mappedBy = "shortUrl", cascade = CascadeType.ALL, orphanRemoval = true)
    private UrlStatistics urlStatistics;
}
