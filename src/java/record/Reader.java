package record;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class Reader {

    private final Gson gson;
    private List<Model> data;

    /**
     * Create moves reader
     *
     * @param filePath path to file with data
     * @throws NoSuchFileException      if file does not exists
     * @throws IllegalArgumentException if file contains invalid data type
     * @throws RuntimeException         if couldn't read data from file
     */
    public Reader(Path filePath) throws NoSuchFileException {
        gson = new Gson();

        if (Files.isRegularFile(filePath)) {
            try {
                data = gson.fromJson(Files.readString(filePath), new TypeToken<List<Model>>() {
                }.getType());
            } catch (JsonSyntaxException e) {
                throw new IllegalArgumentException("File contains invalid data!");
            } catch (IOException e) {
                throw new RuntimeException("Couldn't read data from file!");
            }
        } else {
            throw new NoSuchFileException("File doesn't exists!");
        }
    }

}
