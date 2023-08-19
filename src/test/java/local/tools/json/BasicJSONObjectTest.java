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

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicJSONObjectTest {

    String object_unpretty = "{\"name\":\"John\",\"lastname\":\"Doe\",\"age\":22,\"male\":true,\"weight\":73.2,\"height\":182}";

    String object_pretty = "{\n" +
        "    \"name\": \"John\",\n" +
        "    \"lastname\": \"Doe\",\n" +
        "    \"age\": 22,\n" +
        "    \"male\": true,\n" +
        "    \"weight\": 73.2,\n" +
        "    \"height\": 182\n" +
        "}";
    
    String object_compact = "{\"name\":\"John\",\"lastname\":\"Doe\",\"age\":22,\"male\":true,\"weight\":73.2,\"height\":182}";
    
    String object_subobject = "{\n" +
        "    \"name\": \"John\",\n" +
        "    \"lastname\": \"Doe\",\n" +
        "    \"age\": 22,\n" +
        "    \"male\": true,\n" +
        "    \"weight\": 73.2,\n" +
        "    \"height\": 182,\n" +
        "    \"child\": {\n" +
        "        \"name\": \"Mark\",\n" +
        "        \"lastname\": \"Doe\",\n" +
        "        \"age\": 2,\n" +
        "        \"male\": true,\n" +
        "        \"weight\": 3.2,\n" +
        "        \"height\": 45\n" +
        "    },\n" +
        "    \"datasets\": []" +
        "}";
    
    String object_uncomplete = "{\n" +
        "    \"name\": \"John\",\n" +
        "    \"lastname\": \"Doe\",\n" +
        "    \"age\": 22,\n" +
        "    \"male\": true\n" +
        "}";
    
    String object_single_quoted = "{'geometry':{'x':-77.036667,'y':38.895111}}";
    
    String object_no_quots = "{geometry:{x:-77.036667,y:38.895111}}";
    String object_no_quots_2 = "{\"sourceCountry\":KR,\"dataset\":KOR_OPENmate}";
    String object_no_quots_3 = "{sourceCountry:KR,dataset:KOR_OPENmate}";

    @Test
    public void testBaseJSONObjectParsing1() throws JSONException {
        JSONObject obj = new JSONObject(object_unpretty);

        assertEquals("John", obj.getString("name"));
        assertEquals("Doe", obj.getString("lastname"));
        assertEquals(22, obj.getInt("age"));
        assertEquals(true, obj.getBoolean("male"));
        assertTrue(Objects.equals(73.2, obj.getDouble("weight")));
        assertEquals(182, obj.getInt("height"));
    }

    @Test
    public void testBaseJSONObjectParsing() throws JSONException {        
        JSONObject obj = new JSONObject(object_pretty);

        assertEquals("John", obj.getString("name"));
        assertEquals("Doe", obj.getString("lastname"));
        assertEquals(22, obj.getInt("age"));
        assertEquals(true, obj.getBoolean("male"));
        assertTrue(Objects.equals(73.2, obj.getDouble("weight")));
        assertEquals(182, obj.getInt("height"));
    }
    
    @Test
    public void testBaseJSONObjectBuilding() throws JSONException {       
        JSONObject obj = new JSONObject();
        obj.put("name", "John");
        obj.put("lastname", "Doe");
        obj.put("age", 22);
        obj.put("male", true);
        obj.put("weight", 73.2);
        obj.put("height", 182);

        assertEquals("John", obj.getString("name"));
        assertEquals("Doe", obj.getString("lastname"));
        assertEquals(22, obj.getInt("age"));
        assertEquals(true, obj.getBoolean("male"));
        assertTrue(Objects.equals(73.2, obj.getDouble("weight")));
        assertEquals(182, obj.getInt("height"));
    }
    
    @Test
    public void testSubObjectParsing() throws JSONException {       
        JSONObject obj = new JSONObject(object_subobject);

        assertEquals("John", obj.getString("name"));
        assertEquals("Doe", obj.getString("lastname"));
        assertEquals(22, obj.getInt("age"));
        assertEquals(true, obj.getBoolean("male"));
        assertTrue(Objects.equals(73.2, obj.getDouble("weight")));
        assertEquals(182, obj.getInt("height"));
        
        JSONObject child = obj.getJSONObject("child");
        
        assertEquals("Mark", child.getString("name"));
        assertEquals("Doe", child.getString("lastname"));
        assertEquals(2, child.getInt("age"));
        assertEquals(true, child.getBoolean("male"));
        assertTrue(Objects.equals(3.2, child.getDouble("weight")));
        assertEquals(45, child.getInt("height"));
    }
    
    @Test
    public void testBaseJSONObjectMixedBuilding() throws JSONException {               
        JSONObject obj = new JSONObject(object_uncomplete);
        obj.put("weight", 73.2);
        obj.put("height", 182);

        assertEquals("John", obj.getString("name"));
        assertEquals("Doe", obj.getString("lastname"));
        assertEquals(22, obj.getInt("age"));
        assertEquals(true, obj.getBoolean("male"));
        assertTrue(Objects.equals(73.2, obj.getDouble("weight")));
        assertEquals(182, obj.getInt("height"));
        
        assertEquals(object_pretty, obj.toString(4));
    }
    
    @Test
    public void testJSONFormattingPretty() throws JSONException {       
        JSONObject obj = new JSONObject(object_pretty);
        assertEquals(object_pretty, obj.toString(4));
    }
    
    @Test
    public void testJSONFormattingUnpretty() throws JSONException {        
        JSONObject obj = new JSONObject(object_pretty);
        assertEquals(object_compact, obj.toString());
    }
    
    @Test
    public void testJSONFormattingPrettify() throws JSONException {        
        JSONObject obj = new JSONObject(object_compact);
        assertEquals(object_pretty, obj.toString(4));
    }
    
    @Test
    public void testSingleQuotedJSON() throws JSONException {
        JSONObject obj = new JSONObject(object_single_quoted);
        
        JSONObject geometry = obj.getJSONObject("geometry");
        
        assertTrue(geometry.getDouble("x") == -77.036667);
        assertTrue(geometry.getDouble("y") == 38.895111);
    }
    
    @Test
    public void testNoQuotedJSON() throws JSONException {
        JSONObject obj = new JSONObject(object_no_quots);
        
        JSONObject geometry = obj.getJSONObject("geometry");
        
        assertTrue(geometry.getDouble("x") == -77.036667);
        assertTrue(geometry.getDouble("y") == 38.895111);
    }

    @Test
    public void testNoQuotedJSONValues() throws JSONException {
        JSONObject obj = new JSONObject(object_no_quots_2);

        assertEquals(obj.getString("sourceCountry"), "KR");
        assertEquals(obj.getString("dataset"), "KOR_OPENmate");
    }

    @Test
    public void testNoQuotedJSONKeysValues() throws JSONException {
        JSONObject obj = new JSONObject(object_no_quots_3);

        assertEquals(obj.getString("sourceCountry"), "KR");
        assertEquals(obj.getString("dataset"), "KOR_OPENmate");
    }
}

