package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.TipoOperacao;

public class TipoOperacaoRowMapper implements RowMapper<TipoOperacao> {

    @Override
    public TipoOperacao mapRow(RowSet rowSet) throws Exception {
        if (rowSet.getCurrentRowIndex() == 0) {
            return null;
        }

        String descricaoHistorico = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[5]);
        String sensibilizacaoValue = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[6]);

        if (sensibilizacaoValue != null && !sensibilizacaoValue.equals("0")) {
            TipoOperacao tipoOperacao = new TipoOperacao();

            tipoOperacao.setIdGrupoCaixa(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[0]));
            tipoOperacao.setIdOperacaoCaixa(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[1]));
            tipoOperacao.setDescricaoOperacao(FormatCellUtils.getStringValue(rowSet.getCurrentRow()[3]));
            tipoOperacao.setIdHistorico(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[4]));
            tipoOperacao.setDescricaoHistorico(descricaoHistorico);
            tipoOperacao.setSensibilizacao(sensibilizacaoValue);

            System.out.println("TIPO OPERACAO ------- " + tipoOperacao);

            return tipoOperacao;
        }

        return null;
    }
}
