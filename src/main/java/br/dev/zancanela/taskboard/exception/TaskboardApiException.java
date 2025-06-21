package br.dev.zancanela.taskboard.exception;

public class TaskboardApiException  extends RuntimeException {
    public TaskboardApiException(String message) {
        super(message);
    }
}