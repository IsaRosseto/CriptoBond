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

  public Map<String, Double> getCriptomoedasByCpf(String cpf) {
        String query = "SELECT * FROM carteira WHERE cpf = ?";
        Map<String, Double> criptomoedas = new HashMap<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cpf);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        if (!columnName.equalsIgnoreCase("cpf")) {
                            criptomoedas.put(columnName.replace("saldo_", ""), rs.getDouble(columnName));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return criptomoedas;
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

    public void atualizarUsuario(Usuario usuario) {
        String query = "UPDATE carteira SET real = ? WHERE cpf = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, usuario.getSaldo());
            stmt.setString(2, usuario.getCpf());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 public void registrarTransacao(Transacao transacao) {
        String query = "INSERT INTO transacoes (cpf, data, tipo, cripto, valor, taxa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, transacao.getCpf());
            pstmt.setTimestamp(2, transacao.getData());
            pstmt.setString(3, transacao.getTipo());
            pstmt.setString(4, transacao.getCripto());
            pstmt.setDouble(5, transacao.getValor());
            pstmt.setDouble(6, transacao.getTaxa());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getSaldoCripto(String cpf, String cripto) {
        String query = "SELECT " + getCriptoColumn(cripto) + " FROM carteira WHERE cpf = ?";
        double saldo = 0.0;
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cpf);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    saldo = rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public void atualizarSaldoCripto(String cpf, String cripto, double novoSaldo) {
        String query = "UPDATE carteira SET " + getCriptoColumn(cripto) + " = ? WHERE cpf = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, novoSaldo);
            pstmt.setString(2, cpf);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCriptoColumn(String cripto) {
        return "saldo_" + cripto.toLowerCase();
    }

    public List<Transacao> getTransacoesByCpf(String cpf) {
        List<Transacao> transacoes = new ArrayList<>();
        String query = "SELECT id, cpf, data, tipo, cripto, valor, taxa FROM transacoes WHERE cpf = ? ORDER BY data DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cpf);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transacao transacao = new Transacao(
                        rs.getInt("id"),
                        rs.getString("cpf"),
                        rs.getTimestamp("data"),
                        rs.getString("tipo"),
                        rs.getString("cripto"),
                        rs.getDouble("valor"),
                        rs.getDouble("taxa")
                    );
                    transacoes.add(transacao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transacoes;
    }
    
    public double getCotacao(String cripto) {
    if (cripto == null) {
        throw new IllegalArgumentException("Criptomoeda não pode ser nula.");
    }
    String query = "SELECT cotacao_atual FROM criptos WHERE nome_criptos = ?";
    try (Connection conn = Database.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, cripto.toLowerCase());
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("cotacao_atual");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0;
}

public Map<String, Double> getPrecosCriptomoedas() {
        Map<String, Double> precos = new HashMap<>();
        String query = "SELECT nome_criptos, cotacao_atual FROM criptos";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nome = resultSet.getString("nome_criptos");
                double preco = resultSet.getDouble("cotacao_atual");
                precos.put(nome, preco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return precos;
    }
}
