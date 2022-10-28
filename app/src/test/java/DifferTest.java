import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.Differ;
import hexlet.code.Parser;
import hexlet.code.formatters.plain.PlainFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DifferTest {

    private static String absolutePath;
    private static ObjectMapper mapper;

    public static String getAbsolutePath() {
        return absolutePath;
    }

    /**
     *
     */
    @BeforeEach
    public void setup() {
        String path = "src/test/resources";
        File file = new File(path);
        absolutePath = file.getAbsolutePath();
        mapper = new ObjectMapper();

    }

    /**
     * @throws Exception
     */
    @Test
    public void jsonFormatterTestTest() throws Exception {
        String file1Name = "/file01.json";
        String file2Name = "/file02.json";
        String format = "json";
        String json = Differ.generate(absolutePath + file1Name, absolutePath + file2Name, format);
        JsonNode jsonNode = mapper.readTree(json);
        int actual = jsonNode.get("- id").asInt();
        final int expected = 45;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void isDoubleWithOutSignTest() {
        Set<String> set = Set.of("chars1", "- chars2", "  chars2 ", "    - checked ", " "
                        + "     checked ", "    - default ",
                "      default ", "    - id: ", "      id:  ", "    - key1: ",
                "      key2 ", "      numbers1: ", "    - numbers2:  ", "      "
                        + "numbers2:  ", "    - numbers3:  ",
                "      numbers4:  ", "      obj1: ", "    - setting1:  ",
                "      setting1:  ",
                "    - setting2:  ",
                "      setting2:  ",
                "    - setting3:  ",
                "      setting3:  ");
        boolean doubleWithOutSign = PlainFormatter.isDoubleWithOutSign(set, "- chars2");
        Assertions.assertTrue(doubleWithOutSign);

        boolean doubleWithOutSign3 = PlainFormatter.isDoubleWithOutSign(set, "+setting2");
        Assertions.assertTrue(doubleWithOutSign3);

        boolean doubleWithOutSign1 = PlainFormatter.isDoubleWithOutSign(set, "- chars1");
        Assertions.assertFalse(doubleWithOutSign1);


    }

    /**
     * @throws Exception
     */
    @Test
    public void differTestFilesNotExist() throws Exception {
        String file1Name = "/NonExistFile1.json";
        String file2Name = "/NonExistFile2.json";
        String format = "stylish";
        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate(absolutePath + file1Name,
                    absolutePath + file2Name, format);
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("does not exist"));

    }

    /**
     * @throws Exception
     */
    @Test
    public void differNonFlatYamlFilesTest() throws Exception {
        String file1Name = "/file01.yml";
        String file2Name = "/file02.yml";
        String format = "stylish";
        String dirtyResult = Differ.generate(absolutePath + file1Name, absolutePath + file2Name, format);
        String result = dirtyResult.trim().replace(" ", "").replace("\n", "");
        String dirtyExpected = "{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}";
        String expected = dirtyExpected.trim().replace(" ", "").replace("\n", "");
        assertEquals(expected, result);
    }

    /**
     * @throws Exception
     */
    @Test
    public void differNonFlatJsonFilesTest() throws Exception {
        String file1Name = "/file01.json";
        String file2Name = "/file02.json";
        String format = "stylish";
        String dirtyResult = Differ.generate(absolutePath + file1Name, absolutePath + file2Name, format);
        String result = dirtyResult.trim().replace(" ", "").replace("\n", "");
        String dirtyExpected = "{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}";
        String expected = dirtyExpected.trim().replace(" ", "").replace("\n", "");
        assertEquals(expected, result);
    }

    /**
     * @throws Exception
     */
    @Test
    public void differTestWithEmptyFiles() throws Exception {
        String file1Name = "/file111.json";
        String file2Name = "/file222.json";
        String format = "stylish";
        String dirtyResult = Differ.generate(absolutePath + file1Name, absolutePath + file2Name, format);
        String result = dirtyResult.trim().replace(" ", "").replace("\n", "");
        String dirtyExpected = "{}";
        String expected = dirtyExpected.trim().replace(" ", "").replace("\n", "");
        assertEquals(expected, result);
    }


    @Test
    public void relativePathTest() throws Exception {
        String relativePathFile1 = "src/test/resources/file1.json";
        String relativePathFile2 = "src/test/resources/file2.json";
        String format = "stylish";
        String dirtyResult = Differ.generate(relativePathFile1, relativePathFile2, format);
        String result = dirtyResult.trim().replace(" ", "").replace("\n", "");
        String dirtyExpected = "{age:21\n"
                + " +child:true\n"
                + " name:Aigul\n"
                + " -pet:cat\n"
                + " -surname:Unmarried\n"
                + " +surname:Married}";
        String expected = dirtyExpected.trim().replace(" ", "").replace("\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void parserClassTest() {
        ObjectMapper jsonMapper = Parser.getParser("{");
        assertTrue(jsonMapper instanceof ObjectMapper);

        ObjectMapper yamlMapper = Parser.getParser("");
        assertTrue(yamlMapper instanceof YAMLMapper);
    }


}
