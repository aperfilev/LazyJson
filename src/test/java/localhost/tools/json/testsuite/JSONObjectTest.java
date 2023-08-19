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
package localhost.tools.json.testsuite;

import localhost.tools.json.JSONException;
import localhost.tools.json.JSONObject;
import localhost.tools.json.JSONTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

public class JSONObjectTest {

    @Test
    public void y_object_with_newlines() throws JSONException {
        JSONObject elm = new JSONObject("{\n\"a\": \"b\"\n}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_string_unicode() throws JSONException {
        JSONObject elm = new JSONObject("{\"title\":\"\\u041f\\u043e\\u043b\\u0442\\u043e\\u0440\\u0430 \\u0417\\u0435\\u043c\\u043b\\u0435\\u043a\\u043e\\u043f\\u0430\" }");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_simple() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":[]}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_long_strings() throws JSONException {
        JSONObject elm = new JSONObject("{\"x\":[{\"id\": \"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"}], \"id\": \"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_extreme_numbers() throws JSONException {
        JSONObject elm = new JSONObject("{ \"min\": -1.0e+28, \"max\": 1.0e+28 }");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_escaped_null_in_key() throws JSONException {
        JSONObject elm = new JSONObject("{\"foo\\u0000bar\": 42}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_empty_key() throws JSONException {
        JSONObject elm = new JSONObject("{\"\":0}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_empty() throws JSONException {
        JSONObject elm = new JSONObject("{}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_duplicated_key_and_value() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":\"b\",\"a\":\"b\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_duplicated_key() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":\"b\",\"a\":\"c\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object_basic() throws JSONException {
        JSONObject elm = new JSONObject("{\"asd\":\"sdf\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_object() throws JSONException {
        JSONObject elm = new JSONObject("{\"asd\":\"sdf\", \"dfg\":\"fgh\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void object_same_key_unclear_values() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":0, \"a\":-0}\n");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void object_same_key_same_value() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":1,\"a\":1}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void object_same_key_different_values() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":1,\"a\":2}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void object_key_nfd_nfc() throws JSONException {
        JSONObject elm = new JSONObject("{\"é\":\"NFD\",\"é\":\"NFC\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void object_key_nfc_nfd() throws JSONException {
        JSONObject elm = new JSONObject("{\"é\":\"NFC\",\"é\":\"NFD\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_with_trailing_garbage() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":\"b\"}#");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_with_single_string() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{ \"foo\" : \"bar\", \"a\" }");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_unterminated_value() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"a\":\"a");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_unquoted_key() throws JSONException {
        JSONObject elm = new JSONObject("{a: \"b\"}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_two_commas_in_a_row() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"a\":\"b\",,\"c\":\"d\"}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_trailing_comment_slash_open_incomplete() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":\"b\"}/");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_trailing_comment_slash_open() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":\"b\"}//");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_trailing_comment_open() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":\"b\"}/**//");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_trailing_comment() throws JSONException {
        JSONObject elm = new JSONObject("{\"a\":\"b\"}/**/");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_trailing_comma() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"id\":0,}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_single_quote() throws JSONException {
        JSONObject elm = new JSONObject("{'a':0}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_several_trailing_commas() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"id\":0,,,,,}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_repeated_null_null() throws JSONException {
        JSONObject elm = new JSONObject("{null:null,null:null}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_non_string_key_but_huge_number_instead() throws JSONException {
        JSONObject elm = new JSONObject("{9999E9999:1}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_non_string_key() throws JSONException {
        JSONObject elm = new JSONObject("{1:1}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_no_colon() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"a\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_missing_value() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"a\":");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_missing_semicolon() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"a\" \"b\"}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_missing_key() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{:\"b\"}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_missing_colon() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"a\" b}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_lone_continuation_byte_in_key_and_trailing_comma() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"¹\":\"0\",}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_key_with_single_quotes() throws JSONException {
        JSONObject elm = new JSONObject("{key: 'value'}");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_object_garbage_at_end() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"a\":\"a\" 123}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_emoji() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\uD83C\uDDE8\uD83C\uDDED}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_double_colon() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"x\"::\"b\"}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_comma_instead_of_colon() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{\"x\", null}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_bracket_key() throws JSONException {
        try {
            JSONObject elm = new JSONObject("{[: \"x\"}\n");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_object_bad_value() throws JSONException {
        try {
            JSONObject elm = new JSONObject("[\"x\", truth]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void i_object_key_lone_2nd_surrogate() throws JSONException {
        JSONObject elm = new JSONObject("{\"\\uDFAA\":0}");
        JSONTestUtils.validate(elm);
    }
}
