package br.dev.zancanela.taskboard.controller.dto.response;

import br.dev.zancanela.taskboard.entity.Board;

import java.util.List;

public record BoardResponseDto(
        Long id,
        String name
) {
    public static synchronized BoardResponseDto fromEntity(Board board) {
        return new BoardResponseDto(board.getId(), board.getNome());
    }

    public static synchronized List<BoardResponseDto> fromListEntity(List<Board> boards) {
        return boards.stream().map(BoardResponseDto::fromEntity).toList();
    }
}
