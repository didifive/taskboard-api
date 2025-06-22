package br.dev.zancanela.taskboard.service;

import br.dev.zancanela.taskboard.entity.Card;
import br.dev.zancanela.taskboard.entity.CardBloqueio;
import br.dev.zancanela.taskboard.entity.CardMovimentacao;
import br.dev.zancanela.taskboard.entity.Coluna;
import br.dev.zancanela.taskboard.enums.ColunaTipo;
import br.dev.zancanela.taskboard.exception.BadRequestException;
import br.dev.zancanela.taskboard.exception.DataIntegrityViolationException;
import br.dev.zancanela.taskboard.exception.EntityNotFoundException;
import br.dev.zancanela.taskboard.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    public static final String CARD_NOT_FOUND_WITH_ID = "Coluna not found with id [%s]: ";
    public static final String CANNOT_DELETE_CARD_WITH_ASSOCIATED_MOVIMENTACOES_OR_BLOQUEIOS = "Cannot delete card with associated movimentacoes or bloqueios.";
    public static final String CANNOT_MOVE_CARD_TO_THE_SPECIFIED_COLUMN = "Cannot move card to the specified column.";
    public static final String CANNOT_MOVE_A_BLOCKED_CARD = "Cannot move a blocked card.";
    public static final String NO_BLOQUEIO_FOUND_FOR_THE_CARD = "No bloqueio found for the card.";
    public static final String CARD_IS_NOT_BLOCKED = "Card is not blocked.";
    public static final String CANNOT_CREATE_A_CARD_IN_A_NON_INITIAL_COLUMN = "Cannot create a card in a non-initial column.";
    public static final String CANCELAMENTO_COLUMN_NOT_FOUND_FOR_THE_BOARD = "Cancelamento column not found for the board.";
    public static final String CARD_IS_ALREADY_IN_THE_CANCELAMENTO_COLUMN = "Card is already in the cancelamento column.";

    private final CardRepository cardRepository;
    private final ColunaService colunaService;

    public CardService(CardRepository cardRepository, ColunaService colunaService) {
        this.cardRepository = cardRepository;
        this.colunaService = colunaService;
    }

    public Card createCard(Card card) {
        Coluna coluna = colunaService.getColuna(card.getColunaAtual().getId());
        if (!ColunaTipo.INICIAL.equals(coluna.getTipo())) {
            throw new BadRequestException(CANNOT_CREATE_A_CARD_IN_A_NON_INITIAL_COLUMN);
        }
        card.setColunaAtual(coluna);
        card.setBloqueado(false);
        card.setDataCriacao(LocalDateTime.now());

        CardMovimentacao movimentacao = new CardMovimentacao();
        movimentacao.setDataEntrada(LocalDateTime.now());
        movimentacao.setCard(card);
        movimentacao.setColuna(coluna);
        card.setMovimentacoes(List.of(movimentacao));

        return cardRepository.save(card);
    }

    public Card getCard(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CARD_NOT_FOUND_WITH_ID, id)
                ));
    }

    public Card updateTituloCard(Long id, String titulo) {
        Card card = getCard(id);
        card.setTitulo(titulo);
        return cardRepository.save(card);
    }

    public Card updateDescricaoCard(Long id, String descricao) {
        Card card = getCard(id);
        card.setDescricao(descricao);
        return cardRepository.save(card);
    }

    public Card moveCard(Long cardId, Long novaColunaId) {
        Card card = getCard(cardId);
        Coluna antigaColuna = card.getColunaAtual();
        Coluna novaColuna = colunaService.getColuna(novaColunaId);

        boolean isColunaCancelamento = ColunaTipo.CANCELAMENTO.equals(novaColuna.getTipo());
        boolean isProximaColuna = card.getColunaAtual().getOrdem() + 1 == novaColuna.getOrdem();
        boolean movimentacaoPermitida = isColunaCancelamento || isProximaColuna;
        if (!movimentacaoPermitida) {
            throw new BadRequestException(CANNOT_MOVE_CARD_TO_THE_SPECIFIED_COLUMN);
        }

        if (Boolean.TRUE.equals(card.getBloqueado())) {
            throw new BadRequestException(CANNOT_MOVE_A_BLOCKED_CARD);
        }

        card.setColunaAtual(novaColuna);

        card.getMovimentacoes().stream()
                .filter(m -> m.getColuna().getId().equals(antigaColuna.getId()) && m.getDataSaida() == null)
                .reduce((first, second) -> second)
                .ifPresent(m -> m.setDataSaida(LocalDateTime.now()));

        CardMovimentacao novaMovimentacao = new CardMovimentacao();
        novaMovimentacao.setDataEntrada(LocalDateTime.now());
        novaMovimentacao.setCard(card);
        novaMovimentacao.setColuna(novaColuna);

        card.getMovimentacoes().add(novaMovimentacao);

        return cardRepository.save(card);
    }

    public Card cancelaCard(Long id) {
        Card card = getCard(id);

        Coluna colunaCancelamento = colunaService.getColunasByBoard(card.getColunaAtual().getBoard().getId()).stream()
                .filter(c -> ColunaTipo.CANCELAMENTO.equals(c.getTipo()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(CANCELAMENTO_COLUMN_NOT_FOUND_FOR_THE_BOARD));

        if (colunaCancelamento.equals(card.getColunaAtual())) {
            throw new BadRequestException(CARD_IS_ALREADY_IN_THE_CANCELAMENTO_COLUMN);
        }

        CardMovimentacao cancelamentoCard = new CardMovimentacao();
        cancelamentoCard.setDataEntrada(LocalDateTime.now());
        cancelamentoCard.setCard(card);
        cancelamentoCard.setColuna(colunaCancelamento);

        card.setColunaAtual(colunaCancelamento);

        card.getMovimentacoes().add(cancelamentoCard);

        return cardRepository.save(card);

    }

    public Card bloquearCard(Long id, String motivoBloqueio) {
        Card card = getCard(id);
        if (Boolean.TRUE.equals(card.getBloqueado())) {
            throw new BadRequestException("Card is already blocked.");
        }
        card.setBloqueado(true);

        CardBloqueio cardBloqueio = new CardBloqueio();
        cardBloqueio.setDataBloqueio(LocalDateTime.now());
        cardBloqueio.setMotivoBloqueio(motivoBloqueio);
        cardBloqueio.setCard(card);

        card.getBloqueios().add(cardBloqueio);

        return cardRepository.save(card);
    }

    public Card desbloquearCard(Long id, String motivoDesbloqueio) {
        Card card = getCard(id);
        if (Boolean.FALSE.equals(card.getBloqueado())) {
            throw new BadRequestException(CARD_IS_NOT_BLOCKED);
        }
        card.setBloqueado(false);

        CardBloqueio ultimoBloqueio = card.getBloqueios().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new EntityNotFoundException(NO_BLOQUEIO_FOUND_FOR_THE_CARD));

        ultimoBloqueio.setDataDesbloqueio(LocalDateTime.now());
        ultimoBloqueio.setMotivoDesbloqueio(motivoDesbloqueio);

        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        Card card = getCard(id);

        boolean cardHasMovimentacoes = card.getMovimentacoes() != null
                && !ColunaTipo.INICIAL.equals(card.getColunaAtual().getTipo())
                && card.getMovimentacoes().size() > 1;
        boolean cardHasBloqueios = card.getBloqueios() != null && !card.getBloqueios().isEmpty();
        if (cardHasMovimentacoes || cardHasBloqueios) {
            throw new DataIntegrityViolationException(CANNOT_DELETE_CARD_WITH_ASSOCIATED_MOVIMENTACOES_OR_BLOQUEIOS);
        }
        cardRepository.delete(card);
    }
}
