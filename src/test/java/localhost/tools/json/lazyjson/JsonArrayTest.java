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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class JsonArrayTest {
    @Test
    public void testVeryDeepNestedArray() throws JSONException {
        String str = "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[9" +
                "]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]";
        JSONArray array = new JSONArray(str);
        assertEquals(1, array.length());
    }

    @Test
    public void testLength() throws JSONException {
        String str = "[false,null,true]";
        JSONArray array = new JSONArray(str);
        assertEquals(3, array.length());
    }

    @Test
    public void testHashCode() throws JSONException {
        String str1 = "[false,null,true]";
        String str2 = "[2,[2,2,4],\"foo\"]";
        JSONArray arr1 = new JSONArray(str1);
        JSONArray arr2 = new JSONArray(str2);
        assertNotEquals(arr1.hashCode(), arr2.hashCode());
    }

    @Test
    public void testRemove() throws JSONException {
        String str = "[2,false,null,true,9]";
        JSONArray array = new JSONArray(str);
        array.remove(0);
        assertEquals(4, array.length());
        assertEquals(9, array.getInt(3));
        array.remove(2);
        assertEquals(9, array.getInt(2));
    }

    @Test
    public void testInnerEquals() throws JSONException {
        String str1 = "[9,false,[3,4,5]]";
        String str2 = "[9,false,[3,4,5]]";
        String str3 = "[9,false,[3,4,7]]";
        String str4 = "[9,false,[3,4]]";
        JSONArray array1 = new JSONArray(str1);
        JSONArray array2 = new JSONArray(str2);
        JSONArray array3 = new JSONArray(str3);
        JSONArray array4 = new JSONArray(str4);
        assertTrue(array1.equals(array2));
        assertFalse(array1.equals(array3));
        assertFalse(array1.equals(array4));
    }

    @Test
    public void arrayGet() throws JSONException {
        String str = "[\"foo\",9,true,false,3.1415,null,{\"foo\":42},[2,2,2],\"\\n\"]";
        JSONArray arr = new JSONArray(str);
        assertTrue(arr.get(0) instanceof String);
        assertTrue(arr.get(1) instanceof Long);
        assertTrue(arr.get(2) instanceof Boolean);
        assertTrue(arr.get(3) instanceof Boolean);
        assertTrue(arr.get(4) instanceof Double);
        assertEquals(arr.get(5), null);
        assertTrue(arr.get(6) instanceof JSONObject);
        assertTrue(arr.get(7) instanceof JSONArray);
        assertTrue(arr.get(8) instanceof String);
    }

    @Test
    public void arrayOpt() throws JSONException {
        String str = "[\"foo\",9,true,false,3.1415,null,{\"foo\":42},[2,2,2],\"\\n\"]";
        JSONArray arr = new JSONArray(str);
        assertTrue(arr.opt(0) instanceof String);
        assertTrue(arr.opt(1) instanceof Long);
        assertTrue(arr.opt(2) instanceof Boolean);
        assertTrue(arr.opt(3) instanceof Boolean);
        assertTrue(arr.opt(4) instanceof Double);
        assertEquals(arr.opt(5), null);
        assertTrue(arr.opt(6) instanceof JSONObject);
        assertTrue(arr.opt(7) instanceof JSONArray);
        assertTrue(arr.opt(8) instanceof String);
        assertNull(arr.opt(9));
    }

    @Test(expected = JSONException.class)
    public void testBooleanTypeError() throws JSONException {
        String str = "[\"foo\",-1]";
        JSONArray array = new JSONArray(str);
        array.getBoolean(0);
    }

    @Test(expected = JSONException.class)
    public void notJSONArray() throws JSONException {
        String str = "{\"foo\":-1}";
        JSONArray array = new JSONArray(str);
    }

    @Test
    public void testEmptyLength() throws JSONException {
        String str = "[]";
        JSONArray array = new JSONArray(str);
        assertEquals(0, array.length());
    }

    @Test
    public void testNullValues() throws JSONException {
        String str = "[false,null,true]";
        JSONArray array = new JSONArray(str);
        assertEquals(false, array.getBoolean(0));
        assertTrue(array.isNull(1));
        assertFalse(array.isNull(2));
        assertEquals(true, array.getBoolean(2));
    }

    @Test
    public void testBooleanValues() throws JSONException {
        String str = "[false,42,true]";
        JSONArray array = new JSONArray(str);
        assertEquals(false, array.getBoolean(0));
        assertEquals(42, array.getInt(1));
        assertEquals(true, array.getBoolean(2));
    }

    @Test
    public void testStringValues() throws JSONException {
        String str = "[\"a\",\"\\u0061\"]";
        JSONArray array = new JSONArray(str);
        assertEquals("a", array.getString(0));
        assertEquals("a", array.getString(1));
    }

    @Test
    public void testStringEscapeValues() throws JSONException {
        String str = "[\"\\t99\",\"foo\\r\\n\"]";
        JSONArray array = new JSONArray(str);
        assertEquals("\t99", array.getString(0));
        assertEquals("foo\r\n", array.getString(1));
    }

    @Test
    public void testDoubleValues() throws JSONException {
        String str = "[0.9,3.1415,-3.78,1.2345e+1,1.2345e-1,1.2345E+1,1.2345E-1,2e+1,2e-1,2e1,0.34e-10]";
        JSONArray array = new JSONArray(str);
        assertEquals(0.9, array.getDouble(0), 0);
        assertEquals(3.1415, array.getDouble(1), 0);
        assertEquals(-3.78, array.getDouble(2), 0);
        assertEquals(12.345, array.getDouble(3), 0);
        assertEquals(0.12345, array.getDouble(4), 0);
        assertEquals(12.345, array.getDouble(5), 0);
        assertEquals(0.12345, array.getDouble(6), 0);
        assertEquals(20.0, array.getDouble(7), 0);
        assertEquals(0.2, array.getDouble(8), 0);
        assertEquals(20.0, array.getDouble(9), 0);
    }

    @Test
    public void testIntValues() throws JSONException {
        String str = "[9,0,-378]";
        JSONArray array = new JSONArray(str);
        assertEquals(9, array.getInt(0));
        assertEquals(0, array.getInt(1));
        assertEquals(-378, array.getInt(2));
    }

    @Test
    public void testLongValues() throws JSONException {
        String str = "[9,0,-378]";
        JSONArray array = new JSONArray(str);
        assertEquals(9l, array.getLong(0));
        assertEquals(0l, array.getLong(1));
        assertEquals(-378l, array.getLong(2));
    }

    @Test
    public void testInnerArrayValues() throws JSONException {
        String str = "[[9,0],[\"foo\",-378]]";
        JSONArray array = new JSONArray(str);
        JSONArray a1 = array.getJSONArray(0);
        assertEquals(9, a1.getInt(0));
        assertEquals(0, a1.getInt(1));
        JSONArray a2 = array.getJSONArray(1);
        assertEquals(-378, a2.getInt(1));
    }

    @Test
    public void testObjectValues() throws JSONException {
        String str = "[{\"foo\":\"bar\",\"baz\":{\"test\":9}}]";
        JSONArray array = new JSONArray(str);
        JSONObject obj = array.getJSONObject(0);
        obj = obj.getJSONObject("baz");
        assertNotNull(obj);
        assertEquals(9, obj.getInt("test"));

        str = "[{\"i\":1024,\"b\":2048,\"p\":\"XXXXXXXXXXXXXXXXXXXX\"},{\"i\":2,\"b\":3,\"p\":\"XXXXXXXXXXXXXXXXXXXX\"},{\"i\":1024,\"b\":2048,\"p\":\"XXXXXXXXXXXXXXXXXXXX\"}]";
        array = new JSONArray(str);
        obj = array.getJSONObject(1);
        assertNotNull(obj);
        assertEquals(3, obj.getInt("b"));
    }

    @Test
    public void testArrayValues() throws JSONException {
        String str = "[\"foo\",\"bar\",42]";
        JSONArray array = new JSONArray(str);
        assertEquals("foo", array.getString(0));
        assertEquals(42, array.getInt(2));
    }

    @Test
    public void testNickSample() throws JSONException {
        String str = "[{\"foo\":[{}],\"[]\":\"{}\"}]";
        JSONArray input = new JSONArray(str);
        JSONObject obj = input.getJSONObject(0);
        assertNotNull(obj);
        JSONArray arr = obj.getJSONArray("foo");
        assertNotNull(arr);
        JSONObject obj2 = arr.getJSONObject(0);
        assertNotNull(obj2);
        assertEquals(obj.getString("[]"), "{}");
    }

    @Test
    public void testSerializeStringWithEscapedQuotes() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("\"foo\" bar");
        jsonArray.put("baz");
        assertEquals("[\"\\\"foo\\\" bar\",\"baz\"]", jsonArray.toString());
    }
}