package dio.desafio.jdbc.service;

import dio.desafio.jdbc.dao.impl.BoardColumnDAO;
import dio.desafio.jdbc.exception.DataViolationException;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.Board;
import dio.desafio.jdbc.model.BoardColumn;
import dio.desafio.jdbc.model.ColumnType;

import java.util.ArrayList;
import java.util.List;

public class BoardColumnService {

    private final BoardColumnDAO boardColumnDAO;

    public BoardColumnService(BoardColumnDAO boardColumnDAO) {
        this.boardColumnDAO = boardColumnDAO;
    }

    public void initiateBoard(Board board){
        List<BoardColumn> columns = board.getColumns();
        if(columns.isEmpty()){
            List<BoardColumn> defaultColumns = createDefaultColumns(board);
            for(BoardColumn bc : defaultColumns){
                boardColumnDAO.save(bc);
            }
        }
    }

    public BoardColumn createPendingColumn(BoardColumn column){
        validateExistsByName(column.getName(), column.getBoard().getId());
        List<BoardColumn> columns = boardColumnDAO.findByParentId(column.getBoard().getId());

        int doneColumnPosition = getDoneColumnPosition(columns);

        for(int i = columns.size() - 1; i >= doneColumnPosition; i--){
            BoardColumn col = columns.get(i);
            int newPosition = col.getPosition() + 1;
            col.setPosition(newPosition);
            boardColumnDAO.updatePosition(col.getId(), newPosition);
        }

        BoardColumn newCol = new BoardColumn(column.getBoard(), column.getName().toUpperCase(), doneColumnPosition, ColumnType.PENDING);
        return boardColumnDAO.save(newCol);
    }

    public List<BoardColumn> getAllByParentId(long parentId) {
        return boardColumnDAO.findByParentId(parentId);
    }

    public BoardColumn findById(long id){
        return boardColumnDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("COLUNA NÃO ENCONTRADA COM ID: "+id));
    }

    public BoardColumn updatePendingColumn(BoardColumn column, Board board){
        validateExistsByIdInParentBoard(column.getId(), board);
        BoardColumn columnToUpdate = findById(column.getId());
        if(columnToUpdate.getColumnType() != ColumnType.PENDING)
            throw new DataViolationException("NÃO É PERMITIDO EDITAR COLUNAS DEFAULT");

        validateExistsByNameToUpdate(column.getName(), column.getId(), board.getId());
        return boardColumnDAO.update(column);
    }

    public void deleteBoardColumn(long id, Board board){
        if(!boardColumnDAO.existsById(id)){
            throw new EntityNotFoundException("COLUNA NÃO ENCONTRADO PELO ID: "+id);
        }

        validateExistsByIdInParentBoard(id, board);
        BoardColumn columnToDelete = findById(id);
        if(columnToDelete.getColumnType() != ColumnType.PENDING)
            throw new DataViolationException("NÃO É PERMITIDO EDITAR COLUNAS DEFAULT");

        validateExistsByIdInParentBoard(id, board);

        boardColumnDAO.delete(id);
    }

    private void validateExistsByIdInParentBoard(long id, Board board){
        boolean exists = board.getColumns().stream()
                .anyMatch(column -> column.getId() == id);

        if(!exists)
            throw new DataViolationException("NÃO EXISTE NESSE BOARD COLUNA COM ID: "+id);
    }

    private void validateExistsByNameToUpdate(String name, long columnId, long boardId ){
        List<BoardColumn> columns = boardColumnDAO.findByParentId(boardId);
        for (BoardColumn column : columns) {
            if(column.getName().equalsIgnoreCase(name) && column.getId() != columnId)
                throw new DataViolationException(
                        "JÁ EXISTE OUTRA COLUNA COM O NOME '"+name+"' NO MESMO BOARD.");

        }
    }

    private int getDoneColumnPosition(List<BoardColumn> columns){
        int doneIndex = -1;
        for(int i = 0; i < columns.size(); i++){
            if(columns.get(i).getColumnType() == ColumnType.DONE){
                doneIndex = i;
                break;
            }
        }
        if(doneIndex == -1) throw new DataViolationException("COLUNA 'CONCLUÍDO' NÃO ENCONTRADA.");

        return doneIndex;
    }

    private void validateExistsByName(String name, Long parentId){
        if(boardColumnDAO.existsByNameInParent(name, parentId)){
            throw new DataViolationException(
                    "JÁ EXISTE UMA COLUNA COM O NOME: '"+name+"' NO BOARD DE ID: "+parentId);
        }
    }

    private List<BoardColumn> createDefaultColumns(Board board){
        List<BoardColumn> defaultColumns = new ArrayList<>();
        defaultColumns.add(new BoardColumn(board, "A FAZER", 0, ColumnType.TODO));
        defaultColumns.add(new BoardColumn(board, "CONCLUÍDO", 1, ColumnType.DONE));
        defaultColumns.add(new BoardColumn(board, "CANCELADO", 2, ColumnType.CANCELED));
        return defaultColumns;
    }
}
