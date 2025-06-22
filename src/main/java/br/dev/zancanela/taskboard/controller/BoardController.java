package br.dev.zancanela.taskboard.controller;

import br.dev.zancanela.taskboard.controller.docs.BoardControllerDocs;
import br.dev.zancanela.taskboard.controller.dto.response.BoardResponseDto;
import br.dev.zancanela.taskboard.controller.dto.response.ColunaResponseDto;
import br.dev.zancanela.taskboard.entity.Board;
import br.dev.zancanela.taskboard.entity.Coluna;
import br.dev.zancanela.taskboard.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController implements BoardControllerDocs {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        return ResponseEntity.ok(BoardResponseDto.fromListEntity(boardService.getBoards()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id) {
        Board board = boardService.getBoard(id);
        return ResponseEntity.ok(BoardResponseDto.fromEntity(board));
    }

    @GetMapping("/{id}/coluna")
    public ResponseEntity<List<ColunaResponseDto>> getColunasFromBoards(@PathVariable Long id) {
        return ResponseEntity.ok(ColunaResponseDto.fromListEntity(
                boardService.getColunas(id)
        ));
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestParam String name) {
        Board board = boardService.createBoard(name);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/board/{id}")
                .buildAndExpand(board.getId().toString()).toUri();
        return ResponseEntity.created(uri).body(BoardResponseDto.fromEntity(board));
    }

    @PostMapping("/{id}/coluna")
    public ResponseEntity<ColunaResponseDto> createColuna(
            @PathVariable Long id,
            @RequestParam String name) {
        Coluna coluna = boardService.createColunaPendente(id, name);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/coluna/{id}")
                .buildAndExpand(coluna.getId().toString()).toUri();
        return ResponseEntity.created(uri).body(ColunaResponseDto.fromEntity(coluna));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestParam String name) {
        Board board = boardService.updateBoard(id, name);
        return ResponseEntity.ok(BoardResponseDto.fromEntity(board));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
