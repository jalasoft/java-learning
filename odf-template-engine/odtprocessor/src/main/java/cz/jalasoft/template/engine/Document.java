package cz.jalasoft.template.engine;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Document {

	private final byte[] bytes;

	public Document(byte[] bytes) {
		this.bytes = bytes;
	}

	public void toFile(Path path) throws IOException {
		Files.write(path, bytes);
	}

	public void toFile(String path) throws IOException {
		Path filePath = Paths.get(path);
		toFile(filePath);
	}
}
