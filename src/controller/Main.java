package controller;

import javax.swing.SwingUtilities;
import view.TelaBoasVindas;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaBoasVindas().setVisible(true);
            }
        });
    }
}
