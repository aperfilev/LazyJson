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
import localhost.tools.json.JSONElement;
import localhost.tools.json.JSONException;
import localhost.tools.json.JSONObject;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.*;

public class JsonObjectTest {
    @Test
    public void JSONElementTest() throws JSONException {
        String str = "  [9]";
        JSONElement e = JSONElement.parse(str);
        assertNotNull(e);
    }

    @Test(expected = JSONException.class)
    public void badJSONElementTest() throws JSONException {
        String str = "  9";
        JSONElement e = JSONElement.parse(str);
    }

    @Test
    public void keysetTest() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":42}";
        JSONObject obj = new JSONObject(str);
        Set<String> keys = obj.keySet();
        assertTrue(keys.contains("foo"));
        assertTrue(keys.contains("baz"));
        assertFalse(keys.contains("bar"));
    }

    @Test
    public void keysetWithNullTest() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":42,\"test\":null}";
        JSONObject obj = new JSONObject(str);
        Set<String> keys = obj.keySet();
        assertTrue(keys.contains("foo"));
        assertTrue(keys.contains("baz"));
        assertFalse(keys.contains("bar"));
    }

    @Test
    public void innerEqualsTest() throws JSONException {
        String str1 = "{\"foo\":\"bar\",\"baz\":42}";
        String str2 = "{\"foo\":\"bar\",\"baz\":42}";
        String str3 = "{\"foo\":9,\"baz\":42}";
        String str4 = "{\"baz\":42}";
        String str5 = "{\"foo2\":\"bar\",\"baz\":42}";
        String str6 = "{\"foo\":null,\"baz\":42}";
        JSONObject obj1 = new JSONObject(str1);
        JSONObject obj2 = new JSONObject(str2);
        JSONObject obj3 = new JSONObject(str3);
        JSONObject obj4 = new JSONObject(str4);
        JSONObject obj5 = new JSONObject(str5);
        JSONObject obj6 = new JSONObject(str6);
        assertTrue(obj1.equals(obj2));
        assertFalse(obj1.equals(obj3));
        assertFalse(obj1.equals(obj4));
        assertFalse(obj1.equals(obj5));
        assertFalse(obj1.equals(obj6));
        assertFalse(obj1.equals("foo"));
        assertFalse(obj1.equals(new JSONArray()));
    }

    @Test
    public void ryanTestSample2() throws JSONException {
        String str = "{\"bundle_id\": null, \"application_id\": \"foo\"}";
        JSONObject obj = new JSONObject(str);
        assertNull(obj.getString("bundle_id"));
    }

    @Test(expected = JSONException.class)
    public void testNonObject() throws JSONException {
        String str = "[{\"foo\":42}]";
        JSONObject obj = new JSONObject(str);
    }

    @Test(expected = JSONException.class)
    public void testMissingFields() throws JSONException {
        String str = "{\"foo\":42}";
        JSONObject obj = new JSONObject(str);
        obj.getString("bar");
    }

    @Test
    public void objectGet() throws JSONException {
        String str = "{\"foo\":9,\"bar\":true,\"baz\":3.1415,\"sval\":\"hello world\",\"fval\":false,\"nval\":null,\"aval\":[2,2,4],\"oval\":{\"foo\":42},\"eval\":\"\\n\"}";
        JSONObject obj = new JSONObject(str);
        assertTrue(obj.get("foo") instanceof Long);
        assertTrue(obj.get("bar") instanceof Boolean);
        assertTrue(obj.get("baz") instanceof Double);
        assertTrue(obj.get("sval") instanceof String);
        assertTrue(obj.get("fval") instanceof Boolean);
        assertEquals(obj.get("nval"), null);
        assertTrue(obj.get("aval") instanceof JSONArray);
        assertTrue(obj.get("oval") instanceof JSONObject);
        assertTrue(obj.get("eval") instanceof String);
    }

    @Test
    public void objectOpt() throws JSONException {
        String str = "{\"foo\":9,\"bar\":true,\"baz\":3.1415,\"sval\":\"hello world\",\"fval\":false,\"nval\":null,\"aval\":[2,2,4],\"oval\":{\"foo\":42},\"eval\":\"\\n\"}";
        JSONObject obj = new JSONObject(str);
        assertTrue(obj.opt("foo") instanceof Long);
        assertTrue(obj.opt("bar") instanceof Boolean);
        assertTrue(obj.opt("baz") instanceof Double);
        assertTrue(obj.opt("sval") instanceof String);
        assertTrue(obj.opt("fval") instanceof Boolean);
        assertEquals(obj.opt("nval"), null);
        assertTrue(obj.opt("aval") instanceof JSONArray);
        assertTrue(obj.opt("oval") instanceof JSONObject);
        assertTrue(obj.opt("eval") instanceof String);
        assertNull(obj.opt("does-not-exist"));
    }


    @Test
    public void optionalArrayTest() throws JSONException {
        String str = "{\"foo\":[],\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        assertNull(obj.optJSONArray("bar"));
        assertNull(obj.optJSONArray("baz"));
        assertNotNull(obj.optJSONArray("foo"));
    }

    @Test
    public void optionalBooleanTest() throws JSONException {
        String str = "{\"foo\":true,\"bar\":null,\"off\":false}";
        JSONObject obj = new JSONObject(str);
        assertEquals(obj.optBoolean("badonk"), false);
        assertEquals(obj.optBoolean("bar"), false);
        assertEquals(obj.optBoolean("foo"), true);
        assertEquals(obj.optBoolean("foo", false), true);
        assertEquals(obj.optBoolean("bar", true), true);
        assertEquals(obj.optBoolean("baz", true), true);
        assertEquals(obj.optBoolean("off", true), false);
    }

    @Test
    public void optionalIntTest() throws JSONException {
        String str = "{\"foo\":43,\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        assertEquals(obj.optInt("bar"), 0);
        assertEquals(obj.optInt("foo"), 43);
        assertEquals(obj.optInt("foo", 44), 43);
        assertEquals(obj.optInt("bar", 44), 44);
        assertEquals(obj.optInt("baz", 44), 44);
        assertEquals(obj.optInt("baz"), 0);
    }

    @Test
    public void optionalLongTest() throws JSONException {
        String str = "{\"foo\":43,\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        assertEquals(obj.optLong("bar"), 0l);
        assertEquals(obj.optLong("foo"), 43l);
        assertEquals(obj.optLong("baz"), 0l);
        assertEquals(obj.optLong("foo", 44l), 43l);
        assertEquals(obj.optLong("bar", 44l), 44l);
        assertEquals(obj.optLong("baz", 44l), 44l);
    }

    @Test
    public void optionalObjectTest() throws JSONException {
        String str = "{\"foo\":{},\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        assertNull(obj.optJSONObject("bar"));
        assertNull(obj.optJSONObject("baz"));
        assertNotNull(obj.optJSONObject("foo"));
    }

    @Test
    public void optionalNonObjectTest() throws JSONException {
        String str = "{\"foo\":22,\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        assertNull(obj.optJSONObject("foo"));
    }

    @Test(expected = JSONException.class)
    public void getNonObjectTest() throws JSONException {
        String str = "{\"foo\":22,\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        obj.getJSONObject("foo");
    }

    @Test
    public void optionalNonArrayTest() throws JSONException {
        String str = "{\"foo\":22,\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        assertNull(obj.optJSONArray("foo"));
    }

    @Test(expected = JSONException.class)
    public void getNonArrayTest() throws JSONException {
        String str = "{\"foo\":22,\"bar\":null}";
        JSONObject obj = new JSONObject(str);
        obj.getJSONArray("foo");
    }

    @Test
    public void testRyansSample() throws JSONException {
        String str = "{\"data\":{\"blah\":9},\"header\":{}}";
        JSONObject obj = new JSONObject(str);
        assertTrue(obj.has("header"));
        assertTrue(obj.has("data"));
        assertEquals(obj.toString(), str);
    }

    @Test
    public void testDeepNesting() throws JSONException {
        String str = "{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":{\"foo\":42}}}}}}}}}}}}}}}}";
        JSONObject obj = new JSONObject(str);
        for (int i = 0; i < 15; i++) {
            obj = obj.getJSONObject("foo");
            assertNotNull(obj);
        }
        assertEquals(42, obj.getInt("foo"));
    }

    @Test
    public void testHas() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":{\"key\":42}}";
        JSONObject obj = new JSONObject(str);
        assertTrue(obj.has("foo"));
        assertFalse(obj.has("bar"));
        assertTrue(obj.has("baz"));
        assertFalse(obj.has("key"));
        assertFalse(obj.has("random"));
    }

    @Test
    public void testKeys() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":{\"key\":42}}";
        JSONObject obj = new JSONObject(str);
        Iterator<String> it = obj.keys();
        assertTrue(it.hasNext());
        assertEquals("foo", it.next());
        assertTrue(it.hasNext());
        assertEquals("baz", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testEmptyKeys() throws JSONException {
        String str = "{}";
        JSONObject obj = new JSONObject(str);
        Iterator<String> it = obj.keys();
        assertFalse(it.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testKeysRemove() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":{\"key\":42}}";
        JSONObject obj = new JSONObject(str);
        Iterator<String> it = obj.keys();
        assertTrue(it.hasNext());
        it.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoMoreKeys() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":{\"key\":42}}";
        JSONObject obj = new JSONObject(str);
        Iterator<String> it = obj.keys();
        assertTrue(it.hasNext());
        it.next();
        it.next();
        it.next();
        it.next();
    }

    @Test
    public void testLength() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":{\"key\":42}}";
        JSONObject obj = new JSONObject(str);
        assertEquals(2, obj.length());
        assertEquals(2, obj.length());
        str = "{}";
        obj = new JSONObject(str);
        assertEquals(0, obj.length());
    }

    @Test
    public void testStringFields() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":\"\",\"bonk\":\"\\t\\b\\r\\n\\\\\\\"\\f\"}";
        JSONObject obj = new JSONObject(str);
        String value = obj.getString("foo");
        assertNotNull(value);
        assertEquals(value, "bar");
        value = obj.getString("baz");
        assertNotNull(value);
        assertEquals(value, "");
        assertEquals(obj.getString("bonk"), "\t\b\r\n\\\"\f");
    }

    @Test
    public void testIntegerFields() throws JSONException {
        String str = "{\"foo\":999,\"bar\":0,\"baz\":42,\"bonk\":-378}";
        JSONObject obj = new JSONObject(str);
        assertEquals(999, obj.getInt("foo"));
        assertEquals(0, obj.getInt("bar"));
        assertEquals(42, obj.getInt("baz"));
        assertEquals(-378, obj.getInt("bonk"));
    }

    @Test
    public void testLongFields() throws JSONException {
        String str = "{\"foo\":999,\"bar\":0,\"baz\":42,\"bonk\":-378,\"crazy\":12147483647}";
        JSONObject obj = new JSONObject(str);
        assertEquals(999, obj.getLong("foo"));
        assertEquals(0, obj.getLong("bar"));
        assertEquals(42, obj.getLong("baz"));
        assertEquals(-378, obj.getLong("bonk"));
        assertEquals(12147483647l, obj.getLong("crazy"));

    }

    @Test
    public void testDoubleFields() throws JSONException {
        String str = "{\"foo\":3.1415,\"bar\":0.0,\"baz\":1.2345e+1,\"bonk\":-3.78}";
        JSONObject obj = new JSONObject(str);
        assertEquals(3.1415d, obj.getDouble("foo"), 0);
        assertEquals(0.0, obj.getDouble("bar"), 0);
        assertEquals(12.345, obj.getDouble("baz"), 0);
        assertEquals(-3.78, obj.getDouble("bonk"), 0);
    }

    @Test
    public void testBooleanFields() throws JSONException {
        String str = "{\"foo\":false,\"bar\":true}";
        JSONObject obj = new JSONObject(str);
        assertEquals(false, obj.getBoolean("foo"));
        assertEquals(true, obj.getBoolean("bar"));
    }

    @Test(expected = JSONException.class)
    public void testNonBooleanFields() throws JSONException {
        String str = "{\"foo\":false,\"bar\":42}";
        JSONObject obj = new JSONObject(str);
        obj.getBoolean("bar");
    }

    @Test
    public void testNullFields() throws JSONException {
        String str = "{\"foo\":null,\"bar\":42}";
        JSONObject obj = new JSONObject(str);
        assertEquals(true, obj.isNull("foo"));
        assertEquals(false, obj.isNull("bar"));
    }

    @Test
    public void testObjectSpaces() throws JSONException {
        String str = " {    \"foo\" :\"bar\" ,   \"baz\":  42}   ";
        JSONObject obj = new JSONObject(str);
        assertEquals("bar", obj.getString("foo"));
        assertEquals(42, obj.getInt("baz"));
    }

    @Test
    public void testObjectTabs() throws JSONException {
        String str = "\t{\t\"foo\"\t:\"bar\"\t,\t\t\"baz\":\t42\t}\t";
        JSONObject obj = new JSONObject(str);
        assertEquals("bar", obj.getString("foo"));
        assertEquals(42, obj.getInt("baz"));
    }

    @Test
    public void testNestedObjects() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":{\"test\":9}}";
        JSONObject obj = new JSONObject(str);
        obj = obj.getJSONObject("baz");
        assertNotNull(obj);
        assertEquals(9, obj.getInt("test"));
    }

    @Test
    public void testDeepNestedObjects() throws JSONException {
        String str = "{\"foo\":\"bar\",\"baz\":{\"test\":9,\"test2\":{\"id\":100},\"second\":33}}";
        JSONObject obj = new JSONObject(str);
        obj = obj.getJSONObject("baz");
        assertNotNull(obj);
        assertEquals(9, obj.getInt("test"));
        obj = obj.getJSONObject("test2");
        assertNotNull(obj);
        assertEquals(100, obj.getInt("id"));
    }

    @Test
    public void testJSONOrgSample1() throws JSONException {
        String str = "{\n    \"glossary\": {\n        \"title\": \"example glossary\",\n        \"GlossDiv\": {\n            \"title\": \"S\",\n            \"GlossList\": {\n                \"GlossEntry\": {\n                    \"ID\": \"SGML\",\n                    \"SortAs\": \"SGML\",\n                    \"GlossTerm\": \"Standard Generalized Markup Language\",\n                    \"Acronym\": \"SGML\",\n                    \"Abbrev\": \"ISO 8879:1986\",\n                    \"GlossDef\": {\n                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n                        \"GlossSeeAlso\": [\"GML\", \"XML\"]\n                    },\n                    \"GlossSee\": \"markup\"\n                }\n            }\n        }\n    }}";
        JSONObject obj = new JSONObject(str);
        JSONObject glo = obj.getJSONObject("glossary");
        assertNotNull(glo);
        assertEquals("example glossary", glo.getString("title"));
    }

    @Test
    public void testJSONOrgSample2() throws JSONException {
        String str = "{\"menu\": {\n  \"id\": \"file\",\n  \"value\": \"File\",\n  \"popup\": {\n    \"menuitem\": [\n      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n    ]\n  }\n}}";
        JSONObject obj = new JSONObject(str);
        JSONObject m = obj.getJSONObject("menu");
        assertNotNull(m);
        assertEquals("file", m.getString("id"));
        m = m.getJSONObject("popup");
        assertNotNull(m);
        JSONArray a = m.getJSONArray("menuitem");
        assertNotNull(a);
        JSONObject o = a.getJSONObject(1);
        assertNotNull(o);
        assertEquals("Open", o.getString("value"));
    }

    @Test
    public void testComplexObject() throws Exception {
        String str = "{" +
                "\"Type\":\"rating\"," +
                "\"IsDisabled\":false," +
                "\"EventID\":\"deadbeef-dead-beef-dead-beef00000001\"," +
                "\"Record\":{" +
                "\"Item\":{" +
                "\"ID\":2983980," +
                "\"Rating\":5," +
                "\"Type\":null" +
                "}," +
                "\"User\":{" +
                "\"ID\":478830012," +
                "\"First\":\"Ben\"," +
                "\"Last\":\"Boolean\"," +
                "\"Email\":\"foo@test.local\"," +
                "\"Title\":\"Chief Blame Officer\"," +
                "\"Company\":\"DoubleDutch\"," +
                "\"Department\":null" +
                "}" +
                "}" +
                "}";

        JSONObject obj = new JSONObject(str);
        JSONObject record = obj.getJSONObject("Record");
        assertNotNull(record);
        JSONObject item = record.getJSONObject("Item");
        assertEquals(item.getInt("ID"), 2983980);
        assertEquals("rating", obj.getString("Type"));
        JSONObject user = record.getJSONObject("User");
        assertNotNull(user);
        assertEquals("Ben", user.getString("First"));
        assertEquals("DoubleDutch", user.getString("Company"));
        assertTrue(user.isNull("Department"));
    }
}