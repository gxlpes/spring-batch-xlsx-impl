package smo.admin.web.batch.mappers;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import smo.admin.web.batch.common.FormatCellUtils;
import smo.admin.web.domain.TransportadoraValor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransportadoraValorItemRowMapper implements RowMapper<TransportadoraValor> {

    private final FormatCellUtils formatCellUtils;

    public TransportadoraValorItemRowMapper(FormatCellUtils formatCellUtils) {
        this.formatCellUtils = formatCellUtils;
    }

    @Override
    public TransportadoraValor mapRow(RowSet rowSet) throws Exception {

        if (rowSet.getCurrentRowIndex() == 0) {
            return null;
        }

        String idTransportadoraValor = formatCellUtils.formatCnpj(formatCellUtils.getStringValue(rowSet.getCurrentRow()[1]));
        String nome = formatCellUtils.getStringValue(rowSet.getCurrentRow()[2]);
        String pontosDeAtendimentoString = formatCellUtils.getStringValue(rowSet.getCurrentRow()[3]);

        if (nome != null) {
            TransportadoraValor transportadoraValor = new TransportadoraValor();

            transportadoraValor.setCnpj(idTransportadoraValor);
            transportadoraValor.setNome(nome);

            List<Integer> pontosDeAtendimento = Arrays.stream(pontosDeAtendimentoString.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            transportadoraValor.setPontosDeAtendimento(pontosDeAtendimento);

            return transportadoraValor;
        }

        return null;
    }


}
