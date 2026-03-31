package MainSystem;

import ConsoleSystem.Console;
import ConsoleSystem.ConsoleColors;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import DatabaseSystem.Database;
import EventSystem.Interface.ReconnectExecute;
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
//        LayerMain.showLayer(layerMain_Login);
//        moduleLogin.initShowDefaultLayer();
    }
    
    private void setListeners() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((KeyEvent evt) -> {
            if (SFrame.getKeyLock()) {
                return false;
            }
            if (evt.getID() == KeyEvent.KEY_PRESSED) {
//                LayerMain.keyPressed(evt);
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

//        moduleHome.menuSwitch_Fullscreen.setActive(fullscreen);
//        moduleHome.menuMinSwitch_Fullscreen.setActive(fullscreen);
        
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Avida Prime Taft");
        setBackground(new java.awt.Color(9, 12, 16));
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1158, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
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
    // End of variables declaration//GEN-END:variables

}
