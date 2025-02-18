package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс представляет собой приложение, которое запускает Spring Boot.
 */
@SpringBootApplication
public class EWMStatsServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(EWMStatsServiceApp.class, args);
    }
}