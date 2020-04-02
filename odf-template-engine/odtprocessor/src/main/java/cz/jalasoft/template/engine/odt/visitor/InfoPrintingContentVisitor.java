package cz.jalasoft.template.engine.odt.visitor;

import cz.jalasoft.template.engine.odt.ContentVisitor;
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

import java.util.stream.IntStream;

public final class InfoPrintingContentVisitor implements ContentVisitor {

	private static final int INDENTATION_LEVEL_SPACES = 2;

	private int indentation = 0;

	private void indentNextLevel() {
		indentation += INDENTATION_LEVEL_SPACES;
	}

	private void indentPreviousLevel() {
		indentation -= INDENTATION_LEVEL_SPACES;
	}

	private String makeIndentation() {
		return IntStream.range(0, indentation)
				.mapToObj(Integer::new)
				.reduce(new StringBuilder(), (b, i) -> b.append(" "), StringBuilder::append)
				.toString();
	}

	private void print(String text) {
		String intendedText = makeIndentation() + text;
		System.out.println(intendedText);
	}

	@Override
	public void preVisit(OfficeTextElement elm) {
		print("PRE office:text");
		indentNextLevel();
	}

	@Override
	public void postVisit(OfficeTextElement elm) {
		indentPreviousLevel();
		print("POST office:text");
	}

	@Override
	public void preVisit(OfficeFormsElement elm) {
		print("PRE office:forms");
		indentNextLevel();
	}

	@Override
	public void postVisit(OfficeFormsElement elm) {
		indentPreviousLevel();
		print("POST office:forms");
	}

	@Override
	public void preVisit(TextSElement elm) {
		print("PRE text:s");
		indentNextLevel();
	}

	@Override
	public void postVisit(TextSElement elm) {
		indentPreviousLevel();
		print("POST text:s");
	}

	@Override
	public void preVisit(TextSequenceDeclsElement elm) {
		print("PRE text:sequence-decls");
		indentNextLevel();
	}

	@Override
	public void postVisit(TextSequenceDeclsElement elm) {
		indentPreviousLevel();
		print("POST text:sequence-decls");
	}

	@Override
	public void preVisit(TextSequenceDeclElement sequence) {
		print("PRE text:sequence-decl");
		indentNextLevel();
	}

	@Override
	public void postVisit(TextSequenceDeclElement sequence) {
		indentPreviousLevel();
		print("POST text:sequence-decl");
	}

	@Override
	public void preVisit(OdfTextParagraph elm) {
		print("PRE text:paragraph");
		indentNextLevel();
	}

	@Override
	public void postVisit(OdfTextParagraph elm) {
		indentPreviousLevel();
		print("POST text:paragraph");
	}

	@Override
	public void preVisit(TableTableElement elm) {
		print("PRE table:table");
		indentNextLevel();
	}

	@Override
	public void postVisit(TableTableElement elm) {
		indentPreviousLevel();
		print("POST table:table");
	}

	@Override
	public void preVisit(TableTableColumnElement elm) {
		print("PRE table:table-column");
		indentNextLevel();
	}

	@Override
	public void postVisit(TableTableColumnElement elm) {
		indentPreviousLevel();
		print("POST table:table-column");
	}

	@Override
	public void preVisit(TableTableRowElement elm) {
		print("PRE table:table-row");
		indentNextLevel();
	}

	@Override
	public void postVisit(TableTableRowElement elm) {
		indentPreviousLevel();
		print("POST table:table-row");
	}

	@Override
	public void preVisit(TableTableCellElement elm) {
		print("PRE table:table-cell");
		indentNextLevel();
	}

	@Override
	public void postVisit(TableTableCellElement elm) {
		indentPreviousLevel();
		print("POST table:table-cell");
	}

	@Override
	public void preVisit(OfficeAnnotationElement elm) {
		print("PRE office:annotation");
		indentNextLevel();
	}

	@Override
	public void postVisit(OfficeAnnotationElement elm) {
		indentPreviousLevel();
		print("POST office:annotation");
	}

	@Override
	public void preVisit(DcCreatorElement elm) {
		print("PRE dc:creator");
		indentNextLevel();
	}

	@Override
	public void postVisit(DcCreatorElement elm) {
		indentPreviousLevel();
		print("POST dc:creator");
	}

	@Override
	public void preVisit(DcDateElement elm) {
		print("PRE dc:date");
		indentNextLevel();
	}

	@Override
	public void postVisit(DcDateElement elm) {
		indentPreviousLevel();
		print("POST dc:date");
	}

	@Override
	public void preVisit(OdfTextSpan elm) {
		print("PRE text:span");
		indentNextLevel();
	}

	@Override
	public void postVisit(OdfTextSpan elm) {
		indentPreviousLevel();
		print("POST text:span");
	}

	@Override
	public void preUnknown(Node node) {
		print("PRE unknown: " + node.getClass().getSimpleName());
		indentNextLevel();
	}

	@Override
	public void postUnknown(Node node) {
		indentPreviousLevel();
		print("POST unknown: " + node.getClass().getSimpleName());
	}

	@Override
	public void visit(Text text) {
		print("TEXT: " + text.getWholeText());
	}
}
