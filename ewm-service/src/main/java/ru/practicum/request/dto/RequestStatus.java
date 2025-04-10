package ru.practicum.request.dto;

/**
 * Перечисление, представляющее возможные статусы запроса на участие в событии.
 * Используется для указания текущего состояния запроса.
 */
public enum RequestStatus {
    CONFIRMED, REJECTED, PENDING, CANCELED
}