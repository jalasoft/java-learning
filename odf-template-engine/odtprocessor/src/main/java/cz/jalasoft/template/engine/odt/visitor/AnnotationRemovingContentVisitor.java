package cz.jalasoft.template.engine.odt.visitor;

import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.element.office.OfficeAnnotationElement;

public final class AnnotationRemovingContentVisitor extends DomBuildingContentVisitor {

	public AnnotationRemovingContentVisitor(OdfContentDom dom) {
		super(dom);
	}

	@Override
	public void preVisit(OfficeAnnotationElement elm) {
		//super.preVisit(elm);
	}

	@Override
	public void postVisit(OfficeAnnotationElement elm) {
		//super.postVisit(elm);
	}
}
