
package FrameSystem.SLibrary.SAnimated;

import MainSystem.CustomGraphics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Path2D;
import java.beans.BeanProperty;

public class SAnimatedOffline extends SPanelAnimated{

    // Abstract tracking of the Head and Tail of the line (0.0 to 1.0+)
    private float headPos = 0.05f; 
    private float tailPos = 0.0f;  
    private boolean growing = true;

    // Animation Speeds
    private final float BASE_SPEED = 0.0025f;  // Base speed traveling the perimeter
    private final float SWEEP_SPEED = 0.015f;  // Maximum speed of growing/shrinking
    private final float ACCELERATION = 0.0005f; // How fast the speed transitions

    // Current speeds for smooth transitions
    private float currentHeadSweep = SWEEP_SPEED;
    private float currentTailSweep = 0.0f;

    public SAnimatedOffline() {
        super(10);
    }

// Implementations ===========================================================================================
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (growing) {
            // Smoothly increase head sweep speed and decrease tail sweep speed
            if (currentHeadSweep < SWEEP_SPEED) currentHeadSweep += ACCELERATION;
            if (currentHeadSweep > SWEEP_SPEED) currentHeadSweep = SWEEP_SPEED;
            
            if (currentTailSweep > 0) currentTailSweep -= ACCELERATION;
            if (currentTailSweep < 0) currentTailSweep = 0;
            
            if (headPos - tailPos >= 0.7f) {
                growing = false;
            }
        } else {
            // Smoothly decrease head sweep speed and increase tail sweep speed
            if (currentHeadSweep > 0) currentHeadSweep -= ACCELERATION;
            if (currentHeadSweep < 0) currentHeadSweep = 0;
            
            if (currentTailSweep < SWEEP_SPEED) currentTailSweep += ACCELERATION;
            if (currentTailSweep > SWEEP_SPEED) currentTailSweep = SWEEP_SPEED;
            
            if (headPos - tailPos <= 0.2f) {
                growing = true;
            }
        }

        // Apply calculated speeds
        headPos += BASE_SPEED + currentHeadSweep;
        tailPos += BASE_SPEED + currentTailSweep;

        // Prevent floating point numbers from losing precision over time
        if (tailPos >= 1.0f) {
            tailPos -= 1.0f;
            headPos -= 1.0f;
        }

        repaint();
    }
    
// Setters and Getters =======================================================================================
    
    protected Color lineColor = Color.white;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setLineColor(Color lineColor){
        this.lineColor = lineColor;
    }

    public Color getLineColor(){
        return lineColor;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    protected float lineWidth = 0;

    @BeanProperty(preferred = true, visualUpdate = true, description = "")
    public void setLineWidth(float lineWidth){
        this.lineWidth = lineWidth;
    }

    public float getLineWidth(){
        return lineWidth;
    }
    
// Overrided Methods =========================================================================================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = CustomGraphics.getGraphics2D(g);

        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        
        float strokeWidth;
        if(lineWidth != 0){
            strokeWidth = lineWidth;
        }else{
            strokeWidth = size * 0.1f;
        }
        
        // Center coordinates
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        
        // Triangle Radius
        double R = (size / 2.0) - strokeWidth;

        // Calculate the 3 Vertices of a perfectly upright Equilateral Triangle
        double px1 = centerX;
        double py1 = centerY - R; // Top
        
        double px2 = centerX + R * Math.cos(Math.PI / 6.0);
        double py2 = centerY + R * Math.sin(Math.PI / 6.0); // Bottom Right
        
        double px3 = centerX - R * Math.cos(Math.PI / 6.0);
        double py3 = centerY + R * Math.sin(Math.PI / 6.0); // Bottom Left

        // Calculations for distance mapping
        double S = R * Math.sqrt(3); // The exact length of one side
        double P = 3 * S;            // The total perimeter

        double tailAbsolute = tailPos * P;
        double headAbsolute = headPos * P;

        // Manually build the path from Tail to Head to guarantee perfect wrapping!
        Path2D.Double path = new Path2D.Double();
        
        // Start drawing at the tail
        path.moveTo(getX(tailAbsolute, S, px1, px2, px3), getY(tailAbsolute, S, py1, py2, py3));

        // Find and add any intermediate corners the line crosses
        long nextCornerIdx = (long) Math.floor(tailAbsolute / S) + 1;
        double nextCornerDist = nextCornerIdx * S;

        while (nextCornerDist < headAbsolute) {
            path.lineTo(getX(nextCornerDist, S, px1, px2, px3), getY(nextCornerDist, S, py1, py2, py3));
            nextCornerIdx++;
            nextCornerDist = nextCornerIdx * S;
        }

        // Stop drawing at the head
        path.lineTo(getX(headAbsolute, S, px1, px2, px3), getY(headAbsolute, S, py1, py2, py3));

        // Draw the cleanly wrapped segment
        g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(lineColor);
        g2.draw(path);
    }
    
    private double getX(double absoluteDistance, double sideLength, double px1, double px2, double px3) {
        double d = absoluteDistance % (3 * sideLength);
        if (d < 0) d += 3 * sideLength; 

        if (d <= sideLength) return px1 + (px2 - px1) * (d / sideLength); // Top to Bottom-Right
        if (d <= 2 * sideLength) return px2 + (px3 - px2) * ((d - sideLength) / sideLength); // Bottom-Right to Bottom-Left
        return px3 + (px1 - px3) * ((d - 2 * sideLength) / sideLength); // Bottom-Left back to Top
    }

    private double getY(double absoluteDistance, double sideLength, double py1, double py2, double py3) {
        double d = absoluteDistance % (3 * sideLength);
        if (d < 0) d += 3 * sideLength; 

        if (d <= sideLength) return py1 + (py2 - py1) * (d / sideLength); // Top to Bottom-Right
        if (d <= 2 * sideLength) return py2 + (py3 - py2) * ((d - sideLength) / sideLength); // Bottom-Right to Bottom-Left
        return py3 + (py1 - py3) * ((d - 2 * sideLength) / sideLength); // Bottom-Left back to Top
    }

}
