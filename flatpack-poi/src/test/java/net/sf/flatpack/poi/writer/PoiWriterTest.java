package net.sf.flatpack.poi.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.junit.Test;

import net.sf.flatpack.writer.Writer;

public class PoiWriterTest {

	@Test
	public void printHeader() throws IOException {
		OutputStream outputStream = new FileOutputStream("test.xlsx");
		final Writer writer = new PoiWriterFactory().setOutputStream(outputStream).createWriter(null);

		writer
				.printHeader()
				.addRecordEntry("LASTNAME", "DOE") // New fluent
				.addRecordEntry("ADDRESS", "1234 CIRCLE CT") //
				.addRecordEntry("STATE", "OH") //
				.addRecordEntry("ZIP", "44035") //
				.addRecordEntry("FIRSTNAME", "JOHN") //
				.addRecordEntry("CITY", "ELYRIA") //
				.addRecordEntry("REVENUE", BigDecimal.ZERO) //
				.nextRecord() //
				.printFooter()
				.flush()
				.close();
		outputStream.close();
	}
}
