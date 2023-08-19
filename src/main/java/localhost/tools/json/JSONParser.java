/**
 * MIT License
 *
 * Copyright (c) 2023 Alexander Perfilev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package localhost.tools.json;

final class JSONParser {

    private final char Char_SPACE = ' ';
    private final char Char_TAB = '\t';
    private final char Char_NL = '\n';
    private final char Char_CR = '\r';
    private final char Char_Double_QUOTE = '"';
    private final char Char_Single_QUOTE = '\'';
    private final char Char_SLASH = '/';
    private final char Char_BACKSLASH = '\\';
    private final char Char_b = 'b';
    private final char Char_F = 'F';
    private final char Char_f = 'f';
    private final char Char_N = 'N';
    private final char Char_n = 'n';
    private final char Char_r = 'r';
    private final char Char_T = 'T';
    private final char Char_t = 't';
    private final char Char_u = 'u';
    private final char Char_l = 'l';
    private final char Char_a = 'a';
    private final char Char_s = 's';
    private final char Char_e = 'e';
    private final char Char_E = 'E';
    private final char Char_MINUS = '-';
    private final char Char_PLUS = '+';
    private final char Char_COMMA = ',';
    private final char Char_COLON = ':';
    private final char Char_0 = '0';
    private final char Char_9 = '9';
    private final char Char_DOT = '.';
    private final char Char_CURLY_OPEN = '{';
    private final char Char_CURLY_CLOSE = '}';
    private final char Char_SQUARE_OPEN = '[';
    private final char Char_SQUARE_CLOSE = ']';

    private JSONNode root;
    private final char[] charBuffer;
    private final int length;
    private int pos = 0;

    private final int DEFAULT_CAPACITY = 32;
    private final int STACK_RESIZE_MASK = 0b11111; //31

    private JSONNode[] stack = new JSONNode[DEFAULT_CAPACITY];
    private JSONNode stackTop = null;
    private int stackSize = 0;

    private ParserState state = null;

    public enum ParserState {
        ObjectReadKey,
        ObjectReadColon,
        ObjectReadValue,
        ObjectReadComma,
        ArrayReadValue,
        ArrayReadComma
    }

    JSONParser(final String source) {
        length = source.length();
        charBuffer = new char[length];
        source.getChars(0, length, charBuffer, 0);
    }

    public void push(JSONNode node) {
        stackTop.addChild(node);

        if ((stackSize & STACK_RESIZE_MASK) == STACK_RESIZE_MASK)
            reallocate(stackSize + STACK_RESIZE_MASK + 1);
        
        stack[stackSize++] = node;
        stackTop = node;
    }

    public JSONNode pop() {
        JSONNode value = stackTop;
        if (--stackSize > 0)
            stackTop = stack[stackSize - 1];
        
        return value;
    }

    public void drop() {
        stackTop = stack[--stackSize - 1];
    }

    private void reallocate(int size) {
        JSONNode[] new_data = new JSONNode[size];
        System.arraycopy(stack, 0, new_data, 0, stack.length);
        stack = new_data;
    }

    public JSONNode getRoot() {
        return root;
    }

    void parse() throws JSONException {
        try {
            pos = 0;
            skipWhiteSpace();
            switch (charBuffer[pos]) {
                case Char_CURLY_OPEN:
                    stack[stackSize++] = JSONNode.createObjectNode(pos);
                    state = ParserState.ObjectReadKey;
                    break;
                case Char_SQUARE_OPEN:
                    stack[stackSize++] = JSONNode.createArrayNode(pos);
                    state = ParserState.ArrayReadValue;
                    break;
                default:
                    throw new JSONException("Must be either object or array", pos);
            }

            root = stack[0];
            stackTop = root;

            boolean firstValue = true;
            for (++pos; pos < length && stackSize > 0; ++pos) {
                char c = charBuffer[pos];
                JSONNode node;
                switch (state) {
                    case ObjectReadKey : {
                        switch (c) {
                            case Char_Single_QUOTE: {
                                push(JSONNode.createFieldNode(pos + 1));
                                if (skipSingleQuotedString()) {
                                    //Escaped string detected
                                    stackTop.type = JSONType.EField;
                                }
                                stackTop.endIndex = pos;
                                state = ParserState.ObjectReadColon;
                                firstValue = false;
                                break;
                            }
                            case Char_Double_QUOTE: {
                                push(JSONNode.createFieldNode(pos + 1));
                                if (skipString()) {
                                    //Escaped string detected
                                    stackTop.type = JSONType.EField;
                                }
                                stackTop.endIndex = pos;
                                state = ParserState.ObjectReadColon;
                                firstValue = false;
                                break;
                            }

                            case Char_CURLY_CLOSE: {
                                node = pop();
                                if (node == null || node.type != JSONType.JSONObject || !firstValue)
                                    throw new JSONException("Unexpected end of object character", pos);


                                node.endIndex = pos + 1;
                                if (stackTop != null && (stackTop.type == JSONType.Field || stackTop.type == JSONType.EField))
                                    drop();

                                switch(stackTop.type) {
                                    case JSONArray: {
                                        state = ParserState.ArrayReadComma;
                                        break;
                                    }
                                    case JSONObject : {
                                        state = ParserState.ObjectReadComma;
                                        break;
                                    }
                                    default: {
                                        throw new JSONException("Unexpected Node Type.", pos);
                                    }
                                }
                                firstValue = false;
                                break;
                            }

                            case Char_SPACE:
                            case Char_TAB:
                            case Char_NL:
                            case Char_CR:
                                trySkipWhiteSpace();
                                break;
                            default: {
                                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '.' || c == '_') {
                                    node = JSONNode.createFieldNode(pos);
                                    push(node);
                                    skipUnquotedStringValue();
                                    node.type = JSONType.Field;
                                    node.endIndex = pos;

                                    state = ParserState.ObjectReadColon;
                                    firstValue = false;
                                    --pos;
                                    break;
                                } else {
                                    throw new JSONException("Unexpected character sequesnce", pos);
                                }
                            }
                        }
                        break;
                    }
                    case ObjectReadColon: {
                        switch (c) {
                            case Char_COLON: {
                                state = ParserState.ObjectReadValue;
                                break;
                            }
                            case Char_SPACE:
                            case Char_TAB:
                            case Char_NL:
                            case Char_CR:
                                trySkipWhiteSpace();
                                break;
                            default : {
                                throw new JSONException("Unexpected character sequesnce", pos);
                            }
                        }

                        break;
                    }
                    case ObjectReadValue : {
                        switch (c) {
                            case Char_CURLY_OPEN: {
                                push(JSONNode.createObjectNode(pos));

                                state = ParserState.ObjectReadKey;
                                firstValue = true;
                                break;
                            }
                            case Char_SQUARE_OPEN: {
                                push(JSONNode.createArrayNode(pos));

                                state = ParserState.ArrayReadValue;
                                firstValue = true;
                                break;
                            }
                            case Char_Single_QUOTE: {
                                node = JSONNode.createStringValueNode(pos + 1);
                                stackTop.addChild(node);
                                if (skipSingleQuotedString()) {
                                    //Escaped string detected
                                    node.type = JSONType.EString;
                                }
                                node.endIndex = pos;
                                drop();
                                state = ParserState.ObjectReadComma;
                                firstValue = false;
                                break;
                            }
                            case Char_Double_QUOTE: {
                                node = JSONNode.createStringValueNode(pos + 1);
                                stackTop.addChild(node);
                                if (skipString()) {
                                    //Escaped string detected
                                    node.type = JSONType.EString;
                                }
                                node.endIndex = pos;
                                drop();
                                state = ParserState.ObjectReadComma;
                                firstValue = false;
                                break;
                            }
                            case '+':
                            case '-':
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9': { //number starts from one of these symbols
                                node = JSONNode.createIntegerValueNode(pos);
                                stackTop.addChild(node);
                                if (skipNumber(c)) { //Float detected
                                    node.type = JSONType.Float;
                                }
                                node.endIndex = pos;
                                --pos;
                                if (stackTop.type == JSONType.Field || stackTop.type == JSONType.EField)
                                    drop();

                                state = ParserState.ObjectReadComma;
                                firstValue = false;
                                break;
                            }

                            case Char_SPACE:
                            case Char_TAB:
                            case Char_NL:
                            case Char_CR:
                                trySkipWhiteSpace();
                                break;
                            default : {
                                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '.' || c == '_') {
                                    if (charBuffer.length >= pos + 4 && (charBuffer[pos] == Char_n || charBuffer[pos] == Char_N) && charBuffer[pos + 1] == Char_u && charBuffer[pos + 2] == Char_l && charBuffer[pos + 3] == Char_l) {
                                        node = JSONNode.createNullValueNode(pos);
                                        stackTop.addChild(node);
                                        pos += 4;
                                        node.endIndex = pos;
                                    } else if (charBuffer.length >= pos + 5 && (charBuffer[pos] == Char_f || charBuffer[pos] == Char_F) && charBuffer[pos + 1] == Char_a && charBuffer[pos + 2] == Char_l && charBuffer[pos + 3] == Char_s && charBuffer[pos + 4] == Char_e) {
                                        node = JSONNode.createBooleanNode(pos, false);
                                        stackTop.addChild(node);
                                        pos += 5;
                                        node.endIndex = pos;
                                    } else if (charBuffer.length >= pos + 4 && (charBuffer[pos] == Char_t || charBuffer[pos] == Char_T) && charBuffer[pos + 1] == Char_r && charBuffer[pos + 2] == Char_u && charBuffer[pos + 3] == Char_e) {
                                        node = JSONNode.createBooleanNode(pos, true);
                                        stackTop.addChild(node);
                                        pos += 4;
                                        node.endIndex = pos;
                                    } else {
                                        node = JSONNode.createStringValueNode(pos);
                                        stackTop.addChild(node);
                                        skipUnquotedStringValue();
                                        node.type = JSONType.String;
                                        node.endIndex = pos;
                                    }
                                    if (stackTop.type == JSONType.Field || stackTop.type == JSONType.EField)
                                        drop();

                                    state = ParserState.ObjectReadComma;
                                    firstValue = false;
                                    --pos;
                                    break;
                                } else {
                                    throw new JSONException("Unexpected character sequesnce", pos);
                                }
                            }
                        }
                        break;
                    }
                    case ObjectReadComma : {
                        switch (c) {
                            case Char_COMMA: {
                                state = ParserState.ObjectReadKey;
                                break;
                            }
                            case Char_CURLY_CLOSE: {
                                node = pop();
                                if (node == null || node.type != JSONType.JSONObject)
                                    throw new JSONException("Unexpected end of object character", pos);

                                node.endIndex = pos + 1;
                                if (stackTop != null && (stackTop.type == JSONType.Field || stackTop.type == JSONType.EField))
                                    drop();

                                switch(stackTop.type) {
                                    case JSONArray: {
                                        state = ParserState.ArrayReadComma;
                                        break;
                                    }
                                    case JSONObject : {
                                        state = ParserState.ObjectReadComma;
                                        break;
                                    }
                                    default: {
                                        throw new JSONException("Unexpected Node Type.", pos);
                                    }
                                }
                                firstValue = false;
                                break;
                            }
                            case Char_SPACE:
                            case Char_TAB:
                            case Char_NL:
                            case Char_CR:
                                trySkipWhiteSpace();
                                break;
                            default : {
                                throw new JSONException("Unexpected character sequesnce", pos);
                            }
                        }
                        break;
                    }

                    case ArrayReadComma: {
                        switch (c) {
                            case Char_COMMA: {
                                state = ParserState.ArrayReadValue;
                                break;
                            }
                            case Char_SQUARE_CLOSE: {
                                node = pop();
                                if (node == null) {
                                    throw new JSONException("Unexpected end of array character", pos);
                                } else if (node.type != JSONType.JSONArray) {
                                    if (node.endIndex == -1)
                                        node.endIndex = pos;

                                    node = pop();
                                    if (node == null || node.type != JSONType.JSONArray)
                                        throw new JSONException("Unexpected end of array", pos);
                                }
                                node.endIndex = pos + 1;
                                if (stackTop != null && (stackTop.type == JSONType.Field || stackTop.type == JSONType.EField))
                                    drop();

                                switch(stackTop.type) {
                                    case JSONArray: {
                                        state = ParserState.ArrayReadComma;
                                        break;
                                    }
                                    case JSONObject : {
                                        state = ParserState.ObjectReadComma;
                                        break;
                                    }
                                    default: {
                                        throw new JSONException("Unexpected Node Type.", pos);
                                    }
                                }
                                firstValue = false;
                                break;
                            }
                            case Char_SPACE:
                            case Char_TAB:
                            case Char_NL:
                            case Char_CR:
                                trySkipWhiteSpace();
                                break;
                            default : {
                                throw new JSONException("Unexpected character sequesnce", pos);
                            }
                        }
                        break;
                    }
                    case ArrayReadValue : {
                        switch (c) {
                            case Char_CURLY_OPEN: {
                                push(JSONNode.createObjectNode(pos));

                                state = ParserState.ObjectReadKey;
                                firstValue = true;
                                break;
                            }
                            case Char_SQUARE_OPEN: {
                                push(JSONNode.createArrayNode(pos));

                                state = ParserState.ArrayReadValue;
                                firstValue = true;
                                break;
                            }
                            case Char_Single_QUOTE: {
                                node = JSONNode.createStringValueNode(pos + 1);
                                stackTop.addChild(node);
                                if (skipSingleQuotedString()) {
                                    //Escaped string detected
                                    node.type = JSONType.EString;
                                }
                                node.endIndex = pos;
                                state = ParserState.ArrayReadComma;
                                firstValue = false;
                                break;
                            }
                            case Char_Double_QUOTE: {
                                node = JSONNode.createStringValueNode(pos + 1);
                                stackTop.addChild(node);
                                if (skipString()) {
                                    //Escaped string detected
                                    node.type = JSONType.EString;
                                }
                                node.endIndex = pos;
                                state = ParserState.ArrayReadComma;
                                firstValue = false;
                                break;
                            }
                            case '+':
                            case '-':
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9': { // expecting numeric value
                                node = JSONNode.createIntegerValueNode(pos);
                                stackTop.addChild(node);
                                if (skipNumber(c)) { //Float detected
                                    node.type = JSONType.Float;
                                }
                                node.endIndex = pos;
                                --pos;

                                state = ParserState.ArrayReadComma;
                                firstValue = false;
                                break;
                            }
                            case Char_SQUARE_CLOSE: {
                                node = pop();
                                if (node == null || !firstValue) {
                                    throw new JSONException("Unexpected end of array character", pos);
                                } else if (node.type != JSONType.JSONArray) {
                                    if (node.endIndex == -1)
                                        node.endIndex = pos;

                                    node = pop();
                                    if (node == null || node.type != JSONType.JSONArray)
                                        throw new JSONException("Unexpected end of array", pos);
                                }
                                node.endIndex = pos + 1;
                                if (stackTop != null && (stackTop.type == JSONType.Field || stackTop.type == JSONType.EField))
                                    drop();

                                switch(stackTop.type) {
                                    case JSONArray: {
                                        state = ParserState.ArrayReadComma;
                                        break;
                                    }
                                    case JSONObject : {
                                        state = ParserState.ObjectReadComma;
                                        break;
                                    }
                                    default: {
                                        throw new JSONException("Unexpected Node Type.", pos);
                                    }
                                }
                                firstValue = false;
                                break;
                            }
                            case Char_SPACE:
                            case Char_TAB:
                            case Char_NL:
                            case Char_CR:
                                trySkipWhiteSpace();
                                break;
                            default : {
                                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '.') {
                                    if (charBuffer.length >= pos + 4 && (charBuffer[pos] == Char_n || charBuffer[pos] == Char_N) && charBuffer[pos + 1] == Char_u && charBuffer[pos + 2] == Char_l && charBuffer[pos + 3] == Char_l) {
                                        node = JSONNode.createNullValueNode(pos);
                                        stackTop.addChild(node);
                                        pos += 4;
                                        node.endIndex = pos;
                                    } else if (charBuffer.length >= pos + 5 && (charBuffer[pos] == Char_f || charBuffer[pos] == Char_F) && charBuffer[pos + 1] == Char_a && charBuffer[pos + 2] == Char_l && charBuffer[pos + 3] == Char_s && charBuffer[pos + 4] == Char_e) {
                                        node = JSONNode.createBooleanNode(pos, false);
                                        stackTop.addChild(node);
                                        pos += 5;
                                        node.endIndex = pos;
                                    } else if (charBuffer.length >= pos + 4 && (charBuffer[pos] == Char_t || charBuffer[pos] == Char_T) && charBuffer[pos + 1] == Char_r && charBuffer[pos + 2] == Char_u && charBuffer[pos + 3] == Char_e) {
                                        node = JSONNode.createBooleanNode(pos, true);
                                        stackTop.addChild(node);
                                        pos += 4;
                                        node.endIndex = pos;
                                    } else {
                                        node = JSONNode.createStringValueNode(pos);
                                        stackTop.addChild(node);
                                        skipUnquotedStringValue();
                                        node.type = JSONType.String;
                                        node.endIndex = pos;
                                    }
                                    state = ParserState.ArrayReadComma;
                                    firstValue = false;
                                    --pos;
                                    break;
                                } else {
                                    throw new JSONException("Unexpected character sequesnce", pos);
                                }
                            }
                        }
                        break;
                    }
                    default: {
                        throw new JSONException("Unknown state", pos);
                    }
                }
            }
            if (stackSize != 0)
                throw new JSONException("Unexpected end of JSONObject");

            setBuffer(root);
        } catch (JSONException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new JSONException("Unexpected end of input", pos);
        } catch (Exception e) {
            throw new JSONException(e);
        }
    }

    private void skipWhiteSpace() {
        char c = charBuffer[pos];
        while (c == Char_SPACE
                || c == Char_NL
                || c == Char_TAB
                || c == Char_CR) {
            c = charBuffer[++pos];
        }
    }

    private void trySkipWhiteSpace() {
        ++pos;
        skipWhiteSpace();
        --pos;
    }

    private boolean skipString() throws JSONException {
        boolean escaped = false;
        char c = charBuffer[++pos];
        while (c != Char_Double_QUOTE) {
            if (c == Char_BACKSLASH) {
                ++pos;
                c = charBuffer[pos];
                if (!(c == Char_Double_QUOTE
                        || c == Char_BACKSLASH
                        || c == Char_SLASH
                        || c == Char_b
                        || c == Char_f
                        || c == Char_n
                        || c == Char_r
                        || c == Char_t
                        || c == Char_u)) {
                    throw new JSONException("Invalid escape code", pos);
                }
                escaped = true;
            }
            ++pos;
            c = charBuffer[pos];
        }
        return escaped;
    }

    //VERY SLOW METHOD - Exceptional case to support old Pro Application
    private void skipUnquotedStringValue() throws JSONException {
        char c = charBuffer[++pos];
        while ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '.' || c == '_') {
            c = charBuffer[++pos];
        }
    }

    private boolean skipSingleQuotedString() throws JSONException {
        boolean escaped = false;
        char c = charBuffer[++pos];
        while (c != Char_Single_QUOTE) {
            if (c == Char_BACKSLASH) {
                ++pos;
                c = charBuffer[pos];
                if (!(c == Char_Double_QUOTE
                        || c == Char_BACKSLASH
                        || c == Char_SLASH
                        || c == Char_b
                        || c == Char_f
                        || c == Char_n
                        || c == Char_r
                        || c == Char_t
                        || c == Char_u)) {
                    throw new JSONException("Invalid escape code", pos);
                }
                escaped = true;
            }
            ++pos;
            c = charBuffer[pos];
        }
        return escaped;
    }

    private boolean skipNumber(char c) throws JSONException {
        boolean floatChar = false;
        if (c == Char_MINUS) {
            c = charBuffer[++pos];
            if (c < Char_0 || c > Char_9) {
                throw new JSONException("Digit expected", pos);
            }
        }
        ++pos;
        if (c == Char_0) {
            c = charBuffer[pos];
            if (c >= Char_0 && c <= Char_9) {
                throw new JSONException("Number may not start with leading zero", pos);
            }
        } else {
            c = charBuffer[pos];
        }
        while (!(c < Char_0 || c > Char_9)) {
            c = charBuffer[++pos];
        }
        if (c == Char_DOT) {
            floatChar = true;
            c = charBuffer[++pos];
            if (c < Char_0 || c > Char_9) {
                throw new JSONException("Digit expected", pos);
            }
            c = charBuffer[++pos];
            while (!(c < Char_0 || c > Char_9)) {
                c = charBuffer[++pos];
            }
        }
        if (c == Char_e || c == Char_E) {
            floatChar = true;
            c = charBuffer[++pos];
            if (c == Char_MINUS || c == Char_PLUS) {
                c = charBuffer[++pos];
                if (c < Char_0 || c > Char_9)
                    throw new JSONException("Digit expected", pos);
            } else {
                if (c < Char_0 || c > Char_9)
                    throw new JSONException("Exponential part expected", pos);
            }
            c = charBuffer[++pos];
            while (!(c < Char_0 || c > Char_9)) {
                c = charBuffer[++pos];
            }
        }
        return floatChar;
    }

    private void setBuffer(JSONNode node) {
        node.charBuffer = charBuffer;
        JSONNode pointer = node.child;
        while (pointer != null) {
            if (pointer.child != null) {
                setBuffer(pointer);
            } else {
                pointer.charBuffer = charBuffer;
            }
            pointer = pointer.next;
        }
    }
}
