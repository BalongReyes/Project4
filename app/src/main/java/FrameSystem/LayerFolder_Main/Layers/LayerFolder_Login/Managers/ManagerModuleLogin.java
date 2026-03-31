
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Managers;

import EventSystem.Listeners.MouseClickedAdaptor;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Module.ModuleLogin;
import MainSystem.Manager;
import MainSystem.Managers.ManagerLogin;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ManagerModuleLogin extends Manager{
    
    public static ModuleLogin moduleLogin;

    public static void initDefault(){
        moduleLogin = frame.moduleLogin;
        
        ManagerLogin.resetUI();
        frame.layerMain_Login.addLayeredPanelShowListener(evt -> {
            ManagerLogin.resetUI();
            moduleLogin.loginUsernameField.requestFocus();
        });
        
        moduleLogin.loginButton.addMouseListener((MouseClickedAdaptor) evt -> {
            ManagerLogin.loginAccount();
        });
        
        moduleLogin.loginUsernameField.addActionListener(evt -> {
            moduleLogin.loginPasswordField.requestFocus();
        });
        moduleLogin.loginPasswordField.addActionListener(evt -> {
            ManagerLogin.loginAccount();
        });
        
        moduleLogin.loginUsernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt){
                if(!moduleLogin.loginUsernameField.getText().isEmpty()){
                    moduleLogin.loginError1.setVisible(false);
                }
            }
        });
        moduleLogin.loginPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt){
                if(moduleLogin.loginPasswordField.getPassword().length != 0){
                    moduleLogin.loginError2.setVisible(false);
                }
            }
        });
    }
    
}
