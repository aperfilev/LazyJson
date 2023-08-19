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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class JSONObject extends JSONElement {

    public JSONObject() {
        this(JSONNode.createObjectNode(0));
    }
    
    public JSONObject(String source) throws JSONException {
        JSONParser parser = new JSONParser(source);
        parser.parse();
        if (parser.getRoot().type != JSONType.JSONObject) {
            throw new JSONException("JSON Object must start with {", 0);
        }
        root = parser.getRoot();
    }

    protected JSONObject(JSONNode root) {
        super(root);
    }

    //<editor-fold desc="Put Methods">
    public JSONObject put(String key, String value) throws JSONException {
        if (value == null) {
            remove(key);
            return this;
        }
        JSONNode child;
        if (shouldQuoteString(value)) {
            child = appendAndSetMixedString(JSONType.EString, quoteString(value));
        } else {
            child = appendAndSetMixedString(JSONType.String, value);
        }
        attachField(key, child);
        return this;
    }

    public JSONObject put(String key, int value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Integer, Integer.toString(value));
        attachField(key, child);
        return this;
    }

    public JSONObject put(String key, long value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Integer, Long.toString(value));
        attachField(key, child);
        return this;
    }

    public JSONObject put(String key, float value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, Float.toString(value));
        attachField(key, child);
        return this;
    }

    public JSONObject put(String key, double value) throws JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, Double.toString(value));
        attachField(key, child);
        return this;
    }

    public JSONObject put(String key, Number value) throws  JSONException {
        JSONNode child = appendAndSetMixedString(JSONType.Float, value.toString());
        attachField(key, child);
        return this;
    }

    public JSONObject put(String key, boolean value) throws JSONException {
        JSONNode child;
        child = JSONNode.createBooleanNode(-1, value);
        child.mixed = true;
        attachField(key, child);
        return this;
    }

    public JSONObject put(String key, JSONObject value) throws JSONException {
        attachField(key, value.root);
        return this;
    }

    public JSONObject put(String key, JSONArray value) throws JSONException {
        attachField(key, value.root);
        return this;
    }

    public JSONObject put(String key, Object value) throws JSONException {
        if (value == null) {
            JSONNode child = JSONNode.createNullValueNode(-1);
            child.mixed = true;
            attachField(key, child);
            return this;
        }
        if (value instanceof java.lang.Integer) {
            return put(key, ((Integer) value).intValue());
        }
        if (value instanceof java.lang.Long) {
            return put(key, ((Long) value).longValue());
        }
        if (value instanceof java.lang.Float) {
            return put(key, ((Float) value).floatValue());
        }
        if (value instanceof java.lang.Double) {
            return put(key, ((Double) value).doubleValue());
        }
        if (value instanceof Number) {
            return put(key, (Number) value);
        }
        if (value instanceof java.lang.Boolean) {
            return put(key, ((Boolean) value).booleanValue());
        }
        if (value instanceof java.lang.String) {
            return put(key, (String) value);
        }
        if (value instanceof JSONObject) {
            return put(key, (JSONObject) value);
        }
        if (value instanceof JSONArray) {
            return put(key, (JSONArray) value);
        }
        return put(key, value.toString());
    }
    //</editor-fold>

    //<editor-fold desc="Get Methods">
    public Object get(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        if (node != null) {
            switch (node.type) {
                case JSONObject:
                    JSONObject object = new JSONObject(node);
                    object.parent = this;
                    return object;
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
                case EString:
                    return node.getStringValue();
                case Integer:
                    return (Long) node.getLongValue();
                case Float:
                    return (Double) node.getDoubleValue();
            }
        }
        return null;
    }
    
    public JSONObject getJSONObject(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        if (node.type != JSONType.JSONObject) {
            throw new JSONException("Requested value is not an object", node);
        }
        JSONObject obj = new JSONObject(node);
        obj.parent = this;
        return obj;
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        if (node.type != JSONType.JSONArray) {
            throw new JSONException("Requested value is not an array", node);
        }
        JSONArray arr = new JSONArray(node);
        arr.parent = this;
        return arr;
    }

    public String getString(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        if (node == null)
            return null;
        return node.getStringValue();
    }

    public String getString(String key, String defaultValue) {
        JSONNode node = optFieldNode(key);
        if (node == null)
            return defaultValue;
        return node.getStringValue();
    }

    public boolean getBoolean(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        if (node.type == JSONType.String || node.type == JSONType.EString) {
            String str = node.getStringValue();
            if (str == null)
                throw new JSONException("Requested value is not a boolean", node);

            str = str.toLowerCase().trim();
            if (str.equals("true")) {
                return true;
            }
            if (str.equals("false")) {
                return false;
            }
            throw new JSONException("Requested value is not a boolean", node);
        }
        if (node.type == JSONType.BooleanTrue) {
            return true;
        }
        if (node.type == JSONType.BooleanFalse) {
            return false;
        }
        throw new JSONException("Requested value is not a boolean", node);
    }

    public int getInt(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        return node.getIntValue();
    }

    public long getLong(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        return node.getLongValue();
    }

    public double getDouble(String key) throws JSONException {
        JSONNode node = getFieldNode(key);
        return node.getDoubleValue();
    }
    //</editor-fold>

    //<editor-fold desc="Opt Methods">
    public Object opt(String key) {
        try {
            JSONNode node = optFieldNode(key);
            if (node != null) {
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
                    case EString:
                        return node.getStringValue();
                    case Integer:
                        return (Long) node.getLongValue();
                    case Float:
                        return (Double) node.getDoubleValue();
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
    
    public JSONObject optJSONObject(String key) {
        JSONNode node = optFieldNode(key);
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
    }

    public JSONArray optJSONArray(String key) {
        JSONNode node = optFieldNode(key);
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
    }

    public String optString(String key) {
        return this.optString(key, "");
    }

    public String optString(String key, String defaultValue) {
        JSONNode node = optFieldNode(key);
        if (node == null) {
            return defaultValue;
        }
        if (node.type == JSONType.Null) {
            return defaultValue;
        }
        return node.getStringValue();
    }

    public boolean optBoolean(String key) {
        return optBoolean(key, false);
    }

    public boolean optBoolean(String key, boolean defaultValue) {
        try {
            JSONNode node = optFieldNode(key);
            if (node == null) {
                return defaultValue;
            }
            if (node.type == JSONType.Null) {
                return defaultValue;
            }
            if (node.type == JSONType.String || node.type == JSONType.EString) {
                String str = node.getStringValue();
                if (str == null)
                    throw new JSONException("Requested value is not a boolean", node);

                str = str.toLowerCase().trim();
                if (str.equals("true")) {
                    return true;
                }
                if (str.equals("false")) {
                    return false;
                }
                throw new JSONException("Requested value is not a boolean", node);
            }
            return node.type == JSONType.BooleanTrue;
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public int optInt(String key) {
        return this.optInt(key, 0);
    }

    public int optInt(String key, int defaultValue) {
        try {
            JSONNode node = optFieldNode(key);
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

    public long optLong(String key) {
        return this.optLong(key, 0L);
    }

    public long optLong(String key, long defaultValue) {
        try {
            JSONNode node = optFieldNode(key);
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

    public double optDouble(String key) {
        return this.optDouble(key, Double.NaN);
    }

    public double optDouble(String key, double defaultValue) {
        try {
            JSONNode node = optFieldNode(key);
            if (node == null) {
                return defaultValue;
            }
            if (node.type == JSONType.Null) {
                return defaultValue;
            }
            return node.getDoubleValue();
        } catch(Exception ignored) {
            return defaultValue;
        }
    }
    //</editor-fold>

    public Object remove(String key) {
        Object obj = opt(key);
        JSONNode node = optField(key);
        if (node != null) {
            JSONNode pointer = this.root.child;
            if (pointer == node) {
                root.child = node.next;
                if (root.lastChild == pointer) {
                    root.lastChild = null;
                }
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
        return obj;
    }

    public boolean isNull(String key) {
        JSONNode node = optFieldNode(key);
        if (node == null) {
            return true;
        }
        return node.type == JSONType.Null;
    }

    public boolean has(String key) {
        JSONNode child = root.child;
        while (child != null) {
            if (isKeyFieldMatch(key, child)) {
                return true;
            }
            child = child.next;
        }
        return false;
    }
    
    public Iterator<String> keys() {
        return root.getStringIterator();
    }

    public Set<String> keySet() {
        Set<String> set = new LinkedHashSet<>();
        Iterator<String> keys = keys();
        while (keys.hasNext()) {
            set.add(keys.next());
        }
        return set;
    }

    private boolean isKeyFieldMatch(String key, JSONNode node) {
        if (node.type == JSONType.EField) {
            String field = node.getStringValue();
            return Objects.equals(field, key);
        } else {
            int length = key.length();
            if (node.endIndex - node.startIndex != length) {
                return false;
            }
            if (node.mixed) {
                for (int i = 0; i < length; i++) {
                    char c = key.charAt(i);
                    if (c != node.mixedBuffer.charAt(node.startIndex + i)) {
                        return false;
                    }
                }
            } else {
                for (int i = 0; i < length; i++) {
                    char c = key.charAt(i);
                    if (c != node.charBuffer[node.startIndex + i]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private JSONNode getFieldNode(String key) throws JSONException {
        JSONNode child = root.child;
        while (child != null) {
            if (isKeyFieldMatch(key, child)) {
                return child.child;
            }
            child = child.next;
        }
        throw new JSONException("Unknown field '" + key + "'");
    }

    private JSONNode optFieldNode(String key) {
        JSONNode child = root.child;
        while (child != null) {
            if (isKeyFieldMatch(key, child)) {
                return child.child;
            }
            child = child.next;
        }
        return null;
    }

    private JSONNode optField(String key) {
        JSONNode child = root.child;
        while (child != null) {
            if (isKeyFieldMatch(key, child)) {
                return child;
            }
            child = child.next;
        }
        return null;
    }
    
    private void attachField(String key, JSONNode child) {
        StringBuilder mixedBuffer = root.getMixedBuffer();
        JSONNode node = optField(key);
        if (node == null) {
            // new field
            node = JSONNode.createFieldNode(mixedBuffer.length());
            node.mixed = true;
            node.mixedBuffer = mixedBuffer;
            // TODO: we should be encoding the value
            mixedBuffer.append(key);
            node.endIndex = mixedBuffer.length();
            if (root.child == null) {
                root.child = node;
                root.lastChild = node;
            } else {
                root.lastChild.next = node;
                root.lastChild = node;
            }
        }
        node.child = child;
        node.lastChild = child;
    }
    
    @Override
    public JSONType getType() {
        return JSONType.JSONObject;
    }

    public JSONType getElementType(String key) {
        JSONNode node = optFieldNode(key);
        if (node == null) 
            return JSONType.Null;
        return node.type;
    }
    
    @Override
    protected String serializeElement() {
        return this.serializeElement(0);
    }
    
    @Override
    protected String serializeElement(int indentFactor) {
        return this.serializeElement(indentFactor, 0);
    }

    protected String serializeElement(int indentFactor, int indent) {
        StringBuilder output = new StringBuilder();
        output.append("{");
        
        int newIndent = indent + indentFactor;
        JSONNode pointer = root.child;
        boolean commanate = false;
        while (pointer != null) {
            if (commanate)
                output.append(",");

            if (indentFactor > 0)
                output.append('\n');
            
            appendIndent(output, newIndent);
            
            output.append("\"");
            output.append(pointer.getStringValue());
            output.append("\":");
            if (indentFactor > 0)
                output.append(' ');

            switch (pointer.child.type) {
                case JSONObject:
                    output.append(new JSONObject(pointer.child).serializeElement(indentFactor, newIndent));
                    break;
                case JSONArray:
                    output.append(new JSONArray(pointer.child).serializeElement(indentFactor, newIndent));
                    break;
                case String:
                case EString:
                    output.append("\"");
                    output.append(pointer.child.getRawStringValue());
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
                    output.append(pointer.child.getStringValue());
                    break;
            }
            pointer = pointer.next;
            commanate = true;
        }
        if (indentFactor > 0)
            output.append('\n');
        
        appendIndent(output, indent);
        output.append("}");
        return output.toString();
    }
}
