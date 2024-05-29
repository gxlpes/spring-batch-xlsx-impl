package smo.admin.web.domain;

public class MovimentacaoPa {

    private String data;
    private int idPontoAtendimento;
    private int idTerminal;
    private int idGrupoCaixa;
    private int idOperacaoCaixa;
    private int idHistorico;
    private double valor;

    public String getData() {
        return data;
    }

    public int getIdPontoAtendimento() {
        return idPontoAtendimento;
    }

    public int getIdTerminal() {
        return idTerminal;
    }

    public int getIdGrupoCaixa() {
        return idGrupoCaixa;
    }

    public int getIdOperacaoCaixa() {
        return idOperacaoCaixa;
    }

    public int getIdHistorico() {
        return idHistorico;
    }

    public double getValor() {
        return valor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setIdPontoAtendimento(int idPontoAtendimento) {
        this.idPontoAtendimento = idPontoAtendimento;
    }

    public void setIdTerminal(int idTerminal) {
        this.idTerminal = idTerminal;
    }

    public void setIdGrupoCaixa(int idGrupoCaixa) {
        this.idGrupoCaixa = idGrupoCaixa;
    }

    public void setIdOperacaoCaixa(int idOperacaoCaixa) {
        this.idOperacaoCaixa = idOperacaoCaixa;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "MovimentacaoPa{" +
                "data='" + data + '\'' +
                ", idPontoAtendimento=" + idPontoAtendimento +
                ", idTerminal=" + idTerminal +
                ", idGrupoCaixa=" + idGrupoCaixa +
                ", idOperacaoCaixa=" + idOperacaoCaixa +
                ", idHistorico=" + idHistorico +
                ", valor=" + valor +
                '}';
    }
}
