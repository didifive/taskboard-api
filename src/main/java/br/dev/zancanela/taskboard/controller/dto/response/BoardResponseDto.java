package br.dev.zancanela.taskboard.controller.dto.response;

import br.dev.zancanela.taskboard.entity.Board;

import java.util.List;

public record BoardResponseDto(
        Long id,
        String name
) {
    public static BoardResponseDto fromEntity(Board board) {
        return new BoardResponseDto(board.getId(), board.getNome());
    }

    public static List<BoardResponseDto> fromListEntity(List<Board> boards) {
        return boards.stream().map(BoardResponseDto::fromEntity).toList();
    }
}
