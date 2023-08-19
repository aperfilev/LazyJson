package localhost.tools.json.resources;

import localhost.tools.json.JSONArray;
import localhost.tools.json.JSONException;
import localhost.tools.json.JSONObject;
import localhost.tools.json.JSONTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NegativeTests {

    @ParameterizedTest
    @MethodSource({"allArrayNegativeTests"})
    void testArraysNegativeTest(String filename, String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONTestUtils.validate(jsonArray);
            fail(String.format("Required exception wasn't thrown in test file '%s'.", filename));
        } catch (JSONException e) {
        }
    }

    @ParameterizedTest
    @MethodSource({"allObjectNegativeTests"})
    void testObjectsNegativeTest(String filename, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONTestUtils.validate(jsonObject);
            fail(String.format("Required exception wasn't thrown in test file '%s'.", filename));
        } catch (JSONException e) {
        }
    }

    private static Stream<Arguments> allArrayNegativeTests() throws IOException {
        return browseAllTestFiles("negative/arrays");
    }

    private static Stream<Arguments> allObjectNegativeTests() throws IOException {
        return browseAllTestFiles("negative/objects");
    }

    private static Stream<Arguments> browseAllTestFiles(String resourceFolder) throws IOException {
        URL resourceUrl = NegativeTests.class.getClassLoader().getResource(resourceFolder);
        if (resourceUrl != null) {
            File resourceFile = new File(resourceUrl.getFile());
            String resourceFolderPath = resourceFile.getAbsolutePath();
            return getFileContents(resourceFolderPath);
        }
        throw new FileNotFoundException(String.format("Resource folder '%s' not found.", resourceFolder));
    }

    private static Stream<Arguments> getFileContents(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath)).filter(Files::isRegularFile) // Only regular files, not directories
                .map(Path::toAbsolutePath).map(path -> {
                    try {
                        return arguments(path.getFileName().toString(), Files.lines(path).collect(Collectors.joining("\n")));
                    } catch (IOException ignored) {
                    }
                    return null;
                });
    }
}
