package view;

import DAO.UsuarioDAO;
import model.Usuario;
import model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TelaExtrato extends JFrame {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private JTextPane textPane;

    public TelaExtrato(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();
        initialize();
        loadTransacoes();
    }

    private void initialize() {
        setTitle("Extrato");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.BLACK);

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.WHITE);
        textPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 2));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTransacoes() {
        List<Transacao> transacoes = usuarioDAO.getTransacoesByCpf(usuario.getCpf());
        Map<String, Double> saldosCriptomoedas = usuarioDAO.getCriptomoedasByCpf(usuario.getCpf());
        StringBuilder sb = new StringBuilder();

        // Cabeçalho
        sb.append("<html><body style='font-family:SansSerif;color:white;'>");
        sb.append("<h1 style='text-align:center;color:rgb(255,204,0);'>Extrato de Transações</h1>");
        sb.append("<h2>Nome: ").append(usuario.getNome()).append("</h2>");
        sb.append("<h2>CPF: ").append(usuario.getCpf()).append("</h2>");
        sb.append("<hr style='border:1px solid rgb(255,204,0);'>");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        double saldoRealAtual = usuario.getSaldo();
        Map<String, Double> saldosCriptomoedasAtual = usuarioDAO.getCriptomoedasByCpf(usuario.getCpf());

        for (Transacao transacao : transacoes) {
            sb.append("<div style='margin-bottom:20px;'>");
            sb.append("<strong>Data:</strong> ").append(dateFormat.format(transacao.getData())).append("<br>");
            sb.append("<strong>Tipo:</strong> ").append(transacao.getTipo()).append("<br>");
            sb.append("<strong>Valor:</strong> ").append(currencyFormat.format(transacao.getValor())).append("<br>");
            sb.append("<strong>Taxa:</strong> ").append(currencyFormat.format(transacao.getTaxa())).append("<br>");
            if (transacao.getCripto() != null && !transacao.getCripto().isEmpty()) {
                sb.append("<strong>Criptomoeda:</strong> ").append(transacao.getCripto()).append("<br>");
            }

            // Atualiza o saldo real e o saldo das criptomoedas após cada transação
            if (transacao.getTipo().equals("Depósito")) {
                saldoRealAtual += transacao.getValor();
            } else if (transacao.getTipo().equals("Saque")) {
                saldoRealAtual -= transacao.getValor();
            } else if (transacao.getTipo().equals("Compra")) {
                saldoRealAtual -= transacao.getValor() + transacao.getTaxa();
                if (transacao.getCripto() != null && !transacao.getCripto().isEmpty()) {
                    String cripto = transacao.getCripto();
                    double saldoCripto = saldosCriptomoedasAtual.getOrDefault(cripto, 0.0);
                    double cotacao = usuarioDAO.getCotacao(cripto);
                    if (cotacao != 0.0) {
                        saldosCriptomoedasAtual.put(cripto, saldoCripto + transacao.getValor() / cotacao);
                    }
                }
            } else if (transacao.getTipo().equals("Venda")) {
                saldoRealAtual += transacao.getValor() - transacao.getTaxa();
                if (transacao.getCripto() != null && !transacao.getCripto().isEmpty()) {
                    String cripto = transacao.getCripto();
                    double saldoCripto = saldosCriptomoedasAtual.getOrDefault(cripto, 0.0);
                    double cotacao = usuarioDAO.getCotacao(cripto);
                    if (cotacao != 0.0) {
                        saldosCriptomoedasAtual.put(cripto, saldoCripto - transacao.getValor() / cotacao);
                    }
                }
            }

            sb.append("<strong>Saldo Atual:</strong><br>");
            for (Map.Entry<String, Double> entry : saldosCriptomoedasAtual.entrySet()) {
                sb.append(entry.getKey().toUpperCase()).append(": ").append(entry.getValue()).append("<br>");
            }
            sb.append("</div>");
            sb.append("<hr style='border:0.5px solid rgb(255,204,0);'>");
        }

        sb.append("</body></html>");

        textPane.setContentType("text/html");
        textPane.setText(sb.toString());
    }

    private String formatCurrency(double value) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return currencyFormat.format(value);
    }
}
