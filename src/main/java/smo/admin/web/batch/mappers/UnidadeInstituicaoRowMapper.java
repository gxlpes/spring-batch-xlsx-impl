package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.UnidadeInstituicao;

public class UnidadeInstituicaoRowMapper implements RowMapper<UnidadeInstituicao> {

    public UnidadeInstituicao mapRow(RowSet rowSet) throws Exception {
        var result = rowSet.getCurrentRow();
        UnidadeInstituicao unidadeInstituicao = new UnidadeInstituicao();
        int idUnidadeInstituicao = FormatCellUtils.getNumericValueInt((rowSet.getCurrentRow()[1]));
        String nomeUnidadeInstituicao = rowSet.getCurrentRow()[3];

        if (nomeUnidadeInstituicao != null) {
            unidadeInstituicao.setIdUnidadeInstituicao(idUnidadeInstituicao);
            unidadeInstituicao.setNome(nomeUnidadeInstituicao);
            unidadeInstituicao.setTipoLocalidade(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[7]));
        }

        return unidadeInstituicao;
    }

}
