package smo.admin.web.domain;

public class Usuario {

    private String idUsuario;

    private int idUnidadeInstituicao;

    private String nome;

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUnidadeInstituicao(int idUnidadeInstituicao) {
        this.idUnidadeInstituicao = idUnidadeInstituicao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdUnidadeInstituicao() {
        return idUnidadeInstituicao;
    }

    public String getNome() {
        return nome;
    }
}
