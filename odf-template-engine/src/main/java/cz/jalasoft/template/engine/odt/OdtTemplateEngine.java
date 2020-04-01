package cz.jalasoft.template.engine.odt;

import cz.jalasoft.template.engine.DataModel;
import cz.jalasoft.template.engine.Document;
import cz.jalasoft.template.engine.TemplateCompilationException;
import cz.jalasoft.template.engine.TemplateEngine;
import cz.jalasoft.template.engine.TemplateSource;
import cz.jalasoft.template.engine.odt.expression.ExpressionEvaluator;
import cz.jalasoft.template.engine.odt.visitor.AnnotationResolvingContentVisitor;
import cz.jalasoft.template.engine.odt.visitor.ConditionalSubTreeContentVisitor;
import cz.jalasoft.template.engine.odt.visitor.DomBuildingContentVisitor;
import cz.jalasoft.template.engine.odt.visitor.InterpolationResolvingContentVisitor;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;

import java.io.ByteArrayOutputStream;

public final class OdtTemplateEngine implements TemplateEngine {

	public Document compile(TemplateSource source, DataModel model) throws TemplateCompilationException {

		try (OdfTextDocument document = (OdfTextDocument) OdfDocument.loadDocument(source.input())) {
			return compile(document, model);
		} catch (Exception exc) {
			throw new TemplateCompilationException(exc);
		}
	}

	private Document compile(OdfTextDocument document, DataModel model) throws Exception {

		ExpressionEvaluator resolver = new ExpressionEvaluator(model);
		domModificationIteration(document, new InterpolationResolvingContentVisitor(document.getContentDom(), resolver));
		domModificationIteration(document, new AnnotationResolvingContentVisitor(document.getContentDom(), resolver));
		domModificationIteration(document, new ConditionalSubTreeContentVisitor(document.getContentDom(), resolver));

		ByteArrayOutputStream s = new ByteArrayOutputStream();
		document.save(s);

		return new Document(s.toByteArray());
	}

	private void domModificationIteration(OdfTextDocument document, DomBuildingContentVisitor visitor) throws Exception {
		constructNewDom(document, visitor);
		replaceContent(document, visitor);
	}

	private void constructNewDom(OdfTextDocument document, DomBuildingContentVisitor visitor) throws Exception {
		OfficeTextElement root = document.getContentRoot();
		new ContentWalker(root).walk(visitor);
	}

	private void replaceContent(OdfTextDocument document, DomBuildingContentVisitor visitor) throws Exception {
		OfficeTextElement root = document.getContentRoot();

		while(root.hasChildNodes()) {
			root.removeChild(root.getFirstChild());
		}

		visitor.content().forEach(p -> root.insert(p, 0));
	}
}
