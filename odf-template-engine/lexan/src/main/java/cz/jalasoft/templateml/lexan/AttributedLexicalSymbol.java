package cz.jalasoft.templateml.lexan;

/**
 * @author Jan Lastovicka
 * @since 31/03/2020
 */
public final class AttributedLexicalSymbol {

    static AttributedLexicalSymbol symbol(LexicalSymbol symbol) {
        return new AttributedLexicalSymbol(symbol, null);
    }

    private final LexicalSymbol symbol;
    private final Object attribute;

    AttributedLexicalSymbol(LexicalSymbol symbol, Object attribute) {
        this.symbol = symbol;
        this.attribute = attribute;
    }
}
