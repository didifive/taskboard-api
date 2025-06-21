package br.dev.zancanela.taskboard.controller.dto.response;

import br.dev.zancanela.taskboard.entity.CardMovimentacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardMovimentacaoResponse(
        Long id,
        Long colunaId,
        @JsonProperty(value = "data_entrada")
        String dataEntrada,
        @JsonProperty(value = "data_saida")
        String dataSaida

) {
    public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    public static CardMovimentacaoResponse fromEntity(CardMovimentacao movimentacao) {
        return new CardMovimentacaoResponse(
                movimentacao.getId(),
                movimentacao.getColuna() != null
                        ? movimentacao.getColuna().getId()
                        : null,
                movimentacao.getDataEntrada() != null
                        ? movimentacao.getDataEntrada().format(DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS))
                        : null,
                movimentacao.getDataSaida() != null
                        ? movimentacao.getDataSaida().format(DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS))
                        : null
        );
    }
}