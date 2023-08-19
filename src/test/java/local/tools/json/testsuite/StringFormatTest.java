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

public class StringFormatTest {

    @Test
    public void y_string_with_del_character() throws JSONException {
        JSONArray elm = new JSONArray("[\"a\u007Fa\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_utf8() throws JSONException {
        JSONArray elm = new JSONArray("[\"€\uD834\uDD1E\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicodeEscapedBackslash() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u005C\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_U_FFFE_nonchar() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uFFFE\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_U_FDD0_nonchar() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uFDD0\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_U_2064_invisible_plus() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u2064\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_U_200B_ZERO_WIDTH_SPACE() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u200B\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_U_1FFFE_nonchar() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD83F\\uDFFE\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_U_10FFFE_nonchar() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uDBFF\\uDFFE\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_escaped_double_quote() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u0022\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode_2() throws JSONException {
        JSONArray elm = new JSONArray("[\"⍂㈴⍂\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unicode() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uA66D\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_unescaped_char_delete() throws JSONException {
        JSONArray elm = new JSONArray("[\"\u007F\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_uescaped_newline() throws JSONException {
        JSONArray elm = new JSONArray("[\"new\\u000Aline\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_uEscape() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u0061\\u30af\\u30EA\\u30b9\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_u_2029_par_sep() throws JSONException {
        JSONArray elm = new JSONArray("[\"\u2029\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_u_2028_line_sep() throws JSONException {
        JSONArray elm = new JSONArray("[\"\u2028\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_two_byte_utf_8() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u0123\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_three_byte_utf_8() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u0821\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_surrogates_U_1D11E_MUSICAL_SYMBOL_G_CLEF() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD834\\uDd1e\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_space() throws JSONException {
        JSONArray elm = new JSONArray("[\" \"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_simple_ascii() throws JSONException {
        JSONArray elm = new JSONArray("[\"asd \"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_reservedCharacterInUTF_8_U_1BFFF() throws JSONException {
        JSONArray elm = new JSONArray("[\"\uD82F\uDFFF\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_pi() throws JSONException {
        JSONArray elm = new JSONArray("[\"π\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_one_byte_utf_8() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u002c\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_null_escape() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u0000\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_nonCharacterInUTF_8_U_FFFF() throws JSONException {
        JSONArray elm = new JSONArray("[\"\uFFFF\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_nonCharacterInUTF_8_U_10FFFF() throws JSONException {
        JSONArray elm = new JSONArray("[\"\uDBFF\uDFFF\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_nbsp_uescaped() throws JSONException {
        JSONArray elm = new JSONArray("[\"new\\u00A0line\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_last_surrogates_1_and_2() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uDBFF\\uDFFF\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_in_array_with_leading_space() throws JSONException {
        JSONArray elm = new JSONArray("[ \"asd\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_in_array() throws JSONException {
        JSONArray elm = new JSONArray("[\"asd\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_escaped_noncharacter() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uFFFF\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_escaped_control_character() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u0012\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_double_escape_n() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\\\n\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_double_escape_a() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\\\a\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_comments() throws JSONException {
        JSONArray elm = new JSONArray("[\"a/*b*/c/*d//e\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_backslash_doublequotes() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\\"\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_backslash_and_u_escaped_zero() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\\\u0000\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_allowed_escapes() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\\"\\\\\\/\\b\\f\\n\\r\\t\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_accepted_surrogate_pairs() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\ud83d\\ude39\\ud83d\\udc8d\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_accepted_surrogate_pair() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD801\\udc37\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_string_1_2_3_bytes_UTF_8_sequences() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\u0060\\u012a\\u12AB\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void string_with_escaped_NULL() throws JSONException {
        JSONArray elm = new JSONArray("[\"A\\u0000B\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void string_3_invalid_codepoints() throws JSONException {
        JSONArray elm = new JSONArray("[\"���������\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void string_3_escaped_invalid_codepoints() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD800\\uD800\\uD800\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void string_2_invalid_codepoints() throws JSONException {
        JSONArray elm = new JSONArray("[\"������\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void string_2_escaped_invalid_codepoints() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD800\\uD800\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void string_1_invalid_codepoint() throws JSONException {
        JSONArray elm = new JSONArray("[\"���\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void string_1_escaped_invalid_codepoint() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD800\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_string_with_trailing_garbage() {
        try {
            JSONArray elm = new JSONArray("\"\"x");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_unicode_CapitalU() {
        try {
            JSONArray elm = new JSONArray("\"\\UA66D\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_unescaped_tab() throws JSONException {
        JSONArray elm = new JSONArray("[\"\t\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_string_unescaped_newline() {
        try {
            JSONArray elm = new JSONArray("n_string_unescaped_newline");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_start_escape_unclosed() {
        try {
            JSONArray elm = new JSONArray("[\"\\");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_single_string_no_double_quotes() {
        try {
            JSONArray elm = new JSONArray("abc");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_single_quote() throws JSONException {
        JSONArray elm = new JSONArray("['single quote']");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_string_single_doublequote() {
        try {
            JSONArray elm = new JSONArray("\"");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_no_quotes_with_bad_escape() {
        try {
            JSONArray elm = new JSONArray("[\\n]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_leading_uescaped_thinspace() {
        try {
            JSONArray elm = new JSONArray("[\\u0020\"asd\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_invalid_utf8_after_escape() {
        try {
            JSONArray elm = new JSONArray("[\"\\å\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_invalid_unicode_escape() {
        try {
            JSONArray elm = new JSONArray("[\"\\uqqqq\"]");
            elm.getString(0);
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_invalid_backslash_esc() {
        try {
            JSONArray elm = new JSONArray("[\"\\a\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_invalid_utf_8_in_escape() {
        try {
            JSONArray elm = new JSONArray("[\"\\uå\"]");
            elm.getString(0);
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_incomplete_surrogate_escape_invalid() {
        try {
            JSONArray elm = new JSONArray("[\"\\uD800\\uD800\\x\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_incomplete_escaped_character() {
        try {
            JSONArray elm = new JSONArray("[\"\\u00A\"]");
            elm.getString(0);
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_incomplete_escape() {
        try {
            JSONArray elm = new JSONArray("[\"\\\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_escaped_emoji() {
        try {
            JSONArray elm = new JSONArray("[\"\\\uD83C\uDF00\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_escaped_ctrl_char_tab() {
        try {
            JSONArray elm = new JSONArray("[\"\\\t\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_escaped_backslash_bad() {
        try {
            JSONArray elm = new JSONArray("[\"\\\\\\\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_escape_x() {
        try {
            JSONArray elm = new JSONArray("[\"\\x00\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_backslash_00() {
        try {
            JSONArray elm = new JSONArray("[\"\\");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_accentuated_char_no_quotes() {
        try {
            JSONArray elm = new JSONArray("[é]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_1_surrogate_then_escape_u1() {
        try {
            JSONArray elm = new JSONArray("[\"\\uD800\\u1\"]");
            elm.getString(0);
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_1_surrogate_then_escape_u() {
        try {
            JSONArray elm = new JSONArray("[\"\\uD800\\u\"]");
            elm.getString(0);
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_string_1_surrogate_then_escape() {
        try {
            JSONArray elm = new JSONArray("[\"\\uD800\\\"]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void i_string_UTF8_surrogate_U_D800() throws JSONException {
        JSONArray elm = new JSONArray("[\"���\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_utf16LE_no_BOM() throws JSONException {
        JSONArray elm = new JSONArray("[\"é\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_utf16BE_no_BOM() throws JSONException {
        JSONArray elm = new JSONArray("[\"é\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_UTF_8_invalid_sequence() throws JSONException {
        JSONArray elm = new JSONArray("[\"日ш�\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_UTF_16LE_with_BOM() throws JSONException {
        JSONArray elm = new JSONArray("[\"é\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_truncated_utf_8() throws JSONException {
        JSONArray elm = new JSONArray("[\"ая\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_overlong_sequence_6_bytes_null() throws JSONException {
        JSONArray elm = new JSONArray("[\"������\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_overlong_sequence_6_bytes() throws JSONException {
        JSONArray elm = new JSONArray("[\"������\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_overlong_sequence_2_bytes() throws JSONException {
        JSONArray elm = new JSONArray("[\"��\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_not_in_unicode_range() throws JSONException {
        JSONArray elm = new JSONArray("[\"����\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_lone_utf8_continuation_byte() throws JSONException {
        JSONArray elm = new JSONArray("[\"\u0081\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_lone_second_surrogate() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uDFAA\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_iso_latin_1() throws JSONException {
        JSONArray elm = new JSONArray("[\"é\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_inverted_surrogates_U_1D11E() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uDd1e\\uD834\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_invalid_utf_8() throws JSONException {
        JSONArray elm = new JSONArray("[\"ÿ\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_invalid_surrogate() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\ud800abc\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_invalid_lonely_surrogate() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\ud800\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_incomplete_surrogates_escape_valid() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD800\\uD800\\n\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_incomplete_surrogate_pair() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uDd1ea\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_incomplete_surrogate_and_escape_valid() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD800\\n\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_1st_valid_surrogate_2nd_invalid() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uD888\\u1234\"]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_string_1st_surrogate_but_2nd_missing() throws JSONException {
        JSONArray elm = new JSONArray("[\"\\uDADA\"]");
        JSONTestUtils.validate(elm);
    }
}
