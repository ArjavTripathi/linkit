package com.url.shortener.repository;

import com.url.shortener.models.Mapping;
import com.url.shortener.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MappingRepository extends JpaRepository<Mapping, Long> {
    Mapping findByShortUrl(String shortUrl);
    List<Mapping> findByUser(User user);
}
