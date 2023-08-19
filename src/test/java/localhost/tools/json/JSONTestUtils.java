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

import java.util.Iterator;

public class JSONTestUtils {

    /**
     * This method access value of JSONObject which makes lazy object parser to actually parse its value.
     * @param object
     */
    public static final void validate(JSONObject object) {
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = object.get(key);
            if (value instanceof Boolean) {
                value = object.getBoolean(key);
            } else if (value instanceof Integer) {
                value = object.getInt(key);
            } else if (value instanceof Double) {
                value = object.getDouble(key);
            } else if (value instanceof String) {
                value = object.getString(key);
            } else if (value instanceof JSONObject) {
                value = object.getJSONObject(key);
            } else if (value instanceof JSONArray) {
                value = object.getJSONArray(key);
            }
        }
    }

    /**
     * This method access value of JSONObject which makes lazy object parser to actually parse its value.
     * @param array
     */
    public static final void validate(JSONArray array) {
        for (int i = 0; i < array.length(); ++i) {
            Object value = array.get(i);
            if (value instanceof Boolean) {
                value = array.getBoolean(i);
            } else if (value instanceof Integer) {
                value = array.getInt(i);
            } else if (value instanceof Double) {
                value = array.getDouble(i);
            } else if (value instanceof String) {
                value = array.getString(i);
            } else if (value instanceof JSONObject) {
                value = array.getJSONObject(i);
            } else if (value instanceof JSONArray) {
                value = array.getJSONArray(i);
            }
        }
    }
}
