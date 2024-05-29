package smo.admin.web.domain;

public class Terminal {

    private int idTerminal;
    private int idUnidadeInstituicao;
    private int idTipoTerminal;
    private String idUsuario;

    public int getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(int idTerminal) {
        this.idTerminal = idTerminal;
    }

    public void setIdUnidadeInstituicao(int idUnidadeInstituicao) {
        this.idUnidadeInstituicao = idUnidadeInstituicao;
    }

    public void setIdTipoTerminal(int idTipoTerminal) {
        this.idTipoTerminal = idTipoTerminal;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUnidadeInstituicao() {
        return idUnidadeInstituicao;
    }

    public int getIdTipoTerminal() {
        return idTipoTerminal;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "idTerminal=" + idTerminal +
                ", idUnidadeInstituicao=" + idUnidadeInstituicao +
                ", idTipoTerminal=" + idTipoTerminal +
                ", idUsuario='" + idUsuario + '\'' +
                '}';
    }
}
