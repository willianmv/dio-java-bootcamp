package dio.desafio.jdbc.dao.impl;

import dio.desafio.jdbc.dao.IGenericDAO;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.Board;
import dio.desafio.jdbc.util.DataBaseUtils;
import dio.desafio.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardDAO implements IGenericDAO<Board, Long> {

    @Override
    public Board save(Board entity) {
        String sql = "INSERT INTO tb_board (name) VALUES (?) RETURNING id, created_at";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, entity.getName());
            rs = ps.executeQuery();

            if(rs.next()){
                entity.setId(rs.getLong("id"));
                entity.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            conn.commit();

        } catch (SQLException ex) {
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(ex);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return entity;
    }

    @Override
    public Optional<Board> findById(Long id) {
        String sql = "SELECT * FROM tb_board WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if(rs.next()){
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setName(rs.getString("name"));
                board.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return Optional.of(board);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM tb_board WHERE id = ?";
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
        String sql = "SELECT 1 FROM tb_board WHERE LOWER(name) = LOWER(?) LIMIT 1";
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
        String sql = "SELECT 1 FROM tb_board WHERE LOWER(name) = LOWER(?) AND id <> ?";
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
    public List<Board> findAll() {
        String sql = "SELECT * FROM tb_board ORDER BY id";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Board> boards = new ArrayList<>();

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setName(rs.getString("name"));
                board.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                boards.add(board);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return boards;
    }

    @Override
    public Board update(Board entity) {
        String sql = "UPDATE tb_board SET name = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getId());
            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated == 0)
                throw new EntityNotFoundException("BOARD NÃO ENCONTRADO PARA ATUALIZAÇÃO PELO ID: "+entity.getId());

            conn.commit();
            return entity;

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM tb_board WHERE id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, id);
            int deletedRows = ps.executeUpdate();

            if(deletedRows == 0)
                throw new EntityNotFoundException("BOARD NÃO ENCONTRADO COM ID: "+id);

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }

    }
}
