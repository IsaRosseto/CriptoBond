package controllers;

import views.*;
import models.User;
import models.Cryptocurrency;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

public class MainController {
    private MainMenuView mainMenuView;

    public MainController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;

        mainMenuView.addRegisterUserListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterUser();
            }
        });

        mainMenuView.addDeleteUserListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteUser();
            }
        });

        mainMenuView.addRegisterCryptoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterCrypto();
            }
        });

        mainMenuView.addDeleteCryptoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteCrypto();
            }
        });

        mainMenuView.addCheckBalanceListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckBalance();
            }
        });

        mainMenuView.addUpdateQuotesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuotes();
            }

            private void updateQuotes() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    private void showRegisterUser() {
        RegisterUserView view = new RegisterUserView();
        view.setVisible(true);
        view.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.getName();
                String cpf = view.getCpf();
                String password = view.getPassword();
                User user = new User(name, cpf, password);
                try {
                    User.addUser(user);
                    JOptionPane.showMessageDialog(view, "Usuário cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showDeleteUser() {
        DeleteUserView view = new DeleteUserView();
        view.setVisible(true);
        view.addDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = view.getCpf();
                try {
                    User.deleteUser(cpf);
                    JOptionPane.showMessageDialog(view, "Usuário excluído com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao excluir usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showRegisterCrypto() {
        RegisterCryptoView view = new RegisterCryptoView();
        view.setVisible(true);
        view.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.getName();
                double quotation = view.getQuotation();
                Cryptocurrency cryptocurrency = new Cryptocurrency(name, quotation);
                try {
                    Cryptocurrency.addCryptocurrency(cryptocurrency);
                    JOptionPane.showMessageDialog(view, "Criptomoeda cadastrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao cadastrar criptomoeda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showDeleteCrypto() {
        DeleteCryptoView view = new DeleteCryptoView();
        view.setVisible(true);
        view.addDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.getName();
                try {
                    Cryptocurrency.deleteCryptocurrency(name);
                    JOptionPane.showMessageDialog(view, "Criptomoeda excluída com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao excluir criptomoeda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showCheckBalance() {
        CheckBalanceView view = new CheckBalanceView();
        view.setVisible(true);
        view.addCheckListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = view.getCpf();
                try {
                    User user = User.getUserByCpf(cpf);
                    if (user != null) {
                        JOptionPane.showMessageDialog(view, "Saldo: " + user.getReal(), "Saldo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(view, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao consultar saldo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
