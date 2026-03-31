
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Managers;

import ConsoleSystem.Console;
import DatabaseSystem.DataTable.DataTableOrder;
import DatabaseSystem.UnitsData.UnitsDataHandler;
import DatabaseSystem.UnitsData.UnitsDataTable;
import EventSystem.Listeners.MousePressedAdaptor;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Components.LayerUnits;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Components.ObjectUnit;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Module.ModuleUnits;
import FrameSystem.SLibrary.SGenericComponents.SFilterTitlePanel;
import MainSystem.Manager;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagerObjectUnits extends Manager{

    public static ModuleUnits moduleUnits;
    
    private static ArrayList<ObjectUnit> objects = new ArrayList<>();
    
    public static void initDefault(){
        moduleUnits = frame.moduleHome.moduleUnits;
        
        moduleUnits.objectUnitScrollPane.setObjectContentHeight(60);
        
//        frame.sTargetPanel3.addMouseListener((MousePressedAdaptor) evt -> {
//            if(ManagerItemDetails.isOfflineMode()) return;
//            filterObjectCategory();
//        });
//            
//        frame.sTargetPanel2.addMouseListener((MousePressedAdaptor) evt -> {
//            clearFilterObject(true);
//        });
//        
//        frame.sTargetPanel1.addMouseListener((MousePressedAdaptor) evt -> {
//            if(ManagerItemDetails.isOfflineMode()) return;
//            addObject();
//        });
//        
//        frame.sTextField1.getDocument().addDocumentListener((DocumentAdapter) evt -> {
//            filterObjectSearch(frame.sTextField1.getText());
//        });
        
        moduleUnits.sFilterTitlePanel1.addMouseListener(buildFilterTitleMouseListener(UnitsDataTable.TOWER, moduleUnits.sFilterTitlePanel1));
        moduleUnits.sFilterTitlePanel2.addMouseListener(buildFilterTitleMouseListener(UnitsDataTable.FLOOR, moduleUnits.sFilterTitlePanel2));
        moduleUnits.sFilterTitlePanel3.addMouseListener(buildFilterTitleMouseListener(UnitsDataTable.UNIT, moduleUnits.sFilterTitlePanel3));
        moduleUnits.sFilterTitlePanel5.addMouseListener(buildFilterTitleMouseListener(UnitsDataTable.MODEL, moduleUnits.sFilterTitlePanel5));
        moduleUnits.sFilterTitlePanel4.addMouseListener(buildFilterTitleMouseListener(UnitsDataTable.FLOOR_AREA, moduleUnits.sFilterTitlePanel4));
    }

// Main Methods ==============================================================================================
    
// Filter Title ----------------------------------------------------------------------------------------------
    
    private static MouseListener buildFilterTitleMouseListener(int f, SFilterTitlePanel c){
        return (MousePressedAdaptor) evt -> {
            if(getCurrentFilterTitlePanel() != c) resetCurrentFilterTitlePanel();
            switch(c.setNextArrowDirection()){
                case 1 -> {
                    filterSort = f;
                    filterOrder = DataTableOrder.Desc;
                }
                case 2 -> {
                    filterSort = f;
                    filterOrder = DataTableOrder.Asc;
                }
                default -> {
                    filterSort = 1;
                    filterOrder = DataTableOrder.Desc;
                }
            }
            checkFilterActive();
            setCurrentFilterTitlePanel(c);
            refreshObjects(false);
        };
    }
    
    private static SFilterTitlePanel currentFilterTitlePanel = null;
    
    private static void setCurrentFilterTitlePanel(SFilterTitlePanel c){
        currentFilterTitlePanel = c;
    }
    
    private static SFilterTitlePanel getCurrentFilterTitlePanel(){
        return currentFilterTitlePanel;
    }
    
    private static void resetCurrentFilterTitlePanel(){
        if(currentFilterTitlePanel != null) currentFilterTitlePanel.setArrowDirection(-1);
    }
    
// Refresh ---------------------------------------------------------------------------------------------------
    
    private static void refreshObjects(boolean refresh){
        refreshObjects(currentObject, refresh);
    }
    
    public static void refreshObjects(ObjectUnit recentObject, boolean refresh) {
        LayerUnits.showLayer(moduleUnits.layerUnitsLoading);

//        ExecutorDriver.execute(() -> {
//            long startTime = System.currentTimeMillis();
//            try {
//                UnitsDataTable[] dataArray = UnitsDataHandler.getAllDataSorted(refresh, filterSort, filterOrder);
//
//                long elapsedTime = System.currentTimeMillis() - startTime;
//                long minLoadingTime = 3000; // 1 second minimum
//
//                if (elapsedTime < minLoadingTime) {
//                    try {
//                        Thread.sleep(minLoadingTime - elapsedTime);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//
//                javax.swing.SwingUtilities.invokeLater(() -> {
//                    moduleUnits.objectUnitWrapper.removeAll();
//                    objects.clear();
//
//                    for (UnitsDataTable data : dataArray) {
//                        ObjectUnit o = new ObjectUnit(data);
//                        objects.add(o);
//                        moduleUnits.objectUnitWrapper.add(o);
//                        moduleUnits.objectUnitScrollPane.addInnerListeners(o);
//                    }
//
//                    resizeContainer();
//                    LayerUnits.showLayer(moduleUnits.layerUnitsOnline);
//                });
//
//            } catch (SQLException e) {
//                javax.swing.SwingUtilities.invokeLater(() -> {
//                    Console.errorOut("Gathering object unit error", e);
//                    ManagerModuleUnits.reconnectMode(() -> refreshObjects(recentObject, true));
//                });
//            }
//        });

        try{
            UnitsDataTable[] dataArray = UnitsDataHandler.getAllDataSorted(refresh, filterSort, filterOrder);
            moduleUnits.objectUnitWrapper.removeAll();
            objects.clear();

            for (UnitsDataTable data : dataArray) {
                ObjectUnit o = new ObjectUnit(data);
                objects.add(o);
                moduleUnits.objectUnitWrapper.add(o);
                moduleUnits.objectUnitScrollPane.addInnerListeners(o);
            }

            resizeContainer();
            LayerUnits.showLayer(moduleUnits.layerUnitsOnline);
        }catch(SQLException e){
            Console.errorOut("Gathering object unit error", e);
            ManagerModuleUnits.reconnectMode(() -> refreshObjects(recentObject, true));
        }
    }
    
// Resize ----------------------------------------------------------------------------------------------------
    
    private static void resizeContainer(){
        int height = (objects.size() * 60);
        moduleUnits.objectUnitContainer.setPreferredSize(new Dimension(0, height));
        moduleUnits.objectUnitWrapper.setPreferredSize(new Dimension(0, height));
        moduleUnits.revalidate();
        moduleUnits.repaint();
    }
    
// Current Object --------------------------------------------------------------------------------------------
    
    private static ObjectUnit currentObject = null;
    private static int currentObjectIndex = -1;
    
    private static void resetCurrentObject(){
        currentObject = null;
        currentObjectIndex = -1;
    }
    
    private static void changeCurrentObject(int increment){
        if(currentObject == null || objects.size() <= 1) return;
        int i = currentObjectIndex + increment;
        if(i < 0 || i > objects.size() - 1) return;
        
        changeCurrentObject(objects.get(i));
    }
    
    public static void changeCurrentObject(ObjectUnit object){
        if(object == null) return;
        int i = 0;
        for(ObjectUnit o : objects){
            o.setActive(false);
            if(o == object){
                currentObjectIndex = i;
                moduleUnits.objectUnitScrollPane.scrollToObjectContent(i + 1);
            }
            i++;
        }
        object.setActive(true);
        currentObject = object;
        moduleUnits.objectUnitScrollPane.repaint();
    }
    
// Filter ----------------------------------------------------------------------------------------------------
    
    private static String filter = "";
    private static int filterCategory = -1;
    private static int filterSort = 1;
    private static DataTableOrder filterOrder = DataTableOrder.Desc;
    
    public static void filterObjectCategory(){
//        CategoryDialogFilter dialog = new CategoryDialogFilter(frame, filterCategory);
//        if(!dialog.isConfirmed()) return;
//        
//        filterCategory = dialog.getSelectedCategoryId();
//        checkFilterActive();
//        refreshObjects(false);
    }
    
    public static void filterObjectSearch(String f){
        filter = f;
        checkFilterActive();
        refreshObjects(false);
    }
    
    public static void clearFilterObject(boolean reloadObject){
//        filter = "";
//        filterCategory = -1;
//        filterSort = 1;
//        filterOrder = DataTableOrder.Desc;
//        
//        frame.sTextField1.setText("");
//        frame.sFilterPanel1.setActive(false);
//        
//        resetCurrentFilterTitlePanel();
//        
//        if(reloadObject) refreshObjects(false);
    }
    
    private static void checkFilterActive(){
//        if(!filter.equals("") || filterCategory != -1 || filterSort != 1 || filterOrder != DataTableOrder.Desc){
//            frame.sFilterPanel1.setActive(true);
//        }else{
//            frame.sFilterPanel1.setActive(false);
//        }
    }
    
// Add Edit Remove -------------------------------------------------------------------------------------------
    
    public static void addObject(){
//        ItemDetailsDialogUpsert dialog = new ItemDetailsDialogUpsert(frame, null);
//        if(!dialog.isConfirmed()) return;
//        
//        ItemsDataHandler.insertData(dialog.getNewData());
//        refreshObjects(true);
    }
    
    public static void removeObject(ObjectUnit object){
//        object.setDanger(true);
//        ItemDetailsDialogDelete dialog = new ItemDetailsDialogDelete(frame);
//        object.setDanger(false);
//        if(!dialog.isConfirmed()) return;
//        
//        ItemsDataHandler.deleteData(object.getId());
//        refreshObjects(true);
    }
    
    public static void editObject(ObjectUnit object){
//        ItemDetailsDialogUpsert dialog = new ItemDetailsDialogUpsert(frame, object);
//        if(!dialog.isConfirmed()) return;
//        
//        ItemsDataHandler.updateData(dialog.getNewData(), object.getId());
//        refreshObjects(object, true);
    }

}
