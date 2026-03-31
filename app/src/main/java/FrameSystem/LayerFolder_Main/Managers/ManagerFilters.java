
package FrameSystem.LayerFolder_Main.Managers;

import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Module.ModuleUnits;
import MainSystem.Manager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagerFilters extends Manager{

    public static ModuleUnits moduleUnits;
    
    public static void initDefault(){
        moduleUnits = frame.moduleHome.moduleUnits;
        
        moduleUnits.sFilterTower1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                moduleUnits.sFilterTower1.toggleActive();
            }
        });
        moduleUnits.sFilterTower2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                moduleUnits.sFilterTower2.toggleActive();
            }
        });
        moduleUnits.sFilterTower3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                moduleUnits.sFilterTower3.toggleActive();
            }
        });
    }

}
