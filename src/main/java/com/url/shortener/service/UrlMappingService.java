package com.url.shortener.service;

import com.url.shortener.DTOs.ClickEventDTO;
import com.url.shortener.DTOs.UrlMappingDTO;
import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.User;
import com.url.shortener.models.Mapping;
import com.url.shortener.repository.ClickEventRepository;
import com.url.shortener.repository.MappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {
    private ClickEventRepository clickEventRepository;
    private MappingRepository urlRepository;

    private UrlMappingDTO convertToDto(Mapping urlMapping){
        UrlMappingDTO dto = new UrlMappingDTO();
        dto.setClickCount(urlMapping.getClickCount());
        dto.setUsername(urlMapping.getUser().getUsername());
        dto.setOriginalUrl(urlMapping.getOriginalUrl());
        dto.setShortUrl(urlMapping.getShortUrl());
        dto.setId(urlMapping.getId());
        dto.setCreationDate(urlMapping.getCreatedDate());
        return dto;
    }

    public UrlMappingDTO createShortUrl(String originalUrl, User user){
        String shortUrl = generateShortUrl();
        Mapping urlMapping = new Mapping();
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        Mapping saved = urlRepository.save(urlMapping);
        return convertToDto(saved);
    }

    private String generateShortUrl() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder url = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            url.append(characters.charAt(random.nextInt(characters.length())));
        }
        return url.toString();
    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {
        return urlRepository.findByUser(user).stream().map(this::convertToDto).toList();
    }

    public List<ClickEventDTO> getClickEventsByDate(String shorturl, LocalDateTime start, LocalDateTime end) {
        Mapping urlMapping = urlRepository.findByShortUrl(shorturl);
        if(urlMapping != null){
            return clickEventRepository.findByMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(),
                            Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickEventDTO dto = new ClickEventDTO();
                        dto.setClickDate(entry.getKey());
                        dto.setCount(entry.getValue());
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<Mapping> urlMappings = urlRepository.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepository.findByMappingInClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));
    }
}
