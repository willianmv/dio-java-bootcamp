package dio.desafio.jdbc.dao.impl;

import dio.desafio.jdbc.dao.ITransitionHistoryDAO;
import dio.desafio.jdbc.model.CardTransitionHistory;
import dio.desafio.jdbc.util.DataBaseUtils;
import dio.desafio.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardTransitionDAO implements ITransitionHistoryDAO<CardTransitionHistory, Long> {

    @Override
    public CardTransitionHistory move(Long entityId, Long fromId, Long toId) {
        String sql = "INSERT INTO tb_card_transition (card_id, origin_column_id, destination_column_id) VALUES (?, ?, ?) RETURNING id, moved_at";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        CardTransitionHistory cardTransaction = new CardTransitionHistory();

        try{
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, entityId);
            ps.setLong(2, fromId);
            ps.setLong(3, toId);

            rs = ps.executeQuery();

            if(rs.next()){
                cardTransaction.setId(rs.getLong("id"));
                cardTransaction.setMovedAt(rs.getTimestamp("moved_at").toLocalDateTime());
            }

            conn.commit();

            }catch (SQLException e){
                DataBaseUtils.performRollback(conn);
                throw new RuntimeException(e);

            } finally {
                DataBaseUtils.closeQuietly(rs, ps, conn);
            }
            return cardTransaction;
        }

    }
