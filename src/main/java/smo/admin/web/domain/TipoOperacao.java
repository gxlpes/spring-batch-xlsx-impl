package smo.admin.web.domain;

public class TipoOperacao {

    private int idGrupoCaixa;
    private int idOperacaoCaixa;
    private String descricaoOperacao;
    private int idHistorico;
    private String descricaoHistorico;
    private String sensibilizacao;

    public String getSensibilizacao() {
        return sensibilizacao;
    }

    public void setSensibilizacao(String sensibilizacao) {
        this.sensibilizacao = sensibilizacao;
    }

    public int getIdGrupoCaixa() {
        return idGrupoCaixa;
    }

    public void setIdGrupoCaixa(int idGrupoCaixa) {
        this.idGrupoCaixa = idGrupoCaixa;
    }

    public int getIdOperacaoCaixa() {
        return idOperacaoCaixa;
    }

    public void setIdOperacaoCaixa(int idOperacaoCaixa) {
        this.idOperacaoCaixa = idOperacaoCaixa;
    }

    public String getDescricaoOperacao() {
        return descricaoOperacao;
    }

    public void setDescricaoOperacao(String descricaoOperacao) {
        this.descricaoOperacao = descricaoOperacao;
    }

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public String getDescricaoHistorico() {
        return descricaoHistorico;
    }

    public void setDescricaoHistorico(String descricaoHistorico) {
        this.descricaoHistorico = descricaoHistorico;
    }

    @Override
    public String toString() {
        return "TipoOperacao{" +
                "idGrupoCaixa=" + idGrupoCaixa +
                ", idOperacaoCaixa=" + idOperacaoCaixa +
                ", descricaoOperacao='" + descricaoOperacao + '\'' +
                ", idHistorico=" + idHistorico +
                ", descricaoHistorico='" + descricaoHistorico + '\'' +
                ", sensibilizacao='" + sensibilizacao + '\'' +
                '}';
    }
}
