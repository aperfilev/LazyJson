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
package local.tools.json.lazyjson;

import local.tools.json.JSONArray;
import local.tools.json.JSONException;
import local.tools.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


public class BadJSONDataTest {

    @Test
    public void testBadNumber1() {
        try {
            String str = "{\"foo\":-f}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadComma1() {
        try {
            String str = "{\"foo\":42,}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadComma2() throws JSONException {
        try {
            String str = "[\"foo\",42,]";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadComma3() {
        try {
            String str = "[],";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadNumber2() {
        try {
            String str = "{\"foo\":-9.f}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadNumber3() {
        try {
            String str = "{\"foo\":-9.1ef}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadNumber4() {
        try {
            String str = "{\"foo\":-9.1e-f}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }


    @Test
    public void testRawValue() {
        try {
            String str = "9";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testUnexpectedEndObjectChar() {
        try {
            String str = "[}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testUnexpectedEndArrayChar() {
        try {
            String str = "{]";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testUnexpectedEndArrayChar2() {
        try {
            String str = "{\"foo\"]";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testMissingFieldValue() {
        try {
            String str = "{\"foo\":}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testMissingField() {
        try {
            String str = "{\"foo\"}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadFieldValue1() throws JSONException {
        String str = "{\"foo\":trye}";
        JSONObject obj = new JSONObject(str);
    }

    @Test
    public void testBadFieldValue2() throws JSONException {
        String str = "{\"foo\":nil}";
        JSONObject obj = new JSONObject(str);
    }

    @Test
    public void testBadFieldValue3() throws JSONException {
        String str = "{\"foo\":folse}";
        JSONObject obj = new JSONObject(str);
    }

    @Test
    public void testBadField() throws JSONException {
        String str = "{42:\"foo\"}";
        JSONObject obj = new JSONObject(str);
    }

    @Test
    public void testMissingFieldForArray() {
        try {
            String str = "{[\"foo\"]}";
            JSONObject obj = new JSONObject(str);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testHalfArray2() throws JSONException {
        String str = "[]]";
        JSONArray obj = new JSONArray(str);
    }

    @Test
    public void testBadType() {
        try {
            String str = "[9,4]";
            JSONArray obj = new JSONArray(str);
            obj.getJSONObject(0);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadOptType() throws JSONException {
        String str = "[9,4]";
        JSONArray obj = new JSONArray(str);
        assertNull(obj.optJSONObject(0));
    }


    @Test
    public void testBadAType() {
        try {
            String str = "[9,4]";
            JSONArray obj = new JSONArray(str);
            obj.getJSONArray(0);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadOptAType() throws JSONException {
        String str = "[9,4]";
        JSONArray obj = new JSONArray(str);
        assertNull(obj.optJSONArray(0));
    }


    @Test
    public void testBadObjectType() throws JSONException {
        try {
            String str = "{\"foo\":4}";
            JSONObject obj = new JSONObject(str);
            obj.getJSONObject("foo");
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testBadOptObjectType() throws JSONException {
        String str = "{\"foo\":4}";
        JSONObject obj = new JSONObject(str);
        assertNull(obj.optJSONObject("foo"));
    }

    @Test
    public void testBadValueType() throws JSONException {
        try {
            String str = "{\"foo\":\"bar\"}";
            JSONObject obj = new JSONObject(str);
            obj.getLong("foo");
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testJSONException() {
        String str = "{{\"foo\":4}";
        try {
            JSONObject obj = new JSONObject(str);
            obj.getString("bar");
        } catch (JSONException e) {
        }
    }

    @Test
    public void testJSONException2() {
        String str = "{\"foo\":}";
        try {
            JSONObject obj = new JSONObject(str);
            obj.getString("bar");
        } catch (JSONException e) {
        }
    }
}