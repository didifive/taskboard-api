package br.dev.zancanela.taskboard.controller.docs;

import br.dev.zancanela.taskboard.controller.dto.response.ApiErrorDto;
import br.dev.zancanela.taskboard.controller.dto.response.BoardResponseDto;
import br.dev.zancanela.taskboard.controller.dto.response.ColunaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Board", description = "Operações relacionadas aos boards e respectivas colunas")
public interface BoardControllerDocs {

    @Operation(summary = "Lista todos os boards",
            description = "Lista todos os boards existentes")
    @ApiResponse(responseCode = "200", description = "Boards encontrados")
    ResponseEntity<List<BoardResponseDto>> getBoards();

    @Operation(summary = "Recupera um board pelo id",
            description = "Recupera um board pelo id especificado")
    @ApiResponse(responseCode = "200", description = "Board encontrado")
    @ApiResponse(responseCode = "404",
            description = "Board não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<BoardResponseDto> getBoard(Long id);

    @Operation(summary = "Lista as colunas de um board",
            description = "Lista as colunas de um board com o id especificado")
    @ApiResponse(responseCode = "200", description = "Colunas do board encontradas")
    @ApiResponse(responseCode = "404",
            description = "Board não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<List<ColunaResponseDto>> getColunasFromBoards(Long id);

    @Operation(summary = "Cria um novo board",
            description = "Cria um novo board com o nome especificado")
    @ApiResponse(responseCode = "201", description = "Board criado")
    ResponseEntity<BoardResponseDto> createBoard(String name);

    @Operation(summary = "Cria uma nova coluna para o board",
            description = "Cria uma nova coluna para o board com o id especificado")
    @ApiResponse(responseCode = "201", description = "Coluna criada")
    @ApiResponse(responseCode = "404",
            description = "Board não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<ColunaResponseDto> createColuna(
            Long id,
            String name);

    @Operation(summary = "Atualiza o nome de um board",
            description = "Atualiza o nome de um board com o id especificado")
    @ApiResponse(responseCode = "200", description = "Nome do board atualizado")
    @ApiResponse(responseCode = "404",
            description = "Board não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<BoardResponseDto> updateBoard(
            Long id,
            String name);

    @Operation(summary = "Deleta um board",
            description = "Deleta um board com o id especificado")
    @ApiResponse(responseCode = "204", description = "Board deletado com sucesso")
    @ApiResponse(responseCode = "404",
            description = "Board não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    @ApiResponse(responseCode = "409",
            description = "Board não pode ser deletado",
            content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))
    ResponseEntity<Void> deleteBoard(@PathVariable Long id);

}
