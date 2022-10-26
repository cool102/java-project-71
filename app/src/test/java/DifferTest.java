import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.Differ;
import hexlet.code.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DifferTest {

    public static String getAbsolutePath() {
        return absolutePath;
    }

    private static String absolutePath;

    /**
     *
     */
    @BeforeEach
    public void setup() {
        String path = "src/test/resources";
        File file = new File(path);
        absolutePath = file.getAbsolutePath();

    }

    /**
     * @throws Exception
     */
    @Test
    public void differTestFilesNotExist() throws Exception {
        String file1Name = "/NonExistFile1.json";
        String file2Name = "/NonExistFile2.json";
        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate(absolutePath + file1Name,
                    absolutePath + file2Name);
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("does not exist"));

    }


    /**
     * @throws Exception
     */
    @Test
    public void differTwoJsonFilesTest() throws Exception {
        String file1Name = "/file1.json";
        String file2Name = "/file2.json";
        String dirtyResult = Differ.generate(absolutePath + file1Name, absolutePath + file2Name);
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

    /**
     * @throws Exception
     */
    @Test
    public void differTestWithEmptyFiles() throws Exception {
        String file1Name = "/file111.json";
        String file2Name = "/file222.json";
        String dirtyResult = Differ.generate(absolutePath + file1Name, absolutePath + file2Name);
        String result = dirtyResult.trim().replace(" ", "").replace("\n", "");
        String dirtyExpected = "{}";
        String expected = dirtyExpected.trim().replace(" ", "").replace("\n", "");
        assertEquals(expected, result);
    }


    @Test
    public void relativePathTest() throws Exception {
        String relativePathFile1 = "src/test/resources/file1.json";
        String relativePathFile2 = "src/test/resources/file2.json";
        String dirtyResult = Differ.generate(relativePathFile1, relativePathFile2);
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
    public void differTwoYamlFilesTest() throws Exception {
        String file1Name = "/file1.yml";
        String file2Name = "/file2.yml";
        String dirtyResult = Differ.generate(absolutePath + file1Name, absolutePath + file2Name);
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
