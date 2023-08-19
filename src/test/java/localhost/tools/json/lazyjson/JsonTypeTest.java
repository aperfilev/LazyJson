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
package localhost.tools.json.lazyjson;

import localhost.tools.json.JSONArray;
import localhost.tools.json.JSONException;
import localhost.tools.json.JSONObject;
import localhost.tools.json.JSONType;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;

public class JsonTypeTest {
    @Test
    public void testObjectValueTypes() throws JSONException {
        JSONObject obj = new JSONObject("{\"foo\":42,\"bar\":3.1415,\"baz\":\"Hello World!\",\"baz2\":\"\\n\",\"bonk\":false,\"test\":null,\"obj\":{},\"arr\":[]}");
        assertEquals(obj.getElementType("foo"), JSONType.Integer);
        assertEquals(obj.getElementType("bar"), JSONType.Float);
        assertEquals(obj.getElementType("baz"), JSONType.String);
        assertEquals(obj.getElementType("baz2"), JSONType.EString);
        assertEquals(obj.getElementType("bonk"), JSONType.BooleanFalse);
        assertEquals(obj.getElementType("test"), JSONType.Null);
        assertEquals(obj.getElementType("obj"), JSONType.JSONObject);
        assertEquals(obj.getElementType("arr"), JSONType.JSONArray);
    }

    @Test
    public void testArrayValueTypes() throws JSONException {
        JSONArray arr = new JSONArray("[42,3.1415,\"Hello World!\",false,null,{},[],\"\\n\"]");
        assertEquals(arr.getElementType(0), JSONType.Integer);
        assertEquals(arr.getElementType(1), JSONType.Float);
        assertEquals(arr.getElementType(2), JSONType.String);
        assertEquals(arr.getElementType(3), JSONType.BooleanFalse);
        assertEquals(arr.getElementType(4), JSONType.Null);
        assertEquals(arr.getElementType(5), JSONType.JSONObject);
        assertEquals(arr.getElementType(6), JSONType.JSONArray);
        assertEquals(arr.getElementType(7), JSONType.EString);

    }

    @Test
    public void testValueNaming() throws JSONException {
        assertEquals(JSONType.valueOf("Integer"), JSONType.Integer);
    }
}