package MainSystem;

import FrameSystem.Modules.Frame.SFrame;
import com.formdev.flatlaf.FlatLightLaf;

import DatabaseSystem.Database; // Import FlatLaf

public class Main {

    public static boolean debugDataHandlerRefresh = true;

    public static SFrame frame;

    public static void main(String[] args) {
        Database.openConnection();

        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        frame = new SFrame();
        frame.setVisible(true);
    }

}
