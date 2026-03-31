package FrameSystem.SLibrary.Layer;

import java.util.HashMap;
import java.util.Map;

/**
 * SLayerManager handles a group of SLayer components.
 * It ensures only one layer in the group is visible at a time.
 */
public class SLayerManager {
    
    // Stores layers by their unique names
    private final Map<String, SLayer> layers = new HashMap<>();
    
    // Tracks the currently visible layer
    private SLayer currentLayer = null;

    public SLayerManager() {
        // Empty constructor
    }

    /**
     * Registers a single layer with the manager.
     * @param layer The SLayer to add.
     */
    public void addLayer(SLayer layer) {
        if (layer != null && layer.getLayerName() != null) {
            layers.put(layer.getLayerName(), layer);
        }
    }

    /**
     * A convenience method to register multiple layers at once.
     * @param layersToAdd A comma-separated list of SLayer components.
     */
    public void addLayers(SLayer... layersToAdd) {
        for (SLayer l : layersToAdd) {
            addLayer(l);
        }
    }

    /**
     * Hides the current layer and shows the target layer.
     * @param layerName The name of the layer you want to show.
     */
    public void showLayer(String layerName) {
        // 1. Check if the layer exists
        if (!layers.containsKey(layerName)) {
            System.err.println("SLayerManager: Layer '" + layerName + "' not found.");
            return;
        }

        SLayer targetLayer = layers.get(layerName);

        // 2. If it's already the active layer, do nothing
        if (targetLayer == currentLayer) {
            return;
        }

        // 3. Hide the currently active layer (if there is one)
        if (currentLayer != null) {
            currentLayer.setVisible(false);
        }

        // 4. Show the new target layer
        targetLayer.setVisible(true);
        currentLayer = targetLayer;
    }
    
    /**
     * Retrieves the currently active layer.
     * @return The active SLayer.
     */
    public SLayer getCurrentLayer() {
        return currentLayer;
    }
}