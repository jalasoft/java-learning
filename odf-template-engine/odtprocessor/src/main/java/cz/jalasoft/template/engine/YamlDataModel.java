package cz.jalasoft.template.engine;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

public final class YamlDataModel implements DataModel {

	public static YamlDataModel fromFile(String path) throws IOException {
		Yaml yaml = new Yaml();

		Path file = Paths.get(path);
		InputStream is = Files.newInputStream(file);
		Map<String, Object> data = yaml.load(is);
		return new YamlDataModel(data);
	}

	//-------------------------------------------------------------------------------
	//INSTANCE SCOPE
	//-------------------------------------------------------------------------------

	private final Map<String, Object> data;

	private YamlDataModel(Map<String, Object> data) {
		this.data = data;
	}

	public Optional<Boolean> booleanValue(String path) {
		return Optional.empty();
	}

	@Override
	public Optional<String> value(String expression) {
		String[] segments = expression.split("\\.");
		int length = segments.length;

		Map<String, Object> path = this.data;

		for(int i=0;i<length;i++) {
			String segment = segments[i];
			Object nextLevel = path.get(segment);

			if (nextLevel == null) {
				return Optional.empty();
			}

			if (i == length-1) {
				return Optional.of(nextLevel.toString());
			}

			path = (Map<String, Object>) nextLevel;
		}

		return Optional.empty();
	}
}
