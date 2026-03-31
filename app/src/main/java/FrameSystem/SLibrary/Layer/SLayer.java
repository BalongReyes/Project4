package FrameSystem.SLibrary.Layer;

import EventSystem.EventSLayer.SLayerBeforeShowEvent;
import EventSystem.EventSLayer.SLayerBeforeShowListener;
import EventSystem.EventSLayer.SLayerHideEvent;
import EventSystem.EventSLayer.SLayerHideListener;
import EventSystem.EventSLayer.SLayerKeyPressedListener;
import EventSystem.EventSLayer.SLayerShowEvent;
import EventSystem.EventSLayer.SLayerShowListener;
import FrameSystem.SLibrary.Panel.SPanel;

import javax.swing.event.EventListenerList;
import java.awt.event.KeyEvent;
import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean(description = "A drag-and-drop layer component with custom event listeners")
public class SLayer extends SPanel {
    
    private String layerName = "DefaultLayer";
    protected final EventListenerList listenerList = new EventListenerList();

    public SLayer() {
        super();
        this.setOpaque(false);
        this.setVisible(false); 
    }

    @BeanProperty(preferred = true, description = "The unique identifier name for this layer")
    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerName() {
        return layerName;
    }

// Events ====================================================================================================

    // --- Show Listener ---
    public void addLayeredPanelShowListener(SLayerShowListener l) {
        listenerList.add(SLayerShowListener.class, l);
    }
    public void removeLayeredPanelShowListener(SLayerShowListener l) {
        listenerList.remove(SLayerShowListener.class, l);
    }
    public void fireLayeredPanelShowListener(boolean showing, String recent) {
        Object[] listeners = listenerList.getListenerList();
        SLayerShowEvent event = new SLayerShowEvent(this, showing, recent);
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SLayerShowListener.class) {
                ((SLayerShowListener) listeners[i + 1]).layeredPanelShow(event);
            }
        }
    }

    // --- Hide Listener ---
    public void addLayeredPanelHideListener(SLayerHideListener l) {
        listenerList.add(SLayerHideListener.class, l);
    }
    public void removeLayeredPanelHideListener(SLayerHideListener l) {
        listenerList.remove(SLayerHideListener.class, l);
    }
    public boolean fireLayeredPanelHideListener(String next) {
        Object[] listeners = listenerList.getListenerList();
        SLayerHideEvent event = new SLayerHideEvent(this, next);
        boolean result = true;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SLayerHideListener.class) {
                if (!((SLayerHideListener) listeners[i + 1]).layeredPanelHide(event)) {
                    result = false;
                }
            }
        }
        return result;
    }

    // --- Before Show Listener ---
    public void addLayeredPanelBeforeShowListener(SLayerBeforeShowListener l) {
        listenerList.add(SLayerBeforeShowListener.class, l);
    }
    public void removeLayeredPanelBeforeShowListener(SLayerBeforeShowListener l) {
        listenerList.remove(SLayerBeforeShowListener.class, l);
    }
    public boolean fireLayeredPanelBeforeShowListener(boolean showing, String recent) {
        Object[] listeners = listenerList.getListenerList();
        SLayerBeforeShowEvent event = new SLayerBeforeShowEvent(this, showing, recent);
        boolean result = true;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SLayerBeforeShowListener.class) {
                if (!((SLayerBeforeShowListener) listeners[i + 1]).layeredPanelBeforeShow(event)) {
                    result = false;
                }
            }
        }
        return result;
    }

    // --- Key Pressed Listener ---
    public void addLayeredPanelKeyPressedListener(SLayerKeyPressedListener l) {
        listenerList.add(SLayerKeyPressedListener.class, l);
    }
    public void removeLayeredPanelKeyPressedListener(SLayerKeyPressedListener l) {
        listenerList.remove(SLayerKeyPressedListener.class, l);
    }
    public void fireLayeredPanelKeyPressedListener(KeyEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SLayerKeyPressedListener.class) {
                ((SLayerKeyPressedListener) listeners[i + 1]).layeredPanelKeyPressed(evt);
            }
        }
    }
}