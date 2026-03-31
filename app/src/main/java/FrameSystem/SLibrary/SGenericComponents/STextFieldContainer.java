
package FrameSystem.SLibrary.SGenericComponents;

import EventSystem.Listeners.MouseClickedAdaptor;
import FrameSystem.SLibrary.SComponents.SPanel;
import MainSystem.CustomGraphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.BeanProperty;
import java.beans.JavaBean;
import javax.swing.JTextField;

@JavaBean(description = "A component that displays a jpanel with corner radius")
public class STextFieldContainer extends SPanel{

    private JTextField textFieldChild = null;
    
    private boolean focused = false;
    
// Constructor ===============================================================================================
    
    public STextFieldContainer(){
        setOpaque(false);
        setPreferredSize(new Dimension(50, 50));
    }
    
// Setters and Getters =======================================================================================

    @BeanProperty(preferred = true, description = "Setup listener to the child")
    public void setTextFieldChild(JTextField textFieldChild){
        if(this.textFieldChild != null || textFieldChild == null) return;
        
        this.textFieldChild = textFieldChild;
        textFieldChild.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt){
                super.focusGained(evt);
                focused = true;
                repaint();
            }
            @Override
            public void focusLost(FocusEvent evt){
                super.focusLost(evt);
                focused = false;
                repaint();
            }
        });
        
        addMouseListener((MouseClickedAdaptor) evt -> {
            textFieldChild.requestFocus();
        });
    }

    public JTextField getTextFieldChild(){
        return textFieldChild;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private int lineGap = 0;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setLineGap(int lineGap){
        this.lineGap = lineGap;
    }

    public int getLineGap(){
        return lineGap;
    }
    
// Overrided Methods =========================================================================================

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);
        
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
