package com.example.lanternaverde.crud_sqlite.model;

import java.io.Serializable;

/**
 * Created by Lanterna Verde on 27/08/2017.
 * manipula todas as informações de um objeto Pessoa
 */

public class Pessoa implements Serializable {
    private int id;
    private String nome, endereco, cpf, telefone;

    public Pessoa() { //metodo construtor
    }

    //metodos GET e Set:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone= telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() { //sobrescrevendo o toString pra retornar somente o campo nome
        return nome.toString();
    }
}
