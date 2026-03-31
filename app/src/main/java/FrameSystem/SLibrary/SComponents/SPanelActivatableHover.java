
package FrameSystem.SLibrary.SComponents;

import MainSystem.CustomGraphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean(description = "A component that displays a jlabel that change color when hovered")
public class SPanelActivatableHover extends SPanelActivatable{

    protected MouseListener hoverListener;
    protected ComponentAdapter componentAdapter;
    
// Constructor ===============================================================================================

    public SPanelActivatableHover(){
        super();
        
        this.setBorder(null);
        hoverListener = (MouseListener)(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt){
                setHovering(true);
            }
            @Override
            public void mouseExited(MouseEvent evt){
                setHovering(false);
            }
        });
        componentAdapter = new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                super.componentResized(e);
                checkHoverState();
            }
            @Override
            public void componentMoved(ComponentEvent e){
                super.componentMoved(e);
                checkHoverState();
            }
        };
        super.addMouseListener(hoverListener);
        super.addComponentListener(componentAdapter);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
// Methods ===================================================================================================

    protected boolean hovering = false;
    
    public void setHovering(boolean hovering){
        this.hovering = hovering;
        repaint();
    }
    
    public boolean isHovering(){
        return hovering;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private void checkHoverState() {
        if (hovering && isShowing()) {
            try {
                Point mousePos = MouseInfo.getPointerInfo().getLocation();
                Point compPos = getLocationOnScreen();
                Rectangle bounds = new Rectangle(compPos, getSize());
                if (!bounds.contains(mousePos)) {
                    setHovering(false);
                }
            } catch (HeadlessException ex) {
                setHovering(false);
            }
        }
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
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);
        Dimension s = getSize();
        
        int radiusPaint = 0;
        if(rounded){
            radiusPaint = this.radius;
        }
        
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

        // Draw Background (Inner Body)
        if(hovering){
            g2.setColor(hoverBackgroundColor);
        }else if(active){
            g2.setColor(activeBackgroundColor);
        }else{
            g2.setColor(inactiveBackgroundColor);
        }
        g2.fillRoundRect(
            x + borderLine, 
            y + borderLine, 
            w - (borderLine * 2), 
            h - (borderLine * 2), 
            radiusPaint - borderLine, 
            radiusPaint - borderLine
        );
        
        super.paintOverrideAll(g);
    }

}
