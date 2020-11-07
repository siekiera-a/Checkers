import ui.Frame;

import java.awt.HeadlessException;

public class Main {

    public static void main(String[] args) {
        try {
            new Frame("Warcaby", 800, 800);
        } catch (HeadlessException e) {
            System.out.println("Headless environment detected! Couldn't start the app!");
        }
    }

}
