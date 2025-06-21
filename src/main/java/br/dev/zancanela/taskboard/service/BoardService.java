package br.dev.zancanela.taskboard.service;

import br.dev.zancanela.taskboard.entity.Board;
import br.dev.zancanela.taskboard.exception.DataIntegrityViolationException;
import br.dev.zancanela.taskboard.exception.EntityNotFoundException;
import br.dev.zancanela.taskboard.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    public static final String BOARD_NOT_FOUND_WITH_ID = "Board not found with id [%s]: ";
    public static final String CANNOT_DELETE_THE_BOARD_WITH_ASSOCIATED_CARDS = "Cannot delete the board with columns that have associated cards.";
    private final BoardRepository boardRepository;
    private final ColunaService colunaService;

    public BoardService(BoardRepository boardRepository, ColunaService colunaService) {
        this.boardRepository = boardRepository;
        this.colunaService = colunaService;
    }

    public Board createBoard(String boardName) {
        Board board = new Board();
        board.setNome(boardName);
        return boardRepository.save(board);
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(BOARD_NOT_FOUND_WITH_ID, id)
                ));
    }

    public Board updateBoard(Long id, String boardName) {
        Board board = getBoard(id);
        board.setNome(boardName);
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        Board board = getBoard(id);

        boolean allColunasWithoutCards = colunaService.getColunasByBoard(id).stream()
                .allMatch(coluna -> coluna.getCards().isEmpty());

        if (allColunasWithoutCards) {
            throw new DataIntegrityViolationException(CANNOT_DELETE_THE_BOARD_WITH_ASSOCIATED_CARDS);
        }

        boardRepository.delete(board);
    }
}
