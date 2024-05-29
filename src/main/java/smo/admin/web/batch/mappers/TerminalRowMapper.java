package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.Terminal;

public class TerminalRowMapper implements RowMapper<Terminal> {

    @Override
    public Terminal mapRow(RowSet rowSet) throws Exception {
        if (rowSet.getCurrentRowIndex() == 0) {
            return null;
        }

        String idUsuario = FormatCellUtils.getStringValue(rowSet.getCurrentRow()[7]);

        if (idUsuario != null) {
            Terminal terminal = new Terminal();
            terminal.setIdUnidadeInstituicao(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[2]));
            terminal.setIdTerminal(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[4]));
            terminal.setIdTipoTerminal(FormatCellUtils.getNumericValueInt(rowSet.getCurrentRow()[5]));
            terminal.setIdUsuario(idUsuario);

            System.out.println(terminal.toString());

            return terminal;
        }

        return null;
    }

}
