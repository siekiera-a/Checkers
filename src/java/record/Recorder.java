package record;

import com.google.gson.Gson;
import logic.Field;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recorder {

    private final Gson gson;
    private final Path filePath;
    private final SimpleDateFormat formatter;
    private final List<MoveData> moves;
    private boolean gameFinished;

    /**
     * @param filePath path where game progress will be stored
     */
    public Recorder(Path filePath) {
        this.filePath = filePath;
        gson = new Gson();
        formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        moves = new ArrayList<>();
    }

    /**
     * Capture player move
     *
     * @param fields game fields in capture moment
     */
    public void capture(Field[][] fields) {
        new Thread(() -> {
            String move = gson.toJson(fields);
            String currentDate = formatter.format(new Date());
            moves.add(new MoveData(currentDate, move));
            save();
        }).start();
    }

    /**
     * mark current game as ended
     */
    public void finishGame() {
        this.gameFinished = true;
    }

    /**
     * save current game state to file
     */
    private void save() {
        Model model = new Model(gameFinished, moves);
        try {
            Files.writeString(filePath, gson.toJson(model));
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Błąd zapisu do pliku!"));
        }
    }
}
