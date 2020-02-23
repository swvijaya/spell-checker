package blmfltr.splchk.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    public static List<String> readFileIntoList(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }
}
