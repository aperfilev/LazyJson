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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

public class NumberFormatTest {

    @Test
    public void number_10000000000000000999() throws JSONException {
        JSONArray elm = new JSONArray("[10000000000000000999]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void number_1000000000000000() throws JSONException {
        JSONArray elm = new JSONArray("[1000000000000000]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void number_1e6() throws JSONException {
        JSONArray elm = new JSONArray("[1E6]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void number_1e_999() throws JSONException {
        JSONArray elm = new JSONArray("[1E-999]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void number_1_000000000000000005() throws JSONException {
        JSONArray elm = new JSONArray("[1.000000000000000005]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void number_1_0() throws JSONException {
        JSONArray elm = new JSONArray("[1.0]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_simple_real() throws JSONException {
        JSONArray elm = new JSONArray("[123.456789]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_simple_int() throws JSONException {
        JSONArray elm = new JSONArray("[123]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_real_pos_exponent() throws JSONException {
        JSONArray elm = new JSONArray("[1e+2]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_real_neg_exp() throws JSONException {
        JSONArray elm = new JSONArray("[1e-2]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_real_fraction_exponent() throws JSONException {
        JSONArray elm = new JSONArray("[123.456e78]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_real_exponent() throws JSONException {
        JSONArray elm = new JSONArray("[123e45]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_real_capital_e_pos_exp() throws JSONException {
        JSONArray elm = new JSONArray("[1E+2]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_real_capital_e_neg_exp() throws JSONException {
        JSONArray elm = new JSONArray("[1E-2]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_real_capital_e() throws JSONException {
        JSONArray elm = new JSONArray("[1E22]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_negative_zero() throws JSONException {
        JSONArray elm = new JSONArray("[-0]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_negative_one() throws JSONException {
        JSONArray elm = new JSONArray("[-1]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_minus_zero() throws JSONException {
        JSONArray elm = new JSONArray("[-0]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_int_with_exp() throws JSONException {
        JSONArray elm = new JSONArray("[20e1]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_double_close_to_zero() throws JSONException {
        JSONArray elm = new JSONArray("[-0.000000000000000000000000000000000000000000000000000000000000000000000000000001]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_after_space() throws JSONException {
        JSONArray elm = new JSONArray("[ 4]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_0e1() throws JSONException {
        JSONArray elm = new JSONArray("[0e1]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number_0e_1() throws JSONException {
        JSONArray elm = new JSONArray("[0e+1]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void y_number() throws JSONException {
        JSONArray elm = new JSONArray("[123e65]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_number_with_leading_zero() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[012]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_with_alpha_char() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1.8011670033376514H-308]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_with_alpha() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1.2a-3]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_U_FF11_fullwidth_digit_one() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[\\uFF11]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_starting_with_dot() throws JSONException {
        JSONArray elm = new JSONArray("[.123]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_number_real_without_fractional_part() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1.]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_real_with_invalid_utf8_after_e() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1eå]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_real_garbage_after_e() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1ea]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_neg_with_garbage_at_end() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-1x]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_neg_real_without_int_part() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-.123]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_neg_int_starting_with_zero() throws JSONException {
        try {
            JSONArray elm = new JSONArray("");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    @Disabled("In current implementation supported to have unquoted strings. This json treated as valid.")
    public void n_number_NaN() throws JSONException {
        JSONArray elm = new JSONArray("[NaN]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_number_minus_space_1() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[- 1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_minus_sign_with_trailing_garbage() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-foo]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_minus_infinity() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-Infinity]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_invalid__() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0e+-1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_invalid_utf_8_in_int() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0å]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_invalid_utf_8_in_exponent() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1e1å]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_invalid_utf_8_in_bigger_int() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[123å]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_invalid_negative_real() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-123.123foo]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_infinity() throws JSONException {
        JSONArray elm = new JSONArray("[Infinity]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_number_Inf() throws JSONException {
        JSONArray elm = new JSONArray("[Inf]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_number_hex_2_digits() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0x42]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_hex_1_digit() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0x1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_expression() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1+2]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_9_e_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[9.e+]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_2_e3() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[2.e3]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_2_e_3() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[2.e+3]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_2_e_3_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[2.e-3]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_1eE2() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1eE2]");
            elm.getDouble(0);
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_1_000() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1 000.0]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_1_0e_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1.0e+]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_1_0e__() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1.0e-]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_1_0e() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[1.0e]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0e_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0e+]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0e() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0e]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0_capital_E_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0E+]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0_capital_E() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0E]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0_e1() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0.e1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0_3e_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0.3e+]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0_3e() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0.3e]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number_0_1_2() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[0.1.2]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number__Inf() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[+Inf]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number__1() throws JSONException {
        JSONArray elm = new JSONArray("[+1]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_number___() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[++1234]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number__2e_3() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[.2e-3]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number___1() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[.-1]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number__NaN() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-NaN]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number__2_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-2.]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number__1_0_() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-1.0.]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void n_number__01() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-01]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    @Test
    public void i_number_pos_double_huge_exp() throws JSONException {
        JSONArray elm = new JSONArray("[1.5e+9999]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_double_huge_neg_exp() throws JSONException {
        JSONArray elm = new JSONArray("[123.456e-789]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_huge_exp() throws JSONException {
        JSONArray elm = new JSONArray("[0.4e00669999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999969999999006]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_neg_int_huge_exp() throws JSONException {
        JSONArray elm = new JSONArray("[-1e+9999]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_real_neg_overflow() throws JSONException {
        JSONArray elm = new JSONArray("[-123123e100000]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_real_pos_overflow() throws JSONException {
        JSONArray elm = new JSONArray("[123123e100000]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_real_underflow() throws JSONException {
        JSONArray elm = new JSONArray("[123e-10000000]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_too_big_neg_int() throws JSONException {
        JSONArray elm = new JSONArray("[-123123123123123123123123123123]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_too_big_pos_int() throws JSONException {
        JSONArray elm = new JSONArray("[100000000000000000000]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void i_number_very_big_negative_int() throws JSONException {
        JSONArray elm = new JSONArray("[-237462374673276894279832749832423479823246327846]");
        JSONTestUtils.validate(elm);
    }

    @Test
    public void n_number_minus_01() throws JSONException {
        try {
            JSONArray elm = new JSONArray("[-01]");
            JSONTestUtils.validate(elm);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }
}