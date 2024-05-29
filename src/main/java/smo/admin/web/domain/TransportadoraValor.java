package smo.admin.web.domain;

import java.util.List;

public class TransportadoraValor {

    private String cnpj;

    private String nome;

    private List<Integer> pontosDeAtendimento;

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPontosDeAtendimento(List<Integer> pontosDeAtendimento) {
        this.pontosDeAtendimento = pontosDeAtendimento;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public List<Integer> getPontosDeAtendimento() {
        return pontosDeAtendimento;
    }
}
