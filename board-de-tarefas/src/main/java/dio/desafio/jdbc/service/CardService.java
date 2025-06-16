package dio.desafio.jdbc.service;

import dio.desafio.jdbc.dao.impl.CardBlockHistoryDAO;
import dio.desafio.jdbc.dao.impl.CardDAO;
import dio.desafio.jdbc.dao.impl.CardTransitionDAO;
import dio.desafio.jdbc.exception.DataViolationException;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.*;

import java.util.List;
import java.util.Optional;

public class CardService {

    private final CardDAO cardDAO;
    private final CardTransitionDAO cardTransitionDAO;
    private final CardBlockHistoryDAO cardBlockHistoryDAO;
    private final BoardColumnService boardColumnService;

    public CardService(CardDAO cardDAO, CardTransitionDAO cardTransitionDAO, BoardColumnService boardColumnService, CardBlockHistoryDAO cardBlockHistoryDAO) {
        this.cardDAO = cardDAO;
        this.cardTransitionDAO = cardTransitionDAO;
        this.boardColumnService = boardColumnService;
        this.cardBlockHistoryDAO = cardBlockHistoryDAO;
    }

    public Card createCard(Card card, Board board) {
        validateExistsByTitle(card.getTitle(), board.getId());
        return cardDAO.save(card);
    }

    public Card findById(long id){
        Card card = cardDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CARD NÃO ENCONTRADO COM ID: " + id));

        BoardColumn boardColumn = boardColumnService.findById(card.getBoardColumn().getId());
        card.setBoardColumn(boardColumn);
        return card;
    }

    public Card updateCard(Card card, Board board) {
        if(card.getBoardColumn().getColumnType() == ColumnType.DONE){
            throw new DataViolationException("NÃO É PERMITIDO EDITAR CARDS JÁ CONCLUÍDOS.");
        }
        validateExistsByTitleToUpdate(card.getTitle(), card.getId(), board);
        return cardDAO.update(card);
    }

    public List<Card> getAllByParentId(long id) {
        return cardDAO.findByParentId(id);
    }

    public void deleteCard(long id, Board board){
        validateExistsByIdInParentBoard(id, board);
        cardDAO.delete(id);
    }

    public CardTransitionHistory moveCard(Board board, Card cardToMove, long originId, long toId) {
        validateExistsByIdInParentBoard(cardToMove.getId(), board);
        Card freshCard = findById(cardToMove.getId());

        if(freshCard.isBlocked()){
            throw new DataViolationException("NÃO É POSSÍVEL MOVER CARDS BLOQUEADOS");
        }

        cardDAO.updateColumn(cardToMove.getId(), toId);
        return cardTransitionDAO.move(cardToMove.getId(), originId, toId);
    }

    public CardBlockHistory blockCard(Board board, Card cardToBlock, String reason) {
        validateExistsByIdInParentBoard(cardToBlock.getId(),board);
        if(cardToBlock.isBlocked()){
            throw new DataViolationException("CARD JÁ SE ENCONTRA BLOQUEADO");
        }

        block(cardToBlock);
        return cardBlockHistoryDAO.registerBlock(cardToBlock.getId(), reason);
    }

    public CardBlockHistory unblockCard(Board board, Card cardToUnblock, String reason) {
        validateExistsByIdInParentBoard(cardToUnblock.getId(),board);
        if(!cardToUnblock.isBlocked()){
            throw new DataViolationException("CARD JÁ SE ENCONTRA DESBLOQUEADO");
        }

        unblock(cardToUnblock);
        return cardBlockHistoryDAO.registerUnblock(cardToUnblock.getId(), reason);
    }

    public void validateExistsByIdInParentBoard(long id, Board board){
        boolean exists = board.getColumns().stream()
                .flatMap(column -> column.getCards().stream())
                .anyMatch(card -> card.getId() == id);

        if(!exists)
            throw new DataViolationException("NÃO EXISTE NESSE BOARD CARD COM ID: "+id);
    }

    private void validateExistsByTitleToUpdate(String title, long cardId, Board board){
        boolean exists = board.getColumns().stream()
                .flatMap(column -> column.getCards().stream())
                .anyMatch(card ->
                        card.getTitle().equalsIgnoreCase(title) && card.getId() != cardId);

        if(exists)
            throw new DataViolationException("JÁ EXISTE UM CARD COM ESSE TÍTULO NESSE BOARD.");
    }

    private void validateExistsByTitle(String title, long parentId ){
        if(cardDAO.existsByNameInParent(title, parentId)){
            throw new DataViolationException(
                    "JÁ EXISTE UM CARD COM O TÍTULO'"+title+"' NESSE BOARD.");
        }

    }

    private void block(Card card){
        card.setBlocked(true);
        cardDAO.updateBlockedStatus(card);
    }

    private void unblock(Card card){
        card.setBlocked(false);
        cardDAO.updateBlockedStatus(card);
    }
}
