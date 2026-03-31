package MainSystem;

import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Managers.ManagerModuleUnits;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Managers.ManagerObjectUnits;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Managers.ManagerModuleHome;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Managers.ManagerModuleLogin;
import FrameSystem.LayerFolder_Main.Managers.ManagerFilters;
import MainSystem.Managers.ManagerLogin;

public class Manager {

    protected static SFrame frame;

    public static void setDefault(SFrame frame) {
        Manager.frame = frame;
        
        ManagerLogin.initDefault();
        ManagerModuleLogin.initDefault();
        ManagerModuleHome.initDefault();
        
        ManagerModuleUnits.initDefault();
        ManagerObjectUnits.initDefault();
        ManagerFilters.initDefault();
    }

}
