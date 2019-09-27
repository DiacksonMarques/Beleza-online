package com.evo.belezaonline_2.Controller;

public class GetServico {

    private int id;
    private String nome;

    public GetServico() {
    }

    public GetServico(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }
}
