package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.MovimentacaoPa;

public class MovimentacaoPaRowMapper implements RowMapper<MovimentacaoPa> {

    @Override
    public MovimentacaoPa mapRow(RowSet rowSet) throws Exception {
        if (rowSet.getCurrentRowIndex() == 0) {
            return null;
        }

        String data = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[2]);

        if (data != null) {
            MovimentacaoPa movimentacaoPa = new MovimentacaoPa();
            movimentacaoPa.setData(data);

            String[] parts = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[5]).split("/");

            if (parts.length == 3) {
                try {
                    int pontoAtendimentoId = Integer.parseInt(parts[1].replaceFirst("^0+(?!$)", ""));
                    int terminalId = Integer.parseInt(parts[2].replaceFirst("^0+(?!$)", ""));

                    movimentacaoPa.setIdPontoAtendimento(pontoAtendimentoId);
                    movimentacaoPa.setIdTerminal(terminalId);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(movimentacaoPa);

            return movimentacaoPa;
        }

        return null;
    }

}
