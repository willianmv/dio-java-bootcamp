package dio.desafio.jdbc.dao.impl;

import dio.desafio.jdbc.dao.IChildDAO;
import dio.desafio.jdbc.dao.IGenericDAO;
import dio.desafio.jdbc.exception.EntityNotFoundException;
import dio.desafio.jdbc.model.BoardColumn;
import dio.desafio.jdbc.model.Card;
import dio.desafio.jdbc.util.DataBaseUtils;
import dio.desafio.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardDAO implements IGenericDAO<Card, Long>, IChildDAO<Card, Long> {

    @Override
    public List<Card> findByParentId(Long parentId) {
        String sql = "SELECT * FROM tb_card WHERE board_column_id = ? ORDER BY id";
        List<Card> cards = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, parentId);
            rs = ps.executeQuery();

            while (rs.next()){
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setTitle(rs.getString("title"));
                card.setDescription(rs.getString("description"));
                card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                card.setBlocked(rs.getBoolean("blocked"));
                cards.add(card);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return cards;
    }

    @Override
    public boolean existsByNameInParent(String name, Long parentId) {
        String sql = "SELECT 1 FROM tb_card WHERE LOWER(title) = LOWER(?) AND board_column_id = ?";
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
    public Card save(Card entity) {
        String sql = "INSERT INTO tb_card (board_column_id, title, description) VALUES (?, ?, ?) RETURNING id, created_at";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, entity.getBoardColumn().getId());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getDescription());
            rs = ps.executeQuery();

            if(rs.next()){
                entity.setId(rs.getLong("id"));
                entity.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        } finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }

        return entity;
    }

    @Override
    public Optional<Card> findById(Long id) {
        String sql = "SELECT * FROM tb_card WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if(rs.next()){
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setTitle(rs.getString("title"));
                card.setDescription(rs.getString("description"));
                card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                card.setBlocked(rs.getBoolean("blocked"));

                BoardColumn column = new BoardColumn();
                column.setId(rs.getLong("board_column_id"));

                card.setBoardColumn(column);
                return Optional.of(card);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DataBaseUtils.closeQuietly(rs, ps , conn);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT  1 FROM tb_card WHERE id = ?";
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
        String sql = "SELECT 1 FROM tb_card WHERE LOWER(name) = LOWER(?) LIMIT 1";
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
        String sql = "SELECT 1 FROM tb_card WHERE LOWER(name) = LOWER(?) AND id <> ?";
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
    public List<Card> findAll() {
        String sql = "SELECT * FROM tb_card ORDER BY id";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Card> cards = new ArrayList<>();

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setTitle(rs.getString("title"));
                card.setDescription(rs.getString("description"));
                card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                card.setBlocked(rs.getBoolean("blocked"));
                cards.add(card);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return cards;
    }

    @Override
    public Card update(Card entity) {
        String sql = "UPDATE tb_card SET title = ?, description = ?  WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getTitle().toUpperCase());
            ps.setString(2, entity.getDescription());
            ps.setLong(3, entity.getId());
            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated == 0)
                throw new EntityNotFoundException("CARD NÃO ENCONTRADO PARA ATUALIZAÇÃO PELO ID: "+entity.getId());

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
        String sql = "DELETE FROM tb_card WHERE id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, id);
            int deletedRows = ps.executeUpdate();

            if(deletedRows == 0)
                throw new EntityNotFoundException("CARD NÃO ENCONTRADO COM ID: "+id);

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }
    }

    public void updateBlockedStatus(Card card){
        String sql = "UPDATE tb_card SET blocked = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, card.isBlocked());
            ps.setLong(2, card.getId());

            ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);
        } finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }
    }

    public void updateColumn(long cardId, long newColumnId){
        String sql = "UPDATE tb_card SET board_column_id = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, newColumnId);
            ps.setLong(2, cardId);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new EntityNotFoundException("CARD NÃO ENCONTRADO PARA ATUALIZAÇÃO.");
            }

            conn.commit();

        } catch (SQLException e) {
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);
        } finally {
            DataBaseUtils.closeQuietly(null, ps, conn);
        }
    }
}

