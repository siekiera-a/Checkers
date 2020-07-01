package record;

import com.google.gson.Gson;
import logic.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {

    private final Gson gson;
    private Model object;

    /**
     * Create moves reader
     *
     * @param filePath path to file with data
     * @throws IOException if file does not exists or json is invalid
     */
    public Reader(Path filePath) throws IOException {
        gson = new Gson();

        if (Files.isRegularFile(filePath)) {
            try {
                String objectString = String.join("", Files.readAllLines(filePath));
                object = gson.fromJson(objectString, Model.class);
            } catch (Exception e) {
                throw new IOException("Couldn't read data from file!");
            }
        }
    }

    /**
     * get saved list of fields
     *
     * @return fields list
     */
    public List<Field[][]> getFields() {
        return object.getMoves().stream()
            .map(move -> gson.fromJson(move.getFields(), Field[][].class))
            .collect(Collectors.toList());
    }

    /**
     * Get game status
     *
     * @return true if game finished, otherwise false
     */
    public boolean isGameFinished() {
        return object.isGameFinished();
    }

}
