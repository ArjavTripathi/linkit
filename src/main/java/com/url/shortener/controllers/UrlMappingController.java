package com.url.shortener.controllers;

import com.url.shortener.DTOs.ClickEventDTO;
import com.url.shortener.DTOs.UrlMappingDTO;
import com.url.shortener.models.User;
import com.url.shortener.service.UrlMappingService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService userService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String, String> Request,
                                                        Principal principal){
        String originalUrl = Request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());

        UrlMappingDTO finals = urlMappingService.createShortUrl(originalUrl, user);
        return ResponseEntity.ok(finals);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/myurls")
    public ResponseEntity<List<UrlMappingDTO>> getUsersUrls(Principal principal){
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingDTO> urls = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/analytics/{shorturl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shorturl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> dto = urlMappingService.getClickEventsByDate(shorturl, start, end);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
                                                                     @RequestParam("startDate") String startDate,
                                                                     @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        User user = userService.findByUsername(principal.getName());
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Map<LocalDate, Long> dto = urlMappingService.getTotalClicksByUserAndDate(user, start, end);
        return ResponseEntity.ok(dto);
    }
}
