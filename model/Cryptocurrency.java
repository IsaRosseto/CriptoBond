// models/Cryptocurrency.java
package model;

import DAO.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cryptocurrency {
    private String name;
    private double quotation;

    public Cryptocurrency(String name, double quotation) {
        this.name = name;
        this.quotation = quotation;
    }

    public String getName() {
        return name;
    }

    public double getQuotation() {
        return quotation;
    }

    public void setQuotation(double quotation) {
        this.quotation = quotation;
    }

    public static void addCryptocurrency(Cryptocurrency cryptocurrency) throws SQLException {
        Connection connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);
            
            // Inserir a nova criptomoeda na tabela criptomoedas
            String sqlInsert = "INSERT INTO criptos (nome, cotacao) VALUES (?, ?)";
            try (PreparedStatement statementInsert = connection.prepareStatement(sqlInsert)) {
                statementInsert.setString(1, cryptocurrency.getName());
                statementInsert.setDouble(2, cryptocurrency.getQuotation());
                statementInsert.executeUpdate();
            }

            // Alterar a tabela 'criptos' para adicionar uma nova coluna com o nome da criptomoeda
            String sqlAlterTable = "ALTER TABLE carteira ADD COLUMN " + cryptocurrency.getName() + " double precision NOT NULL DEFAULT 0";
            try (PreparedStatement statementAlterTable = connection.prepareStatement(sqlAlterTable)) {
                statementAlterTable.executeUpdate();
            }

            // Atualizar o valor da nova coluna com a cotação fornecida
            String sqlUpdate = "UPDATE carteira SET " + cryptocurrency.getName() + " = ?";
            try (PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdate)) {
                statementUpdate.setDouble(1, cryptocurrency.getQuotation());
                statementUpdate.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
    
     public static void deleteCryptocurrency(String name) throws SQLException {
        Connection connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);

            // Remover a coluna correspondente da tabela 'criptos'
            String sqlAlterTable = "ALTER TABLE carteira DROP COLUMN " + name;
            try (PreparedStatement statementAlterTable = connection.prepareStatement(sqlAlterTable)) {
                statementAlterTable.executeUpdate();
            }

            // Remover a criptomoeda da tabela 'criptomoedas'
            String sqlDelete = "DELETE FROM criptos WHERE nome = ?";
            try (PreparedStatement statementDelete = connection.prepareStatement(sqlDelete)) {
                statementDelete.setString(1, name);
                statementDelete.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
