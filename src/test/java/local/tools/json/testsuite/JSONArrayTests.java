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
package local.tools.json.testsuite;

import local.tools.json.JSONArray;
import local.tools.json.JSONException;
import local.tools.json.JSONTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

public class JSONArrayTests {

    @Test
    public void n_array_comma_and_number() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[,1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_double_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1,,2]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_inner_array_no_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[3[4]]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_missing_value() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[ , \"\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void y_array_with_leading_space() throws JSONException {
        JSONArray elm = new JSONArray(" [1]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_with_trailing_space() throws JSONException {
        JSONArray elm = new JSONArray("[2] ");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_with_several_null() throws JSONException {
        JSONArray elm = new JSONArray("[1,null,null,null,2]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_with_1_and_newline() throws JSONException {
        JSONArray elm = new JSONArray("[1\n]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_null() throws JSONException {
        JSONArray elm = new JSONArray("[null]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_heterogeneous() throws JSONException {
        JSONArray elm = new JSONArray("[null, 1, \"1\", {}]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_false() throws JSONException {
        JSONArray elm = new JSONArray("[false]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_ending_with_newline() throws JSONException {
        JSONArray elm = new JSONArray("[\"a\"]\n");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_empty_string() throws JSONException {
        JSONArray elm = new JSONArray("[\"\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_empty() throws JSONException {
        JSONArray elm = new JSONArray("[]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_array_arraysWithSpaces() throws JSONException {
        JSONArray elm = new JSONArray("[[]   ]");
        JSONTestUtils.validate(elm);
    }


    @Test
    public void n_array_unclosed_with_object_inside() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[{}");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_unclosed_with_new_lines() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1,\n1\n,1");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_unclosed_trailing_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1,");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_unclosed() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_star_inside() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[*]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_spaces_vertical_tab_formfeed() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"\u000Ba\"\\f]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_number_and_several_commas() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1,,]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_number_and_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1,]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_newlines_unclosed() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"a\",\n4\n,1,");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_just_minus() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_just_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[,]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_items_separated_by_semicolon() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1:2]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_invalid_utf8() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[ÿ]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_incomplete_invalid_value() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[x");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_incomplete() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"x\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }


    @Test
    public void n_array_extra_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"\",]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_extra_close() throws JSONException {
        JSONArray elm = new JSONArray("[\"x\"]]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_array_double_extra_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"x\",,]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_comma_after_close() throws JSONException {
        JSONArray elm = new JSONArray("[\"\"],");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_array_colon_instead_of_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\"\": 1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_a_invalid_utf8() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[aå]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_array_1_true_without_comma() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1 true]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }
}
