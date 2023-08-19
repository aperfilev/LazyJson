package localhost.tools.json;

import localhost.tools.json.JSONArray;
import localhost.tools.json.JSONException;
import localhost.tools.json.JSONTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NegativeTests {

    @ParameterizedTest
    @MethodSource("allNegativeArrays")
    void testNegativeArraysTest(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONTestUtils.validate(jsonArray);
            fail("Required exception wasn't thrown.");
        } catch (JSONException e) {
        }
    }

    private static Stream<Arguments> allNegativeArrays() {
        return Stream.of(arguments("[1,,2]"));
    }

}
