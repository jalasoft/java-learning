package cz.jalasoft.template.engine.odt.visitor;

import cz.jalasoft.template.engine.odt.expression.ExpressionEvaluator;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.element.dc.DcCreatorElement;
import org.odftoolkit.odfdom.dom.element.dc.DcDateElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeAnnotationElement;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.w3c.dom.Text;

public final class AnnotationResolvingContentVisitor extends DomBuildingContentVisitor {

	private boolean inAnnotation;
	private boolean collectText;
	private StringBuilder annotationTextBuilder;

	private final ExpressionEvaluator interpolationResolver;

	public AnnotationResolvingContentVisitor(OdfContentDom dom, ExpressionEvaluator interpolationResolver) {
		super(dom);

		this.interpolationResolver = interpolationResolver;
		this.inAnnotation = false;
	}

	@Override
	public void preVisit(OdfTextParagraph elm) {
		if (!inAnnotation) {
			super.preVisit(elm);
		}
	}

	@Override
	public void postVisit(OdfTextParagraph elm) {
		if (!inAnnotation) {

			if (annotationTextBuilder != null) {
				String text = annotationTextBuilder.toString();
				OdfTextParagraph actualModifing = (OdfTextParagraph) top();
				updateParagraphWithResolvedExpression(actualModifing, text);
			}

			this.annotationTextBuilder = null;
			super.postVisit(elm);
		}
	}

	private void updateParagraphWithResolvedExpression(OdfTextParagraph par, String expression) {
		while(par.hasChildNodes()) {
			par.removeChild(par.getFirstChild());
		}

		String value = this.interpolationResolver.eval(expression);
		par.addContent(value);
	}

	@Override
	public void preVisit(DcCreatorElement elm) {
		this.collectText = false;
	}

	@Override
	public void postVisit(DcCreatorElement elm) {
		this.collectText = true;
	}

	@Override
	public void preVisit(DcDateElement elm) {
		this.collectText = false;
	}

	@Override
	public void postVisit(DcDateElement elm) {
		this.collectText = true;
	}

	@Override
	public void preVisit(OfficeAnnotationElement elm) {
		this.inAnnotation = true;
		this.annotationTextBuilder = new StringBuilder();
	}

	@Override
	public void postVisit(OfficeAnnotationElement elm) {
		this.inAnnotation = false;
	}

	@Override
	public void visit(Text text) {
		if (!inAnnotation) {
			super.visit(text);
			return;
		}

		if (inAnnotation && collectText) {
			annotationTextBuilder.append(text.getWholeText());
		}
	}
}
