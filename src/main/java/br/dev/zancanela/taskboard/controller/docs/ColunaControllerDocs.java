package br.dev.zancanela.taskboard.controller.docs;

import br.dev.zancanela.taskboard.controller.dto.response.ApiErrorDto;
import br.dev.zancanela.taskboard.controller.dto.response.ColunaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Coluna", description = "Operações relacionadas às colunas")
public interface ColunaControllerDocs {

    @Operation(summary = "Recupera uma coluna pelo id",
            description = "Recupera uma coluna com o id especificado com os respectivos cards")
    @ApiResponse(responseCode = "200", description = "Coluna encontrada")
    @ApiResponse(responseCode = "404",
            description = "Coluna não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<ColunaResponseDto> getColuna(Long id);

    @Operation(summary = "Atualiza o nome de uma coluna",
            description = "Atualiza o nome de uma coluna do tipo PENDENTE com o id especificado")
    @ApiResponse(responseCode = "200", description = "Nome da coluna atualizada")
    @ApiResponse(responseCode = "404",
            description = "Coluna não encontrada",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<ColunaResponseDto> updateNomeColuna(
            Long id,
            String name);

    @Operation(summary = "Deleta uma coluna",
            description = "Deleta uma coluna do tipo PENDENTE com o id especificado")
    @ApiResponse(responseCode = "204", description = "Coluna deletada com sucesso")
    @ApiResponse(responseCode = "404",
            description = "Coluna não encontrada",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "409",
            description = "Coluna não pode ser deletada",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<Void> deleteColuna(Long id);

}
