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

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicJSONArrayTest {

    String array_unpretty = "[\"John\",\"Doe\",22,true,73.2,182,[]]";
    
    String array_pretty = "[\n" +
        "    \"John\",\n" +
        "    \"Doe\",\n" +
        "    22,\n" +
        "    true,\n" +
        "    73.2,\n" +
        "    182\n" +
        "]";
    
    String array_uncomplete = "[\n" +
        "    \"John\",\n" +
        "    \"Doe\",\n" +
        "    73.2,\n" +
        "    182\n" +
        "]";
    
    String array_compact = "[\"John\",\"Doe\",22,true,73.2,182]";
    
    String array_single_quoted = "[{'geometry':{'x':-77.036667,'y':38.895111}}]";
    
    String array_no_quots = "[TOTPOP, AVGHHSZ]";
    
    private static final ThreadLocal<DecimalFormat> decimalFormat = ThreadLocal.withInitial(() -> {
        DecimalFormat format = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.US));
        format.setMaximumFractionDigits(Integer.MAX_VALUE);
        return format;
    });

    @Test
    public void testBaseJSONArrayParsing0() throws JSONException {
        JSONArray obj = new JSONArray(array_unpretty);

        assertEquals("John", obj.getString(0));
        assertEquals("Doe", obj.getString(1));
        assertEquals(22, obj.getInt(2));
        assertEquals(true, obj.getBoolean(3));
        assertTrue(Objects.equals(73.2, obj.getDouble(4)));
        assertEquals(182, obj.getInt(5));
    }

    @Test
    public void testBaseJSONArrayParsing1() throws JSONException {       
        JSONArray obj = new JSONArray(array_pretty);

        assertEquals("John", obj.getString(0));
        assertEquals("Doe", obj.getString(1));
        assertEquals(22, obj.getInt(2));
        assertEquals(true, obj.getBoolean(3));
        assertTrue(Objects.equals(73.2, obj.getDouble(4)));
        assertEquals(182, obj.getInt(5));
    }
    
    @Test
    public void testBaseJSONArrayMixed() throws JSONException {       
        JSONArray a = new JSONArray(array_uncomplete);
        a.put(2, 22);
        a.put(3, true);

        assertEquals("John", a.getString(0));
        assertEquals("Doe", a.getString(1));
        assertEquals(22, a.getInt(2));
        assertEquals(true, a.getBoolean(3));
        assertTrue(Objects.equals(73.2, a.getDouble(4)));
        assertEquals(182, a.getInt(5));
        
        assertEquals(array_compact, a.toString());
    }
    
    @Test
    public void testBaseJSONArrayParsing2() throws JSONException {       
        JSONArray obj = new JSONArray(array_compact);

        assertEquals("John", obj.getString(0));
        assertEquals("Doe", obj.getString(1));
        assertEquals(22, obj.getInt(2));
        assertEquals(true, obj.getBoolean(3));
        assertTrue(Objects.equals(73.2, obj.getDouble(4)));
        assertEquals(182, obj.getInt(5));
    }
    
    @Test
    public void testJSONArrayMixedParsing() throws JSONException {       
        JSONArray a = new JSONArray(array_compact);
        a.put(0, "insertion");
        a.put("append");

        assertEquals("insertion", a.getString(0));
        assertEquals("John", a.getString(1));
        assertEquals("Doe", a.getString(2));
        assertEquals(22, a.getInt(3));
        assertEquals(true, a.getBoolean(4));
        assertTrue(Objects.equals(73.2, a.getDouble(5)));
        assertEquals(182, a.getInt(6));
        assertEquals("append", a.getString(7));
    }
    
    @Test
    public void testJSONArrayFormatting() throws JSONException {       
        JSONArray a = new JSONArray(array_compact);

        assertEquals(array_pretty, a.toString(4));
    }
    
    @Test
    public void testJSONArrayFormatting2() throws JSONException {       
        JSONArray a = new JSONArray(array_pretty);

        assertEquals(array_compact, a.toString());
    }
    
    @Test
    public void testDedicatedNumberPutting() throws JSONException {
        JSONObject obj = new JSONObject();
        
        final double d = 1.111;
        Number number = new Number() {
            @Override
            public int intValue() {
                return (int) d;
            }

            @Override
            public long longValue() {
                return (long) d;
            }

            @Override
            public float floatValue() {
                return (float) d;
            }

            @Override
            public double doubleValue() {
                return d;
            }

            @Override
            public String toString() {
                return decimalFormat.get().format(d);
            }
        };
        obj.put("value", number);
        
        assertTrue(obj.getDouble("value") == d);
    }

    @Test
    public void testEmptyArray() throws JSONException {
        JSONArray array = new JSONArray("[]");
    }
    
    @Test
    public void testSingleQuotedJSON() throws JSONException {
        JSONArray array = new JSONArray(array_single_quoted);
        
        JSONObject geometry = array.getJSONObject(0).getJSONObject("geometry");
        
        assertTrue(geometry.getDouble("x") == -77.036667);
        assertTrue(geometry.getDouble("y") == 38.895111);
    }
    
    @Test
    public void testNoQuotedJSON() throws JSONException {
        JSONArray array = new JSONArray(array_no_quots);
        assertEquals(array.length(), 2);
        assertEquals(array.getString(0), "TOTPOP");
        assertEquals(array.getString(1), "AVGHHSZ");
    }

    @Test
    public void testJSONArrayQuotedStringParsing() throws JSONException {
        JSONArray obj = new JSONArray("[\"DE.States\"]");
        assertEquals("DE.States", obj.getString(0));
    }

    @Test
    public void testJSONArraySingleQuotedStringParsing() throws JSONException {
        JSONArray obj = new JSONArray("['DE.States']");
        assertEquals("DE.States", obj.getString(0));
    }

    @Test
    public void testJSONArrayUnQuotedStringParsing() throws JSONException {
        JSONArray obj = new JSONArray("[DE.States]");
        assertEquals("DE.States", obj.getString(0));
    }
}
