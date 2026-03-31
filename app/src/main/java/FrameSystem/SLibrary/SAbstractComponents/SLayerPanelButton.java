
package FrameSystem.SLibrary.SAbstractComponents;

import java.awt.event.MouseListener;

public class SLayerPanelButton extends SLayerButton{

    public SLayerPanelButton(){
        super();
        super.setBorder(null);
    }

// Overrided Methods =========================================================================================
    
    @Override
    public void addLayeredPanelMouseListener(MouseListener m){
        addMouseListener(m);
    }
    
    @Override
    public void setActiveButton(boolean active){
        setActive(active);
    }

}
