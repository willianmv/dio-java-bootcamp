package dio.desafio.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection(){}

    public static Connection getConnection(){
        if(connection == null){
            try{
                connection = DriverManager.getConnection(
                        System.getenv("DB_URL"),
                        System.getenv("DB_USER"),
                        System.getenv("DB_PASSWORD")
                );
                System.out.println("Conexão com o banco criada com sucesso!");

            }catch (SQLException ex){
                System.err.println("Erro ao conectar com o banco de dados.");
                ex.printStackTrace();
            }
        }
        return connection;
    }

}
