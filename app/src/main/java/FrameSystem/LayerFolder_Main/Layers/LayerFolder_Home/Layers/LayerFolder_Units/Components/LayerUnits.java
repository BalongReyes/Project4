
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Components;

import ConsoleSystem.Console;
import ConsoleSystem.ConsoleColors;
import FrameSystem.SLibrary.SAbstractComponents.SLayer;
import FrameSystem.SLibrary.SAbstractComponents.SLayerButton;
import MainSystem.Main;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LayerUnits extends SLayer{

// Constructor ===============================================================================================
    
    public LayerUnits(){
        addLayer();
    }

    private void addLayer(){
        addLayer(this);
    }
    
// Implementations ===========================================================================================
    
    @Override
    protected void showLayeredPanel(){
        showLayer(this);
    }

// Static Methods ============================================================================================

    private static ArrayList<LayerUnits> layeredPanels = new ArrayList<>();
    private static LayerUnits currentLayeredPanel = null;
    
    private static void addLayer(LayerUnits layer){
        layeredPanels.add(layer);
    }
    
    public static LayerUnits getCurrentLayeredPanel(){
        return currentLayeredPanel;
    }
    
    public static void showLayer(LayerUnits showLayer){
        if(currentLayeredPanel != null && (!currentLayeredPanel.fireLayeredPanelHideListener(showLayer.getAccessibleContext().getAccessibleName()))){
            return;
        }
        if(!showLayer.fireLayeredPanelBeforeShowListener(currentLayeredPanel == showLayer, showLayer.toString())){
            return;
        }
        
        if(Main.debugDataHandlerRefresh) Console.line().out("SHOWING LAYER " + showLayer.getName(), ConsoleColors.GREEN);
        
        for(LayerUnits layer : layeredPanels){
            SLayerButton layeredButton = layer.getLayerButton();
            
            layer.setVisible(layer == showLayer);
            if(layeredButton != null) layeredButton.setActiveButton(layer == showLayer);
        }
        showLayer.fireLayeredPanelShowListener(currentLayeredPanel == showLayer, showLayer.toString());
        currentLayeredPanel = showLayer;
    }
    
    public static void keyPressed(KeyEvent evt){
        if(currentLayeredPanel == null) return;
        currentLayeredPanel.fireLayeredPanelKeyPressedListener(evt);
    }

}
