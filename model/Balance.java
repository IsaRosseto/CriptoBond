package model;

import DAO.UsuarioDAO;
import view.CheckBalanceView;
import view.TelaSaldoADM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Balance {
    private CheckBalanceView checkBalanceView;
    private UsuarioDAO usuarioDAO;

    public Balance(CheckBalanceView checkBalanceView) {
        this.checkBalanceView = checkBalanceView;
        this.usuarioDAO = new UsuarioDAO();
        this.checkBalanceView.addCheckListener(new CheckButtonListener());
    }

    class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cpf = checkBalanceView.getCpf();
            Usuario usuario = usuarioDAO.getUsuarioByCpf(cpf);
            if (usuario != null) {
                TelaSaldoADM telaSaldoADM = new TelaSaldoADM(usuario);
                telaSaldoADM.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(checkBalanceView, "Usuário não encontrado!");
            }
        }
    }
}
