package cz.jalasoft.template.engine;

import java.util.Optional;

public interface DataModel {

	Optional<String> value(String path);

	Optional<Boolean> booleanValue(String path);
}
