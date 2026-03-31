
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Managers;

import DatabaseSystem.AccountsData.AccountsDataTable;
import EventSystem.Listeners.MouseClickedAdaptor;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Components.LayerHome;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Module.ModuleHome;
import MainSystem.Manager;
import MainSystem.Managers.ManagerLogin;
import java.awt.event.MouseListener;

public class ManagerModuleHome extends Manager{

    public static ModuleHome moduleHome;
    
    public static void initDefault(){
        moduleHome = frame.moduleHome;
        
        MouseListener m3 = (MouseClickedAdaptor) evt -> {
            LayerHome.showLayer(moduleHome.layerHome_Dashboard);
        };
        moduleHome.sLayerPanelButton1.addMouseListener(m3);
        moduleHome.sPanelHover2.addMouseListener(m3);
        
        // ---------------
        
        MouseListener m1 = (MouseClickedAdaptor) evt -> {
            frame.toggleFullscreen();
        };
        moduleHome.menuSwitch_Fullscreen.addMouseListener(m1);
        moduleHome.menuMinSwitch_Fullscreen.addMouseListener(m1);
        
        moduleHome.sScrollPane1.applyInnerListeners();
        moduleHome.sScrollPane2.applyInnerListeners();
        
        // ---------------

        MouseListener m2 = (MouseClickedAdaptor) evt -> {
            ManagerLogin.logoutAccount();
        };
        moduleHome.logoutButton.addMouseListener(m2);
        moduleHome.logoutButton1.addMouseListener(m2);
        
        // ---------------
        
        moduleHome.sPanelMenu2.setVisible(false);

        moduleHome.sLabelHover1.addMouseListener((MouseClickedAdaptor) evt -> {
            moduleHome.sPanelMenu1.setVisible(false);
            moduleHome.sPanelMenu2.setVisible(true);
        });
        
        moduleHome.sLabelHover2.addMouseListener((MouseClickedAdaptor) evt -> {
            moduleHome.sPanelMenu1.setVisible(true);
            moduleHome.sPanelMenu2.setVisible(false);
        });
        
    }
    
    public static void changeAccount(AccountsDataTable user){
        switch(user.getRole()){
            case 1 -> {
                moduleHome.userRole.setText("Super Admin");
            }
            case 2 -> {
                moduleHome.userRole.setText("Admin");
            }
            case 3 -> {
                moduleHome.userRole.setText("User");
            }
        }
    }

}
