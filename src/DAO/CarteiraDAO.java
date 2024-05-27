package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarteiraDAO {
    private String cpf;
    private double real;

    public CarteiraDAO(String cpf, double real) {
        this.cpf = cpf;
        this.real = real;
    }

    public String getCpf() {
        return cpf;
    }

    public double getReal() {
        return real;
    }

    public static CarteiraDAO getCarteiraByCpf(String cpf) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM carteira WHERE cpf = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new CarteiraDAO(cpf, resultSet.getDouble("real"));
        }
        return null;
    }

    public static void addColumnForNewCrypto(String cryptoName) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "ALTER TABLE carteira ADD COLUMN " + cryptoName + " DOUBLE PRECISION DEFAULT 0";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    public static void deleteColumnForCrypto(String cryptoName) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "ALTER TABLE carteira DROP COLUMN " + cryptoName;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    public static void updateCotacao(String cpf, String nome, double cotacao) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "UPDATE carteira SET " + nome + " = ? WHERE cpf = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, cotacao);
        statement.setString(2, cpf);
        statement.executeUpdate();
    }
}
