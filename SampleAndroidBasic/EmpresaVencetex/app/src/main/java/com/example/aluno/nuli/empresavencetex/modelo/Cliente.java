package com.example.aluno.nuli.empresavencetex.modelo;

/**
 * Created by aluno on 28/10/2015.
 */
public class Cliente {

    private long cnpj;

    private String nomeFantasia;

    private String telefone;

    public long getCnpj() {
        return cnpj;
    }

    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
