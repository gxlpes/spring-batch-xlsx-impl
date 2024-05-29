package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.MovimentacaoPa;

public class SaldoInicialRowMapper implements RowMapper<MovimentacaoPa> {

    @Override
    public MovimentacaoPa mapRow(RowSet rowSet) throws Exception {
        if (rowSet.getCurrentRowIndex() == 0) {
            return null;
        }

        String operacao = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[0]);

        if (operacao != null) {
            MovimentacaoPa movimentacaoPa = new MovimentacaoPa();
            String[] parts = operacao.split("/");

            if (parts.length >= 3) {
                int idGrupo = Integer.parseInt(parts[1]);
                int idOperacao = Integer.parseInt(parts[2]);

                movimentacaoPa.setIdGrupoCaixa(idGrupo);
                movimentacaoPa.setIdOperacaoCaixa(idOperacao);
            }

            String terminalInfo = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[5]);
            if (terminalInfo != null && terminalInfo.split("/").length >= 3) {
                int idTerminal = Integer.parseInt(terminalInfo.split("/")[2]);
                movimentacaoPa.setIdTerminal(idTerminal);
            }

            movimentacaoPa.setIdHistorico(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[2]));
            movimentacaoPa.setData(FormatCellUtils.getStringValue(rowSet.getCurrentRow()[4]));
            movimentacaoPa.setIdPontoAtendimento(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[6]));
            movimentacaoPa.setValor(FormatCellUtils.getNumericValueDouble(rowSet.getCurrentRow()[8]));

            System.out.println(movimentacaoPa);

            return movimentacaoPa;
        } else {
            return null;
        }
    }
}
