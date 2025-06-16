package dio.desafio.jdbc.ui;

import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.*;
import dio.desafio.jdbc.service.BoardService;
import dio.desafio.jdbc.service.CardService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CardMenu {

    private final DateTimeFormatter df  = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    private final CardService cardService;
    private final BoardService boardService;

    public CardMenu(CardService cardService, BoardService boardService) {
        this.cardService = cardService;
        this.boardService = boardService;
    }

    public void showCards(Board board) {

        while (true){
            OutputMenu.showCompleteBoard(board);

            System.out.println("""
                    ╔════════════════════════════════════════════════════╗
                    ║                       MENU                         ║
                    ╠════════════════════════════════════════════════════╣
                    ║  1 - Criar Card                                    ║
                    ║  2 - Editar Card                                   ║
                    ║  3 - Excluir Card                                  ║
                    ║  4 - Mover Card                                    ║
                    ║  5 - Bloquear Card                                 ║
                    ║  6 - Desbloquear Card                              ║
                    ║ 10 - Voltar                                        ║
                    ╚════════════════════════════════════════════════════╝
                    """);

            int op = (int) InputMenu.collectLong(" - ESCOLHA UMA OPÇÃO: ");
            switch (op){
                case 1 -> createCard(board);
                case 2 -> updateCard(board);
                case 3 -> deleteCard(board);
                case 4 -> moveCard(board);
                case 5 -> blockCard(board);
                case 6 -> unblockCard(board);
                case 10 -> {
                    System.out.println(" - VOLTANDO...");
                    return;
                }
                default -> System.out.println(" - INSIRA UMA OPÇÃO VÁLIDA.");
            }

        }
    }

    private void createCard(Board board){
        Board reloadedBord = boardService.findById(board.getId());
        BoardColumn boardColumn = reloadedBord.getColumns().getFirst();

        String title = InputMenu.collectText(" - DIGITE O NOME DO CARD: ");
        String description = InputMenu.collectText(" - DIGITE A DESCRIÇÃO: ");

        Card card = new Card();
        card.setBoardColumn(boardColumn);
        card.setTitle(title);
        card.setDescription(description);

        try{
           Card createdCard = cardService.createCard(card, reloadedBord);
           System.out.println(" - CARD CRIADO COM SUCESSO.");
           System.out.println(" - TÍTULO: "+ createdCard.getTitle());
           System.out.println(" - DESCRIÇÃO: "+ createdCard.getDescription());
           System.out.println(" - CRIADO EM: "+ createdCard.getCreatedAt().format(df));

        }catch (Exception e){
            System.err.println(" - ERRO AO CRIAR CARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void updateCard(Board board){
        Board reloadedBord = boardService.findById(board.getId());

        long id = InputMenu.collectLong(" - DIGITE O ID DO CARD PARA ATUALIZAR: ");
        String title = InputMenu.collectText(" - DIGITE O NOVO TÍTULO: ");
        String description = InputMenu.collectText(" - DIGITE A NOVA DESCRIÇÃO: ");

        try{
             Card cardToUpdate = cardService.findById(id);
             cardService.validateExistsByIdInParentBoard(id, reloadedBord);
             cardToUpdate.setTitle(title);
             cardToUpdate.setDescription(description);

            Card updatedCard = cardService.updateCard(cardToUpdate, reloadedBord);

            System.out.println(" - CARD ATUALIZADO COM SUCESSO.");
            System.out.println(" - TÍTULO: "+ updatedCard.getTitle());
            System.out.println(" - DESCRIÇÃO: "+ updatedCard.getTitle());

        }catch (Exception e){
            System.err.println(" - ERRO AO ATUALIZAR CARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void deleteCard(Board board){
        long id = InputMenu.collectLong(" - DIGITE O ID PARA EXLUIR: ");
        try{
            cardService.deleteCard(id, board);
            System.out.println(" - BOARD EXCLUÍDO COM SUCESSO.");

        }catch (Exception e){
            System.err.println(" - ERRO AO EXCLUIR CARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void moveCard(Board board){
        long cardId = InputMenu.collectLong(" - DIGITE O ID DO CARD: ");

        try{
            Card cardToMove = cardService.findById(cardId);
            BoardColumn currentColumn = cardToMove.getBoardColumn();

            long toId = collectDestinationColumnId(board, currentColumn);
            long originId = currentColumn.getId();

            CardTransitionHistory transitionHistory = cardService.moveCard(board, cardToMove, originId, toId);

            System.out.println(" - CARD MOVIDO COM SUCESSO.");
            System.out.println(" - ID DA TRANSIÇÃO: "+ transitionHistory.getId());
            System.out.println(" - TÍTULO: "+ cardToMove.getTitle());
            System.out.println(" - COLUNA ORIGEM: "+ cardToMove.getBoardColumn().getId());
            System.out.println(" - COLUNA DESTINO: "+ toId);
            System.out.println(" - DATA DA TRANSIÇÃO: "+ transitionHistory.getMovedAt().format(df));

        }catch (Exception e){
            System.err.println(" - ERRO AO MOVER CARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private long collectDestinationColumnId(Board board, BoardColumn boardColumn){
        Board reloadedBoard = boardService.findById(board.getId());
        List<BoardColumn> availableColumns = reloadedBoard.getColumns().stream()
                .filter(c -> c.getId() != boardColumn.getId())
                .toList();

        if(availableColumns.isEmpty()){
            throw new EntityNotFoundException("SEM COLUNAS ENCONTRADAS PARA MOVER ESSE CARD");
        }

        System.out.println(" - ESCOLHA A COLUNA DESTINO: ");
        for(int i = 0; i < availableColumns.size(); i++ ){
            BoardColumn column = availableColumns.get(i);
            System.out.printf("  [%d] %s\n", i+1, column.getName());
        }


        int option = (int) InputMenu.collectLong("\n - OPÇÃO: ");
        if (option < 1 || option > availableColumns.size()) {
            throw new IllegalArgumentException(" - OPÇÃO INVÁLIDA.");
        }

        return availableColumns.get(option - 1).getId();
    }

    private void blockCard(Board board){
        long cardId = InputMenu.collectLong(" - DIGITE O ID DO CARD PARA BLOQUEAR: ");
        String reason = InputMenu.collectText(" - RAZÃO PARA O BLOQUEIO: ");

        try{
            Card cardToBlock = cardService.findById(cardId);
            CardBlockHistory blockedCardInfo = cardService.blockCard(board, cardToBlock, reason);
            System.out.println(" - CARD BLOQUEADO COM SUCESSO.");
            System.out.println(" - ID DA TRANSIÇÃO: "+ blockedCardInfo.getId());
            System.out.println(" - RAZÃO: "+ reason);
            System.out.println(" - DATA DO BLOQUEIO: "+ blockedCardInfo.getBlockedAt().format(df));

        }catch (Exception e){
            System.err.println(" - ERRO AO BLOQUEAR CARD");
            System.err.println("  - "+e.getMessage());
        }
    }

    private void unblockCard(Board board){
        long cardId = InputMenu.collectLong(" - DIGITE O ID DO CARD PARA DESBLOQUEAR: ");
        String reason = InputMenu.collectText(" - RAZÃO PARA O DESBLOQUEIO: ");

        try{
            Card cardToUnblock = cardService.findById(cardId);
            CardBlockHistory blockedCardInfo = cardService.unblockCard(board, cardToUnblock, reason);
            System.out.println(" - CARD DESBLOQUEADO COM SUCESSO.");
            System.out.println(" - ID DA TRANSIÇÃO: "+ blockedCardInfo.getId());
            System.out.println(" - RAZÃO: "+ reason);
            System.out.println(" - DATA DO DESBLOQUEIO: "+ blockedCardInfo.getUnblockedAt().format(df));

        }catch (Exception e){
            System.err.println(" - ERRO AO DESBLOQUEAR CARD");
            System.err.println("  - "+e.getMessage());
        }
    }

}
