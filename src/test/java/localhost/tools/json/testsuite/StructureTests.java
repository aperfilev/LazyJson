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

import localhost.tools.json.JSONArray;
import localhost.tools.json.JSONException;
import localhost.tools.json.JSONTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

public class StructureTests {

    @Test
    public void y_structure_whitespace_array() throws JSONException {
        JSONArray elm = new JSONArray("	[]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_structure_whitespace_U_2060_word_joiner() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\u2060]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_whitespace_formfeed() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\f]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_UTF8_BOM_no_data() throws JSONException {
        try {
            JSONArray elm = new JSONArray("å");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_unicode_identifier() throws JSONException {
        try {
            JSONArray elm = new JSONArray("");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_unclosed_object() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{\"asd\":\"asd\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_unclosed_array_unfinished_true() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[ false, tru");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_unclosed_array_unfinished_false() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[ true, fals");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_unclosed_array_partial_null() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[ false, nul");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_unclosed_array() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_uescaped_LF_before_string() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\\u000A\"\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_U_2060_word_joined() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\u2060]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_trailing__() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{\"a\":\"b\"}#{}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_single_star() throws JSONException {
        try {
            JSONArray elm = new JSONArray("*");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_single_eacute() throws JSONException {
        try {
            JSONArray elm = new JSONArray("é");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_open() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"\\{[\"\\{[\"\\{[\"\\{");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_object_string_with_apostrophes() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{'a'");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_object_open_string() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{\"a");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_object_open_array() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{[");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_object_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{,");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_object_close_array() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_object() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_array_string() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"a\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_array_open_string() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"a");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_array_open_object() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[{");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_array_object() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\"" +
                ":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":" +
                "[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[" +
                "{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{\"\":[{" +
                "\"\":[{\"\":");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_array_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[,");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_open_array_apostrophe() throws JSONException {
        try {
            JSONArray elm = new JSONArray("['");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_object_with_trailing_garbage() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{\"a\": true} \"x\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_object_with_comment() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{\"a\":/*comment*/\"b\"}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_object_unclosed_no_value() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{\"\":");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_object_followed_by_closing_object() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{}}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_number_with_trailing_garbage() throws JSONException {
        try {
            JSONArray elm = new JSONArray("2@");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_null_byte_outside_string() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\0]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_no_data() throws JSONException {
        try {
            JSONArray elm = new JSONArray("");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_lone_open_bracket() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_lone_invalid_utf_8() throws JSONException {
        try {
            JSONArray elm = new JSONArray("å");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_incomplete_UTF8_BOM() throws JSONException {
        try {
            JSONArray elm = new JSONArray("ï»{}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_end_array() throws JSONException {
        try {
            JSONArray elm = new JSONArray("]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_double_array() throws JSONException {
        JSONArray elm = new JSONArray("[][]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_structure_comma_instead_of_closing_brace() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{\"x\": true,");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_close_unopened_array() throws JSONException {
        try {
            JSONArray elm = new JSONArray("1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_capitalized_True() throws JSONException {
        JSONArray elm = new JSONArray("[True]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_structure_ascii_unicode_identifier() throws JSONException {
        try {
            JSONArray elm = new JSONArray("aå");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_array_with_unclosed_string() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"asd]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_array_with_extra_array_close() throws JSONException {
        JSONArray elm = new JSONArray("[1]]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_structure_array_trailing_garbage() throws JSONException {
        JSONArray elm = new JSONArray("[1]x");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_structure_angle_bracket_null() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[<null>]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_angle_bracket_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("<.>");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_structure_50_opening_arrays() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_single_space() throws JSONException {
        try {
            JSONArray elm = new JSONArray(" ");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_multidigit_number_then_00() throws JSONException {
        try {
            JSONArray elm = new JSONArray("123\0");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }


    @Test
    public void n_incomplete_true() throws JSONException {
        JSONArray elm = new JSONArray("[tru]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_incomplete_null() throws JSONException {
        JSONArray elm = new JSONArray("[nul]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_incomplete_false() throws JSONException {
        JSONArray elm = new JSONArray("[fals]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_structure_UTF_8_BOM_empty_object() throws JSONException {
        try {
            JSONArray elm = new JSONArray("{}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void i_structure_500_nested_arrays() throws JSONException {
        JSONArray elm = new JSONArray("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_structure_true_in_array() throws JSONException {
        JSONArray elm = new JSONArray("[true]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_structure_trailing_newline() throws JSONException {
        JSONArray elm = new JSONArray("[\"a\"]\n");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_structure_string_empty() throws JSONException {
        try {
            JSONArray elm = new JSONArray("\"\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void y_structure_lonely_true() throws JSONException {
        try {
            JSONArray elm = new JSONArray("true");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void y_structure_lonely_string() throws JSONException {
        try {
            JSONArray elm = new JSONArray("\"asd\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void y_structure_lonely_null() throws JSONException {
        try {
            JSONArray elm = new JSONArray("null");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void y_structure_lonely_negative_real() throws JSONException {
        try {
            JSONArray elm = new JSONArray("-0.1");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void y_structure_lonely_int() throws JSONException {
        try {
            JSONArray elm = new JSONArray("42");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void y_structure_lonely_false() throws JSONException {
        try {
            JSONArray elm = new JSONArray("false");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }
}
