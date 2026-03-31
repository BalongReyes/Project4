package FrameSystem.Modules.Frame;

import FrameSystem.SLibrary.Layer.SLayer;
import FrameSystem.SLibrary.Layer.SLayerManager;

/**
 * SFrameLayers acts as a central controller for the main application screens.
 * It abstracts away the SLayerManager and provides easy-to-use methods for screen switching.
 */
public class SFrameLayers {

    // Define constant names for your layers to avoid spelling mistakes
    public static final String LAYER_LOADING = "LoadingLayer";
    public static final String LAYER_LOGIN = "LoginLayer";
    public static final String LAYER_HOME = "HomeLayer";

    private final SLayerManager layerManager;

    /**
     * Constructor for the layers controller.
     * @param loadingLayer The Loading screen SLayer
     * @param loginLayer The Login screen SLayer
     * @param homeLayer The Home screen SLayer
     */
    public SFrameLayers(SLayer loadingLayer, SLayer loginLayer, SLayer homeLayer) {
        this.layerManager = new SLayerManager();
        
        // Ensure the layers have the exact correct names set before registering
        loadingLayer.setLayerName(LAYER_LOADING);
        loginLayer.setLayerName(LAYER_LOGIN);
        homeLayer.setLayerName(LAYER_HOME);

        // Register the layers with the manager all at once
        layerManager.addLayers(loadingLayer, loginLayer, homeLayer);
    }

// Control Methods ===========================================================================================

    /**
     * Switches the view to the Loading layer.
     */
    public void showLoading() {
        layerManager.showLayer(LAYER_LOADING);
    }

    /**
     * Switches the view to the Login layer.
     */
    public void showLogin() {
        layerManager.showLayer(LAYER_LOGIN);
    }

    /**
     * Switches the view to the Home (dashboard) layer.
     */
    public void showHome() {
        layerManager.showLayer(LAYER_HOME);
    }
    
    /**
     * Retrieves the underlying SLayerManager if advanced control is needed.
     * @return The active SLayerManager.
     */
    public SLayerManager getManager() {
        return layerManager;
    }
}