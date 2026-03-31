
package FrameSystem.SLibrary.SComponents;

import MainSystem.CustomGraphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean(description = "A component that displays a jlabel that change color when hovered")
public class SPanelHover extends SPanel{

    protected MouseAdapter hoverListener;
    
// Constructor ===============================================================================================

    public SPanelHover(){
        super();
        
        this.setBorder(null);
        hoverListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt){
                setHovering(true);
            }
            @Override
            public void mouseExited(MouseEvent evt){
                setHovering(false);
            }
        };
        super.addMouseListener(hoverListener);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
// Main Methods ==============================================================================================

    public void applyHoverInnerListener(){
        addInnerListeners(hoverListener);
    }
    
    public MouseAdapter getHoverListener(){
        return hoverListener;
    }
    
// Methods ===================================================================================================

    protected boolean hovering = false;
    
    public void setHovering(boolean hovering){
        if(this.hovering == hovering) return;
        this.hovering = hovering;
        repaint();
    }
    
    public boolean isHovering(){
        return hovering;
    }

    @Override
    public void setEnabled(boolean enabled){
        if(!enabled){
            setHovering(false);
        }
        super.setEnabled(enabled);
    }
    
// Setters and Getters =======================================================================================

    protected Color hoverBackgroundColor = Color.white;
    
    @BeanProperty(preferred = true, visualUpdate = true, description = "The hover color")
    public void setHoverBackgroundColor(Color hoverBackgroundColor){
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getHoverBackgroundColor(){
        return hoverBackgroundColor;
    }
    
// Overrided Methods =========================================================================================

    @Override
    public void paintOverrideAll(Graphics g){
        super.paintOverrideAll(g);
    }
    
    @Override
    public void paintOverride(Graphics g, int n){
        if(n > 0){
            n--;
            if(n > 0){
                super.paintOverride(g, n);
            }else{
                super.paintOverride(g);
            }
        }else{
            super.paint(g);
        }
    }
    
    @Override
    public void paintOverride(Graphics g){
        super.paint(g);
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    @Override
    public final void setCursor(Cursor cursor){
        super.setCursor(cursor);
    }
    
    @Override
    public void paint(Graphics g){
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

        if(hovering){
            g2.setColor(hoverBackgroundColor);
        }else{
            g2.setColor(getBackground());
        }
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
