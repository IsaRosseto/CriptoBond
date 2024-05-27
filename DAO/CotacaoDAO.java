package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class CotacaoDAO {

    public static void atualizarCotações() throws SQLException {
        Connection connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);

            // Selecionar as cotações atuais de todas as criptomoedas
            String sqlSelect = "SELECT nome_criptos, cotacao_atual FROM public.criptos;";
            String sqlUpdate = "UPDATE public.criptos SET cotacao_atual = ? WHERE nome_criptos = ?;";

            try (PreparedStatement selectStatement = connection.prepareStatement(sqlSelect);
                 PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate);
                 ResultSet resultSet = selectStatement.executeQuery()) {

                Random random = new Random();

                while (resultSet.next()) {
                    String nomeCripto = resultSet.getString("nome_criptos");
                    double cotacaoAtual = resultSet.getDouble("cotacao_atual");

                    // Gerar variação aleatória entre -5% e +5%
                    double variacaoPercentual = (random.nextDouble() * 10) - 5;
                    double novaCotacao = cotacaoAtual + (cotacaoAtual * variacaoPercentual / 100);

                    // Atualizar a nova cotação
                    updateStatement.setDouble(1, novaCotacao);
                    updateStatement.setString(2, nomeCripto);
                    updateStatement.executeUpdate();
                }
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
