package dio.desafio.jdbc.ui;

import dio.desafio.jdbc.dao.impl.*;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.Board;
import dio.desafio.jdbc.model.BoardColumn;
import dio.desafio.jdbc.service.BoardColumnService;
import dio.desafio.jdbc.service.BoardService;
import dio.desafio.jdbc.service.CardService;

import java.time.format.DateTimeFormatter;

public class BoardColumnMenu {

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    private final BoardColumnDAO boardColumnDAO = new BoardColumnDAO();
    private final BoardDAO boardDao = new BoardDAO();
    private final CardDAO cardDAO = new CardDAO();
    private final CardTransitionDAO cardTransitionDAO = new CardTransitionDAO();
    private final CardBlockHistoryDAO cardBlockHistoryDAO = new CardBlockHistoryDAO();

    private final BoardColumnService boardColumnService = new BoardColumnService(boardColumnDAO);
    private final CardService cardService = new CardService(cardDAO, cardTransitionDAO, boardColumnService, cardBlockHistoryDAO);
    private final BoardService boardService = new BoardService(boardDao, boardColumnService, cardService);
    private final CardMenu cardMenu = new CardMenu(cardService, boardService);

    public void showSelectBoardColumn(Board board){

        while (true){

            OutputMenu.showBoardColumns(board);

            System.out.println("""
                    ╔════════════════════════════════════════════════════╗
                    ║                       MENU                         ║
                    ╠════════════════════════════════════════════════════╣
                    ║  1 - Acessar Cards                                 ║
                    ║  2 - Criar Coluna                                  ║
                    ║  3 - Editar Coluna                                 ║
                    ║  4 - Excluir Coluna                                ║
                    ║ 10 - Voltar                                        ║
                    ╚════════════════════════════════════════════════════╝
                    """);

            int op = (int) InputMenu.collectLong(" - ESCOLHA UMA OPÇÃO: ");
            switch (op){
                case 1 -> accessCards(board);
                case 2 -> createColum(board);
                case 3 -> updateColum(board);
                case 4 -> deleteColum(board);
                case 10 -> {
                    System.out.println(" - VOLTANDO PARA O MENU INICIAL...");
                    return;
                }
                default -> System.out.println(" - INSIRA UMA OPÇÃO VÁLIDA.");
            }
        }
    }

    private void accessCards(Board board){
        try{
            cardMenu.showCards(board);

        }catch (EntityNotFoundException e){
            System.err.println(" - ERRO AO EXIBIR COLUNAS DO BOARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void createColum(Board board){
        String name = InputMenu.collectText(" - DIGITE O NOME DA COLUNA: ");
        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setName(name);
        boardColumn.setBoard(board);

        try{
            BoardColumn pendingColumn = boardColumnService.createPendingColumn(boardColumn);
            System.out.println(" - COLUNA CRIADA COM SUCESSO.");
            System.out.println(" - NOME: "+ pendingColumn.getName());
            System.out.println(" - TIPO: "+ pendingColumn.getColumnType().name());
            System.out.println(" - POSIÇÃO: "+ pendingColumn.getPosition());
            System.out.println(" - CRIADO EM: "+ pendingColumn.getCreatedAt().format(df));

        } catch (Exception e){
            System.err.println(" - ERRO AO CRIAR COLUNA");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void updateColum(Board board){
        Board realoadedBoard = boardService.findById(board.getId());
        long id = InputMenu.collectLong(" - DIGITE O ID DA COLUNA QUE SERÁ ATUALIZADA: ");
        String name = InputMenu.collectText(" - DIGITE O NOVO NOME: ");
        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setId(id);
        boardColumn.setName(name);
        boardColumn.setBoard(board);

        try{
            BoardColumn updatedColumn = boardColumnService.updatePendingColumn(boardColumn, realoadedBoard);
            System.out.println(" - COLUNA ATUALIZADA COM SUCESSO.");
            System.out.println(" - NOME: "+ updatedColumn.getName());

        }catch (Exception e){
            System.err.println(" - ERRO AO ATUALIZAR COLUNA");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void deleteColum(Board board){
        Board realoadedBoard = boardService.findById(board.getId());
        long id = InputMenu.collectLong(" - DIGITE O ID DA COLUNA PARA EXCLUIR: ");
        try{
            boardColumnService.deleteBoardColumn(id, realoadedBoard);
            System.out.println(" - COLUNA EXCLUÍDA COM SUCESSO.");

        }catch (Exception e){
            System.err.println(" - ERRO AO EXCLUIR COLUNA");
            System.err.println("  - "+e.getMessage());
        }
    }

}
