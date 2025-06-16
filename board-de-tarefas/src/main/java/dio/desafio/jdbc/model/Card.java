package dio.desafio.jdbc.model;

import java.time.LocalDateTime;
import java.util.List;

public class Card {

    private long id;
    private String title;
    private String description;
    private boolean blocked;
    private BoardColumn boardColumn;

    public BoardColumn getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(BoardColumn boardColumn) {
        this.boardColumn = boardColumn;
    }

    private List<CardBlockHistory> blocks;
    private List<CardTransitionHistory> transitions;
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public List<CardBlockHistory> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<CardBlockHistory> blocks) {
        this.blocks = blocks;
    }

    public List<CardTransitionHistory> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<CardTransitionHistory> transitions) {
        this.transitions = transitions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
