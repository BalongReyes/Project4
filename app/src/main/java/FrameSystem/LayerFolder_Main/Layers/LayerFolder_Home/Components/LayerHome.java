
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Components;

import ConsoleSystem.Console;
import ConsoleSystem.ConsoleColors;
import FrameSystem.SLibrary.SAbstractComponents.SLayer;
import FrameSystem.SLibrary.SAbstractComponents.SLayerButton;
import MainSystem.Main;
import java.awt.event.KeyEvent;
import java.beans.BeanProperty;
import java.util.ArrayList;

public class LayerHome extends SLayer{

// Constructor ===============================================================================================
    
    public LayerHome(){
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

    private static ArrayList<LayerHome> layeredPanels = new ArrayList<>();
    private static LayerHome currentLayeredPanel = null;
    
    private static void addLayer(LayerHome layer){
        layeredPanels.add(layer);
    }
    
    public static LayerHome getCurrentLayeredPanel(){
        return currentLayeredPanel;
    }
    
    public static void showLayer(LayerHome showLayer){
        if(currentLayeredPanel != null && (!currentLayeredPanel.fireLayeredPanelHideListener(showLayer.getAccessibleContext().getAccessibleName()))){
            return;
        }
        if(!showLayer.fireLayeredPanelBeforeShowListener(currentLayeredPanel == showLayer, showLayer.toString())){
            return;
        }
        
        if(Main.debugDataHandlerRefresh) Console.line().out("SHOWING LAYER " + showLayer.getName(), ConsoleColors.GREEN);
        
        for(LayerHome layer : layeredPanels){
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
    
// Overrided Methods =========================================================================================

    @BeanProperty(preferred = true, visualUpdate = true, description = "The button that will show this panel")
    public void setLayerButton(MenuButton button){
        super.setLayerButton(button);
    }

    @Override
    @BeanProperty(hidden = true)
    public void setLayerButton(SLayerButton button){
    }

}
