package br.dev.zancanela.taskboard.controller.dto.request;

import br.dev.zancanela.taskboard.entity.Card;
import br.dev.zancanela.taskboard.entity.Coluna;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CardRequestDto(
        @NotBlank
        String titulo,
        @NotBlank
        String description,
        @Min(1)
        Long colunaId
) {

    public Card toEntity() {
        Coluna coluna = new Coluna();
        coluna.setId(this.colunaId());

        Card card = new Card();
        card.setTitulo(this.titulo());
        card.setDescricao(this.description());
        card.setColunaAtual(coluna);
        return card;
    }
}
