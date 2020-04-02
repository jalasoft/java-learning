package cz.jalasoft.template.engine.odt.expression;

import cz.jalasoft.template.engine.DataModel;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ExpressionEvaluator {

	private static final Pattern PATTERN = Pattern.compile("\\$\\{([a-zA-Z\\.]+)\\}");

	private final DataModel model;

	public ExpressionEvaluator(DataModel model) {
		this.model = model;
	}


	public String eval(String input) {

		Matcher m = PATTERN.matcher(input);

		String result = input;
		while(m.find()) {
			String pattern = m.group(0);

			String expression = m.group(1);
			Optional<String> maybeValue = model.value(expression);

			String value = maybeValue.isPresent() ? maybeValue.get() : pattern;

			result = result.replace(pattern, value);
		}

		return result;
	}
}
