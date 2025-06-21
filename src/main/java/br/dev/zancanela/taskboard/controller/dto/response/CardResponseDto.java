package br.dev.zancanela.taskboard.controller.dto.response;

import br.dev.zancanela.taskboard.entity.Card;
import br.dev.zancanela.taskboard.entity.CardMovimentacao;
import br.dev.zancanela.taskboard.enums.ColunaTipo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record CardResponseDto(
        Long id,
        String titulo,
        String descricao,
        @JsonProperty(value = "data_criacao")
        String dataCriacao,
        @JsonProperty(value = "coluna_atual_id")
        Long colunaAtualId,
        Boolean bloqueado,
        List<CardBloqueioResponse> bloqueios,
        List<CardMovimentacaoResponse> movimentacoes
) {
    public static CardResponseDto fromEntity(Card card) {
        List<CardBloqueioResponse> bloqueios = card.getBloqueios() != null
                ? card.getBloqueios().stream().map(CardBloqueioResponse::fromEntity).toList()
                : List.of();
        List<CardMovimentacaoResponse> movimentacoes = card.getMovimentacoes() != null
                ? card.getMovimentacoes().stream().map(CardMovimentacaoResponse::fromEntity).toList()
                : List.of();

        return new CardResponseDto(
                card.getId(),
                card.getTitulo(),
                card.getDescricao(),
                card.getDataCriacao() != null
                        ? card.getDataCriacao().toString()
                        : null,
                card.getColunaAtual() != null
                        ? card.getColunaAtual().getId()
                        : null,
                card.getBloqueado(),
                bloqueios,
                movimentacoes
        );
    }

}