package br.dev.zancanela.taskboard.controller;

import br.dev.zancanela.taskboard.controller.docs.CardControllerDocs;
import br.dev.zancanela.taskboard.controller.dto.request.CardRequestDto;
import br.dev.zancanela.taskboard.controller.dto.response.CardResponseDto;
import br.dev.zancanela.taskboard.entity.Card;
import br.dev.zancanela.taskboard.exception.BadRequestException;
import br.dev.zancanela.taskboard.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/card")
public class CardController implements CardControllerDocs {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponseDto> createCard(
            @RequestBody @Valid CardRequestDto cardRequestDto
            , BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(
                    bindingResult.getFieldErrors().stream()
                            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                            .collect(Collectors.joining("||")));
        }

        Card card = cardService.createCard(cardRequestDto.toEntity());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(card.getId().toString()).toUri();
        return ResponseEntity.created(uri).body(CardResponseDto.fromEntity(card));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long id) {
        return ResponseEntity.ok(CardResponseDto.fromEntity(cardService.getCard(id)));
    }

    @PatchMapping("/{id}/titulo/{titulo}")
    public ResponseEntity<CardResponseDto> updateTituloCard(
            @PathVariable Long id,
            @PathVariable String titulo) {
        return ResponseEntity.ok(CardResponseDto.fromEntity(
                cardService.updateTituloCard(id, titulo)
        ));
    }

    @PatchMapping("/{id}/descricao/{descricao}")
    public ResponseEntity<CardResponseDto> updateDescricaoCard(
            @PathVariable Long id,
            @PathVariable String descricao) {
        return ResponseEntity.ok(CardResponseDto.fromEntity(
                cardService.updateDescricaoCard(id, descricao)
        ));
    }

    @PatchMapping("/{id}/move/coluna/{colunaId}")
    public ResponseEntity<CardResponseDto> moveCard(
            @PathVariable Long id,
            @PathVariable Long colunaId) {
        return ResponseEntity.ok(CardResponseDto.fromEntity(
                cardService.moveCard(id, colunaId)
        ));
    }

    @PatchMapping("/{id}/cancela")
    public ResponseEntity<CardResponseDto> cancelaCard(
            @PathVariable Long id) {
        return ResponseEntity.ok(CardResponseDto.fromEntity(
                cardService.cancelaCard(id)
        ));
    }

    @PatchMapping("/{id}/bloquear/{motivoBloqueio}")
    public ResponseEntity<CardResponseDto> bloquearCard(
            @PathVariable Long id,
            @PathVariable String motivoBloqueio) {
        return ResponseEntity.ok(CardResponseDto.fromEntity(
                cardService.bloquearCard(id, motivoBloqueio)
        ));
    }

    @PatchMapping("/{id}/desbloquear/{motivoDesbloqueio}")
    public ResponseEntity<CardResponseDto> desbloquearCard(
            @PathVariable Long id,
            @PathVariable String motivoDesbloqueio) {
        return ResponseEntity.ok(CardResponseDto.fromEntity(
                cardService.desbloquearCard(id, motivoDesbloqueio)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(
            @PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

}
