package smo.admin.web.domain;

public class UnidadeInstituicao {

    private int idUnidadeInstituicao;

    private String nome;

    private int tipoLocalidade;

    public int getIdUnidadeInstituicao() {
        return idUnidadeInstituicao;
    }

    public void setIdUnidadeInstituicao(int idUnidadeInstituicao) {
        this.idUnidadeInstituicao = idUnidadeInstituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipoLocalidade() {
        return tipoLocalidade;
    }

    public void setTipoLocalidade(int tipoLocalidade) {
        this.tipoLocalidade = tipoLocalidade;
    }

    @Override
    public String toString() {
        return "UnidadeInstituicao{" +
                "idUnidadeInstituicao=" + idUnidadeInstituicao +
                ", nome='" + nome + '\'' +
                ", tipoLocalidade=" + tipoLocalidade +
                '}';
    }
}