package cz.jalasoft.template.engine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TemplateSource {

	static TemplateSource from(Path file) {
		return new TemplateSource(() -> Files.newInputStream(file));
	}

	static TemplateSource from(String file) {
		Path path = Paths.get(file);
		return from(path);
	}

	static TemplateSource from(InputStream is) {
		return new TemplateSource(() -> is);
	}

	//---------------------------------------------------------------------------
	//INSTANCE SCOPE
	//---------------------------------------------------------------------------

	private final Source source;

	private TemplateSource(Source source) {
		this.source = source;
	}

	public InputStream input() throws IOException {
		return source.stream();
	}

	//---------------------------------------------------------------------------
	//SOURCE SUPPLIER
	//---------------------------------------------------------------------------

	private interface Source {

		InputStream stream() throws IOException;
	}

}
