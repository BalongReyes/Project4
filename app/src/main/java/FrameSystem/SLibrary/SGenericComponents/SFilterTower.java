package FrameSystem.SLibrary.SGenericComponents;

import MainSystem.CustomGraphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanProperty;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class SFilterTower extends SFilterPanel implements ActionListener{

    private Timer timer;
    
    public SFilterTower(){
        initComponents();
        timer = new Timer(10, this);
    }

// ===========================================================================================================
    
    private int currentLineHeight = 0;
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(active){
            if(currentLineHeight > activeLineHeight){
                currentLineHeight--;
            }else{
                timer.stop();
            }
        }else{
            if(currentLineHeight < inactiveLineHeight){
                currentLineHeight++;
            }else{
                timer.stop();
            }
        }
        repaint();
    }
    
// Setters and Getters =======================================================================================
    
    public void toggleActive(){
        setActive(!active);
    }
    
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
    
    private int activeLineHeight;
    
    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setActiveLineHeight(int activeLineHeight){
        this.activeLineHeight = activeLineHeight;
    }

    public int getActiveLineHeight(){
        return activeLineHeight;
    }
    
    private int inactiveLineHeight;
    
    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setInactiveLineHeight(int inactiveLineHeight){
        this.inactiveLineHeight = inactiveLineHeight;
        currentLineHeight = inactiveLineHeight;
    }

    public int getInactiveLineHeight(){
        return inactiveLineHeight;
    }
    
    @Override
    @BeanProperty(hidden = true)
    public void setLineHeight(int lineHeight){
        super.setLineHeight(lineHeight);
    }
    
// ===========================================================================================================

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);
        
        int width = getWidth();
        int height = getHeight();

        // The main panel's body position, adjusted by shadow size and offsets
        // This must match the EmptyBorder logic in setBorderPadding()
        int x = 0;
        int y = 0;
        int w = width;
        int h = height;
        
        if(isShadowX()){
            x = shadowSize - shadowOffsetX;
            w = width - (shadowSize * 2);
        }
        if(isShadowY()){
            y = shadowSize - shadowOffsetY;
            h = height - (shadowSize * 2);
        }
        
        if(active){
            g2.setColor(activeLineColor);
        }else{
            g2.setColor(inactiveLineColor);
        }
        g2.fillRoundRect(x + w - 10, y + currentLineHeight, 4, (h - (currentLineHeight * 2)), 4, 4);
    }
    
// ===========================================================================================================
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sLabel1 = new FrameSystem.SLibrary.SComponents.SLabel();

        setActiveLineColor(new java.awt.Color(255, 127, 127));
        setHoverBackgroundColor(new java.awt.Color(245, 245, 245));
        setInactiveLineColor(new java.awt.Color(214, 214, 214));
        setRadius(20);
        setRounded(true);
        setShadowColor(new java.awt.Color(230, 230, 230));
        setShadowOpacity(0.3F);
        setShadowSize(5);
        setShadowX(true);
        setShadowY(true);

        sLabel1.setForeground(new java.awt.Color(56, 56, 56));
        sLabel1.setText("Tower 1");
        sLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sLabel1.setIconTextGap(18);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(sLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private FrameSystem.SLibrary.SComponents.SLabel sLabel1;
    // End of variables declaration//GEN-END:variables
}
