package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Module;

import ConsoleSystem.Console;
import DatabaseSystem.Database;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LayerLogin;
import java.awt.Color;
import java.beans.BeanProperty;
import javax.swing.Timer;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LoginPanelContainer;
import java.sql.SQLException;



public class ModuleLogin extends LoginPanelContainer {

    public ModuleLogin() {
        initComponents();
    }

// Methods ===================================================================================================

    public void initShowDefaultLayer(){
        try{
            if(Database.getConnection() == null || Database.getConnection().isClosed()){
                offlineMode();
            }else{
                LayerLogin.showLayer(layerLogin_Online);
            }
        }catch(SQLException e){
            Console.errorOut("Initialize show default layer", e);
        }
    }
    
    private Timer refreshTimer;
    
    public final void offlineMode(){
        LayerLogin.showLayer(layerLogin_Offline);
        if(refreshTimer != null && refreshTimer.isRunning()) return;
        refreshTimer = new Timer(5000, (evt) -> { // refreshing connection every 5 seconds
            try{
                Database.openConnection();
                if(Database.getConnection() != null && !Database.getConnection().isClosed()){
                    LayerLogin.showLayer(layerLogin_Online);
                    ((Timer)evt.getSource()).stop();
                }else if(LayerLogin.getCurrentLayeredPanel() != layerLogin_Offline){
                    LayerLogin.showLayer(layerLogin_Offline);
                }
            }catch(SQLException e){
                Console.errorOut("Offline mode error", e);
            }
        });
        refreshTimer.start();
    }
    
// Overrided Methods =========================================================================================
    
    @Override
    @BeanProperty(hidden = true)
    public void setBackground(Color color) {
        super.setBackground(color);
    }

// Generated =================================================================================================
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FrameSystem.SLibrary.SComponents.SPanel sPanelLeft = new FrameSystem.SLibrary.SComponents.SPanel();
        FrameSystem.SLibrary.SComponents.SLabel sLabel25 = new FrameSystem.SLibrary.SComponents.SLabel();
        FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LoginPanel sPanelRight = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LoginPanel();
        javax.swing.JLayeredPane layeredPane_Login = new javax.swing.JLayeredPane();
        layerLogin_Offline = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LayerLogin();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        FrameSystem.SLibrary.SComponents.SLabel sLabel3 = new FrameSystem.SLibrary.SComponents.SLabel();
        FrameSystem.SLibrary.SAnimated.SAnimatedOffline sAnimatedOffline1 = new FrameSystem.SLibrary.SAnimated.SAnimatedOffline();
        layerLogin_Online = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LayerLogin();
        FrameSystem.SLibrary.SComponents.SLabel sLabel1 = new FrameSystem.SLibrary.SComponents.SLabel();
        FrameSystem.SLibrary.SComponents.SLabel sLabel2 = new FrameSystem.SLibrary.SComponents.SLabel();
        loginError1 = new FrameSystem.SLibrary.SComponents.SLabel();
        FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LoginTextFieldContainer loginTextFieldContainer1 = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LoginTextFieldContainer();
        loginUsernameField = new FrameSystem.SLibrary.SComponents.STextField();
        FrameSystem.SLibrary.SComponents.SLabel sLabel4 = new FrameSystem.SLibrary.SComponents.SLabel();
        loginError2 = new FrameSystem.SLibrary.SComponents.SLabel();
        loginTextFieldContainer2 = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LoginTextFieldContainer();
        loginPasswordField = new FrameSystem.SLibrary.SComponents.SPasswordField();
        FrameSystem.SLibrary.SGenericComponents.STogglePassword sTogglePassword1 = new FrameSystem.SLibrary.SGenericComponents.STogglePassword();
        loginError3 = new FrameSystem.SLibrary.SComponents.SLabel();
        loginButton = new FrameSystem.SLibrary.SComponents.SLabelHover();
        layerLogin_Loading = new FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LayerLogin();
        FrameSystem.SLibrary.SAnimated.SAnimatedOffline sAnimatedOffline2 = new FrameSystem.SLibrary.SAnimated.SAnimatedOffline();

        setBackground(new java.awt.Color(255, 209, 209));
        setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/Icons/wave.png"))); // NOI18N
        setRadius(30);
        setRounded(true);
        setShadowColor(new java.awt.Color(230, 230, 230));
        setShadowOpacity(0.3F);
        setShadowSize(5);
        setShadowX(true);
        setShadowY(true);
        setMinimumSize(new java.awt.Dimension(830, 540));
        setPreferredSize(new java.awt.Dimension(830, 540));

        sPanelLeft.setOpaque(false);

        sLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/mainSmallLogo.png"))); // NOI18N

        javax.swing.GroupLayout sPanelLeftLayout = new javax.swing.GroupLayout(sPanelLeft);
        sPanelLeft.setLayout(sPanelLeftLayout);
        sPanelLeftLayout.setHorizontalGroup(
            sPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanelLeftLayout.createSequentialGroup()
                .addGap(0, 34, Short.MAX_VALUE)
                .addComponent(sLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addGap(0, 33, Short.MAX_VALUE))
        );
        sPanelLeftLayout.setVerticalGroup(
            sPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sPanelLeftLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        sPanelRight.setBackground(new java.awt.Color(255, 255, 255));
        sPanelRight.setLine(new java.awt.Color(205, 64, 68));
        sPanelRight.setRadius(30);
        sPanelRight.setRounded(true);

        layeredPane_Login.setPreferredSize(new java.awt.Dimension(500, 530));
        layeredPane_Login.setLayout(new java.awt.CardLayout());

        layerLogin_Offline.setBackground(new java.awt.Color(255, 243, 243));
        layerLogin_Offline.setRadius(30);
        layerLogin_Offline.setRounded(true);
        layerLogin_Offline.setName("Offline"); // NOI18N

        jPanel1.setOpaque(false);

        sLabel3.setForeground(new java.awt.Color(205, 64, 68));
        sLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sLabel3.setText("Database Offline");
        sLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        sAnimatedOffline1.setLineColor(new java.awt.Color(205, 64, 68));
        sAnimatedOffline1.setLineWidth(5.0F);
        sAnimatedOffline1.setOpaque(false);
        sAnimatedOffline1.setPreferredSize(new java.awt.Dimension(160, 160));

        javax.swing.GroupLayout sAnimatedOffline1Layout = new javax.swing.GroupLayout(sAnimatedOffline1);
        sAnimatedOffline1.setLayout(sAnimatedOffline1Layout);
        sAnimatedOffline1Layout.setHorizontalGroup(
            sAnimatedOffline1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );
        sAnimatedOffline1Layout.setVerticalGroup(
            sAnimatedOffline1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(sAnimatedOffline1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addComponent(sLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sAnimatedOffline1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layerLogin_OfflineLayout = new javax.swing.GroupLayout(layerLogin_Offline);
        layerLogin_Offline.setLayout(layerLogin_OfflineLayout);
        layerLogin_OfflineLayout.setHorizontalGroup(
            layerLogin_OfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLogin_OfflineLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layerLogin_OfflineLayout.setVerticalGroup(
            layerLogin_OfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLogin_OfflineLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layeredPane_Login.add(layerLogin_Offline, "card2");

        layerLogin_Online.setBackground(new java.awt.Color(255, 243, 243));
        layerLogin_Online.setRadius(30);
        layerLogin_Online.setRounded(true);
        layerLogin_Online.setName("Online"); // NOI18N

        sLabel1.setForeground(new java.awt.Color(0, 0, 0));
        sLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sLabel1.setText("Login to your account");
        sLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N

        sLabel2.setForeground(new java.awt.Color(56, 56, 56));
        sLabel2.setText("Username");
        sLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N

        loginError1.setForeground(new java.awt.Color(255, 102, 102));
        loginError1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loginError1.setText("*required");
        loginError1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        loginTextFieldContainer1.setBackground(new java.awt.Color(255, 255, 255));
        loginTextFieldContainer1.setFocusedColor(new java.awt.Color(255, 127, 127));
        loginTextFieldContainer1.setForeground(new java.awt.Color(0, 0, 0));
        loginTextFieldContainer1.setRadius(20);
        loginTextFieldContainer1.setTextFieldChild(loginUsernameField);

        loginUsernameField.setBackground(new java.awt.Color(153, 153, 153));
        loginUsernameField.setForeground(new java.awt.Color(0, 0, 0));
        loginUsernameField.setHint("Enter Username");
        loginUsernameField.setHintColor(new java.awt.Color(102, 102, 102));
        loginUsernameField.setHintOffest(26);
        loginUsernameField.setCaretColor(new java.awt.Color(0, 0, 0));
        loginUsernameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout loginTextFieldContainer1Layout = new javax.swing.GroupLayout(loginTextFieldContainer1);
        loginTextFieldContainer1.setLayout(loginTextFieldContainer1Layout);
        loginTextFieldContainer1Layout.setHorizontalGroup(
            loginTextFieldContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginTextFieldContainer1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(loginUsernameField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        loginTextFieldContainer1Layout.setVerticalGroup(
            loginTextFieldContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginTextFieldContainer1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(loginUsernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        sLabel4.setForeground(new java.awt.Color(56, 56, 56));
        sLabel4.setText("Password");
        sLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N

        loginError2.setForeground(new java.awt.Color(255, 102, 102));
        loginError2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loginError2.setText("*required");
        loginError2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        loginTextFieldContainer2.setBackground(new java.awt.Color(255, 255, 255));
        loginTextFieldContainer2.setFocusedColor(new java.awt.Color(255, 127, 127));
        loginTextFieldContainer2.setForeground(new java.awt.Color(0, 0, 0));
        loginTextFieldContainer2.setRadius(20);
        loginTextFieldContainer2.setTextFieldChild(loginPasswordField);

        loginPasswordField.setBackground(new java.awt.Color(153, 153, 153));
        loginPasswordField.setForeground(new java.awt.Color(0, 0, 0));
        loginPasswordField.setHint("Enter Password");
        loginPasswordField.setHintColor(new java.awt.Color(102, 102, 102));
        loginPasswordField.setCaretColor(new java.awt.Color(0, 0, 0));
        loginPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        sTogglePassword1.setDefaultColor(new java.awt.Color(229, 229, 229));
        sTogglePassword1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sTogglePassword1.setHoverColor(new java.awt.Color(193, 193, 193));
        sTogglePassword1.setPasswordField(loginPasswordField);
        sTogglePassword1.setRadius(31);
        sTogglePassword1.setPreferredSize(new java.awt.Dimension(31, 31));

        javax.swing.GroupLayout loginTextFieldContainer2Layout = new javax.swing.GroupLayout(loginTextFieldContainer2);
        loginTextFieldContainer2.setLayout(loginTextFieldContainer2Layout);
        loginTextFieldContainer2Layout.setHorizontalGroup(
            loginTextFieldContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginTextFieldContainer2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(loginPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sTogglePassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        loginTextFieldContainer2Layout.setVerticalGroup(
            loginTextFieldContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginTextFieldContainer2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(loginPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginTextFieldContainer2Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addComponent(sTogglePassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        loginError3.setForeground(new java.awt.Color(255, 102, 102));
        loginError3.setText("Incorrect Password or Username");
        loginError3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        loginButton.setDefaultColor(new java.awt.Color(229, 229, 229));
        loginButton.setForeground(new java.awt.Color(56, 56, 56));
        loginButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginButton.setHoverColor(new java.awt.Color(193, 193, 193));
        loginButton.setRadius(10);
        loginButton.setText("Login");
        loginButton.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        loginButton.setPreferredSize(new java.awt.Dimension(87, 38));

        javax.swing.GroupLayout layerLogin_OnlineLayout = new javax.swing.GroupLayout(layerLogin_Online);
        layerLogin_Online.setLayout(layerLogin_OnlineLayout);
        layerLogin_OnlineLayout.setHorizontalGroup(
            layerLogin_OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLogin_OnlineLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layerLogin_OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layerLogin_OnlineLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(loginError3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layerLogin_OnlineLayout.createSequentialGroup()
                        .addGroup(layerLogin_OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layerLogin_OnlineLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(loginTextFieldContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(sLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loginTextFieldContainer2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layerLogin_OnlineLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layerLogin_OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layerLogin_OnlineLayout.createSequentialGroup()
                                        .addComponent(sLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                                        .addComponent(loginError1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layerLogin_OnlineLayout.createSequentialGroup()
                                        .addComponent(sLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(loginError2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10)))
                        .addGap(60, 60, 60))))
        );
        layerLogin_OnlineLayout.setVerticalGroup(
            layerLogin_OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layerLogin_OnlineLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(sLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layerLogin_OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginError1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(loginTextFieldContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layerLogin_OnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginError2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(loginTextFieldContainer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(loginError3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        layeredPane_Login.setLayer(layerLogin_Online, javax.swing.JLayeredPane.PALETTE_LAYER);
        layeredPane_Login.add(layerLogin_Online, "card1");

        layerLogin_Loading.setBackground(new java.awt.Color(255, 243, 243));
        layerLogin_Loading.setRadius(30);
        layerLogin_Loading.setRounded(true);
        layerLogin_Loading.setName("Loading"); // NOI18N

        sAnimatedOffline2.setLineColor(new java.awt.Color(255, 127, 127));
        sAnimatedOffline2.setLineWidth(5.0F);
        sAnimatedOffline2.setOpaque(false);
        sAnimatedOffline2.setPreferredSize(new java.awt.Dimension(160, 160));

        javax.swing.GroupLayout sAnimatedOffline2Layout = new javax.swing.GroupLayout(sAnimatedOffline2);
        sAnimatedOffline2.setLayout(sAnimatedOffline2Layout);
        sAnimatedOffline2Layout.setHorizontalGroup(
            sAnimatedOffline2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );
        sAnimatedOffline2Layout.setVerticalGroup(
            sAnimatedOffline2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layerLogin_LoadingLayout = new javax.swing.GroupLayout(layerLogin_Loading);
        layerLogin_Loading.setLayout(layerLogin_LoadingLayout);
        layerLogin_LoadingLayout.setHorizontalGroup(
            layerLogin_LoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLogin_LoadingLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sAnimatedOffline2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layerLogin_LoadingLayout.setVerticalGroup(
            layerLogin_LoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layerLogin_LoadingLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sAnimatedOffline2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layeredPane_Login.setLayer(layerLogin_Loading, javax.swing.JLayeredPane.PALETTE_LAYER);
        layeredPane_Login.add(layerLogin_Loading, "card3");

        javax.swing.GroupLayout sPanelRightLayout = new javax.swing.GroupLayout(sPanelRight);
        sPanelRight.setLayout(sPanelRightLayout);
        sPanelRightLayout.setHorizontalGroup(
            sPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(sPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(sPanelRightLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(layeredPane_Login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        sPanelRightLayout.setVerticalGroup(
            sPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
            .addGroup(sPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(sPanelRightLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(layeredPane_Login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(sPanelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sPanelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LayerLogin layerLogin_Loading;
    public FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LayerLogin layerLogin_Offline;
    FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LayerLogin layerLogin_Online;
    public FrameSystem.SLibrary.SComponents.SLabelHover loginButton;
    public FrameSystem.SLibrary.SComponents.SLabel loginError1;
    public FrameSystem.SLibrary.SComponents.SLabel loginError2;
    public FrameSystem.SLibrary.SComponents.SLabel loginError3;
    public FrameSystem.SLibrary.SComponents.SPasswordField loginPasswordField;
    FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Components.LoginTextFieldContainer loginTextFieldContainer2;
    public FrameSystem.SLibrary.SComponents.STextField loginUsernameField;
    // End of variables declaration//GEN-END:variables
}
