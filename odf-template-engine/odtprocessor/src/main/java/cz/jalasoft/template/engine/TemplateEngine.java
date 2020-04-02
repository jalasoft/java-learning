package cz.jalasoft.template.engine;

public interface TemplateEngine {

	Document compile(TemplateSource template, DataModel model) throws TemplateCompilationException;
}
