package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public Usuario authenticate(String cpf, String password) throws SQLException {
        String query = "SELECT u.cpf, u.nome, c.real FROM login u JOIN carteira c ON u.cpf = c.cpf WHERE u.cpf = ? AND u.senha = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cpf);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getDouble("real"));
                } else {
                    return null;
                }
            }
        }
    }

    public Usuario getUsuarioByCPF(String cpf) throws SQLException {
        String query = "SELECT u.cpf, u.nome, c.real FROM login u JOIN carteira c ON u.cpf = c.cpf WHERE u.cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getDouble("real"));
                } else {
                    return null;
                }
            }
        }
    }
}
