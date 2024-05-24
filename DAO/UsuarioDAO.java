package DAO;

import model.Transacao;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioDAO {

    public Usuario getUsuarioByCpfESenha(String cpf, String senha) {
        Usuario usuario = null;
        String loginQuery = "SELECT * FROM login WHERE cpf = ? AND senha = ?";
        String carteiraQuery = "SELECT * FROM carteira WHERE cpf = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
             PreparedStatement carteiraStatement = connection.prepareStatement(carteiraQuery)) {

            loginStatement.setString(1, cpf);
            loginStatement.setString(2, senha);
            ResultSet loginResultSet = loginStatement.executeQuery();

            if (loginResultSet.next()) {
                String nome = loginResultSet.getString("nome");

                carteiraStatement.setString(1, cpf);
                ResultSet carteiraResultSet = carteiraStatement.executeQuery();

                if (carteiraResultSet.next()) {
                    double saldo = carteiraResultSet.getDouble("real");
                    Map<String, Double> criptomoedas = new HashMap<>();

                    ResultSetMetaData metaData = carteiraResultSet.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String columnName = metaData.getColumnName(i);
                        if (!columnName.equals("cpf") && !columnName.equals("real")) {
                            criptomoedas.put(columnName, carteiraResultSet.getDouble(columnName));
                        }
                    }

                    usuario = new Usuario(cpf, nome, saldo, criptomoedas, senha);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public Usuario getUsuarioByCpf(String cpf) {
        Usuario usuario = null;
        String loginQuery = "SELECT * FROM login WHERE cpf = ?";
        String carteiraQuery = "SELECT * FROM carteira WHERE cpf = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
             PreparedStatement carteiraStatement = connection.prepareStatement(carteiraQuery)) {

            loginStatement.setString(1, cpf);
            ResultSet loginResultSet = loginStatement.executeQuery();

            if (loginResultSet.next()) {
                String nome = loginResultSet.getString("nome");
                String senha = loginResultSet.getString("senha");

                carteiraStatement.setString(1, cpf);
                ResultSet carteiraResultSet = carteiraStatement.executeQuery();

                if (carteiraResultSet.next()) {
                    double saldo = carteiraResultSet.getDouble("real");
                    Map<String, Double> criptomoedas = new HashMap<>();

                    ResultSetMetaData metaData = carteiraResultSet.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String columnName = metaData.getColumnName(i);
                        if (!columnName.equals("cpf") && !columnName.equals("real")) {
                            criptomoedas.put(columnName, carteiraResultSet.getDouble(columnName));
                        }
                    }

                    usuario = new Usuario(cpf, nome, saldo, criptomoedas, senha);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public void atualizarSaldo(String cpf, double novoSaldo) {
        String query = "UPDATE carteira SET real = ? WHERE cpf = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, novoSaldo);
            statement.setString(2, cpf);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarCriptomoedas(String cpf, Map<String, Double> criptomoedas) {
        StringBuilder query = new StringBuilder("UPDATE carteira SET ");
        criptomoedas.forEach((k, v) -> query.append(k).append(" = ?, "));
        query.append("WHERE cpf = ?");

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString().replace(", WHERE", " WHERE"))) {

            int index = 1;
            for (double value : criptomoedas.values()) {
                statement.setDouble(index++, value);
            }
            statement.setString(index, cpf);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarTransacao(Transacao transacao) {
        String query = "INSERT INTO transacoes (cpf, data, tipo, valor, taxa, saldo_real, saldo_bitcoin, saldo_ethereum, saldo_ripple) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, transacao.getCpf());
            statement.setTimestamp(2, transacao.getData());
            statement.setString(3, transacao.getTipo());
            statement.setDouble(4, transacao.getValor());
            statement.setDouble(5, transacao.getTaxa());
            statement.setDouble(6, transacao.getSaldoReal());
            statement.setDouble(7, transacao.getSaldoBitcoin());
            statement.setDouble(8, transacao.getSaldoEthereum());
            statement.setDouble(9, transacao.getSaldoRipple());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getCryptoColumns() {
        List<String> cryptoColumns = new ArrayList<>();
        String query = "SELECT * FROM carteira LIMIT 1";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    if (!columnName.equals("cpf") && !columnName.equals("real")) {
                        cryptoColumns.add(columnName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cryptoColumns;
    }

    public Map<String, Double> getPrecosAtuaisCriptomoedas() {
        Map<String, Double> precos = new HashMap<>();
        String query = "SELECT * FROM cripto";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                double preco = resultSet.getDouble("preco");
                precos.put(nome, preco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return precos;
    }

    public List<Transacao> getTransacoesByCpf(String cpf) {
        List<Transacao> transacoes = new ArrayList<>();
        String query = "SELECT * FROM transacoes WHERE cpf = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String data = resultSet.getString("data");
                String tipo = resultSet.getString("tipo");
                double valor = resultSet.getDouble("valor");
                double taxa = resultSet.getDouble("taxa");
                double saldoReal = resultSet.getDouble("saldo_real");
                double saldoBitcoin = resultSet.getDouble("saldo_bitcoin");
                double saldoEthereum = resultSet.getDouble("saldo_ethereum");
                double saldoRipple = resultSet.getDouble("saldo_ripple");

                Transacao transacao = new Transacao(cpf, Timestamp.valueOf(data), tipo, valor, taxa, saldoReal, saldoBitcoin, saldoEthereum, saldoRipple);
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transacoes;
    }

    public boolean verificarSenha(String cpf, String senha) {
        String query = "SELECT 1 FROM login WHERE cpf = ? AND senha = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cpf);
            statement.setString(2, senha);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
