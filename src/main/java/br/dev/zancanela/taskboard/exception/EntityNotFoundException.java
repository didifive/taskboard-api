package br.dev.zancanela.taskboard.exception;

public class EntityNotFoundException extends TaskboardApiException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}