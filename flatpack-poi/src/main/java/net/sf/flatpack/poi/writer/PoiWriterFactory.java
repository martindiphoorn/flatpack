package net.sf.flatpack.poi.writer;

import java.io.IOException;
import java.io.OutputStream;

import net.sf.flatpack.writer.Writer;
import net.sf.flatpack.writer.WriterFactory;

public class PoiWriterFactory implements WriterFactory {

	OutputStream outputStream;

	@Override
	public Writer createWriter(java.io.Writer out) throws IOException {
		return new PoiWriter(this.outputStream);
	}

	public PoiWriterFactory setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		return this;
	}
}
