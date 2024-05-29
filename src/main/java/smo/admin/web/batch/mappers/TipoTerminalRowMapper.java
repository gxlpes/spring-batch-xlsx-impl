package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.TipoTerminal;

public class TipoTerminalRowMapper implements RowMapper<TipoTerminal> {

    @Override
    public TipoTerminal mapRow(RowSet rowSet) throws Exception {
        if (rowSet.getCurrentRowIndex() == 0) {
            return null;
        }

        int idTipoTerminal = FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[1]);

        if (idTipoTerminal >= 0) {
            TipoTerminal tipoTerminal = new TipoTerminal();

            tipoTerminal.setIdPontoAtendimento(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[0]));
            tipoTerminal.setIdTipoTerminal(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[1]));
            tipoTerminal.setDescricao(FormatCellUtils.getStringValue(rowSet.getCurrentRow()[2]));
            tipoTerminal.setLimSuperior(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[3]));
            tipoTerminal.setLimInferior(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[4]));

            System.out.println(tipoTerminal);

            return tipoTerminal;
        }

        return null;
    }
}
