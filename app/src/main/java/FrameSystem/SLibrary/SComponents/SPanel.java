
package FrameSystem.SLibrary.SComponents;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.beans.BeanProperty;
import java.beans.JavaBean;

import javax.swing.JPanel;
import javax.swing.border.Border;

import EventSystem.Interface.InnerListener;
import MainSystem.CustomGraphics;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

@JavaBean(description = "A component that displays a jpanel")
public class SPanel extends JPanel implements InnerListener{

    public SPanel(){
        super();
        super.setBorder(null);
        setOpaque(true);
    }
    
// Setters and Getters =======================================================================================
    
    protected int radius = 0;
    
    @BeanProperty(preferred = true, visualUpdate = true, description = "The corner radius")
    public void setRadius(int radius){
        this.radius = radius;
    }

    public int getRadius(){
        return radius;
    }
    
    public int getRadius(int add){
        int output = radius + add;
        return output < 0 ? 0 : output;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected boolean rounded = false;

    @BeanProperty(preferred = true, visualUpdate = true, description = "If the panel is rounded")
    public void setRounded(boolean rounded){
        this.rounded = rounded;
        if(rounded) setOpaque(false);
    }

    public boolean isRounded(){
        return rounded;
    }
    
// -----------------------------------------------------------------------------------------------------------

    @Override
    @BeanProperty(hidden = true)
    public void setName(String name){
        super.setName(name);
    }
    
    @Override
    @BeanProperty(hidden = true)
    public void setToolTipText(String text){
        super.setToolTipText(text);
    }

    @Override
    @BeanProperty(hidden = true)
    public void setBorder(Border border){
        super.setBorder(border);
    }

    @Override
    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setBackground(Color bg){
        super.setBackground(bg);
    }
    
// ===========================================================================================================
    
    protected int borderLine = 0;

    @BeanProperty(preferred = true, visualUpdate = true, description = "The border line width")
    public void setBorderLine(int borderLine){
        this.borderLine = borderLine;
        if(borderLine != 0) setOpaque(false);
    }
    
    public int getBorderLine(){
        return borderLine;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected Color borderColor = Color.white;

    @BeanProperty(preferred = true, visualUpdate = true, description = "The border color")
    public void setBorderColor(Color borderColor){
        this.borderColor = borderColor;
    }

    public Color getBorderColor(){
        return borderColor;
    }
    
// Drop Shadow ===============================================================================================
    
    protected boolean shadowX = false;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowX(boolean shadowX){
        this.shadowX = shadowX;
        setBorderPadding();
        if(shadowX) setOpaque(false);
    }

    public boolean isShadowX(){
        return shadowX;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected boolean shadowY = false;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowY(boolean shadowY){
        this.shadowY = shadowY;
        setBorderPadding();
        if(shadowY) setOpaque(false);
    }

    public boolean isShadowY(){
        return shadowY;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected int shadowSize;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowSize(int shadowSize){
        this.shadowSize = shadowSize;
        
        setBorderPadding();
    }

    public int getShadowSize(){
        return shadowSize;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected float shadowOpacity;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowOpacity(float shadowOpacity){
        this.shadowOpacity = shadowOpacity;
    }

    public float getShadowOpacity(){
        return shadowOpacity;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected Color shadowColor = Color.white;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowColor(Color shadowColor){
        this.shadowColor = shadowColor;
    }

    public Color getShadowColor(){
        return shadowColor;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected int shadowOffsetX;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowOffsetX(int shadowOffsetX){
        this.shadowOffsetX = shadowOffsetX;
        
        setBorderPadding();
    }

    public int getShadowOffsetX(){
        return shadowOffsetX;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected int shadowOffsetY;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowOffsetY(int shadowOffsetY){
        this.shadowOffsetY = shadowOffsetY;
        
        setBorderPadding();
    }

    public int getShadowOffsetY(){
        return shadowOffsetY;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected void setBorderPadding(){
        // Top, Left, Bottom, Right
        setBorder(new EmptyBorder(
            shadowY ? shadowSize - shadowOffsetY : 0,
            shadowX ? shadowSize - shadowOffsetX : 0,
            shadowY ? shadowSize + shadowOffsetY : 0,
            shadowX ? shadowSize + shadowOffsetX : 0
        ));
    }
    
// Overrided Methods =========================================================================================

    public void paintOverrideAll(Graphics g){
        super.paint(g);
    }
    
    public void paintOverride(Graphics g, int n){
        super.paint(g);
    }
    
    public void paintOverride(Graphics g){
        super.paint(g);
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    @Override
    public void setCursor(Cursor cursor){
        super.setCursor(cursor);
    }
    
    @Override
    public void addInnerListeners(MouseListener listener){
        super.addMouseListener(listener);
        synchronized(getTreeLock()){
            int i = getComponentCount() - 1;
            if(i < 0) return;
            for(; i >= 0; i--){
                Component c = getComponent(i);
                if(c == null) continue;
                if(c instanceof InnerListener innerListener){
                    innerListener.addInnerListeners(listener);
                }else{
                    c.addMouseListener(listener);
                }
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        if(!rounded && !(shadowX || shadowY)) {
            super.paint(g);
            return;
        }
        
        int radiusPaint = 0;
        if(rounded){
            radiusPaint = this.radius;
        }

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

        // Draw the drop shadow
        for (int i = 0; i < shadowSize; i++) {
            float opacity = shadowOpacity * (1.0f - ((float) i / shadowSize));
            g.setColor(new Color(
                shadowColor.getRed(),
                shadowColor.getGreen(),
                shadowColor.getBlue(),
                (int) (opacity * 255)
            ));

            // Draw shadow rectangles that expand relative to the main body's position
            g.fillRoundRect(
                x - i + shadowOffsetX, 
                y - i + shadowOffsetY, 
                w + (i * 2), 
                h + (i * 2), 
                radiusPaint + i, 
                radiusPaint + i
            );
        }

        // Draw Border
        g2.setColor(borderColor);
        g2.fillRoundRect(x, y, w, h, radiusPaint, radiusPaint);

        // Draw Background (Inner Body)
        g2.setColor(getBackground());
        g2.fillRoundRect(
            x + borderLine, 
            y + borderLine, 
            w - (borderLine * 2), 
            h - (borderLine * 2), 
            radiusPaint - borderLine, 
            radiusPaint - borderLine
        );

        paintOverrideAll(g);
    }
    
}
