package name.seajay.libmuffet.files;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StringHelper {

    public static String quote(String string) {
        return '"' + string + '"';
    }

    public static String delChar(String string, int pos) {
        return string.substring(0, pos) + string.substring(pos + 1);
    }

    public static ArrayList<String> getListFromTextFile(String path) {
        try {
            ArrayList<String> list = (ArrayList<String>) Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
