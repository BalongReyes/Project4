
package MainSystem.Managers;

import ConsoleSystem.Console;
import DatabaseSystem.AccountsData.AccountsDataHandler;
import DatabaseSystem.AccountsData.AccountsDataTable;
import FrameSystem.LayerFolder_Main.Components.LayerMain;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Components.LayerHome;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Managers.ManagerModuleHome;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Module.ModuleHome;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Module.ModuleLogin;
import MainSystem.Manager;
import java.sql.SQLException;

public class ManagerLogin extends Manager{
    
    public static ModuleLogin moduleLogin;
    public static ModuleHome moduleHome;

    public static void initDefault(){
        moduleLogin = frame.moduleLogin;
        moduleHome = frame.moduleHome;
    }
    
// Methods ===================================================================================================
    
    private static AccountsDataTable accountLoggedIn = null;

    public static AccountsDataTable getAccountLoggedIn(){
        return accountLoggedIn;
    }

    public static void updateAccountLoggedIn(AccountsDataTable updateAccountLoggedIn){
        if(!accountLoggedIn.idEquals(updateAccountLoggedIn.getId())) return;
        accountLoggedIn = updateAccountLoggedIn;
    }
    
    public static boolean isLoggedIn(){
        return accountLoggedIn != null;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    public static void resetUI(){
        moduleLogin.loginError1.setVisible(false);
        moduleLogin.loginError2.setVisible(false);
        moduleLogin.loginError3.setVisible(false);
        moduleLogin.loginUsernameField.setText("");
        moduleLogin.loginPasswordField.setText("");
        moduleLogin.loginPasswordField.showPassword(false);
    }
    
    public static void logoutAccount(){
        accountLoggedIn = null;
        LayerMain.showLayer(frame.layerMain_Login);
        moduleLogin.loginUsernameField.requestFocus();
    }
    
    public static void loginAccount(){
        boolean insufficientInput = false;
        
        if(moduleLogin.loginUsernameField.getText().isEmpty()) insufficientInput = true;
        moduleLogin.loginError1.setVisible(moduleLogin.loginUsernameField.getText().isEmpty());
        
        if(moduleLogin.loginPasswordField.getPassword().length == 0) insufficientInput = true;
        moduleLogin.loginError2.setVisible(moduleLogin.loginPasswordField.getPassword().length == 0);
        
        if(insufficientInput){
            moduleLogin.loginError3.setVisible(false);
            return;
        }
        
        AccountsDataTable accountSuccessLogin = null;
        try{
            accountSuccessLogin = AccountsDataHandler.loginAccount(moduleLogin.loginUsernameField.getText(), moduleLogin.loginPasswordField.getPassword());
        }catch(SQLException e){
            Console.errorOut("Login error", e);
            moduleLogin.offlineMode();
        }
        if(accountSuccessLogin != null){
            accountLoggedIn = accountSuccessLogin;
            LayerMain.showLayer(frame.layerMain_Home);
            LayerHome.showLayer(moduleHome.layerHome_Dashboard);
            resetUI();
            
            ManagerModuleHome.changeAccount(accountLoggedIn);
        }else{
            moduleLogin.loginError3.setVisible(true);
        }
    }

}
