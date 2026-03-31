package FrameSystem.SLibrary.Layer;

import FrameSystem.SLibrary.Panel.SPanel;
import java.beans.BeanProperty;
import java.beans.JavaBean;

/**
 * SLayer is a concrete, drag-and-drop component intended to act as a screen or layer.
 * It extends SPanel for full CSS styling support.
 */
@JavaBean(description = "A drag-and-drop layer component for screen management")
public class SLayer extends SPanel {
    
    private String layerName = "DefaultLayer";

    public SLayer() {
        super();
        // Set default layer behaviors
        this.setOpaque(false); // Make transparent so backgrounds can show through
        this.setVisible(false); // Layers should be hidden by default until called
    }

    /**
     * Gets the unique name of this layer.
     * @return The layer name.
     */
    public String getLayerName() {
        return layerName;
    }

    /**
     * Sets the unique name of this layer. 
     * You can edit this directly in the NetBeans Properties Window!
     * @param layerName The name to identify this layer.
     */
    @BeanProperty(preferred = true, description = "The unique identifier name for this layer")
    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }
    
    // Note: We don't need custom onShow() or onHide() methods!
    // To trigger code when a layer is shown, simply right-click the SLayer in NetBeans, 
    // go to Events -> Component -> componentShown.
}