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

public abstract class JSONElement {

    protected JSONNode root;
    protected JSONElement parent;

    private int length = -1;

    protected JSONElement() {
    }

    protected JSONElement(JSONNode root) {
        this.root = root;
    }

    JSONNode appendAndSetMixedString(JSONType type, String value) {
        StringBuilder mixedBuffer = root.getMixedBuffer();
        JSONNode child = new JSONNode(type, mixedBuffer.length());
        mixedBuffer.append(value);
        child.endIndex = mixedBuffer.length();
        child.mixed = true;
        child.mixedBuffer = mixedBuffer;
        return child;
    }

    abstract JSONType getType();

    public static JSONElement parse(String source) throws JSONException {
        int index = 0;
        while (index < source.length()) {
            char c = source.charAt(index);
            if (c == '[') {
                return new JSONArray(source);
            }
            if (c == '{') {
                return new JSONObject(source);
            }
            index++;
        }
        throw new JSONException("The given string is not a JSON object or array");
    }

    static boolean shouldQuoteString(String str) {
        if (str == null) {
            return false;
        }
        boolean found = false;
        int length = str.length();
        char[] charBuffer = new char[length];
        str.getChars(0, length, charBuffer, 0);
        for (int i = 0; i < length; i++) {
            char c = charBuffer[i];
            if (c == '\\' || c == '"' || c == '\b' || c == '\t' || c == '\n' || c == '\f' || c == '\r') {
                found = true;
                break;
            }
        }
        return found;
    }

    protected static String quoteString(String str) {
        StringBuilder output = new StringBuilder();
        int length = str.length();
        char[] charBuffer = new char[length];
        str.getChars(0, length, charBuffer, 0);

        for (int i = 0; i < length; i++) {
            char c = charBuffer[i];
            switch (c) {
                case '\\':
                    output.append("\\\\");
                    break;
                case '"':
                    output.append('\\');
                    output.append(c);
                    break;
                case '\b':
                    output.append("\\b");
                    break;
                case '\t':
                    output.append("\\t");
                    break;
                case '\n':
                    output.append("\\n");
                    break;
                case '\f':
                    output.append("\\f");
                    break;
                case '\r':
                    output.append("\\r");
                    break;
                default:
                    output.append(c);
            }
        }
        return output.toString();
    }

    public int length() {
        if (root.child == null) {
            return 0;
        }
        if (length > -1) {
            return length;
        }
        length = root.countChildren();
        return length;
    }

    protected abstract String serializeElement();
    
    protected abstract String serializeElement(int indent);

    @Override
    public String toString() {
        return serializeElement();
    }
    
    public String toString(int indent) {
        return serializeElement(indent);
    }
    
    protected static void appendIndent(StringBuilder builder, int indent) {
        for (int i=0; i<indent; ++i) {
            builder.append(" ");
        }
    }

    int getSourceLength() {
        return root.endIndex - root.startIndex;
    }

    private static boolean equalObjects(JSONObject o1, JSONObject o2) {
        if (o1.length() != o2.length()) {
            return false;
        }
        for (String key : o1.keySet()) {
            if (!o2.has(key)) {
                return false;
            }
            
            JSONType type1 = o1.getElementType(key);
            if (type1 != o2.getElementType(key)) {
                return false;
            }
            switch (type1) {
                case String:
                    if (!o1.optString(key).equals(o2.optString(key))) {
                        return false;
                    }
                    break;
                case Integer:
                    if (o1.optLong(key) != o2.optLong(key)) {
                        return false;
                    }
                    break;
                case Float:
                    if (o1.optDouble(key) != o2.optDouble(key)) {
                        return false;
                    }
                    break;
                case Boolean:
                    if (o1.optBoolean(key) != o2.optBoolean(key)) {
                        return false;
                    }
                    break;
                case JSONObject:
                    if (!o1.optJSONObject(key).equals(o2.optJSONObject(key))) {
                        return false;
                    }
                    break;
                case JSONArray:
                    if (!o1.optJSONArray(key).equals(o2.optJSONArray(key))) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    private static boolean equalArrays(JSONArray a1, JSONArray a2) {
        if (a1.length() != a2.length()) {
            return false;
        }
        for (int i = 0; i < a1.length(); i++) {
            JSONType type1 = a1.getElementType(i);
            if (type1 != a2.getElementType(i)) {
                return false;
            }

            switch (type1) {
                case String:
                    if (!a1.optString(i).equals(a2.optString(i))) {
                        return false;
                    }
                    break;
                case Integer:
                    if (a1.optLong(i) != a2.optLong(i)) {
                        return false;
                    }
                    break;
                case Float:
                    if (a1.optDouble(i) != a2.optDouble(i)) {
                        return false;
                    }
                    break;
                case Boolean:
                    if (a1.optBoolean(i) != a2.optBoolean(i)) {
                        return false;
                    }
                    break;
                case JSONObject:
                    if (!a1.optJSONObject(i).equals(a2.optJSONObject(i))) {
                        return false;
                    }
                    break;
                case JSONArray:
                    if (!a1.optJSONArray(i).equals(a2.optJSONArray(i))) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JSONElement)) {
            return false;
        }
        JSONElement el = (JSONElement) obj;
        if (el.getType() != getType()) {
            return false;
        }
        if (getType() == JSONType.JSONObject) {
            return equalObjects((JSONObject) this, (JSONObject) el);
        } else {
            return equalArrays((JSONArray) this, (JSONArray) el);
        }
    }
}
