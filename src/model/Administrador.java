package model;

import DAO.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador {
    private String nome;
    private String senha;

    public Administrador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public static Administrador getAdmByCpf(String cpf) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "SELECT nome, senha FROM adm WHERE cpf = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Administrador(resultSet.getString("nome"), resultSet.getString("senha"));
        }
        return null;
    }
}
