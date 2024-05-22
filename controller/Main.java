package controller;

import javax.swing.SwingUtilities;
import view.TelaLogin;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Iniciar a tela de login
                    TelaLogin telaLogin = new TelaLogin();
                    telaLogin.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
