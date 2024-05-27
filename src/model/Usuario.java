package model;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private String cpf;
    private String nome;
    private double saldo;
    private Map<String, Double> criptomoedas;
    private String senha;

    public Usuario(String cpf, String nome, double saldo, Map<String, Double> criptomoedas, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.saldo = saldo;
        this.criptomoedas = criptomoedas != null ? criptomoedas : new HashMap<>();
        this.senha = senha;
    }

    // Getters e setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Map<String, Double> getCriptomoedas() {
        return criptomoedas;
    }

    public void setCriptomoedas(Map<String, Double> criptomoedas) {
        this.criptomoedas = criptomoedas;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
