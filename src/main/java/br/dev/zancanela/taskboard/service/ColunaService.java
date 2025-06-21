package br.dev.zancanela.taskboard.service;

import br.dev.zancanela.taskboard.entity.Coluna;
import br.dev.zancanela.taskboard.enums.ColunaTipo;
import br.dev.zancanela.taskboard.exception.DataIntegrityViolationException;
import br.dev.zancanela.taskboard.exception.EntityNotFoundException;
import br.dev.zancanela.taskboard.repository.ColunaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColunaService {

    public static final String COLUNA_NOT_FOUND_WITH_ID = "Coluna not found with id [%s]: ";
    public static final String CANNOT_DELETE_COLUNA_WITH_ASSOCIATED_CARDS = "Cannot delete coluna with associated cards.";
    public static final String CANNOT_DELETE_INICIAL_CANCELAMENTO_OR_FINAL_COLUNA = "Cannot delete inicial, cancelamento or final coluna.";

    private final ColunaRepository colunaRepository;

    public ColunaService(ColunaRepository colunaRepository) {
        this.colunaRepository = colunaRepository;
    }

    public Coluna createColuna(Coluna coluna) {
        return colunaRepository.save(coluna);
    }

    public Coluna getColuna(Long id) {
        return colunaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(COLUNA_NOT_FOUND_WITH_ID, id)
                ));
    }

    public List<Coluna> getColunasByBoard(Long boardId) {
        return colunaRepository.findAllByBoard(boardId);
    }

    public Coluna updateColuna(Long id, Coluna colunaAtualizada) {
        Coluna coluna = getColuna(id);
        coluna.setNome(colunaAtualizada.getNome());
        coluna.setOrdem(colunaAtualizada.getOrdem());
        return colunaRepository.save(coluna);
    }

    public void deleteColuna(Long id) {
        Coluna coluna = getColuna(id);
        if (coluna.getCards() != null && !coluna.getCards().isEmpty()) {
            throw new DataIntegrityViolationException(CANNOT_DELETE_COLUNA_WITH_ASSOCIATED_CARDS);
        }
        if (ColunaTipo.INICIAL.equals(coluna.getTipo())
                || ColunaTipo.FINAL.equals(coluna.getTipo())
                || ColunaTipo.CANCELAMENTO.equals(coluna.getTipo())
        ) {
            throw new DataIntegrityViolationException(CANNOT_DELETE_INICIAL_CANCELAMENTO_OR_FINAL_COLUNA);
        }
        colunaRepository.delete(coluna);
    }
}
