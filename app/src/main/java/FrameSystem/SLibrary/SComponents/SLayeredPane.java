
package FrameSystem.SLibrary.SComponents;

import EventSystem.Interface.InnerListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.JLayeredPane;

public class SLayeredPane extends JLayeredPane implements InnerListener{

    public SLayeredPane(){
        super();
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
    
    public void setPreferredHeight(){
        int totalPreferredHeight = 0;
        
        synchronized(getTreeLock()){
            int i = getComponentCount() - 1;
            if(i < 0) return;
            for(; i >= 0; i--){
                Component c = getComponent(i);
                if(c == null) continue;
                totalPreferredHeight += c.getPreferredSize().height;
            }
        }
        
        setPreferredSize(new Dimension(getPreferredSize().width, totalPreferredHeight));
    }
}
