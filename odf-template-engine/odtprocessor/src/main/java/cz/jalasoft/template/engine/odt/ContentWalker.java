package cz.jalasoft.template.engine.odt;

import org.apache.xerces.dom.TextImpl;
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
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ContentWalker {

	private static Method preUnknown;
	private static Method postUnknown;

	private static Map<Class<?>, Method> preMethods = new HashMap<>();
	private static Map<Class<?>, Method> postMethods = new HashMap<>();
	private static Map<Class<?>, Method> singleMethods = new HashMap<>();

	static {
		try {
			preMethods.put(OfficeTextElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", OfficeTextElement.class));
			preMethods.put(TextSequenceDeclsElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", TextSequenceDeclsElement.class));
			preMethods.put(TextSequenceDeclElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", TextSequenceDeclElement.class));
			preMethods.put(OdfTextParagraph.class, ContentVisitor.class.getDeclaredMethod("preVisit", OdfTextParagraph.class));
			preMethods.put(TableTableElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", TableTableElement.class));
			preMethods.put(TableTableColumnElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", TableTableColumnElement.class));
			preMethods.put(TableTableRowElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", TableTableRowElement.class));
			preMethods.put(TableTableCellElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", TableTableCellElement.class));
			preMethods.put(OfficeAnnotationElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", OfficeAnnotationElement.class));
			preMethods.put(DcCreatorElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", DcCreatorElement.class));
			preMethods.put(DcDateElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", DcDateElement.class));
			preMethods.put(OdfTextSpan.class, ContentVisitor.class.getDeclaredMethod("preVisit", OdfTextSpan.class));
			preMethods.put(OfficeFormsElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", OfficeFormsElement.class));
			preMethods.put(TextSElement.class, ContentVisitor.class.getDeclaredMethod("preVisit", TextSElement.class));

			postMethods.put(OfficeTextElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", OfficeTextElement.class));
			postMethods.put(TextSequenceDeclsElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", TextSequenceDeclsElement.class));
			postMethods.put(TextSequenceDeclElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", TextSequenceDeclElement.class));
			postMethods.put(OdfTextParagraph.class, ContentVisitor.class.getDeclaredMethod("postVisit", OdfTextParagraph.class));
			postMethods.put(TableTableElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", TableTableElement.class));
			postMethods.put(TableTableColumnElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", TableTableColumnElement.class));
			postMethods.put(TableTableRowElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", TableTableRowElement.class));
			postMethods.put(TableTableCellElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", TableTableCellElement.class));
			postMethods.put(OfficeAnnotationElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", OfficeAnnotationElement.class));
			postMethods.put(DcCreatorElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", DcCreatorElement.class));
			postMethods.put(DcDateElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", DcDateElement.class));
			postMethods.put(OdfTextSpan.class, ContentVisitor.class.getDeclaredMethod("postVisit", OdfTextSpan.class));
			postMethods.put(OfficeFormsElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", OfficeFormsElement.class));
			postMethods.put(TextSElement.class, ContentVisitor.class.getDeclaredMethod("postVisit", TextSElement.class));

			singleMethods.put(TextImpl.class, ContentVisitor.class.getDeclaredMethod("visit", Text.class));

			preUnknown = ContentVisitor.class.getDeclaredMethod("preUnknown", Node.class);
			postUnknown = ContentVisitor.class.getDeclaredMethod("postUnknown", Node.class);
		} catch (NoSuchMethodException exc) {
			throw new RuntimeException(exc);
		}
	}

	private final OfficeTextElement contentRoot;

	public ContentWalker(OfficeTextElement contentRoot) {
		this.contentRoot = contentRoot;
	}

	public void walk(ContentVisitor visitor) throws Exception {
		walkNode(contentRoot, visitor);
	}

	private void walkNode(Node node, ContentVisitor visitor) {

		boolean visitInvoked = invokeVisitHandler(node, visitor);

		if (!visitInvoked) {
			invokePreHandler(node, visitor);
		}

		NodeList children = node.getChildNodes();

		for(int i=0;i<children.getLength();i++) {
			Node child = children.item(i);
			walkNode(child, visitor);
		}

		if (!visitInvoked) {
			invokePostHandler(node, visitor);
		}
	}

	private boolean invokeVisitHandler(Node node, ContentVisitor visitor) {
		Method handlerMethod = singleMethods.get(node.getClass());

		if (handlerMethod == null) {
			return false;
		}

		try {
			handlerMethod.invoke(visitor, node);
		} catch (IllegalAccessException | InvocationTargetException exc) {
			throw new RuntimeException(exc);
		}

		return true;
	}

	private void invokePreHandler(Node node, ContentVisitor visitor) {
		invokeVisitors(node, visitor, preMethods, preUnknown);
	}

	private void invokePostHandler(Node node, ContentVisitor visitor) {
		invokeVisitors(node, visitor, postMethods, postUnknown);
	}

	private void invokeVisitors(Node node, ContentVisitor visitor, Map<Class<?>, Method> handlers, Method unknown) {
		Method m = handlers.getOrDefault(node.getClass(), unknown);

		try {
			m.invoke(visitor, node);
		} catch (IllegalAccessException | InvocationTargetException exc)  {
			throw new RuntimeException(exc);
		}
	}
}
