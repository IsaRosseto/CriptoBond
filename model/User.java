package model;

import DAO.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String nome;
    private String cpf;
    private String senha;
    private double real;

    public User(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.real = 0;
    }

    public String getCpf() {
        return cpf;
    }

    public double getReal() {
        return real;
    }

    public static User getUserByCpf(String cpf) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "SELECT u.cpf, u.nome, c.real FROM login u JOIN carteira c ON u.cpf = c.cpf WHERE u.cpf = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user = new User(resultSet.getString("nome"), cpf, resultSet.getString("senha"));
            user.real = resultSet.getDouble("real");
            return user;
        }
        return null;
    }

    public static void addUser(User user) throws SQLException {
        Connection connection = Database.getConnection();
        String sqlUser = "INSERT INTO login (nome, cpf, senha) VALUES (?, ?, ?)";
        PreparedStatement statementUser = connection.prepareStatement(sqlUser);
        statementUser.setString(1, user.nome);
        statementUser.setString(2, user.cpf);
        statementUser.setString(3, user.senha);
        statementUser.executeUpdate();

        String sqlCarteira = "INSERT INTO carteira (cpf) VALUES (?)";
        PreparedStatement statementCarteira = connection.prepareStatement(sqlCarteira);
        statementCarteira.setString(1, user.cpf);
        statementCarteira.executeUpdate();
    }

    public static void deleteUser(String cpf) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM login WHERE cpf = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);
        statement.executeUpdate();
        
        String sqldcarteira = "DELETE FROM carteira WHERE cpf = ?";
        PreparedStatement statementdcarteira = connection.prepareStatement(sqldcarteira);
        statementdcarteira.setString(1, cpf);
        statementdcarteira.executeUpdate();
    }
}
