package br.dev.zancanela.taskboard.controller.dto.response;

import java.time.Instant;

public record ApiErrorDto(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
