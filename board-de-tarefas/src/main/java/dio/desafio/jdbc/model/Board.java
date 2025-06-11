package dio.desafio.jdbc.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private long id;
    private String name;
    private LocalDateTime createdAt;
    private List<BoardColumn> boardColumns = new ArrayList<>();

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<BoardColumn> getColumns() {
        return boardColumns;
    }

    public void setColumns(List<BoardColumn> boardColumns) {
        this.boardColumns = boardColumns;
    }
}
