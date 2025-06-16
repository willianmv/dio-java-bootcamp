package dio.desafio.jdbc.ui;

import dio.desafio.jdbc.dao.impl.*;
import dio.desafio.jdbc.exception.DataViolationException;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.Board;
import dio.desafio.jdbc.service.BoardColumnService;
import dio.desafio.jdbc.service.BoardService;
import dio.desafio.jdbc.service.CardService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BoardMenu {

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
    private final BoardColumnMenu boardColumnMenu = new BoardColumnMenu();

    private final BoardColumnDAO boardColumnDAO = new BoardColumnDAO();
    private final BoardDAO boardDao = new BoardDAO();
    private final CardDAO cardDAO = new CardDAO();
    private final CardTransitionDAO cardTransitionDAO = new CardTransitionDAO();
    private final CardBlockHistoryDAO cardBlockHistoryDAO = new CardBlockHistoryDAO();

    private final BoardColumnService boardColumnService = new BoardColumnService(boardColumnDAO);
    private final CardService cardService = new CardService(cardDAO, cardTransitionDAO,boardColumnService, cardBlockHistoryDAO);
    private final BoardService boardService = new BoardService(boardDao, boardColumnService, cardService);

    public void showMenu(){

        while (true){
            System.out.println("""
                    ╔════════════════════════════════════════════════════╗
                    ║                    BOARD MENU                      ║
                    ╠════════════════════════════════════════════════════╣
                    ║  1 - Ver Boards                                    ║
                    ║  2 - Acessar Board                                 ║
                    ║  3 - Criar Board                                   ║
                    ║  4 - Editar Board                                  ║
                    ║  5 - Excluir Board                                 ║
                    ║ 10 - Sair                                          ║
                    ╚════════════════════════════════════════════════════╝
                    """);

            int op = (int) InputMenu.collectLong(" - ESCOLHA UMA OPÇÃO: ");
            switch (op){
                case 1 -> showBoards();
                case 2 -> selectBoard();
                case 3 -> createBoard();
                case 4 -> editBoard();
                case 5 -> deleteBoard();
                case 10 -> {
                    System.out.println(" - ENCERRANDO...");
                    System.exit(0);
                }
                default -> System.out.println(" - INSIRA UMA OPÇÃO VÁLIDA.");
            }
        }
    }

    private void showBoards(){
        List<Board> boards = boardService.findAll();
        OutputMenu.showBoardList(boards);
    }

    private void createBoard() {
        String boardName = InputMenu.collectText(" - DIGITE O NOME PARA O BOARD: ");
        Board board = new Board();
        board.setName(boardName);
        try{
            Board createdBoard = boardService.createBoard(board);
            System.out.println(" - BOARD CRIADO COM SUCESSO.");
            System.out.println(" - NOME: "+ createdBoard.getName());
            System.out.println(" - CRIADO EM: "+ createdBoard.getCreatedAt().format(df));

        }catch (Exception e){
            System.err.println(" - ERRO AO CRIAR BOARD");
            System.err.println("  - "+e.getMessage());
        }

    }

    private void selectBoard() {
        long boardId = InputMenu.collectLong(" - DIGITE O ID DO BOARD: ");
        try{
            Board selectedBoard = boardService.findById(boardId);
            boardColumnMenu.showSelectBoardColumn(selectedBoard);

        }catch (EntityNotFoundException e){
            System.err.println(" - ERRO AO EXIBIR BOARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void editBoard(){
        long id = InputMenu.collectLong(" - DIGITE O ID DO BOARD QUE SERÁ ATUALIZADO: ");
        String name = InputMenu.collectText(" - DIGITE O NOVO NOME");
        Board boardToUpdate = new Board();
        boardToUpdate.setId(id);
        boardToUpdate.setName(name);

        try{
            Board updatedBoard =  boardService.updateBoard(boardToUpdate);
            System.out.println(" - BOARD ATUALIZADO COM SUCESSO.");
            System.out.println(" - NOVO NOME: "+ updatedBoard.getName());

        }catch (EntityNotFoundException | DataViolationException e){
            System.err.println(" - ERRO AO ATUALIZAR BOARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void deleteBoard(){
        long boardIdToDelete = InputMenu.collectLong(" - DIGITE O ID DO BOARD PARA EXCLUIR: ");
        try{
            boardService.deleteBoard(boardIdToDelete);
            System.out.println(" - BOARD EXCLUÍDO COM SUCESSO.");

        }catch (EntityNotFoundException e){
            System.err.println(" - ERRO AO EXCLUIR BOARD");
            System.err.println("  - "+e.getMessage());
        }

    }

}
