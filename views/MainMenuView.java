package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private JButton registerUserButton;
    private JButton deleteUserButton;
    private JButton registerCryptoButton;
    private JButton deleteCryptoButton;
    private JButton checkBalanceButton;
    private JButton updateQuotesButton;

    public MainMenuView() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        registerUserButton = new JButton("Cadastrar Novo Usuário");
        panel.add(registerUserButton);

        deleteUserButton = new JButton("Excluir Usuário");
        panel.add(deleteUserButton);

        registerCryptoButton = new JButton("Cadastrar Nova Criptomoeda");
        panel.add(registerCryptoButton);

        deleteCryptoButton = new JButton("Excluir Criptomoeda");
        panel.add(deleteCryptoButton);

        checkBalanceButton = new JButton("Consultar Saldo");
        panel.add(checkBalanceButton);

        updateQuotesButton = new JButton("Atualizar Cotações");
        panel.add(updateQuotesButton);
    }

    public void addRegisterUserListener(ActionListener listener) {
        registerUserButton.addActionListener(listener);
    }

    public void addDeleteUserListener(ActionListener listener) {
        deleteUserButton.addActionListener(listener);
    }

    public void addRegisterCryptoListener(ActionListener listener) {
        registerCryptoButton.addActionListener(listener);
    }

    public void addDeleteCryptoListener(ActionListener listener) {
        deleteCryptoButton.addActionListener(listener);
    }

    public void addCheckBalanceListener(ActionListener listener) {
        checkBalanceButton.addActionListener(listener);
    }

    public void addUpdateQuotesListener(ActionListener listener) {
        updateQuotesButton.addActionListener(listener);
    }
}
