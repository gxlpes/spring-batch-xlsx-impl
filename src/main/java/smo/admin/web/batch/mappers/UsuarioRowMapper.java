package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(RowSet rowSet) throws Exception {
        if (rowSet.getCurrentRowIndex() == 0) {
            return null;
        }

        String idUsuario = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[3]);
        String nomeUsuario = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[6]);
        int idUnidadeInstituicao = FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[5]);

        if (nomeUsuario != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario.setNome(nomeUsuario);
            usuario.setIdUnidadeInstituicao(idUnidadeInstituicao);

            return usuario;
        }

        return null;
    }
}
