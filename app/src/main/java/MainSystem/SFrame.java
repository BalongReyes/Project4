package MainSystem;

import ConsoleSystem.Console;
import ConsoleSystem.ConsoleColors;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import DatabaseSystem.Database;
import EventSystem.Interface.ReconnectExecute;
import FrameSystem.LayerFolder_Main.Components.LayerMain;
import java.sql.SQLException;
import javax.swing.Timer;

public class SFrame extends JFrame {

    public SFrame() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Icons/main.png")).getImage());

        setLocationRelativeTo(null);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setListeners();
    }

// Methods ===================================================================================================

    public void initShowDefaultLayer(){
        LayerMain.showLayer(layerMain_Login);
        moduleLogin.initShowDefaultLayer();
    }
    
    private void setListeners() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((KeyEvent evt) -> {
            if (SFrame.getKeyLock()) {
                return false;
            }
            if (evt.getID() == KeyEvent.KEY_PRESSED) {
                LayerMain.keyPressed(evt);
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_F11 -> {
                        toggleFullscreen();
                    }
                }
            }
            return false;
        });
    }

    private boolean fullscreen = false;

    public void toggleFullscreen(){
        fullscreen = !fullscreen;
        
        dispose();
        setUndecorated(fullscreen);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        moduleHome.menuSwitch_Fullscreen.setActive(fullscreen);
        moduleHome.menuMinSwitch_Fullscreen.setActive(fullscreen);
        
        repaint();
    }

// -----------------------------------------------------------------------------------------------------------
    
    private Timer refreshTimer;
    
    private volatile ReconnectExecute currentReconnectExecute;
    
    public void reconnectMode(String caller, ReconnectExecute reconnectExecute){
        currentReconnectExecute = reconnectExecute;
        
        if(refreshTimer != null && refreshTimer.isRunning()) return;
        refreshTimer = new Timer(5000, (evt) -> { // refreshing connection every 5 seconds
            try{
                Database.openConnection();
                if(Database.getConnection() != null && !Database.getConnection().isClosed()){
                    if(Main.debugDataHandlerRefresh) Console.line().out("RECONNECTION OF " + caller, ConsoleColors.GREEN);
                    currentReconnectExecute.reconnect();
                    ((Timer)evt.getSource()).stop();
                }
            }catch(SQLException e){
                Console.errorOut("Reconnecting failed", e);
            }
        });
        refreshTimer.start();
    }
    
// Static Methods ============================================================================================
    
    private static boolean keyLock = false;

    public static void setKeyLock(boolean keyLock) {
        SFrame.keyLock = keyLock;
    }

    public static boolean getKeyLock() {
        return keyLock;
    }

// Generated =================================================================================================
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layeredPane_Main = new javax.swing.JLayeredPane();
        layerMain_Login = new FrameSystem.LayerFolder_Main.Components.LayerMain();
        moduleLogin = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Module.ModuleLogin();
        layerMain_Home = new FrameSystem.LayerFolder_Main.Components.LayerMain();
        moduleHome = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Module.ModuleHome();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Avida Prime Taft");
        setBackground(new java.awt.Color(9, 12, 16));
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        layeredPane_Main.setLayout(new java.awt.CardLayout());

        layerMain_Login.setBackground(new java.awt.Color(255, 255, 255));
        layerMain_Login.setName("Main"); // NOI18N

        javax.swing.GroupLayout layerMain_LoginLayout = new javax.swing.GroupLayout(layerMain_Login);
        layerMain_Login.setLayout(layerMain_LoginLayout);
        layerMain_LoginLayout.setHorizontalGroup(
            layerMain_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerMain_LoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(moduleLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layerMain_LoginLayout.setVerticalGroup(
            layerMain_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layerMain_LoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(moduleLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layeredPane_Main.add(layerMain_Login, "card1");
        layerMain_Login.getAccessibleContext().setAccessibleName("");

        layerMain_Home.setBackground(new java.awt.Color(255, 247, 247));
        layerMain_Home.setName("Home"); // NOI18N

        javax.swing.GroupLayout layerMain_HomeLayout = new javax.swing.GroupLayout(layerMain_Home);
        layerMain_Home.setLayout(layerMain_HomeLayout);
        layerMain_HomeLayout.setHorizontalGroup(
            layerMain_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerMain_HomeLayout.createSequentialGroup()
                .addComponent(moduleHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layerMain_HomeLayout.setVerticalGroup(
            layerMain_HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerMain_HomeLayout.createSequentialGroup()
                .addComponent(moduleHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        layeredPane_Main.setLayer(layerMain_Home, javax.swing.JLayeredPane.PALETTE_LAYER);
        layeredPane_Main.add(layerMain_Home, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(layeredPane_Main)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane_Main)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Database.closeConnection();
        }catch(Exception e){
        }
        try{
            ExecutorDriver.closeExecutor();
        }catch(Exception e){
        }
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public FrameSystem.LayerFolder_Main.Components.LayerMain layerMain_Home;
    public FrameSystem.LayerFolder_Main.Components.LayerMain layerMain_Login;
    private javax.swing.JLayeredPane layeredPane_Main;
    public FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Module.ModuleHome moduleHome;
    public FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Module.ModuleLogin moduleLogin;
    // End of variables declaration//GEN-END:variables

}
