
package FrameSystem.SLibrary.Animated;

import FrameSystem.SLibrary.Panel.SPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class SAnimated extends SPanel implements ActionListener{

    private Timer timer;
    
    public SAnimated(int delay){
        super();
        timer = new Timer(delay, this);
        timer.start();
    }

// Implementations ===========================================================================================
    
    @Override
    public void actionPerformed(ActionEvent e){
        repaint(); // Request a redraw
    }

}
