package br.dev.zancanela.taskboard.controller.dto.response;

import br.dev.zancanela.taskboard.entity.CardBloqueio;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardBloqueioResponse(
        Long id,
        @JsonProperty(value = "motivo_bloqueio")
        String motivoBloqueio,
        @JsonProperty(value = "data_bloqueio")
        String dataBloqueio,
        @JsonProperty(value = "motivo_desbloqueio")
        String motivoDesbloqueio,
        @JsonProperty(value = "data_desbloqueio")
        String dataDesbloqueio
) {

    public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    public static CardBloqueioResponse fromEntity(CardBloqueio bloqueio) {
        return new CardBloqueioResponse(
                bloqueio.getId(),
                bloqueio.getMotivoBloqueio(),
                bloqueio.getDataBloqueio() != null
                        ? bloqueio.getDataBloqueio().format(DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS))
                        : null,
                bloqueio.getMotivoDesbloqueio(),
                bloqueio.getDataDesbloqueio() != null
                        ? bloqueio.getDataDesbloqueio().format(DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS))
                        : null
        );
    }
}