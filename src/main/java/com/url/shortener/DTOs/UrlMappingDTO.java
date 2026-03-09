package com.url.shortener.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlMappingDTO {
    private String originalUrl;
    private String shortUrl;
    private Long id;
    private int clickCount;
    private LocalDateTime creationDate;
    private String username;
}
