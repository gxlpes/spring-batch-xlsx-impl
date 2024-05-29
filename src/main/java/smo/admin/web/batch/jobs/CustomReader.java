package smo.admin.web.batch.jobs;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.extensions.excel.AbstractExcelItemReader;
import org.springframework.batch.extensions.excel.Sheet;
import org.springframework.core.io.Resource;
import smo.admin.web.domain.SaldoInicial;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class CustomReader extends AbstractExcelItemReader<SaldoInicial> {
    private Workbook workbook;
    private int currentSheetIndex = 2;
    private int maxSheetIndex = 4;
    private int sheetCount = 0;
    private Iterator<Row> rowIterator;
    private Sheet sheet;

    @Override
    protected void openExcelFile(Resource resource, String s) throws Exception {
        try (InputStream is = resource.getInputStream()) {
            this.workbook = WorkbookFactory.create(is);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to open the Excel file.", e);
        }
        this.sheetCount = workbook.getNumberOfSheets();
        setSheet(currentSheetIndex);
    }

    @Override
    protected int getNumberOfSheets() {
        return maxSheetIndex - currentSheetIndex + 1;
    }

    @Override
    protected Sheet getSheet(int sheetIndex) {
        if (workbook == null) {
            throw new IllegalStateException("Workbook has not been initialized.");
        }
        return new PoiSheet(workbook.getSheetAt(sheetIndex));
    }

    @Override
    public SaldoInicial read() throws Exception {
        SaldoInicial item = super.read();
        while (item == null && currentSheetIndex < maxSheetIndex) {
            currentSheetIndex++;
            if (currentSheetIndex < sheetCount) {
                setSheet(currentSheetIndex);
                item = super.read();
            } else {
                break;
            }
        }
        return item;
    }

    @Override
    protected void doClose() throws Exception {
        if (this.workbook != null) {
            this.workbook.close();
        }
    }

    private void setSheet(int sheetIndex) {
        this.sheet = getSheet(sheetIndex);
    }
}
