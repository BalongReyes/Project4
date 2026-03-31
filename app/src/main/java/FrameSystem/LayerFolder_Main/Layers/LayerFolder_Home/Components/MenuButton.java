package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.beans.BeanProperty;
import java.beans.JavaBean;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import FrameSystem.SLibrary.SAbstractComponents.SLayerButton;
import MainSystem.CustomGraphics;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

@JavaBean(description = "A component that displays a jpanel as a layered panel button")
public class MenuButton extends SLayerButton implements ActionListener{

    private Timer timer;
    
    public MenuButton(){
        super();
        super.setRounded(true);
        
        initComponents();
        sLabel1.addMouseListener(hoverListener);
        
        timer = new Timer(1, this);
    }
    
// ===========================================================================================================
    
    private int currentLineHeight = 20;
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(active){
            if(currentLineHeight > 9){
                currentLineHeight--;
            }else{
                timer.stop();
            }
        }else{
            if(currentLineHeight < (getHeight()/ 2)){
                currentLineHeight++;
            }else{
                timer.stop();
            }
        }
        repaint();
    }
    
// Setters and Getters =======================================================================================

    @Override
    public void setActive(boolean active){
        timer.start();
        if(this.active == active) return;
        if(active){
            sLabel1.setForeground(activeForegroundColor);
            sLabel1.setIcon(activeIcon);
        }else{
            sLabel1.setForeground(inactiveForegroundColor);
            sLabel1.setIcon(inactiveIcon);
        }
        super.setActive(active);
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private Color activeLineColor = new Color(255, 255, 255);

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setActiveLineColor(Color activeLineColor){
        this.activeLineColor = activeLineColor;
    }

    public Color getActiveLineColor(){
        return activeLineColor;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private Color activeForegroundColor = Color.white;

    @BeanProperty(preferred = true, visualUpdate = true, description = "The active foreground color")
    public void setActiveForegroundColor(Color activeForegroundColor){
        this.activeForegroundColor = activeForegroundColor;
        if(active){
            setForeground(activeForegroundColor);
            sLabel1.setForeground(activeForegroundColor);
        }
    }

    public Color getActiveForegroundColor(){
        return activeForegroundColor;
    }

    private Color inactiveForegroundColor = Color.white;

    @BeanProperty(preferred = true, visualUpdate = true, description = "The inactive foreground color")
    public void setInactiveForegroundColor(Color inactiveForegroundColor){
        this.inactiveForegroundColor = inactiveForegroundColor;
        if(!active){
            setForeground(inactiveForegroundColor);
            sLabel1.setForeground(inactiveForegroundColor);
        }
    }

    public Color getInactiveForegroundColor(){
        return inactiveForegroundColor;
    }
    
    @Override
    @BeanProperty(hidden = true)
    public void setForeground(Color fg){
        super.setForeground(fg);
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private Icon activeIcon = null;

    @BeanProperty(preferred = true, visualUpdate = true, description = "The active icon")
    public void setActiveIcon(ImageIcon activeIcon){
        Image resizedActiveIcon = activeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        this.activeIcon = new ImageIcon(resizedActiveIcon);
        if(active){
            sLabel1.setIcon(this.activeIcon);
        }
    }

    public Icon getActiveIcon(){
        return activeIcon;
    }
    
    private Icon inactiveIcon = null;

    @BeanProperty(preferred = true, visualUpdate = true, description = "The inactive icon")
    public void setInactiveIcon(ImageIcon inactiveIcon){
        Image resizedInactiveIcon = inactiveIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        this.inactiveIcon = new ImageIcon(resizedInactiveIcon);
        if(!active){
            sLabel1.setIcon(this.inactiveIcon);
        }
    }

    public Icon getInactiveIcon(){
        return inactiveIcon;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    public String text = "";

    @BeanProperty(preferred = true, visualUpdate = true, description = "The text of panel")
    public void setText(String text){
        this.text = text;
        sLabel1.setText(text);
    }

    public String getText(){
        return text;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private MenuMinButton minButton = null;
    
    @BeanProperty(preferred = true, visualUpdate = true, description = "The minimize button of itself")
    public void setManuMinButton(MenuMinButton minButton){
        this.minButton = minButton;
        if(layerPanelMouseListener != null){
            minButton.addLayeredPanelMouseListener(layerPanelMouseListener);
        }
    }
    
    public MenuMinButton getSideBarMinButton(){
        return minButton;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private int notificationCount = 0;

    @BeanProperty(preferred = true, visualUpdate = true, description = "Notification count to display")
    public void setNotificationCount(int notificationCount){
        this.notificationCount = notificationCount;
        if(minButton != null){
            minButton.setNotification(notificationCount != 0);
        }
    }

    public int getNotificationCount(){
        return notificationCount;
    }
    
    private static Color notificationForegroundColor = new Color(0,173,0);

    @BeanProperty(preferred = true, visualUpdate = true, description = "The notification foreground color")
    public void setNotificationForegroundColor(Color notificationForegroundColor){
        MenuButton.notificationForegroundColor = notificationForegroundColor;
    }

    public Color getNotificationForegroundColor(){
        return notificationForegroundColor;
    }
    
    private static Color activeNotificationColor = new Color(242,242,242);

    @BeanProperty(preferred = true, visualUpdate = true, description = "Notification active color to display")
    public void setActiveNotificationColor(Color activeNotificationColor){
        MenuButton.activeNotificationColor = activeNotificationColor;
    }

    public Color getActiveNotificationColor(){
        return activeNotificationColor;
    }
    
    private static Color inactiveNotificationColor = new Color(239,239,239);

    @BeanProperty(preferred = true, visualUpdate = true, description = "Notification inactive color to display")
    public void setInactiveNotificationColor(Color inactiveNotificationColor){
        MenuButton.inactiveNotificationColor = inactiveNotificationColor;
    }

    public Color getInactiveNotificationColor(){
        return inactiveNotificationColor;
    }
    
// -----------------------------------------------------------------------------------------------------------

    @Override
    @BeanProperty(hidden = true)
    public void setRounded(boolean rounded){
        super.setRounded(rounded);
    }
    
// ===========================================================================================================
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sLabel1 = new FrameSystem.SLibrary.SComponents.SLabel();

        setMinimumSize(new java.awt.Dimension(0, 40));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(200, 40));

        sLabel1.setForeground(new java.awt.Color(255, 255, 255));
        sLabel1.setText(text);
        sLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sLabel1.setIconTextGap(18);
        sLabel1.setPreferredSize(new java.awt.Dimension(73, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(sLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

// Overrided Methods =========================================================================================
    
    private MouseListener layerPanelMouseListener = null;
    
    @Override
    public void addLayeredPanelMouseListener(MouseListener m){
        this.layerPanelMouseListener = m;
        if(minButton != null){
            minButton.addLayeredPanelMouseListener(m);
        }
        
        addMouseListener(m);
        sLabel1.addMouseListener(m);
    }

    @Override
    public void setActiveButton(boolean active){
        setActive(active);
        if(minButton != null) minButton.setActiveButton(active);
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);
        Dimension s = getSize();
        
        super.paintComponent(g);
        
        g2.setColor(activeLineColor);
        g2.fillRoundRect(8, currentLineHeight, 4, (s.height - (currentLineHeight * 2)), 4, 4);
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);
        Dimension s = getSize();
        
        if(hovering){
            g2.setColor(hoverBackgroundColor);
        }else if(active){
            g2.setColor(activeBackgroundColor);
        }else{
            g2.setColor(inactiveBackgroundColor);
        }
        g2.fillRoundRect(0, 0, s.width, s.height, radius, radius);
        
        if(notificationCount != 0){
            g2.setFont(getFont());
            FontMetrics fm = g.getFontMetrics();
            String str = String.valueOf(notificationCount);
            int stringWidth = fm.stringWidth(str);
            int stringHeight = fm.getHeight() - 4;
            int heightHalf = (((int)s.getHeight()) / 2);
            
            int x = s.width - 15 - stringWidth;
            int y = heightHalf + (stringHeight / 2);
            
            if(!isHovering() && !isActive()){
                if(active){
                    g2.setColor(activeNotificationColor);
                }else{
                    g2.setColor(inactiveNotificationColor);
                }
                int[] paddding = new int[]{18, 3, 25, 6};
                g2.fillRoundRect(x - paddding[0], y - stringHeight - paddding[1], stringWidth + paddding[2], stringHeight + paddding[3], 20, 20);
            }
           
            g2.setColor(notificationForegroundColor);
            g2.fillRoundRect(x - 12, y - (stringHeight / 2) - 3, 6, 6, 6, 6);
            g2.drawString(str, x, y - 1);
        }
        
        super.paintOverrideAll(g);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private FrameSystem.SLibrary.SComponents.SLabel sLabel1;
    // End of variables declaration//GEN-END:variables

}
