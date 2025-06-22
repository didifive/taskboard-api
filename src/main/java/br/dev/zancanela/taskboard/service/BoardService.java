package br.dev.zancanela.taskboard.service;

import br.dev.zancanela.taskboard.entity.Board;
import br.dev.zancanela.taskboard.entity.Coluna;
import br.dev.zancanela.taskboard.enums.ColunaTipo;
import br.dev.zancanela.taskboard.exception.DataIntegrityViolationException;
import br.dev.zancanela.taskboard.exception.EntityNotFoundException;
import br.dev.zancanela.taskboard.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    public static final String BOARD_NOT_FOUND_WITH_ID = "Board not found with id [%s]: ";
    public static final String CANNOT_DELETE_THE_BOARD_WITH_ASSOCIATED_CARDS = "Cannot delete the board with columns that have associated cards.";
    public static final String FAILED_TO_CREATE_INITIAL_COLUMNS_FOR_THE_BOARD = "Failed to create initial columns for the board.";
    private final BoardRepository boardRepository;
    private final ColunaService colunaService;

    public BoardService(BoardRepository boardRepository, ColunaService colunaService) {
        this.boardRepository = boardRepository;
        this.colunaService = colunaService;
    }

    public Board createBoard(String boardName) {
        Board board = new Board();
        board.setNome(boardName);

        Board savedBoard = boardRepository.save(board);

        try {
            Coluna colunaInicial = new Coluna();
            colunaInicial.setNome("To Do");
            colunaInicial.setOrdem(1);
            colunaInicial.setTipo(ColunaTipo.INICIAL);

            Coluna colunaFinal = new Coluna();
            colunaFinal.setNome("Done");
            colunaFinal.setOrdem(Integer.MAX_VALUE - 1);
            colunaFinal.setTipo(ColunaTipo.FINAL);

            Coluna colunaCancelamento = new Coluna();
            colunaCancelamento.setNome("Cancelamento");
            colunaCancelamento.setOrdem(Integer.MAX_VALUE);
            colunaCancelamento.setTipo(ColunaTipo.CANCELAMENTO);

            List<Coluna> colunas = List.of(colunaInicial, colunaFinal, colunaCancelamento);
            colunas.forEach(c -> {
                c.setBoard(savedBoard);
                colunaService.createColuna(c);
            });
        } catch (Exception e) {
            deleteBoard(savedBoard.getId());
            throw new DataIntegrityViolationException(FAILED_TO_CREATE_INITIAL_COLUMNS_FOR_THE_BOARD);
        }

        return savedBoard;
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(BOARD_NOT_FOUND_WITH_ID, id)
                ));
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
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

        if (!allColunasWithoutCards) {
            throw new DataIntegrityViolationException(CANNOT_DELETE_THE_BOARD_WITH_ASSOCIATED_CARDS);
        }

        boardRepository.delete(board);
    }

    public List<Coluna> getColunas(Long id) {
        Board board = getBoard(id);
        return colunaService.getColunasByBoard(board.getId());
    }

    public Coluna createColunaPendente(Long id, String name) {
        Board board = getBoard(id);
        Coluna coluna = new Coluna();
        coluna.setNome(name);
        coluna.setOrdem(
                colunaService.getColunasByBoard(board.getId()).stream()
                        .filter(c -> ColunaTipo.PENDENTE.equals(c.getTipo()))
                        .mapToInt(Coluna::getOrdem)
                        .max()
                        .orElse(1) + 1
        );
        coluna.setTipo(ColunaTipo.PENDENTE);
        coluna.setBoard(board);
        return colunaService.createColuna(coluna);
    }
}
