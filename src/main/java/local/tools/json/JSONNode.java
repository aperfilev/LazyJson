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
package local.tools.json;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JSONNode {

    protected JSONType type;

    protected boolean mixed = false;
    protected char[] charBuffer = null;
    protected StringBuilder mixedBuffer = null;

    protected int startIndex;
    protected int endIndex = -1;

    protected JSONNode child;
    protected JSONNode lastChild;
    protected JSONNode next;

    protected JSONNode(JSONType type, int startIndex) {
        this.startIndex = startIndex;
        this.type = type;
    }

    protected StringBuilder getMixedBuffer() {
        if (mixedBuffer == null) {
            mixedBuffer = new StringBuilder();
        }
        return mixedBuffer;
    }

    protected boolean isMixed() {
        if (mixed) {
            return true;
        }
        if (child == null) {
            return false;
        }
        JSONNode pointer = child;
        while (pointer != null) {
            if (pointer.isMixed()) {
                return true;
            }
            pointer = pointer.next;
        }
        return false;
    }

    protected void addChild(JSONNode node) {
        if (lastChild == null) {
            child = node;
            lastChild = node;
            return;
        }
        lastChild.next = node;
        lastChild = node;
    }

    protected int countChildren() {
        int count = 0;
        JSONNode node = child;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    protected static JSONNode createArrayNode(int index) {
        return new JSONNode(JSONType.JSONArray, index);
    }

    protected static JSONNode createObjectNode(int index) {
        return new JSONNode(JSONType.JSONObject, index);
    }

    protected static JSONNode createFieldNode(int index) {
        return new JSONNode(JSONType.Field, index);
    }

    protected static JSONNode createStringValueNode(int index) {
        return new JSONNode(JSONType.String, index);
    }

    protected static JSONNode createIntegerValueNode(int index) {
        return new JSONNode(JSONType.Integer, index);
    }

    protected static JSONNode createBooleanNode(int index, boolean value) {
        return new JSONNode(value ? JSONType.BooleanTrue : JSONType.BooleanFalse, index);
    }

    protected static JSONNode createNullValueNode(int index) {
        return new JSONNode(JSONType.Null, index);
    }

    protected int getIntValue() throws JSONException {
        int i = startIndex;
        boolean sign = false;
        int value = 0;
        switch (type) {
            case Float:
                return (int) getDoubleValue();
            case String:
            case EString:
                if (mixed) {
                    if (mixedBuffer.charAt(i) == '-') {
                        sign = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = mixedBuffer.charAt(i);
                        if (c < '0' || c > '9') {
                            throw new JSONException("'" + getStringValue() + "' is not a valid integer", startIndex);
                        }
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                } else {
                    if (charBuffer[i] == '-') {
                        sign = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = charBuffer[i];
                        if (c < '0' || c > '9') {
                            throw new JSONException("'" + getStringValue() + "' is not a valid integer", startIndex);
                        }
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                }
                return sign ? value : -value;
            case Integer:
                if (mixed) {
                    if (mixedBuffer.charAt(i) == '-') {
                        sign = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = mixedBuffer.charAt(i);
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                } else {
                    if (charBuffer[i] == '-') {
                        sign = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = charBuffer[i];
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                }
                return sign ? value : -value;
        }
        throw new JSONException("Not an integer", startIndex);
    }

    protected long getLongValue() throws JSONException {
        int i = startIndex;
        boolean isNegative = false;
        long value = 0;
        switch (type) {
            case Float:
                return (long) getDoubleValue();
            case String:
            case EString:
                if (mixed) {
                    if (mixedBuffer.charAt(i) == '-') {
                        isNegative = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = mixedBuffer.charAt(i);
                        if (c < '0' || c > '9') {
                            throw new JSONException("'" + getStringValue() + "' is not a valid long", startIndex);
                        }
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                } else {
                    if (charBuffer[i] == '-') {
                        isNegative = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = charBuffer[i];
                        if (c < '0' || c > '9') {
                            throw new JSONException("'" + getStringValue() + "' is not a valid long", startIndex);
                        }
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                }
                return isNegative ? value : -value;
            case Integer:
                if (mixed) {
                    if (mixedBuffer.charAt(i) == '-') {
                        isNegative = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = mixedBuffer.charAt(i);
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                } else {
                    if (charBuffer[i] == '-') {
                        isNegative = true;
                        i++;
                    }
                    for (; i < endIndex; i++) {
                        char c = charBuffer[i];
                        value += '0' - c;
                        if (i + 1 < endIndex) {
                            value *= 10;
                        }
                    }
                }
                return isNegative ? value : -value;
        }
        throw new JSONException("Not a long", startIndex);
    }

    protected double getDoubleValue() throws JSONException {
        String str = getStringValue();
        if (str == null)
            throw new JSONException("'null' is not a valid double",startIndex);

        double d;
        try {
            d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new JSONException("'"+str+"' is not a valid double",startIndex);
        }
        return d;
    }

    protected String getStringValue() {
        switch (type) {
            case Null:
                return null;
            case String:
            case EString: {
                StringBuilder buffer = new StringBuilder(endIndex - startIndex);
                if (mixed) {
                    for (int i = startIndex; i < endIndex; i++) {
                        char c = mixedBuffer.charAt(i);
                        if (c == '\\') {
                            i++;
                            c = mixedBuffer.charAt(i);
                            switch (c) {
                                case '"':
                                case '\\':
                                case '/':
                                    buffer.append(c);
                                    break;
                                case 'b':
                                    buffer.append('\b');
                                    break;
                                case 'f':
                                    buffer.append('\f');
                                    break;
                                case 'n':
                                    buffer.append('\n');
                                    break;
                                case 'r':
                                    buffer.append('\r');
                                    break;
                                case 't':
                                    buffer.append('\t');
                                    break;
                                case 'u':
                                    String code = mixedBuffer.substring(i + 1, i + 5);
                                    buffer.append((char) Integer.parseInt(code, 16));
                                    i += 4;
                                    break;
                            }
                        } else {
                            buffer.append(c);
                        }
                    }
                } else {
                    for (int i = startIndex; i < endIndex; ++i) {
                        char c = charBuffer[i];
                        if (c == '\\') {
                            i++;
                            c = charBuffer[i];
                            switch (c) {
                                case '"':
                                case '\\':
                                case '/':
                                    buffer.append(c);
                                    break;
                                case 'b':
                                    buffer.append('\b');
                                    break;
                                case 'f':
                                    buffer.append('\f');
                                    break;
                                case 'n':
                                    buffer.append('\n');
                                    break;
                                case 'r':
                                    buffer.append('\r');
                                    break;
                                case 't':
                                    buffer.append('\t');
                                    break;
                                case 'u':
                                    if (i + 1 + 4 >= charBuffer.length)
                                        throw new JSONException("Invalid unicode symbol code");

                                    String code = new String(charBuffer, i + 1, 4);
                                    try {
                                        buffer.append((char) Integer.parseInt(code, 16));
                                    } catch (NumberFormatException e) {
                                        throw new JSONException(e);
                                    }
                                    i += 4;
                                    break;
                                default: {
                                    throw new JSONException("Unknown escape symbol");
                                }
                            }
                        } else {
                            buffer.append(c);
                        }
                    }
                }
                return buffer.toString();
            }
            default: {
                if (mixed) {
                    return mixedBuffer.substring(startIndex, endIndex);
                }
                return new String(charBuffer, startIndex, endIndex - startIndex);
            }
        }
    }

    protected String getRawStringValue() {
        if (mixed) {
            return mixedBuffer.substring(startIndex, endIndex);
        } else {
            return new String(charBuffer, startIndex, endIndex - startIndex);
        }
    }

    protected Iterator<String> getStringIterator() {
        return new StringIterator(this);
    }

    protected static final class StringIterator implements Iterator<String> {

        private JSONNode next;

        protected StringIterator(JSONNode node) {
            next = node.child;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Iterator doesn't support this operation");
        }

        @Override
        public String next() throws NoSuchElementException {
            if (hasNext()) {
                String value = next.getStringValue();
                next = next.next;
                return value;
            }
            throw new NoSuchElementException();
        }
    }
}
