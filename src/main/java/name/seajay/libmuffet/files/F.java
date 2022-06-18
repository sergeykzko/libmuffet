package name.seajay.libmuffet.files;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class F {
    public final static String SEP = File.separator.equals("/") ? "/" : "\\";

    public static void writeString(String path, String string) throws IOException {
        File file = new File(path);
        if (file.getParent() != null && !new File(file.getParent()).exists()) {
            mkdirs(file.getParentFile().getPath());
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(string.getBytes());
        outputStream.close();
    }

    public static void touch(String path) throws IOException {
        long timestamp = System.currentTimeMillis();
        touch(new File(path), timestamp);
    }

    public static void touch(File file, long timestamp) throws IOException {
        if (!file.exists()) {
            new FileOutputStream(file).close();
        }

        file.setLastModified(timestamp);
    }

    public static String readFromResAsString(Class target_class, String inResPath) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(target_class.getClassLoader().getResourceAsStream(inResPath), StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        String str;
        while ((str = reader.readLine()) != null) {
            sb.append(str + '\n');
        }
        return sb.toString();
    }

    public static String[] ls(String dir) {
        File f = new File(dir);
        return f.isDirectory() ? f.list() : null;
    }

    public static void mkdirs(String dir) throws IOException {
        if (!new File(dir).mkdirs())
            throw new IOException("Could not create " + StringHelper.quote(dir) + " directory");
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static String readAsString(String path) throws IOException {
        return readFileAsStingUTF8(path);
    }

    private static byte[] readAllBytesJava7(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    private static String readFileAsStingUTF8(String path) {
        StringBuilder str = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)
        ) {


            String s;
            while ((s = reader.readLine()) != null) {
                str.append(s).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public static String getFirstDir(String path) {
        assert path != null;
        File f = new File(path);
        File last = null;
        do {
            f = f.getParentFile();
            if (f != null) last = f;
        } while (f != null);
        assert last != null;
        return last.getName();
    }

    public static String clearSubpath(File fullPath, File newSubRoot) {
        return fullPath.getAbsolutePath().substring(newSubRoot.getAbsolutePath().length() + 1);
    }

    public static String readAsString(File f) throws IOException {
        return readAsString(f.getPath());
    }
}
