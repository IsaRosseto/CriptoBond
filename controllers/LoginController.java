package controllers;

import views.LoginView;
import views.MainMenuView;
import models.Adm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private LoginView loginView;
    private Map<String, Adm> adms;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        adms = new HashMap<>();
        adms.put("12345678900", new Adm("Admin", "123456"));
        
        this.loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    private void login() {
        String cpf = loginView.getCpf();
        String password = loginView.getPassword();
        try {
            Adm adm = Adm.getAdmByCpf(cpf);
            if (adm != null && adm.getSenha().equals(password)) {
                MainMenuView mainMenuView = new MainMenuView();
                new MainController(mainMenuView);
                mainMenuView.setVisible(true);
                loginView.dispose();
            } else {
                JOptionPane.showMessageDialog(loginView, "CPF ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loginView, "Erro de conexão com o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
