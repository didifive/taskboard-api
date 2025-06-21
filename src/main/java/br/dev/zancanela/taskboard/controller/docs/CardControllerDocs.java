package br.dev.zancanela.taskboard.controller.docs;

import br.dev.zancanela.taskboard.controller.dto.request.CardRequestDto;
import br.dev.zancanela.taskboard.controller.dto.response.ApiErrorDto;
import br.dev.zancanela.taskboard.controller.dto.response.CardResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Tag(name = "Card", description = "Operações relacionadas aos cards")
public interface CardControllerDocs {

    @Operation(summary = "Cria um novo card",
            description = "Cria um novo card na coluna INICIAL")
    @ApiResponse(responseCode = "201", description = "Card criado com sucesso")
    @ApiResponse(responseCode = "400",
            description = "Dados para card inválidos ou coluna não é inicial",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Coluna não encontrada")
    ResponseEntity<CardResponseDto> createCard(
            CardRequestDto cardRequestDto,
            BindingResult bindingResult);

    @Operation(summary = "Recupera um card pelo id",
            description = "Recupera um card com o id especificado")
    @ApiResponse(responseCode = "200", description = "Coluna encontrada")
    @ApiResponse(responseCode = "404",
            description = "Card não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<CardResponseDto> getCard(Long id);

    @Operation(summary = "Atualiza o nome de um card",
            description = "Atualiza o nome de um card com o id especificado")
    @ApiResponse(responseCode = "200", description = "Nome do card atualizado")
    @ApiResponse(responseCode = "404",
            description = "Card não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<CardResponseDto> updateTituloCard(
            Long id,
            String titulo);

    @Operation(summary = "Atualiza a descrição de um card",
            description = "Atualiza a descrição de um card com o id especificado")
    @ApiResponse(responseCode = "200", description = "Descrição do card atualizada")
    @ApiResponse(responseCode = "404",
            description = "Card não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<CardResponseDto> updateDescricaoCard(
            Long id,
            String descricao);

    @Operation(summary = "Move card para próxima coluna",
            description = "Move um card para a próxima coluna na sequência")
    @ApiResponse(responseCode = "200", description = "Card movido com sucesso")
    @ApiResponse(responseCode = "404",
            description = "Card não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Coluna não encontrada",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<CardResponseDto> moveCard(
            Long id,
            Long colunaId);

    @Operation(summary = "Bloqueia um card",
            description = "Bloqueia um card com o id especificado")
    @ApiResponse(responseCode = "200", description = "Card bloqueado com sucesso")
    @ApiResponse(responseCode = "400",
            description = "Não é possível bloquear um card que já está bloqueado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Card não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<CardResponseDto> bloquearCard(
            Long id,
            String motivoBloqueio);

    @Operation(summary = "Desbloqueia um card",
            description = "Desbloqueia um card com o id especificado")
    @ApiResponse(responseCode = "200", description = "Card desbloqueado com sucesso")
    @ApiResponse(responseCode = "400",
            description = "Não é possível desbloquear um card que não está bloqueado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Card não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "404",
            description = "Não foi encontrado bloqueio cadastrado para o card",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<CardResponseDto> desbloquearCard(
            Long id,
            String motivoDesbloqueio);

    @Operation(summary = "Deleta um card",
            description = "Deleta um card com o id especificado")
    @ApiResponse(responseCode = "204", description = "Card deletado com sucesso")
    @ApiResponse(responseCode = "404",
            description = "Card não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "409",
            description = "Card não pode ser deletado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<Void> deleteCard(Long id);

}
