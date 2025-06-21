package br.dev.zancanela.taskboard.exception;

public class BadRequestException extends TaskboardApiException {
    public BadRequestException(String message) {
        super(message);
    }
}