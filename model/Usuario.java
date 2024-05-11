package model;

public class Usuario {
    private String cpf;
    private String senha;

    public Usuario(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return senha;
    }

    public void setPassword(String password) {
        this.senha = senha;
    }
}
