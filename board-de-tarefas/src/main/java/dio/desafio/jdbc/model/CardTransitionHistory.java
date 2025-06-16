package dio.desafio.jdbc.model;

import java.time.LocalDateTime;

public class CardTransition {

    private long id;
    private Card card;
    private BoardColumn originBoardColumn;
    private BoardColumn destinationBoardColumn;
    private LocalDateTime movedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BoardColumn getOriginColumn() {
        return originBoardColumn;
    }

    public void setOriginColumn(BoardColumn originBoardColumn) {
        this.originBoardColumn = originBoardColumn;
    }

    public BoardColumn getDestinationColumn() {
        return destinationBoardColumn;
    }

    public void setDestinationColumn(BoardColumn destinationBoardColumn) {
        this.destinationBoardColumn = destinationBoardColumn;
    }

    public LocalDateTime getMovedAt() {
        return movedAt;
    }

    public void setMovedAt(LocalDateTime movedAt) {
        this.movedAt = movedAt;
    }
}
