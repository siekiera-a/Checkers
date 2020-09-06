package record;

import com.google.gson.Gson;
import logic.Field;
import logic.Player;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Recorder {

    private final Gson gson;
    private final Path filePath;
    private final List<Model> moves;

    /**
     * @param filePath path where game progress will be stored
     */
    public Recorder(Path filePath) {
        this.filePath = filePath;
        gson = new Gson();
        moves = new ArrayList<>();
    }

    /**
     * Capture player move
     *
     * @param fields game fields in capture moment
     */
    public void capture(Field[][] fields, Player player) {
        Model model = new Model(System.currentTimeMillis(), fields, player);
        moves.add(model);
    }

    /**
     * save current game state to file
     */
    public void save() {
        try {
            Files.writeString(filePath, gson.toJson(moves));
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Błąd zapisu do pliku!"));
        }
    }
}
