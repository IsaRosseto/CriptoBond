package view;

import DAO.UsuarioDAO;
import model.Usuario;
import model.Transacao;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;

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
        StringBuilder sb = new StringBuilder();
        
        // Cabeçalho
        sb.append("<html><body style='font-family:SansSerif;color:white;'>");
        sb.append("<h1 style='text-align:center;color:rgb(255,204,0);'>Extrato de Transações</h1>");
        sb.append("<h2>Nome: ").append(usuario.getNome()).append("</h2>");
        sb.append("<h2>CPF: ").append(usuario.getCpf()).append("</h2>");
        sb.append("<hr style='border:1px solid rgb(255,204,0);'>");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        for (Transacao transacao : transacoes) {
            sb.append("<div style='margin-bottom:20px;'>");
            sb.append("<strong>Data:</strong> ").append(dateFormat.format(transacao.getData())).append("<br>");
            sb.append("<strong>Tipo:</strong> ").append(transacao.getTipo()).append("<br>");
            sb.append("<strong>Valor:</strong> ").append(String.format("%.2f", transacao.getValor())).append("<br>");
            sb.append("<strong>Taxa:</strong> ").append(String.format("%.2f", transacao.getTaxa())).append("<br>");
            sb.append("<strong>Saldo após transação:</strong><br>");
            sb.append("Reais: ").append(String.format("%.2f", transacao.getSaldoReal())).append("<br>");
            sb.append("Bitcoin: ").append(transacao.getSaldoBitcoin()).append("<br>");
            sb.append("Ethereum: ").append(transacao.getSaldoEthereum()).append("<br>");
            sb.append("Ripple: ").append(transacao.getSaldoRipple()).append("<br>");
            sb.append("</div>");
            sb.append("<hr style='border:0.5px solid rgb(255,204,0);'>");
        }
        
        sb.append("</body></html>");

        textPane.setContentType("text/html");
        textPane.setText(sb.toString());
    }
}
