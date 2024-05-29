package smo.admin.web.domain;

public class TipoTerminal {

    private int idPontoAtendimento;
    private int idTipoTerminal;
    private int limSuperior;
    private int limInferior;
    private String descricao;

    public int getIdPontoAtendimento() {
        return idPontoAtendimento;
    }

    public void setIdPontoAtendimento(int idPontoAtendimento) {
        this.idPontoAtendimento = idPontoAtendimento;
    }

    public int getIdTipoTerminal() {
        return idTipoTerminal;
    }

    public void setIdTipoTerminal(int idTipoTerminal) {
        this.idTipoTerminal = idTipoTerminal;
    }

    public int getLimSuperior() {
        return limSuperior;
    }

    public void setLimSuperior(int limSuperior) {
        this.limSuperior = limSuperior;
    }

    public int getLimInferior() {
        return limInferior;
    }

    public void setLimInferior(int limInferior) {
        this.limInferior = limInferior;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "TipoTerminal{" +
                "idPontoAtendimento=" + idPontoAtendimento +
                ", idTipoTerminal=" + idTipoTerminal +
                ", limSuperior=" + limSuperior +
                ", limInferior=" + limInferior +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}