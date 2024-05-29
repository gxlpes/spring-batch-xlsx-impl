package smo.admin.web.domain;

public class SaldoInicial {

    private int idOperacao;
    private String dataOperacao;
    private int idGrupoCaixa;
    private int idTerminal;
    private int idPontoAtendimento;
    private double valorOperacao;

    public int getIdOperacao() {
        return idOperacao;
    }

    public String getDataOperacao() {
        return dataOperacao;
    }

    public int getIdGrupoCaixa() {
        return idGrupoCaixa;
    }

    public void setIdGrupoCaixa(int idGrupoCaixa) {
        this.idGrupoCaixa = idGrupoCaixa;
    }

    public int getIdTerminal() {
        return idTerminal;
    }

    public int getIdPontoAtendimento() {
        return idPontoAtendimento;
    }

    public double getValorOperacao() {
        return valorOperacao;
    }

    public void setIdOperacao(int idOperacao) {
        this.idOperacao = idOperacao;
    }

    public void setDataOperacao(String dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public void setIdTerminal(int idTerminal) {
        this.idTerminal = idTerminal;
    }

    public void setIdPontoAtendimento(int idPontoAtendimento) {
        this.idPontoAtendimento = idPontoAtendimento;
    }

    public void setValorOperacao(double valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    @Override
    public String toString() {
        return "SaldoInicial{" +
                "idOperacao=" + idOperacao +
                ", dataOperacao='" + dataOperacao + '\'' +
                ", idGrupoCaixa=" + idGrupoCaixa +
                ", idTerminal=" + idTerminal +
                ", idPontoAtendimento=" + idPontoAtendimento +
                ", valorOperacao=" + valorOperacao +
                '}';
    }
}
