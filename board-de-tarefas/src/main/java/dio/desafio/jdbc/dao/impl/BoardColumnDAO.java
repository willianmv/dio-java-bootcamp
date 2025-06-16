package dio.desafio.jdbc.dao.impl;

import dio.desafio.jdbc.dao.IChildDAO;
import dio.desafio.jdbc.dao.IGenericDAO;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.Board;
import dio.desafio.jdbc.model.BoardColumn;
import dio.desafio.jdbc.model.ColumnType;
import dio.desafio.jdbc.util.DataBaseUtils;
import dio.desafio.jdbc.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardColumnDAO implements IGenericDAO<BoardColumn, Long>, IChildDAO<BoardColumn, Long> {

    @Override
    public BoardColumn save(BoardColumn entity) {
        String sql = """
                INSERT INTO tb_board_column (board_id, name, position, column_type)
                VALUES (?, ?, ?, ?)
                RETURNING id, created_at
                """;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, entity.getBoard().getId());
            ps.setString(2, entity.getName());
            ps.setInt(3, entity.getPosition());
            ps.setObject(4, entity.getColumnType().name(), Types.OTHER);
            rs = ps.executeQuery();

            if(rs.next()){
                entity.setId(rs.getLong("id"));
                entity.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return entity;
    }

    @Override
    public Optional<BoardColumn> findById(Long id) {
        String sql = "SELECT * FROM tb_board_column WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if(rs.next()){
                BoardColumn column = new BoardColumn();
                column.setId(rs.getLong("id"));
                column.setName(rs.getString("name"));
                column.setPosition(rs.getInt("position"));
                column.setColumnType(Enum.valueOf(ColumnType.class, rs.getString("column_type")));
                column.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                Board board = new Board();
                board.setId(rs.getLong("board_id"));
                column.setBoard(board);

                return Optional.of(column);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return Optional.empty();
    }

    @Override
    public List<BoardColumn> findByParentId(Long parentId) {
        String sql = "SELECT * FROM tb_board_column WHERE board_id = ? ORDER BY POSITION";
        List<BoardColumn> columns = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, parentId);
            rs = ps.executeQuery();

            while (rs.next()){
                BoardColumn column = new BoardColumn();
                column.setId(rs.getLong("id"));
                column.setName(rs.getString("name"));
                column.setPosition(rs.getInt("position"));
                column.setColumnType(Enum.valueOf(ColumnType.class, rs.getString("column_type")));
                column.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                columns.add(column);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return columns;
    }

    @Override
    public boolean existsByNameInParent(String name, Long parentId) {
        String sql = "SELECT 1 FROM tb_board_column WHERE LOWER(name) = LOWER(?) AND board_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setLong(2, parentId);
            rs = ps.executeQuery();;
            return rs.next();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT  1 FROM tb_board_column WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            return rs.next();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM tb_board_column WHERE LOWER(name) = LOWER(?) LIMIT 1";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            return rs.next();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
    }

    @Override
    public boolean existsByNameExcludingId(String name, Long id) {
        String sql = "SELECT 1 FROM tb_board_column WHERE LOWER(name) = LOWER(?) AND id <> ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setLong(2, id);
            rs = ps.executeQuery();
            return rs.next();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
    }

    @Override
    public List<BoardColumn> findAll() {
        String sql = "SELECT * FROM tb_board_column ORDER BY id";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<BoardColumn> columns = new ArrayList<>();

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                BoardColumn column = new BoardColumn();
                column.setId(rs.getLong("id"));
                column.setName(rs.getString("name"));
                column.setPosition(rs.getInt("position"));
                column.setColumnType(Enum.valueOf(ColumnType.class, rs.getString("column_type")));
                column.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                columns.add(column);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return columns;
    }

    @Override
    public BoardColumn update(BoardColumn entity) {
        String sql = "UPDATE tb_board_column SET name = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getName().toUpperCase());
            ps.setLong(2, entity.getId());
            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated == 0)
                throw new EntityNotFoundException("COLUNA NÃO ENCONTRADA PARA ATUALIZAÇÃO PELO ID: "+entity.getId());

            conn.commit();
            return entity;

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }
    }

    public void updatePosition(long columnId, int newPosition){
        String sql = "UPDATE tb_board_column SET position = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, newPosition);
            ps.setLong(2, columnId);
            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated == 0)
                throw new EntityNotFoundException("COLUNA NÃO ENCONTRADA PARA ATUALIZAÇÃO PELO ID: "+columnId);

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM tb_board_column WHERE id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, id);
            int deletedRows = ps.executeUpdate();

            if(deletedRows == 0)
                throw new EntityNotFoundException("COLUNA NÃO ENCONTRADA COM ID: "+id);

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }
    }
}
