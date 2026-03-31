package FrameSystem.SLibrary.Layer;

import java.util.HashMap;
import java.util.Map;

public class SLayerManager {
    
    private final Map<String, SLayer> layers = new HashMap<>();
    private SLayer currentLayer = null;

    public SLayerManager() {}

    public void addLayer(SLayer layer) {
        if (layer != null && layer.getLayerName() != null) {
            layers.put(layer.getLayerName(), layer);
        }
    }

    public void addLayers(SLayer... layersToAdd) {
        for (SLayer l : layersToAdd) {
            addLayer(l);
        }
    }

    /**
     * Attempts to switch the visible layer, firing custom events in the process.
     */
    public void showLayer(String layerName) {
        if (!layers.containsKey(layerName)) {
            System.err.println("SLayerManager: Layer '" + layerName + "' not found.");
            return;
        }

        SLayer targetLayer = layers.get(layerName);
        if (targetLayer == currentLayer) return;

        String currentLayerName = (currentLayer != null) ? currentLayer.getLayerName() : "";

        // 1. Fire BEFORE SHOW event on the target layer
        // If the listener returns false (e.g., waiting on database), abort the transition.
        if (!targetLayer.fireLayeredPanelBeforeShowListener(true, currentLayerName)) {
            return; 
        }

        // 2. Fire HIDE event on the current layer
        if (currentLayer != null) {
            // If the listener returns false (e.g., unsaved form data), abort the transition.
            if (!currentLayer.fireLayeredPanelHideListener(layerName)) {
                return; 
            }
            currentLayer.setVisible(false);
        }

        // 3. Show the new layer and fire SHOW event
        targetLayer.setVisible(true);
        targetLayer.fireLayeredPanelShowListener(true, currentLayerName);
        
        currentLayer = targetLayer;
    }
    
    public SLayer getCurrentLayer() {
        return currentLayer;
    }
}