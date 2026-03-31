package FrameSystem.SLibrary.Layer; // Adjust package if you put it elsewhere

import java.util.HashMap;
import java.util.Map;

/**
 * SLayerManager handles a group of SLayer components.
 * It ensures only the targeted layer is visible at any given time.
 */
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
     * Attempts to switch the visible layer, explicitly hiding all non-targeted layers.
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
        if (!targetLayer.fireLayeredPanelBeforeShowListener(true, currentLayerName)) {
            return; // Abort if the listener blocks it
        }

        // 2. Fire HIDE event ONLY on the current conceptual layer
        if (currentLayer != null) {
            if (!currentLayer.fireLayeredPanelHideListener(layerName)) {
                return; // Abort if the listener blocks it (e.g., unsaved changes)
            }
        }

        // 3. IMPROVEMENT: Explicitly loop through and hide ALL layers that are not the target
        for (SLayer layer : layers.values()) {
            if (!layer.getLayerName().equals(layerName)) {
                layer.setVisible(false);
            }
        }

        // 4. Show the new layer and fire SHOW event
        targetLayer.setVisible(true);
        targetLayer.fireLayeredPanelShowListener(true, currentLayerName);
        
        currentLayer = targetLayer;
    }
    
    public SLayer getCurrentLayer() {
        return currentLayer;
    }
}