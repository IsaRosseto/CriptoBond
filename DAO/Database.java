package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements AutoCloseable {
    private Connection connection;

    public Database() throws SQLException {
        // Certifique-se de substituir 'seuBanco', 'usuario' e 'senha' com seus dados reais
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost/criptoBond", "postgres", "220423");
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
