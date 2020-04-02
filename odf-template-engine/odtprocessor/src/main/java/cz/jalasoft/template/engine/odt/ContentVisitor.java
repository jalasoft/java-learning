package cz.jalasoft.template.engine.odt;

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
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public interface ContentVisitor {

	default void preVisit(OfficeTextElement elm) {}
	default void postVisit(OfficeTextElement elm) {}

	default void preVisit(OfficeFormsElement elm) {}
	default void postVisit(OfficeFormsElement elm) {}

	default void preVisit(TextSequenceDeclsElement elm) {}
	default void postVisit(TextSequenceDeclsElement elm) {}

	default void preVisit(OdfTextParagraph elm) {}
	default void postVisit(OdfTextParagraph elm) {}

	default void preVisit(TextSElement elm) {}
	default void postVisit(TextSElement elm) {}

	default void preVisit(TextSequenceDeclElement elm) {}
	default void postVisit(TextSequenceDeclElement elm) {}

	default void preVisit(TableTableElement elm) {}
	default void postVisit(TableTableElement elm) {}

	default void preVisit(TableTableColumnElement elm) {}
	default void postVisit(TableTableColumnElement elm) {}

	default void preVisit(TableTableRowElement elm) {}
	default void postVisit(TableTableRowElement elm) {}

	default void preVisit(TableTableCellElement elm) {}
	default void postVisit(TableTableCellElement elm) {}

	default void preVisit(OfficeAnnotationElement elm) {}
	default void postVisit(OfficeAnnotationElement elm) {}

	default void preVisit(DcCreatorElement elm) {}
	default void postVisit(DcCreatorElement elm) {}

	default void preVisit(DcDateElement elm) {}
	default void postVisit(DcDateElement elm) {}

	default void preVisit(OdfTextSpan elm) {}
	default void postVisit(OdfTextSpan elm) {}

	default void visit(Text text) {}

	default void preUnknown(Node node) {}
	default void postUnknown(Node node) {}

}
