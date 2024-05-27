package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConnection() throws SQLException {
        // Carrega o driver do PostgreSQL
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado", e);
        }

        // Conecta ao banco de dados
        return DriverManager.getConnection("jdbc:postgresql://localhost/criptoBond", "postgres", "220423");
    }
}
