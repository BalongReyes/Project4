
package FrameSystem.SLibrary.SAnimated;

import FrameSystem.SLibrary.SComponents.SPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class SPanelAnimated extends SPanel implements ActionListener{

    private Timer timer;
    
    public SPanelAnimated(int delay){
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
