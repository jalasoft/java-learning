package cz.jalasoft.template.engine.odt.visitor;

import cz.jalasoft.template.engine.odt.ContentVisitor;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.element.dc.DcCreatorElement;
import org.odftoolkit.odfdom.dom.element.dc.DcDateElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeAnnotationElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeFormsElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableColumnElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;
import org.odftoolkit.odfdom.dom.element.text.TextSElement;
import org.odftoolkit.odfdom.dom.element.text.TextSequenceDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextSequenceDeclsElement;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextSpan;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DomBuildingContentVisitor implements ContentVisitor {

	private final LinkedList<Node> stack = new LinkedList<>();

	OdfContentDom dom;

	public DomBuildingContentVisitor(OdfContentDom dom) {
		this.dom = dom;
		stack.push(new OfficeTextElement(dom));
	}

	final Node top() {
		return stack.getFirst();
	}

	@Override
	public void preVisit(TextSElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(TextSElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(OfficeFormsElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(OfficeFormsElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(OdfTextSpan elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(OdfTextSpan elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(OfficeAnnotationElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(OfficeAnnotationElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(DcDateElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(DcDateElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(DcCreatorElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(DcCreatorElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(TableTableCellElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(TableTableCellElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(TableTableRowElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(TableTableRowElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(TableTableColumnElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(TableTableColumnElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(TableTableElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(TableTableElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(TextSequenceDeclElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(TextSequenceDeclElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(TextSequenceDeclsElement elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(TextSequenceDeclsElement elm) {
		removeNode(elm);
	}

	@Override
	public void preVisit(OdfTextParagraph elm) {
		appendNode(elm);
	}

	@Override
	public void postVisit(OdfTextParagraph elm) {
		removeNode(elm);
	}

	@Override
	public void visit(Text text) {
		Node copy = text.cloneNode(false);
		Node first = stack.pop();
		first.appendChild(copy);
		stack.push(first);
	}

	public Collection<Node> content() {
		Node node = stack.pop();

		NodeList list = node.getChildNodes();

		List<Node> children = new ArrayList<>();

		for(int i=list.getLength()-1;i>=0;i--) {
			children.add(list.item(i));
		}

		return children;
	}

	private void appendNode(Node node) {

		Node first = stack.pop();

		Node copy = node.cloneNode(false);
		first.appendChild(copy);

		stack.push(first);
		stack.push(copy);
	}

	private void removeNode(Node node) {
		stack.pop();
	}
}
