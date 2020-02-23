package blmfltr.splchk.test;

import blmfltr.splchk.util.FileReader;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.List;

public class FileReaderTest {

    @Test
    public void readFileIntoListTest() throws IOException {
        List<String> fileWords = FileReader.readFileIntoList("words_test.txt");
        Assertions.assertNotNull(fileWords);
        Assertions.assertEquals(8, fileWords.size());

    }

    @Test(expected = IOException.class)
    public void readFileIntoListExceptionTest() throws IOException {
        List<String> fileWords = FileReader.readFileIntoList("words_failed.txt");
    }

    @Test
    public void readEmptyFileIntoListTest() throws IOException {
        List<String> fileWords = FileReader.readFileIntoList("empty_words_test.txt");
        Assertions.assertNotNull(fileWords);
        Assertions.assertEquals(0, fileWords.size());
    }
}
