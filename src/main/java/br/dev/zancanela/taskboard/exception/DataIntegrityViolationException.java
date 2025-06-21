package br.dev.zancanela.taskboard.exception;

public class DataIntegrityViolationException extends TaskboardApiException {
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}