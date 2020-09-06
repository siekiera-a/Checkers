package record;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class Reader {

    private final Gson gson;
    private final List<Model> data;
    private int cursor;

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

                if (data.stream()
                    .anyMatch(model -> model.getBoard() == null || model.getPlayer() == null || model.getTimestamp() == 0)) {
                    throw new IllegalArgumentException("File contains invalid data!");
                }
            } catch (JsonParseException e) {
                throw new IllegalArgumentException("File contains invalid data!");
            } catch (IOException e) {
                throw new RuntimeException("Couldn't read data from file!");
            }
        } else {
            throw new NoSuchFileException("File doesn't exists!");
        }
    }

    private void trimCursor() {
        if (cursor < 0) {
            cursor = 0;
        } else if (cursor >= data.size()) {
            cursor = data.size() - 1;
        }
    }

    public void nextMove() {
        cursor++;
        trimCursor();
    }

    public void previousMove() {
        cursor--;
        trimCursor();
    }

    public Model getMove() {
        return data.get(cursor);
    }

}
