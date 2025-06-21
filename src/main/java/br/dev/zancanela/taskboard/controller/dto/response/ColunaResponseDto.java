package br.dev.zancanela.taskboard.controller.dto.response;

import br.dev.zancanela.taskboard.entity.Card;
import br.dev.zancanela.taskboard.entity.Coluna;
import br.dev.zancanela.taskboard.enums.ColunaTipo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public record ColunaResponseDto(
        Long id,
        String name,
        Integer ordem,
        @Enumerated(EnumType.STRING)
        ColunaTipo colunaTipo,
        Long boardId,
        List<Long> cardIds
) {
    public static ColunaResponseDto fromEntity(Coluna coluna) {
        List<Long> cardIds = coluna.getCards() != null
                ? coluna.getCards().stream().map(Card::getId).toList()
                : List.of();
        return new ColunaResponseDto(
                coluna.getId(),
                coluna.getNome(),
                coluna.getOrdem(),
                coluna.getTipo(),
                coluna.getBoard() != null ? coluna.getBoard().getId() : null,
                cardIds
        );
    }

    public static List<ColunaResponseDto> fromListEntity(List<Coluna> colunas) {
        return colunas.stream().map(ColunaResponseDto::fromEntity).toList();
    }
}
