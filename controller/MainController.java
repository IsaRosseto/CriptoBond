package controller;

import view.*;
import model.User;
import DAO.CarteiraDAO;
import DAO.CotacaoDAO;
import DAO.UsuarioDAO;
import DAO.Database;
import DAO.Cryptocurrency;
import DAO.DatabaseUtils;
import model.Balance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import model.Usuario;

public class MainController {
    private MainMenuView mainMenuView;
    private UsuarioDAO usuarioDAO;

    public MainController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        this.usuarioDAO = new UsuarioDAO();

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

        mainMenuView.addRegisterCarteiraListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterCarteira();
            }
        });

        mainMenuView.addDeleteCarteiraListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteCarteira();
            }
        });

        mainMenuView.addCheckBalanceListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckBalance();
            }
        });

        mainMenuView.addUpdateCotacoesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateCotacoes();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainMenuView, "Erro ao atualizar cotações: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        mainMenuView.addExtratoListener(new ActionListener() {
           @Override
            public void actionPerformed(ActionEvent e) {
                showExtrato();
            }
        });
    }

    private void showRegisterUser() {
        RegisterUserView view = new RegisterUserView();
        view.setVisible(true);
        view.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = view.getName();
                String cpf = view.getCpf();
                String senha = view.getPassword();
                User user = new User(nome, cpf, senha);
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

    private void showRegisterCarteira() {
        RegisterCarteiraView view = new RegisterCarteiraView();
        view.setVisible(true);
        view.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome_cripto = view.getNome();
                double cotacao_atual = view.getCotacao();
                double taxa_compra = view.getTaxaC();
                double taxa_venda = view.getTaxaV();
                
                Cryptocurrency cryptocurrency = new Cryptocurrency(nome_cripto,cotacao_atual,taxa_compra,taxa_venda);
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

    private void showDeleteCarteira() {
        DeleteCarteiraView view = new DeleteCarteiraView();
        view.setVisible(true);
        view.addDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.getName().trim();
                System.out.println("Nome da criptomoeda a ser excluída: " + name); // Depuração
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Nome da criptomoeda não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    List<String> columns = DatabaseUtils.getTableColumns("carteira");
                    if (!columns.contains(name)) {
                        JOptionPane.showMessageDialog(view, "A coluna \"" + name + "\" não existe na tabela 'carteira'.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Cryptocurrency.deleteCryptocurrency(name);
                    JOptionPane.showMessageDialog(view, "Criptomoeda excluída com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                } catch (SQLException ex) {
                    System.out.println("Erro SQL: " + ex.getMessage()); // Depuração
                    JOptionPane.showMessageDialog(view, "Erro ao excluir criptomoeda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showCheckBalance() {
         CheckBalanceView checkBalanceView = new CheckBalanceView();
         Balance balanceController = new Balance(checkBalanceView);
         checkBalanceView.setVisible(true);
    }

    private void updateCotacoes() throws SQLException {
        try {
                CotacaoDAO.atualizarCotações();
            JOptionPane.showMessageDialog(mainMenuView, "Cotação atualizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar cotações: " + e.getMessage());
            }
        }
           
    private void showExtrato() {
        String cpf = JOptionPane.showInputDialog(mainMenuView, "Digite o CPF:", "Consultar Extrato", JOptionPane.PLAIN_MESSAGE);
        if (cpf != null && !cpf.trim().isEmpty()) {
            Usuario usuario = usuarioDAO.getUsuarioByCpf(cpf.trim());
            if (usuario != null) {
                TelaExtrato telaExtrato = new TelaExtrato(usuario);
                telaExtrato.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(mainMenuView, "CPF não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainMenuView, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}


