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

public class JSONArray extends JSONElement {

    private JSONNode selectNode = null;
    private int selectInt = -1;

    public JSONArray(String source) throws JSONException {
        JSONParser parser = new JSONParser(source);
        parser.parse();
        if (parser.getRoot().type != JSONType.JSONArray) {
            throw new JSONException("JSON Array must start with '['", 0);
        }
        root = parser.getRoot();
    }

    public JSONArray() {
        this(JSONNode.createArrayNode(0));
    }

    protected JSONArray(JSONNode root) {
        super(root);
    }

    //<editor-fold desc="Put Methods">
    public JSONArray put(String value) throws JSONException {
        JSONNode child;
        if (shouldQuoteString(value)) {
            child = appendAndSetMixedString(JSONType.EString, quoteString(value));
        } else {
            child = appendAndSetMixedString(JSONType.String, value);
        }
        appendChild(child);
        return this;
    }

    public JSONArray put(int value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Integer, Integer.toString(value));
        appendChild(child);
        return this;
    }

    public JSONArray put(long value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Integer, Long.toString(value));
        appendChild(child);
        return this;
    }

    public JSONArray put(float value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, Float.toString(value));
        appendChild(child);
        return this;
    }

    public JSONArray put(double value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, Double.toString(value));
        appendChild(child);
        return this;
    }

    public JSONArray put(Number value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, value.toString());
        appendChild(child);
        return this;
    }

    public JSONArray put(boolean value) throws JSONException {
        JSONNode child = JSONNode.createBooleanNode(-1, value);
        child.mixed = true;
        appendChild(child);
        return this;
    }

    public JSONArray put(JSONArray value) {
        appendChild(value.root);
        return this;
    }

    public JSONArray put(JSONObject value) {
        appendChild(value.root);
        return this;
    }

    public JSONArray put(Object value) throws JSONException {
        if (value == null) {
            JSONNode child = JSONNode.createNullValueNode(-1);
            child.mixed = true;
            appendChild(child);
            return this;
        }
        if (value instanceof java.lang.Integer) {
            return put(((Integer) value).intValue());
        }
        if (value instanceof java.lang.Long) {
            return put(((Long) value).longValue());
        }
        if (value instanceof java.lang.Float) {
            return put(((Float) value).floatValue());
        }
        if (value instanceof java.lang.Double) {
            return put(((Double) value).doubleValue());
        }
        if (value instanceof Number) {
            return put((Number) value);
        }
        if (value instanceof java.lang.Boolean) {
            return put(((Boolean) value).booleanValue());
        }
        if (value instanceof java.lang.String) {
            return put((String) value);
        }
        if (value instanceof JSONObject) {
            return put((JSONObject) value);
        }
        if (value instanceof JSONArray) {
            return put((JSONArray) value);
        }
        return put(value.toString());
    }

    public JSONArray put(int index, Object value) throws JSONException {
        if (value == null) {
            JSONNode child = JSONNode.createNullValueNode(-1);
            child.mixed = true;
            insertChild(index, child);
            return this;
        }
        if (value instanceof java.lang.Integer) {
            return put(index, ((Integer) value).intValue());
        }
        if (value instanceof java.lang.Long) {
            return put(index, ((Long) value).longValue());
        }
        if (value instanceof java.lang.Float) {
            return put(index, ((Float) value).floatValue());
        }
        if (value instanceof java.lang.Double) {
            return put(index, ((Double) value).doubleValue());
        }
        if (value instanceof java.lang.Boolean) {
            return put(index, ((Boolean) value).booleanValue());
        }
        if (value instanceof java.lang.String) {
            return put(index, (String) value);
        }
        if (value instanceof JSONObject) {
            return put(index, (JSONObject) value);
        }
        if (value instanceof JSONArray) {
            return put(index, (JSONArray) value);
        }
        throw new JSONException("Unsupported object type");
    }

    public JSONArray put(int index, String value) throws JSONException {
        JSONNode child;
        if (shouldQuoteString(value)) {
            child = appendAndSetMixedString(JSONType.EString, quoteString(value));
        } else {
            child = appendAndSetMixedString(JSONType.String, value);
        }
        insertChild(index, child);
        return this;
    }

    public JSONArray put(int index, int value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Integer, Integer.toString(value));
        insertChild(index, child);
        return this;
    }

    public JSONArray put(int index, long value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Integer, Long.toString(value));
        insertChild(index, child);
        return this;
    }

    public JSONArray put(int index, float value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, Float.toString(value));
        insertChild(index, child);
        return this;
    }

    public JSONArray put(int index, double value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, Double.toString(value));
        insertChild(index, child);
        return this;
    }

    public JSONArray put(int index, boolean value) throws JSONException {
        JSONNode child = JSONNode.createBooleanNode(-1, value);
        child.mixed = true;
        insertChild(index, child);
        return this;
    }

    public JSONArray put(int index, JSONArray value) throws JSONException {
        insertChild(index, value.root);
        return this;
    }

    public JSONArray put(int index, JSONObject value) throws JSONException {
        insertChild(index, value.root);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Get Methods">
    public Object get(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        if (node == null)
            return null;

        switch (node.type) {
            case JSONObject:
                JSONObject obj = new JSONObject(node);
                obj.parent = this;
                return obj;
            case JSONArray:
                JSONArray array = new JSONArray(node);
                array.parent = this;
                return array;
            case BooleanTrue:
                return (Boolean) true;
            case BooleanFalse:
                return (Boolean) false;
            case Null:
                return null;
            case String:
                return node.getStringValue();
            case EString:
                return node.getStringValue();
            case Integer:
                return (Long) node.getLongValue();
            case Float:
                return (Double) node.getDoubleValue();
            default:
                throw new JSONException("Unsupported object type.");
        }
    }
    
    public JSONObject getJSONObject(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        if (node.type != JSONType.JSONObject) {
            throw new JSONException("Requested value is not an object", node);
        }
        JSONObject obj = new JSONObject(node);
        obj.parent = this;
        return obj;
    }

    public JSONArray getJSONArray(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        if (node.type != JSONType.JSONArray) {
            throw new JSONException("Requested value is not an array", node);
        }
        JSONArray arr = new JSONArray(node);
        arr.parent = this;
        return arr;
    }

    public boolean getBoolean(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        if (node.type == JSONType.BooleanTrue) {
            return true;
        }
        if (node.type == JSONType.BooleanFalse) {
            return false;
        }
        throw new JSONException("Requested value is not a boolean", node);
    }

    public String getString(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        return node.getStringValue();
    }

    public int getInt(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        return node.getIntValue();
    }

    public long getLong(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        return node.getLongValue();
    }

    public double getDouble(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        return node.getDoubleValue();
    }
    //</editor-fold>

    //<editor-fold desc="Opt Methods">
    public Object opt(int index) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null)
                return null;

            switch (node.type) {
                case JSONObject:
                    JSONObject obj = new JSONObject(node);
                    obj.parent = this;
                    return obj;
                case JSONArray:
                    JSONArray arr = new JSONArray(node);
                    arr.parent = this;
                    return arr;
                case BooleanTrue:
                    return (Boolean) true;
                case BooleanFalse:
                    return (Boolean) false;
                case Null:
                    return null;
                case String:
                    return node.getStringValue();
                case EString:
                    return node.getStringValue();
                case Integer:
                    return (Long) node.getLongValue();
                case Float:
                    return (Double) node.getDoubleValue();
            }
        } catch (Exception ignored) {
        }
        return null;
    }
    
    public JSONArray optJSONArray(int index) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null) {
                return null;
            }
            if (node.type == JSONType.Null) {
                return null;
            }
            if (node.type != JSONType.JSONArray) {
                return null;
            }
            JSONArray arr = new JSONArray(node);
            arr.parent = this;
            return arr;
        } catch (Exception ignored) {
            return null;
        }
    }

    public JSONObject optJSONObject(int index) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null) {
                return null;
            }
            if (node.type == JSONType.Null) {
                return null;
            }
            if (node.type != JSONType.JSONObject) {
                return null;
            }
            JSONObject obj = new JSONObject(node);
            obj.parent = this;
            return obj;
        } catch (Exception ignore) {
            return null;
        }
    }

    public boolean optBoolean(int index) {
        return this.optBoolean(index, false);
    }

    public boolean optBoolean(int index, boolean defaultValue) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null) {
                return defaultValue;
            }
            if (node.type == JSONType.Null) {
                return defaultValue;
            }
            if (node.type == JSONType.BooleanTrue) {
                return true;
            }
            if (node.type == JSONType.BooleanFalse) {
                return false;
            }
            throw new JSONException("Requested value is not a boolean", node);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public String optString(int index) {
        return this.optString(index, "");
    }

    public String optString(int index, String defaultValue) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null) {
                return defaultValue;
            }
            if (node.type == JSONType.Null) {
                return defaultValue;
            }
            return node.getStringValue();
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public int optInt(int index) {
        return this.optInt(index, 0);
    }

    public int optInt(int index, int defaultValue) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null) {
                return defaultValue;
            }
            if (node.type == JSONType.Null) {
                return defaultValue;
            }
            return node.getIntValue();
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public long optLong(int index) {
        return this.optLong(index, 0L);
    }

    public long optLong(int index, long defaultValue) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null) {
                return defaultValue;
            }
            if (node.type == JSONType.Null) {
                return defaultValue;
            }
            return node.getLongValue();
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public double optDouble(int index) {
        return this.optDouble(index, Double.NaN);
    }

    public double optDouble(int index, double defaultValue) {
        try {
            JSONNode node = optValueNode(index);
            if (node == null) {
                return defaultValue;
            }
            if (node.type == JSONType.Null) {
                return defaultValue;
            }
            return node.getDoubleValue();
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
    //</editor-fold>

    public Object remove(int index) throws JSONException {
        Object obj = opt(index);
        JSONNode node = optValueNode(index);
        if (node != null) {
            JSONNode pointer = this.root.child;
            if (pointer == node) {
                root.child = node.next;
            } else {
                while (pointer != null) {
                    if (pointer.next == node) {
                        pointer.next = node.next;
                    }
                    pointer = pointer.next;
                }
            }
            root.mixed = true;
        }
        selectNode = null;
        selectInt = -1;
        return obj;
    }

    public boolean isNull(int index) throws JSONException {
        JSONNode node = getValueNode(index);
        return node.type == JSONType.Null;
    }

    private JSONNode getValueNode(int index) {
        if (index < 0)
            throw new IllegalArgumentException("Array index can not be negative");

        int num = 0;
        JSONNode child = root.child;
        if (selectInt > -1 && index >= selectInt) {
            num = selectInt;
            child = selectNode;
        }
        while (child != null) {
            if (num == index) {
                selectInt = index;
                selectNode = child;
                return child;
            }
            num++;
            child = child.next;
        }
        throw new IllegalArgumentException("Array index out of bounds " + index);
    }

    private JSONNode optValueNode(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Array index can not be negative " + index);
        }
        int num = 0;
        JSONNode child = root.child;
        if (selectInt > -1 && index >= selectInt) {
            num = selectInt;
            child = selectNode;
        }
        while (child != null) {
            if (num == index) {
                selectInt = index;
                selectNode = child;
                return child;
            }
            num++;
            child = child.next;
        }
        return null;
    }
    
    private void appendChild(JSONNode node) {
        if (root.child == null) {
            root.child = node;
            root.lastChild = node;
        } else {
            root.lastChild.next = node;
            root.lastChild = node;
        }
        root.mixed = true;
        selectNode = null;
        selectInt = -1;
    }

    private void insertChild(int index, JSONNode node) throws JSONException {
        root.mixed = true;
        if (index == 0) {
            node.next = root.child;
            root.child = node;
            return;
        }
        int current = 1;
        JSONNode pointer = root.child;
        if (pointer == null) {
            throw new JSONException("Trying to put at index " + index + " on an empty LazyArray");
        }
        while (current < index) {
            current++;
            pointer = pointer.next;
            if (pointer == null) {
                throw new JSONException("Index out of bounds " + index);
            }
        }
        node.next = pointer.next;
        pointer.next = node;
        selectNode = null;
        selectInt = -1;
    }
    
    @Override
    public JSONType getType() {
        return JSONType.JSONArray;
    }

    public JSONType getElementType(int index) {
        JSONNode node = getValueNode(index);
        return node.type;
    }
    
    @Override
    protected String serializeElement() {
        return serializeElement(0);
    }
    
    @Override
    protected String serializeElement(int indentFactor) {
        return this.serializeElement(indentFactor, 0);
    }

    protected String serializeElement(int indentFactor, int indent) {
        StringBuilder output = new StringBuilder();
        output.append("[");
        int newIndent = indent + indentFactor;
        JSONNode pointer = root.child;
        boolean single = length() == 1;
        boolean commanate = false;
        while (pointer != null) {
            if (commanate)
                output.append(",");

            if (commanate || !single) {
                if (indentFactor > 0)
                    output.append('\n');
                
                appendIndent(output, newIndent);
            } else {
                newIndent = indent;
            }
            switch (pointer.type) {
                case JSONObject:
                    output.append(new JSONObject(pointer).serializeElement(indentFactor, newIndent));
                    break;
                case JSONArray:
                    output.append(new JSONArray(pointer).serializeElement(indentFactor, newIndent));
                    break;
                case String:
                    output.append("\"");
                    output.append(pointer.getStringValue());
                    output.append("\"");
                    break;
                case EString:
                    output.append("\"");
                    output.append(pointer.getRawStringValue());
                    output.append("\"");
                    break;
                case BooleanTrue:
                    output.append("true");
                    break;
                case BooleanFalse:
                    output.append("false");
                    break;
                case Null:
                    output.append("null");
                    break;
                default:
                    output.append(pointer.getStringValue());
                    break;
            }
            pointer = pointer.next;
            commanate = true;
        }
        if (!single) {
            if (indentFactor > 0)
                output.append("\n");
            
            appendIndent(output, indent);
        }
        output.append("]");
        return output.toString();
    }
}
