package model;

import java.sql.Timestamp;

public class Transacao {
    private String cpf;
    private Timestamp data;
    private String tipo;
    private double valor;
    private double taxa;
    private double saldoReal;
    private double saldoBitcoin;
    private double saldoEthereum;
    private double saldoRipple;

    public Transacao(String cpf, Timestamp data, String tipo, double valor, double taxa, double saldoReal, double saldoBitcoin, double saldoEthereum, double saldoRipple) {
        this.cpf = cpf;
        this.data = data;
        this.tipo = tipo;
        this.valor = valor;
        this.taxa = taxa;
        this.saldoReal = saldoReal;
        this.saldoBitcoin = saldoBitcoin;
        this.saldoEthereum = saldoEthereum;
        this.saldoRipple = saldoRipple;
    }

    // Getters e Setters

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public double getSaldoReal() {
        return saldoReal;
    }

    public void setSaldoReal(double saldoReal) {
        this.saldoReal = saldoReal;
    }

    public double getSaldoBitcoin() {
        return saldoBitcoin;
    }

    public void setSaldoBitcoin(double saldoBitcoin) {
        this.saldoBitcoin = saldoBitcoin;
    }

    public double getSaldoEthereum() {
        return saldoEthereum;
    }

    public void setSaldoEthereum(double saldoEthereum) {
        this.saldoEthereum = saldoEthereum;
    }

    public double getSaldoRipple() {
        return saldoRipple;
    }

    public void setSaldoRipple(double saldoRipple) {
        this.saldoRipple = saldoRipple;
    }
}
