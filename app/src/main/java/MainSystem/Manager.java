package MainSystem;

import FrameSystem.Modules.Frame.SFrame;

public class Manager {

    protected static SFrame frame;

    public static void setDefault(SFrame frame) {
        Manager.frame = frame;
    }

}
