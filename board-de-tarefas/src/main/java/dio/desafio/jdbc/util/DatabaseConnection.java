package dio.desafio.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private DatabaseConnection(){}

    public static Connection getConnection(){

        try {
            Connection connection = DriverManager.getConnection(
                    System.getenv("DB_URL"),
                    System.getenv("DB_USER"),
                    System.getenv("DB_PASSWORD")
            );

            connection.setAutoCommit(false);
            return connection;

        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão com o banco");
            e.printStackTrace();
            throw new RuntimeException("Não foi possível conectar ao banco de dados", e);
        }
    }
}
