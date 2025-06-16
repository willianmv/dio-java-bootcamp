package dio.desafio.jdbc.ui;

import dio.desafio.jdbc.dao.impl.*;
import dio.desafio.jdbc.model.Board;
import dio.desafio.jdbc.model.BoardColumn;
import dio.desafio.jdbc.model.Card;
import dio.desafio.jdbc.model.ColumnType;
import dio.desafio.jdbc.service.BoardColumnService;
import dio.desafio.jdbc.service.BoardService;
import dio.desafio.jdbc.service.CardService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OutputMenu {

    private static final BoardDAO boardDAO = new BoardDAO();
    private static final BoardColumnDAO boardColumnDAO = new BoardColumnDAO();
    private static final CardDAO cardDAO = new CardDAO();
    private static final CardTransitionDAO cardTransitionDAO = new CardTransitionDAO();
    private static final CardBlockHistoryDAO cardBlockHistoryDAO = new CardBlockHistoryDAO();

    private static final BoardColumnService boardColumnService = new BoardColumnService(boardColumnDAO);
    private static final CardService cardService = new CardService(cardDAO, cardTransitionDAO, boardColumnService, cardBlockHistoryDAO);
    private static final BoardService boardService = new BoardService(boardDAO, boardColumnService, cardService);

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    public static void showBoardList(List<Board> boards) {

        List<Board> reloadedBoards = new ArrayList<>();
        for (Board board : boards) {
            Board boardReloaded = boardService.findById(board.getId());
            reloadedBoards.add(boardReloaded);
        }


        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("                    LISTA DE BOARDS                 ");
        System.out.println("╠════════════════════════════════════════════════════╣");

        if (reloadedBoards == null || reloadedBoards.isEmpty()) {
            System.out.println("  NENHUM BOARD ENCONTRADO.");
        } else {
            for (Board board : reloadedBoards) {
                System.out.println("------------------------------------------------------");
                System.out.printf("  ID: %2d\n", board.getId());
                System.out.printf("  NOME: %s\n", board.getName());
                System.out.printf("  CRIADO EM: %s\n", board.getCreatedAt().format(df));
            }
        }

        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    public static void showBoardColumns(Board board) {
        Board boardReloaded = boardService.findById(board.getId());

        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.printf("       BOARD: %s  \n", boardReloaded.getName());
        System.out.println("╠════════════════════════════════════════════════════╣");

        System.out.println("  Criado em: " + boardReloaded.getCreatedAt().format(df));
        System.out.println("  Colunas:");

        List<BoardColumn> columns = boardReloaded.getColumns();
        if (columns == null || columns.isEmpty()) {
            System.out.println("    ↳ NENHUMA COLUNA.");
        } else {
            for (BoardColumn column : columns) {
                System.out.printf("    - ID: %2d | Nome: %s\n", column.getId(), column.getName());
            }
        }

        System.out.println("╚════════════════════════════════════════════════════╝");
    }




    public static void showCompleteBoard(Board board) {
        Board boardReloaded = boardService.findById(board.getId());
        System.out.printf("""
                ╔════════════════════════════════════════════════════╗
                                CARDS DO BOARD [%2d]
                ╠════════════════════════════════════════════════════╣
                """, boardReloaded.getId());

        System.out.println("  NOME: " + boardReloaded.getName());
        System.out.println("  CRIADO EM: " + boardReloaded.getCreatedAt().format(df));
        System.out.println("══════════════════════════════════════════════════════");

        if (boardReloaded.getColumns() == null || boardReloaded.getColumns().isEmpty()) {
            System.out.println("  (NENHUMA COLUNA)");
        } else {
            for (BoardColumn coluna : boardReloaded.getColumns()) {
                System.out.println("------------------------------------------------------");
                System.out.printf("  COLUNA - %s\n", coluna.getName());

                List<Card> cards = coluna.getCards();
                if (cards == null || cards.isEmpty()) {
                    System.out.println("    ↳ (NENHUM CARD NESTA COLUNA)");
                } else {
                    for (Card card : cards) {
                        System.out.println("    ----------------------------------------------");
                        System.out.printf("    [Card %2d] %s\n", card.getId(), limitar(card.getTitle(), 38));
                        System.out.printf("      Desc: %s\n", limitar(card.getDescription(), 41));
                        System.out.printf("      Status: %s\n", card.isBlocked() ? "🔒 Bloqueado" : "🔓 Desbloqueado");
                    }
                }
            }
        }

        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    private static String limitar(String texto, int limite) {
        if (texto == null) return "";
        return texto.length() > limite ? texto.substring(0, limite - 3) + "..." : texto;
    }

}
