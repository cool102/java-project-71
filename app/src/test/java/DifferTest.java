import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.Differ;
import hexlet.code.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DifferTest {

    private static String absolutePath;
    private static ObjectMapper mapper;

    public static String getAbsolutePath() {
        return absolutePath;
    }

    @BeforeEach
    public final void setup() {
        String path = "src/test/resources";
        File file = new File(path);
        absolutePath = file.getAbsolutePath();
        mapper = new ObjectMapper();

    }


    @Test
    public final void jsonFormatAndJsonFiles() throws Exception {
        String file1Name = "/file01.json";
        String file2Name = "/file02.json";
        String format = "json";
        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        String json = Differ.generate(fullPath1, fullPath2, format);
        JsonNode jsonNode = mapper.readTree(json);
        String actual = jsonNode.get(0).get("key").asText();
        final String expected = "chars1";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public final void stylishFormatAndJsonFiles() throws Exception {
        String file1Name = "/file01.json";
        String file2Name = "/file02.json";
        String expectedFile = absolutePath + "/expectedStylishFormat.txt";

        String format = "stylish";
        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        String result = Differ.generate(fullPath1, fullPath2, format);

        Path pathToExpected = Paths.get(expectedFile);
        Path normalizePathToExpected = pathToExpected.toAbsolutePath().normalize();
        String expectedContent = Files.readString(normalizePathToExpected);

        assertEquals(expectedContent.replace("\r", ""), result);

    }

    @Test
    public final void plainFormatAndJsonFiles() throws Exception {
        String file1Name = "/file01.json";
        String file2Name = "/file02.json";
        String expectedFile = absolutePath + "/expectedPlainFormat.txt";

        String format = "plain";
        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        String result = Differ.generate(fullPath1, fullPath2, format);

        Path pathToExpected = Paths.get(expectedFile);
        Path normalizePathToExpected = pathToExpected.toAbsolutePath().normalize();
        String expectedContent = Files.readString(normalizePathToExpected);

        assertEquals(expectedContent.replace("\r", ""), result);

    }

    @Test
    public final void jsonFormatAndYmlFiles() throws Exception {
        String file1Name = "/file01.yml";
        String file2Name = "/file02.yml";
        String format = "json";
        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        String json = Differ.generate(fullPath1, fullPath2, format);
        JsonNode jsonNode = mapper.readTree(json);
        String actual = jsonNode.get(0).get("key").asText();
        final String expected = "chars1";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public final void stylishFormatAndYmlFiles() throws Exception {
        String file1Name = "/file01.yml";
        String file2Name = "/file02.yml";
        String expectedFile = absolutePath + "/expectedStylishFormat.txt";

        String format = "stylish";
        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        String result = Differ.generate(fullPath1, fullPath2, format);

        Path pathToExpected = Paths.get(expectedFile);
        Path normalizePathToExpected = pathToExpected.toAbsolutePath().normalize();
        String expectedContent = Files.readString(normalizePathToExpected);

        assertEquals(expectedContent.replace("\r", ""), result);

    }

    @Test
    public final void plainFormatAndYmlFiles() throws Exception {
        String file1Name = "/file01.yml";
        String file2Name = "/file02.yml";
        String expectedFile = absolutePath + "/expectedPlainFormat.txt";

        String format = "plain";
        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        String result = Differ.generate(fullPath1, fullPath2, format);

        Path pathToExpected = Paths.get(expectedFile);
        Path normalizePathToExpected = pathToExpected.toAbsolutePath().normalize();
        String expectedContent = Files.readString(normalizePathToExpected);

        assertEquals(expectedContent.replace("\r", ""), result);

    }

    @Test
    public final void filesNotExist() throws Exception {
        String file1Name = "/NonExistFile1.json";
        String file2Name = "/NonExistFile2.json";
        String format = "stylish";
        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate(fullPath1,
                    fullPath2, format);
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("does not exist"));

    }

    @Test
    public final void yamlExtensionTest() throws Exception {
        String file1Name = "/file01.yaml";
        String file2Name = "/file02.yaml";
        String format = "stylish";
        String expectedFile = absolutePath + "/expectedStylishFormat.txt";

        String fullPath1 = absolutePath + file1Name;
        String fullPath2 = absolutePath + file2Name;
        String result = Differ.generate(fullPath1, fullPath2, format);

        Path pathToExpected = Paths.get(expectedFile);
        Path normalizePathToExpected = pathToExpected.toAbsolutePath().normalize();
        String expectedContent = Files.readString(normalizePathToExpected);

        assertEquals(expectedContent.replace("\r", ""), result);
    }

    @Test
    public void parserClassTest() {
        ObjectMapper jsonMapper = Parser.getParser("/somepath/somefile.json");
        assertTrue(jsonMapper instanceof ObjectMapper);

        ObjectMapper ymlMapper = Parser.getParser("/somepath/somefile.yml");
        assertTrue(ymlMapper instanceof YAMLMapper);

        ObjectMapper yamlMapper = Parser.getParser("/somepath/somefile.yaml");
        assertTrue(yamlMapper instanceof YAMLMapper);
    }


}
