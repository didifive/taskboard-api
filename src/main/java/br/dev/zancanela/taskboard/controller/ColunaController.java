package br.dev.zancanela.taskboard.controller;

import br.dev.zancanela.taskboard.controller.docs.ColunaControllerDocs;
import br.dev.zancanela.taskboard.controller.dto.response.ColunaResponseDto;
import br.dev.zancanela.taskboard.entity.Coluna;
import br.dev.zancanela.taskboard.service.ColunaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coluna")
public class ColunaController implements ColunaControllerDocs {

    private final ColunaService colunaService;

    public ColunaController(ColunaService colunaService) {
        this.colunaService = colunaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColunaResponseDto> getColuna(@PathVariable Long id) {
        Coluna coluna = colunaService.getColuna(id);
        return ResponseEntity.ok(ColunaResponseDto.fromEntity(coluna));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ColunaResponseDto> updateNomeColuna(@PathVariable Long id, @RequestParam String name) {
        Coluna coluna = colunaService.updateNomeColuna(id, name);
        return ResponseEntity.ok(ColunaResponseDto.fromEntity(coluna));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColuna(@PathVariable Long id) {
        colunaService.deleteColuna(id);
        return ResponseEntity.noContent().build();
    }
}
