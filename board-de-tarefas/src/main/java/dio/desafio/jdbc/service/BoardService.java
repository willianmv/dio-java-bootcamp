package dio.desafio.jdbc.service;

import dio.desafio.jdbc.dao.impl.BoardDAO;
import dio.desafio.jdbc.exception.DataViolationException;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.Board;
import dio.desafio.jdbc.model.BoardColumn;
import dio.desafio.jdbc.model.Card;

import java.util.List;

public class BoardService {

    private final BoardDAO boardDAO;
    private final BoardColumnService boardColumnService;
    private final CardService cardService;

    public BoardService(BoardDAO boardDAO, BoardColumnService boardColumnService, CardService cardService) {
        this.boardDAO = boardDAO;
        this.boardColumnService = boardColumnService;
        this.cardService = cardService;
    }

    public Board createBoard(Board board) {
        validateExistsByName(board.getName());
        Board savedBoard = boardDAO.save(board);
        boardColumnService.initiateBoard(savedBoard);
        return savedBoard;
    }

    public Board findById(Long id){
        Board board =  boardDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BOARD NÃO ENCONTRADO PELO ID: "+id));

        List<BoardColumn> columns = boardColumnService.getAllByParentId(id);

        for (BoardColumn column : columns) {
            List<Card> cards = cardService.getAllByParentId(column.getId());
            column.setCards(cards);
        }

        board.setColumns(columns);
        return board;
    }

    public List<Board> findAll() {
        return boardDAO.findAll();
    }

    public Board updateBoard(Board boardToUpdate) {
        Board boardFound = findById(boardToUpdate.getId());
        validateExistsByNameToUpdate(boardToUpdate.getName(), boardFound.getId());
        boardFound.setName(boardToUpdate.getName());
        return boardDAO.update(boardFound);
    }

    public void deleteBoard(long id){
        if(!boardDAO.existsById(id)){
            throw new EntityNotFoundException("BOARD NÃO ENCONTRADO PELO ID: "+id);
        }
        boardDAO.delete(id);
    }

    private void validateExistsByName(String name){
        if(boardDAO.existsByName(name)){
            throw new DataViolationException("JÁ EXISTE UM BOARD COM O NOME: '"+name+"'");
        }
    }

    private void validateExistsByNameToUpdate(String name, Long id){
        if(boardDAO.existsByNameExcludingId(name, id)){
            throw new DataViolationException("JÁ EXISTE UM BOARD COM O NOME: '"+name+"'");
        }
    }
}
