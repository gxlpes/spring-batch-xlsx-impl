package smo.admin.web.batch.common;

public class FormatCellUtils {

    public static String getStringValue(String cellValue) {
        return cellValue;
    }

    public static int getNumericValueInt(String cellValue) {
        return cellValue != null ? Integer.parseInt(cellValue) : 0;
    }

    public String formatCnpj(String cnpj) {
        return cnpj != null ? cnpj.replaceAll("[^0-9]", "") : null;
    }

    public static Double getNumericValueDouble(String cell) {
        return cell != null ? Double.parseDouble(cell) : null;
    }
}
