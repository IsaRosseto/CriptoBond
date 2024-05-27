package model;

import java.sql.Timestamp;

public class Transacao {
    private int id;
    private String cpf;
    private Timestamp data;
    private String tipo;
    private String cripto;
    private double valor;
    private double taxa;

    public Transacao(int id, String cpf, Timestamp data, String tipo, String cripto, double valor, double taxa) {
        this.id = id;
        this.cpf = cpf;
        this.data = data;
        this.tipo = tipo;
        this.cripto = cripto;
        this.valor = valor;
        this.taxa = taxa;
    }

    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public Timestamp getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCripto() {
        return cripto;
    }

    public double getValor() {
        return valor;
    }

    public double getTaxa() {
        return taxa;
    }
}
