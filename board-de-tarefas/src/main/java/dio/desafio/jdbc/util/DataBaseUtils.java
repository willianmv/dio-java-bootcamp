package dio.desafio.jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUtils {

    public static void performRollback(Connection conn){
        if(conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Erro ao executar Rollback");
                e.printStackTrace();
            }
        }
    }

    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
                System.err.println("Erro ao fechar ResultSet");
            }
        }
    }

    public static void closeQuietly(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ignored) {
                System.err.println("Erro ao fechar PreparedStatement");
            }
        }
    }

    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
                conn.close();
            } catch (SQLException ignored) {
                System.err.println("Erro ao fechar Connection");
            }
        }
    }

    public static void closeQuietly(ResultSet rs, PreparedStatement ps, Connection conn) {
        closeQuietly(rs);
        closeQuietly(ps);
        closeQuietly(conn);
    }

}
