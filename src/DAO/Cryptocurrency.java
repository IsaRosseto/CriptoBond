package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Cryptocurrency {
    private String nome;
    private double cotacao;
    private double taxaC;
    private double taxaV;

    public Cryptocurrency(String nome, double cotacao, double taxaC, double taxaV) {
        this.nome = nome;
        this.cotacao = cotacao;
        this.taxaC = taxaC;
        this.taxaV = taxaV;
    }

    public Cryptocurrency(String nome, double cotacao) {
        this.nome = nome;
        this.cotacao = cotacao;
    }

    public double getTaxaC() {
        return taxaC;
    }

    public double getTaxaV() {
        return taxaV;
    }

    public String getName() {
        return nome;
    }

    public double getCotacao() {
        return cotacao;
    }

    public void setCotacao(double cotacao) {
        this.cotacao = cotacao;
    }

    public static void addCryptocurrency(Cryptocurrency cryptocurrency) throws SQLException {
        Connection connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);

            // Inserir a nova criptomoeda na tabela criptos
            String sqlInsert = "INSERT INTO criptos (nome_criptos, cotacao_atual, taxa_compra, taxa_venda) VALUES (?, ?, ?, ?);";
            try (PreparedStatement statementInsert = connection.prepareStatement(sqlInsert)) {
                statementInsert.setString(1, cryptocurrency.getName());
                statementInsert.setDouble(2, cryptocurrency.getCotacao());
                statementInsert.setDouble(3, cryptocurrency.getTaxaC());
                statementInsert.setDouble(4, cryptocurrency.getTaxaV());

                statementInsert.executeUpdate();
            }

            String sqlAlterTable = "ALTER TABLE carteira ADD COLUMN \"" + cryptocurrency.getName() + "\" double precision NOT NULL DEFAULT 0";
            try (PreparedStatement statementAlterTable = connection.prepareStatement(sqlAlterTable)) {
                statementAlterTable.executeUpdate();
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

    public static void deleteCryptocurrency(String nome) throws SQLException {
        Connection connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);

            // Verificar se a coluna existe na tabela 'carteira'
            List<String> columns = DatabaseUtils.getTableColumns("carteira");
            if (!columns.contains(nome)) {
                throw new SQLException("A coluna \"" + nome + "\" não existe na tabela 'carteira'.");
            }

            // Remover a coluna correspondente da tabela 'carteira'
            String sqlAlterTable = "ALTER TABLE carteira DROP COLUMN \"" + nome + "\"";
            try (PreparedStatement statementAlterTable = connection.prepareStatement(sqlAlterTable)) {
                statementAlterTable.executeUpdate();
            }

            // Remover a criptomoeda da tabela 'criptos'
            String sqlDelete = "DELETE FROM criptos WHERE nome_criptos = ?";
            try (PreparedStatement statementDelete = connection.prepareStatement(sqlDelete)) {
                statementDelete.setString(1, nome);
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
