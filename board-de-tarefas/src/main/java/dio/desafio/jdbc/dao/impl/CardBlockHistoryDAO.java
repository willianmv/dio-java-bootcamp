package dio.desafio.jdbc.dao.impl;

import dio.desafio.jdbc.dao.IBlockHistoryDAO;
import dio.desafio.jdbc.model.CardBlockHistory;
import dio.desafio.jdbc.util.DataBaseUtils;
import dio.desafio.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardBlockHistoryDAO implements IBlockHistoryDAO<CardBlockHistory, Long> {

    @Override
    public CardBlockHistory registerBlock(Long cardId, String reason) {
        String sql = "INSERT INTO tb_card_block_history (card_id, block_reason) VALUES (?, ?) RETURNING id, block_at";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        CardBlockHistory cardBlockHistory = new CardBlockHistory();

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, cardId);
            ps.setString(2, reason);
            rs = ps.executeQuery();

            if(rs.next()){
                cardBlockHistory.setId(rs.getLong("id"));
                cardBlockHistory.setBlockedAt(rs.getTimestamp("block_at").toLocalDateTime());
            }

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return cardBlockHistory;
    }

    @Override
    public CardBlockHistory registerUnblock(Long cardId, String reason) {
        String sql = "UPDATE tb_card_block_history SET unblock_at = CURRENT_TIMESTAMP, unblock_reason = ? WHERE card_id = ? AND unblock_at IS NULL RETURNING id, unblock_at";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        CardBlockHistory cardBlockHistory = new CardBlockHistory();

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, reason);
            ps.setLong(2, cardId);
            rs = ps.executeQuery();

            if(rs.next()){
                cardBlockHistory.setId(rs.getLong("id"));
                cardBlockHistory.setUnblockedAt(rs.getTimestamp("unblock_at").toLocalDateTime());
            }

            conn.commit();

        }catch (SQLException e){
            DataBaseUtils.performRollback(conn);
            throw new RuntimeException(e);

        }finally {
            DataBaseUtils.closeQuietly(rs, ps, conn);
        }
        return cardBlockHistory;
    }
}
