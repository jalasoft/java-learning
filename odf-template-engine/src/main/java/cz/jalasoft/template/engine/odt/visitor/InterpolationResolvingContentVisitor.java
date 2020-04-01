package cz.jalasoft.template.engine.odt.visitor;

import cz.jalasoft.template.engine.odt.expression.ExpressionEvaluator;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.element.office.OfficeAnnotationElement;
import org.w3c.dom.Text;

public final class InterpolationResolvingContentVisitor extends DomBuildingContentVisitor {

	private final ExpressionEvaluator resolver;

	private boolean resolutionAllowed;

	public InterpolationResolvingContentVisitor(OdfContentDom dom, ExpressionEvaluator resolver) {
		super(dom);
		this.resolver = resolver;
		this.resolutionAllowed = true;
	}

	@Override
	public void preVisit(OfficeAnnotationElement elm) {
		resolutionAllowed = false;
		super.preVisit(elm);
	}

	@Override
	public void postVisit(OfficeAnnotationElement elm) {
		super.postVisit(elm);
		resolutionAllowed = true;
	}

	@Override
	public void visit(Text text) {
		if (!resolutionAllowed) {
			super.visit(text);
			return;
		}

		resolveText(text);
	}

	private void resolveText(Text text) {
		String resolvedText = this.resolver.eval(text.getWholeText());

		Text newNode = (Text) text.cloneNode(false);
		newNode.setTextContent(resolvedText);
		super.visit(newNode);
	}
}
