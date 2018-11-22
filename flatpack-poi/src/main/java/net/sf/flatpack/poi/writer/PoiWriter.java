package net.sf.flatpack.poi.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.flatpack.writer.Writer;

public class PoiWriter implements Writer {
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PoiWriter.class);

	private OutputStream out;

	private CreationHelper createHelper;
	private Workbook workbook;
	private Sheet sheet;

	private List<String> columns = new ArrayList<>();
	private Map<String, Object> currentRow = new HashMap<>();


	public PoiWriter(OutputStream out) {
		this.out = out;

		workbook = new XSSFWorkbook();
		createHelper = workbook.getCreationHelper();
		sheet = workbook.createSheet("Sheet1");
	}


	@Override
	public Writer printHeader() {
		// Print the HEADER columns if it is filled
		if (this.columns.isEmpty()) {
			LOGGER.debug("PrintHeader :: No columns defined skipping");
		} else {
			Row headerRow = sheet.createRow(0);
			for (int colIndex = 0; colIndex < this.columns.size(); colIndex++) {
				headerRow.createCell(colIndex).setCellValue(this.columns.get(colIndex));
			}
		}
		return this;
	}

	@Override
	public Writer printFooter() {
		return this;
	}

	@Override
	public Writer addRecordEntry(String columnName, Object value) {
		this.currentRow.put(columnName, value);
		return this;
	}

	@Override
	public Writer nextRecord() throws IOException {
		if (columns.isEmpty()) {
			// Create columns based on this row
			this.currentRow.forEach((name, object) -> this.columns.add(name));
			this.printHeader();
		}

		Row row = sheet.createRow(sheet.getLastRowNum()+1);
		for (int colIndex = 0; colIndex < this.columns.size(); colIndex++) {
			row.createCell(colIndex).setCellValue(currentRow.get(this.columns.get(colIndex)).toString());
		}

		return this;
	}

	@Override
	public Writer flush() {
		return this;
	}

	@Override
	public void close() throws IOException {
		workbook.write(out);
		workbook.close();
	}
}
