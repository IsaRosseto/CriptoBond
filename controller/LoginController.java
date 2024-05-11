package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import view.TelaLogin;
import DAO.Database;
import DAO.UsuarioDAO;
import java.util.Arrays;
import model.Usuario;

public class LoginController {
    private TelaLogin view;

    public LoginController(TelaLogin view) {
        this.view = view;
    }

    public boolean authenticateUser(String cpf, String password) {
        char[] passwordArray = view.getPasswordField().getPassword();
        Usuario usuario = new Usuario(view.getCpfField().getText(), password);

        Arrays.fill(passwordArray, '0');
        try (Database conexao = new Database()) {
            ResultSet res = new UsuarioDAO(conexao.getConnection()).consultar(usuario);

            if (res.next()) {
                view.setVisible(false);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Erro de conexão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
