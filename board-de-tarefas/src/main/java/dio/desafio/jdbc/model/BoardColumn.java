package dio.desafio.jdbc.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardColumn {

    private long id;
    private String name;
    private int position;
    private ColumnType columnType;
    private Board board;
    private List<Card> cards = new ArrayList<>();
    private LocalDateTime createdAt;

    public BoardColumn() {
    }

    public BoardColumn(Board board, String name, int position, ColumnType columnType) {
        this.board = board;
        this.name = name;
        this.position = position;
        this.columnType = columnType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
