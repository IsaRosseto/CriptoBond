package DAO;

import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public ResultSet consultar(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM login WHERE cpf = ? AND senha = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, usuario.getCpf());
        stmt.setString(2, usuario.getPassword());
        return stmt.executeQuery();
    }
}
