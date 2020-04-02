package cz.jalasoft.templateml.lexan;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.function.Supplier;
import cz.jalasoft.templateml.lexan.AttributedLexicalSymbol.*;
import java.util.Optional;

import static java.util.Optional.*;
import static cz.jalasoft.templateml.lexan.AttributedLexicalSymbol.symbol;
import static cz.jalasoft.templateml.lexan.LexicalSymbol.*;

/**
 * @author Jan Lastovicka
 * @since 31/03/2020
 */
public final class Lexan {

    private static final String SMALL_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String SMALL_ALPHABET_SPECIAL = "ěščřžýáíéůú";
    private static final String BIG_ALPHABET_ALL = "ABCČDĎEĚFGHIÍJKLMNŇOóPQRŘSŠUÚVWXYÝZŽ";

    private final InputSystem inputSystem;

    private char charValue;
    private char charType;
    private StringBuilder tokenValue;

    public Lexan(InputSystem inputSystem) {
        this.inputSystem = inputSystem;
    }

    public Optional<AttributedLexicalSymbol> next() throws IOException {
        this.tokenValue = new StringBuilder();

        return q0();
    }

    private Optional<AttributedLexicalSymbol> q0() throws IOException {
        if (!readChar()) {
            return Optional.empty();
        }

        switch (charType) {
            case '}':
                return of(symbol(RBRACE));

            case '{':
                return of(symbol(LBRACE));

            case '$':
                return of(symbol(DOLLAR));

            case '?':
                return c0();

            case 's':
                return w0();

            case 'p':
            case 'b':
            case 'n':
                return w1();

            default:
                return of(symbol(ERROR));
        }
    }

    private Optional<AttributedLexicalSymbol> c0() throws IOException {
        if (!readChar()) {
            return of(symbol(ERROR));
        }

        switch (charType) {
            case '?':
                return of(symbol(COND));

            default:
                return of(symbol(ERROR));
        }
    }

    private Optional<AttributedLexicalSymbol> w0() throws IOException {
        if (!readChar()) {
            return of(symbol(ERROR));
        }

        switch (charType) {
            case 's':
                return w0();

            case '_':
                return null;//

            case '.':
                return w2();

            case 'b':
            case 'p':
                return w1();

            default:
                return of(symbol(ERROR));
        }
    }

    private Optional<AttributedLexicalSymbol> w1() throws IOException {
        if (!readChar()) {
            return of(symbol(ERROR));
        }

        switch (charType) {
            case 's':
            case 'b':
            case 'n':
                return w0();

            case '.':
                return w2();

            default:
                return of(symbol(ERROR));
        }
    }

    private Optional<AttributedLexicalSymbol> w2() throws IOException {
        if (!readChar()) {
            return of(symbol(ERROR));
        }
        return null;

    }
    private boolean readChar() throws IOException {
        Optional<Character> maybeChar = inputSystem.read();

        if (maybeChar.isEmpty()) {
            return false;
        }

        charValue = maybeChar.get();
        charType = charType(charValue);

        tokenValue.append(charValue);

        return true;
    }

    private char charType(char value) {
        if (charValue == '{') {
            return '{';
        } else if (charValue == '}') {
            return '}';
        } else if (charValue == '$') {
            return '$';
        } else if (charValue == '?') {
            return '?';
        } else if (isSmallAlphabet(charValue)) {
            return 's';
        } else if (isSmallAlphabetSpecial(charValue)) {
            return 'p';
        } else if (isBigAlphabetAll(charValue)) {
            return 'b';
        } else if (isNumber(charValue)) {
            return 'n';
        } else if (charValue == '.') {
            return '.';
        } else if (charValue == '_') {
            return '_';
        } else {
            return 'x';
        }
    }

    private boolean isSmallAlphabet(char value) {
        return SMALL_ALPHABET.contains(String.valueOf(value));
    }

    private boolean isSmallAlphabetSpecial(char value) {
        return SMALL_ALPHABET_SPECIAL.contains(String.valueOf(value));
    }

    private boolean isBigAlphabetAll(char value) {
        return BIG_ALPHABET_ALL.contains(String.valueOf(value));
    }

    private boolean isNumber(char value) {
        return '0' >= value && value <= '9';
    }

    //------------------------------------------------------------------
    //INPUT SYSTEM
    //------------------------------------------------------------------

    public interface InputSystem {
        Optional<Character> read() throws IOException;
    }
}
