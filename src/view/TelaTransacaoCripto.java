package view;

import DAO.UsuarioDAO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Map;
import model.Transacao;
import model.Usuario;
import resources.CriptoAPI;

public class TelaTransacaoCripto extends JFrame {
    private JTextField senhaField;
    private JButton autenticarButton;
    private JLabel cotacaoLabel;
    private JComboBox<String> criptoComboBox;
    private JTextField valorField;
    private JLabel quantidadeLabel;
    private JButton comprarButton;
    private JButton venderButton;
    private JLabel saldoLabel;
    private JPanel panelPrincipal;

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioAtual;
    private CriptoAPI criptoAPI;
    private Map<String, Map<String, Double>> criptoInfo;
    private double cotacaoAtual = 0.0;

    public TelaTransacaoCripto(Usuario usuario) {
        this.usuarioAtual = usuario;
        usuarioDAO = new UsuarioDAO();
        criptoAPI = new CriptoAPI();
        try {
            criptoInfo = criptoAPI.getCriptoInfo();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao obter informações das criptomoedas: " + e.getMessage());
            dispose();
        }

        // Inicializando o painel principal com GridBagLayout
        panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.setBackground(Color.BLACK);

        // Configurações de design
        Color buttonColor = new Color(255, 204, 0);
        Color textColor = Color.WHITE;
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        // Configurando os componentes
        senhaField = new JTextField(10);
        senhaField.setFont(fieldFont);
        autenticarButton = createButton("Autenticar", buttonColor, Color.BLACK, labelFont);

        cotacaoLabel = new JLabel("Cotações: ");
        cotacaoLabel.setFont(labelFont);
        cotacaoLabel.setForeground(textColor);

        criptoComboBox = new JComboBox<>();
        criptoComboBox.setFont(fieldFont);
        for (String cripto : criptoInfo.keySet()) {
            criptoComboBox.addItem(cripto);
        }

        valorField = new JTextField(10);
        valorField.setFont(fieldFont);
        quantidadeLabel = new JLabel("Quantidade: 0");
        quantidadeLabel.setFont(labelFont);
        quantidadeLabel.setForeground(textColor);

        comprarButton = createButton("Comprar", buttonColor, Color.BLACK, labelFont);
        venderButton = createButton("Vender", buttonColor, Color.BLACK, labelFont);

        saldoLabel = new JLabel("Saldo: $0.00");
        saldoLabel.setFont(labelFont);
        saldoLabel.setForeground(textColor);

        // Adicionando componentes ao painel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelPrincipal.add(createLabel("Senha:", textColor, labelFont), gbc);

        gbc.gridx = 1;
        panelPrincipal.add(senhaField, gbc);

        gbc.gridx = 2;
        panelPrincipal.add(autenticarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(cotacaoLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(createLabel("Criptomoeda:", textColor, labelFont), gbc);

        gbc.gridx = 1;
        panelPrincipal.add(criptoComboBox, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panelPrincipal.add(createLabel("Valor em R$:", textColor, labelFont), gbc);

        gbc.gridx = 1;
        panelPrincipal.add(valorField, gbc);

        gbc.gridx = 2;
        panelPrincipal.add(quantidadeLabel, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        panelPrincipal.add(comprarButton, gbc);

        gbc.gridx = 1;
        panelPrincipal.add(venderButton, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(saldoLabel, gbc);

        // Configurações da janela
        setTitle("Transação de Criptomoedas");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);

        // Adicionando ação aos botões
        autenticarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    comprarCripto();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao obter cotação: " + ex.getMessage());
                }
            }
        });

        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    venderCripto();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao obter cotação: " + ex.getMessage());
                }
            }
        });

        criptoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCotacao();
            }
        });

        valorField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarQuantidade();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarQuantidade();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizarQuantidade();
            }
        });

        desabilitarTransacoes();
    }

    private JLabel createLabel(String text, Color textColor, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(font);
        return label;
    }

    private JButton createButton(String text, Color bgColor, Color fgColor, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 2));
        return button;
    }

    private void autenticarUsuario() {
        String senha = senhaField.getText();
        if (usuarioAtual.getSenha().equals(senha)) {
            try {
                mostrarCotacoes();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao obter cotações: " + e.getMessage());
            }
            atualizarSaldosCripto();
            atualizarSaldo();
            habilitarTransacoes();
        } else {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
        }
    }

    private void mostrarCotacoes() {
        StringBuilder cotacoes = new StringBuilder();
        for (String cripto : criptoInfo.keySet()) {
            cotacoes.append(String.format("%s: $%.2f | ", cripto, criptoInfo.get(cripto).get("cotacao")));
        }
        cotacaoLabel.setText(cotacoes.toString());
    }

    private void atualizarSaldosCripto() {
        for (String cripto : criptoInfo.keySet()) {
            double saldoCripto = usuarioDAO.getSaldoCripto(usuarioAtual.getCpf(), cripto);
            criptoInfo.get(cripto).put("saldo", saldoCripto);
        }
    }

    private void atualizarSaldo() {
        saldoLabel.setText(String.format("Saldo: $%.2f", usuarioAtual.getSaldo()));
    }

    private void desabilitarTransacoes() {
        comprarButton.setEnabled(false);
        venderButton.setEnabled(false);
        criptoComboBox.setEnabled(false);
        valorField.setEnabled(false);
    }

    private void habilitarTransacoes() {
        comprarButton.setEnabled(true);
        venderButton.setEnabled(true);
        criptoComboBox.setEnabled(true);
        valorField.setEnabled(true);
    }

    private void atualizarCotacao() {
        String criptoSelecionada = (String) criptoComboBox.getSelectedItem();
        cotacaoAtual = criptoInfo.get(criptoSelecionada).get("cotacao");
    }

    private void atualizarQuantidade() {
        String valorText = valorField.getText();
        if (!valorText.isEmpty()) {
            try {
                double valorReais = Double.parseDouble(valorText);
                double quantidade = valorReais / cotacaoAtual;
                quantidadeLabel.setText(String.format("Quantidade: %.8f", quantidade));
            } catch (NumberFormatException e) {
                quantidadeLabel.setText("Quantidade: 0");
            }
        } else {
            quantidadeLabel.setText("Quantidade: 0");
        }
    }

    private void comprarCripto() {
        String criptoSelecionada = (String) criptoComboBox.getSelectedItem();
        double valorReais = Double.parseDouble(valorField.getText());
        double quantidade = valorReais / cotacaoAtual;
        double custo = valorReais * (1 + criptoInfo.get(criptoSelecionada).get("taxa_compra"));

        if (usuarioAtual.getSaldo() >= custo) {
            usuarioAtual.setSaldo(usuarioAtual.getSaldo() - custo);
            usuarioDAO.atualizarUsuario(usuarioAtual);

            double saldoCriptoAtual = usuarioDAO.getSaldoCripto(usuarioAtual.getCpf(), criptoSelecionada);
            saldoCriptoAtual += quantidade;
            usuarioDAO.atualizarSaldoCripto(usuarioAtual.getCpf(), criptoSelecionada, saldoCriptoAtual);

            Transacao transacao = new Transacao(0, usuarioAtual.getCpf(), new Timestamp(System.currentTimeMillis()), "Compra", criptoSelecionada, valorReais, criptoInfo.get(criptoSelecionada).get("taxa_compra"));
            usuarioDAO.registrarTransacao(transacao);
            JOptionPane.showMessageDialog(this, "Compra realizada com sucesso!");
            atualizarSaldo();
        } else {
            JOptionPane.showMessageDialog(this, "Saldo insuficiente!");
        }
    }

    private void venderCripto() {
        String criptoSelecionada = (String) criptoComboBox.getSelectedItem();
        double valorReais = Double.parseDouble(valorField.getText());
        double quantidade = valorReais / cotacaoAtual;
        double receita = valorReais * (1 - criptoInfo.get(criptoSelecionada).get("taxa_venda"));

        double saldoCriptoAtual = usuarioDAO.getSaldoCripto(usuarioAtual.getCpf(), criptoSelecionada);
        if (saldoCriptoAtual >= quantidade) {
            saldoCriptoAtual -= quantidade;
            usuarioDAO.atualizarSaldoCripto(usuarioAtual.getCpf(), criptoSelecionada, saldoCriptoAtual);

            usuarioAtual.setSaldo(usuarioAtual.getSaldo() + receita);
            usuarioDAO.atualizarUsuario(usuarioAtual);

            Transacao transacao = new Transacao(0, usuarioAtual.getCpf(), new Timestamp(System.currentTimeMillis()), "Venda", criptoSelecionada, valorReais, criptoInfo.get(criptoSelecionada).get("taxa_venda"));
            usuarioDAO.registrarTransacao(transacao);
            JOptionPane.showMessageDialog(this, "Venda realizada com sucesso!");
            atualizarSaldo();
        } else {
            JOptionPane.showMessageDialog(this, "Saldo de criptomoeda insuficiente!");
        }
    }


}
