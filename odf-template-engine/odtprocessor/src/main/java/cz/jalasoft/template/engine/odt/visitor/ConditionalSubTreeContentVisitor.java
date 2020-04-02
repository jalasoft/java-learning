package cz.jalasoft.template.engine.odt.visitor;

import cz.jalasoft.template.engine.odt.expression.ExpressionEvaluator;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.element.dc.DcCreatorElement;
import org.odftoolkit.odfdom.dom.element.dc.DcDateElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeAnnotationElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeFormsElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableColumnElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;
import org.odftoolkit.odfdom.dom.element.text.TextSElement;
import org.odftoolkit.odfdom.dom.element.text.TextSequenceDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextSequenceDeclsElement;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextSpan;
import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ConditionalSubTreeContentVisitor extends DomBuildingContentVisitor {

	private static final Pattern CONDITION_START_INSTRUCTION_PATTERN = Pattern.compile("^\\?\\?([a-zA-Z\\.]+)\\?\\?$");
	private static final Pattern CONDITION_STOP_INSTRUCTION_PATTERN = Pattern.compile("^\\?\\?$");

	private final ExpressionEvaluator expressionResolver;
	private boolean inParagraph;
	private boolean includeNode;

	public ConditionalSubTreeContentVisitor(OdfContentDom dom, ExpressionEvaluator expressionResolver) {
		super(dom);

		this.expressionResolver = expressionResolver;
		this.inParagraph = false;
		this.includeNode = true;
	}

	@Override
	public void preVisit(OdfTextParagraph elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
		this.inParagraph = true;
	}

	@Override
	public void postVisit(OdfTextParagraph elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
		this.inParagraph = false;
	}

	@Override
	public void visit(Text text) {
		if (!includeNode) {
			return;
		}

		if (inParagraph && isStartInstruction(text.getWholeText())) {
			String expression = instructionExpression(text.getWholeText());
			//includeNode = expressionResolver.resolveBoolean(expression);
		} if (inParagraph && isStopInstruction(text.getWholeText())) {
			includeNode = true;
		} else {
			super.visit(text);
		}
	}

	private boolean isStartInstruction(String text) {
		Matcher m = CONDITION_START_INSTRUCTION_PATTERN.matcher(text);

		boolean matches = m.matches();
		return matches;
		//return CONDITION_START_INSTRUCTION_PATTERN.matcher(text).matches();
	}

	private String instructionExpression(String text) {
		Matcher t = CONDITION_START_INSTRUCTION_PATTERN.matcher(text);

		boolean m = t.matches();

		return t.group(1);
	}

	private boolean isStopInstruction(String text) {
		return CONDITION_STOP_INSTRUCTION_PATTERN.matcher(text).matches();
	}

	//----------------------------------------------------------------------------
	//SIMPLE OVERRIDES
	//----------------------------------------------------------------------------

	@Override
	public void preVisit(OfficeAnnotationElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(OfficeAnnotationElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(TextSequenceDeclsElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(TextSequenceDeclsElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(TextSequenceDeclElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(TextSequenceDeclElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(TableTableElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(TableTableElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(TableTableColumnElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(TableTableColumnElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(TableTableRowElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(TableTableRowElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(TableTableCellElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(TableTableCellElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(DcCreatorElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(DcCreatorElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(DcDateElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(DcDateElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(OdfTextSpan elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(OdfTextSpan elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(OfficeFormsElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(OfficeFormsElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}

	@Override
	public void preVisit(TextSElement elm) {
		if (!includeNode) {
			return;
		}
		super.preVisit(elm);
	}

	@Override
	public void postVisit(TextSElement elm) {
		if (!includeNode) {
			return;
		}
		super.postVisit(elm);
	}


}
