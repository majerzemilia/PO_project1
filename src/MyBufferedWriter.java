import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyBufferedWriter {

    public BufferedWriter createBufferedWritter(String path) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
    }

}
