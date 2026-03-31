
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Components;

import FrameSystem.SLibrary.SComponents.SPanel;
import MainSystem.CustomGraphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.BeanProperty;
import java.beans.JavaBean;
import javax.swing.border.EmptyBorder;

@JavaBean(description = "A component that displays a jpanel in hero menu bar")
public class SPanelHomeMenu extends SPanel{

    public SPanelHomeMenu(){
        setOpaque(false);
    }
    
// Drop Shadow ===============================================================================================
    
    @Override
    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowX(boolean shadowX){
        this.shadowX = shadowX;
        setBorderPadding();
        if(shadowX) setOpaque(false);
    }
    
    @Override
    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setShadowOffsetX(int shadowOffsetX){
        this.shadowOffsetX = shadowOffsetX;
    }
    
    @Override
    protected void setBorderPadding(){
        // Top, Left, Bottom, Right
        setBorder(new EmptyBorder(
            0,
            0,
            0,
            shadowX ? shadowSize + shadowOffsetX : 0
        ));
    }

// Overrided Methods =========================================================================================
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);

        int width = getWidth();
        int height = getHeight();
        
        int x = 0;
        int y = 0;
        int w = width - shadowSize + shadowOffsetX;
        int h = height;
        
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
                y, 
                w + (i * 2), 
                h, 
                0, 
                0
            );
        }

        // Draw Background (Inner Body)
        g2.setColor(getBackground());
        g2.fillRoundRect(
            x, 
            y, 
            w, 
            h, 
            0, 
            0
        );
        
        super.paintOverrideAll(g);
    }

}
