package com.url.shortener.repository;

import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.Mapping;
import com.url.shortener.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    List<ClickEvent> findByMappingAndClickDateBetween(Mapping mapping, LocalDateTime startDate, LocalDateTime endDate);
    List<ClickEvent> findByMappingInAndClickDateBetween(List<Mapping> mapping, LocalDateTime startDate, LocalDateTime endDate);

}
