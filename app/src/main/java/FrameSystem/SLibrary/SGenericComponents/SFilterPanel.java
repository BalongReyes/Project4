
package FrameSystem.SLibrary.SGenericComponents;

import FrameSystem.SLibrary.SComponents.SPanelActivatableHover;
import MainSystem.CustomGraphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean(description = "A SPanel for filters")
public class SFilterPanel extends SPanelActivatableHover{

    public SFilterPanel(){
        super();
    }

// Setters and Getters =======================================================================================

    protected Color activeLineColor = new Color(255, 255, 255);

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setActiveLineColor(Color activeLineColor){
        this.activeLineColor = activeLineColor;
    }

    public Color getActiveLineColor(){
        return activeLineColor;
    }
    
    protected Color inactiveLineColor = new Color(255, 255, 255);

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setInactiveLineColor(Color inactiveLineColor){
        this.inactiveLineColor = inactiveLineColor;
    }

    public Color getInactiveLineColor(){
        return inactiveLineColor;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected int lineHeight = -1;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setLineHeight(int lineHeight){
        this.lineHeight = lineHeight;
    }

    public int getLineHeight(){
        return lineHeight;
    }
    
// Overrided Methods =========================================================================================
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);
        Dimension s = getSize();

        super.paint(g);
        
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
        
        if(lineHeight != -1){
            if(active){
                g2.setColor(activeLineColor);
            }else{
                g2.setColor(inactiveLineColor);
            }
            g2.fillRoundRect(x + w - 10, y + lineHeight, 4, (h - (lineHeight * 2)), 4, 4);
        }
    }
    
}
