package MainSystem;

import com.formdev.flatlaf.FlatLightLaf;

import DatabaseSystem.Database; // Import FlatLaf

public class Main {

    public static boolean debugDataHandlerRefresh = true;
    public static boolean coloredOutput = true;

    public static SNextFrame frame;

    public static void main(String[] args) {
        Database.openConnection();

        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

//        frame = new SFrame();
//        frame.setVisible(true);
//
//        Manager.setDefault(frame);
//        frame.initShowDefaultLayer();
//        frame.setVisible(true);
        
        frame = new SNextFrame();
        frame.setVisible(true);

        frame.initShowDefaultLayer();
        frame.setVisible(true);
    }

}
