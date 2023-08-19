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
package localhost.tools.json.jettison;

import localhost.tools.json.JSONObject;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class JSONObjectTest extends TestCase {

    @Test
    public void testEquals() throws Exception {
        JSONObject aJsonObj = new JSONObject("{\"x\":\"y\"}");
        JSONObject bJsonObj = new JSONObject("{\"x\":\"y\"}");
        assertEquals(aJsonObj,bJsonObj);
    }

    @Test
    public void testToLong() throws Exception {
        String json = "{\"key\":\"10001325703114005\"}";
        JSONObject jsonObject = new JSONObject(json);
        long actual = jsonObject.getLong("key");
        long expected = 10001325703114005L;
        assertTrue(expected < Long.MAX_VALUE);
        assertEquals(expected, actual);
    }

    @Test
    public void testNotEquals() throws Exception {
        JSONObject aJsonObj = new JSONObject("{\"x\":\"y\"}");
        JSONObject bJsonObj = new JSONObject("{\"x\":\"b\"}");
        assertTrue(!aJsonObj.equals(bJsonObj));
    }

    @Test
    public void testNullInQuotesGetString() throws Exception {
        JSONObject obj = new JSONObject("{\"a\":\"null\"}");
        assertEquals("null", obj.getString("a"));
    }

    @Test
    public void testExplicitNullGetString() throws Exception {
        JSONObject obj = new JSONObject("{\"a\":null}");
        assertNull(obj.getString("a"));
    }

    @Test
    public void testExplicitNullIsNull() throws Exception {
        JSONObject obj = new JSONObject("{\"a\":null}");
        assertTrue(obj.isNull("a"));
    }

    @Test
    public void testMissingIsNull() throws Exception {
        JSONObject obj = new JSONObject("{\"a\":null}");
        assertTrue(obj.isNull("b"));
    }
}