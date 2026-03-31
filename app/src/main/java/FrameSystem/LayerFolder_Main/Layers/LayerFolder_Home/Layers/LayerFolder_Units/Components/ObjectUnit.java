
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Components;

import DatabaseSystem.UnitsData.UnitsDataTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Home.Layers.LayerFolder_Units.Managers.ManagerObjectUnits;
import FrameSystem.SLibrary.SComponents.SPanel;

public class ObjectUnit extends SPanel{

    private UnitsDataTable data;
    private boolean deleteBlocked = false, editBlocked = false;
    
// Constructor ===============================================================================================
    
    public ObjectUnit(UnitsDataTable data){
        this.data = data;
        
        initComponents();
        addInnerListeners(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                if(evt.getButton() == MouseEvent.BUTTON1){
                    setFocus();
                }
            }
            @Override
            public void mouseClicked(MouseEvent evt){
                if(evt.getClickCount() == 2 && !evt.isConsumed()){
                    evt.consume();
                    edit();
                }
            }
        });
        sPanelHighlight1.applyHoverInnerListener();
        
        sLabel2.setText(
            switch(data.getModel()){
                case 1 -> "1 Bedroom";
                case 2 -> "2 Bedroom";
                case 3 -> "Studio";
                default -> "NULL";
            }
            +
            switch(data.getBalcony()){
                case 1 -> "w/ Balcony";
                default -> "";
            }
        );
        
//        this.deleteBlocked = deleteBlocked;
//        if(deleteBlocked){
//            deleteButton.setEnabled(false);
//            deleteButton.overrideColor = new Color(32, 33, 43);
//        }
//        
//        this.editBlocked = editBlocked;
//        if(editBlocked){
//            editButton.setEnabled(false);
//            editButton.overrideColor = new Color(32, 33, 43);
//        }
        
//        if(data.getCategoryId() != null){
//            try{
//                CategoryDataTable category = CategoryDataHandler.findDataById(false, data.getCategoryId());
//                if(category != null) sLabel5.setScaledIcon(category.getImage());
//            }catch(SQLException e){
//                Console.errorOut("Getting category of item error", e);
//            }
//        }
//        if(!data.getBarcode().isEmpty()){
//            sLabel6.setScaledIcon(new ImageIcon(getClass().getResource("/Icons/barcodeStatus.png")));
//        }
        
//        deleteButton.addMouseListener((MousePressedAdaptor) evt -> {
//            delete();
//        });
//        editButton.addMouseListener((MousePressedAdaptor) evt -> {
//            edit();
//        });
    }
    
// Main Methods ==============================================================================================

    public UnitsDataTable getData(){
        return data;
    }
    
    public int getId(){
        return data.getId();
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private void setFocus(){
        this.requestFocus();
        ManagerObjectUnits.changeCurrentObject(this);
    }
    
    public void setActive(boolean active){
        sPanelHighlight1.setActive(active);
    }
    
    public void setDanger(boolean danger){
        sPanelHighlight1.setDanger(danger);
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    private void delete(){
        setFocus();
        if(deleteBlocked) return;
        ManagerObjectUnits.removeObject(this);
    }
    
    private void edit(){
        setFocus();
        if(editBlocked) return;
        ManagerObjectUnits.editObject(this);
    }
    
// Generated =================================================================================================
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sPanelHighlight1 = new FrameSystem.SLibrary.SComponents.SPanelHighlight();
        sLabel1 = new FrameSystem.SLibrary.SComponents.SLabel();
        sPanel1 = new FrameSystem.SLibrary.SComponents.SPanel();
        sLabel2 = new FrameSystem.SLibrary.SComponents.SLabel();
        sLabel4 = new FrameSystem.SLibrary.SComponents.SLabel();
        sLabel9 = new FrameSystem.SLibrary.SComponents.SLabel();
        sLabel10 = new FrameSystem.SLibrary.SComponents.SLabel();
        sPanel3 = new FrameSystem.SLibrary.SComponents.SPanel();
        sLabel7 = new FrameSystem.SLibrary.SComponents.SLabel();
        sLabel8 = new FrameSystem.SLibrary.SComponents.SLabel();

        setBackground(new java.awt.Color(24, 29, 37));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1010, 60));

        sPanelHighlight1.setHoverBackgroundColor(new java.awt.Color(240, 240, 240));
        sPanelHighlight1.setInactiveForegroundColor(new java.awt.Color(240, 240, 240));
        sPanelHighlight1.setRadius(10);
        sPanelHighlight1.setRounded(true);
        sPanelHighlight1.setPreferredSize(new java.awt.Dimension(100, 50));

        sLabel1.setBackground(new java.awt.Color(255, 255, 255));
        sLabel1.setForeground(new java.awt.Color(56, 56, 56));
        sLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sLabel1.setText(String.valueOf(data.getTower()));
        sLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sLabel1.setPreferredSize(new java.awt.Dimension(75, 16));

        sPanel1.setOpaque(false);
        sPanel1.setLayout(new java.awt.GridLayout(1, 0));

        sLabel2.setBackground(new java.awt.Color(255, 255, 255));
        sLabel2.setForeground(new java.awt.Color(56, 56, 56));
        sLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sLabel2.setText("Model");
        sLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sPanel1.add(sLabel2);

        sLabel4.setForeground(new java.awt.Color(56, 56, 56));
        sLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sLabel4.setText(String.valueOf(data.getFloorArea())
        );
        sLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sPanel1.add(sLabel4);

        sLabel9.setForeground(new java.awt.Color(56, 56, 56));
        sLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sLabel9.setText("Occupancy Type");
        sLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sPanel1.add(sLabel9);

        sLabel10.setBackground(new java.awt.Color(255, 255, 255));
        sLabel10.setForeground(new java.awt.Color(56, 56, 56));
        sLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sLabel10.setText("Unit Status");
        sLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sPanel1.add(sLabel10);

        sPanel3.setBackground(new java.awt.Color(255, 255, 255));
        sPanel3.setOpaque(false);
        sPanel3.setPreferredSize(new java.awt.Dimension(75, 0));

        javax.swing.GroupLayout sPanel3Layout = new javax.swing.GroupLayout(sPanel3);
        sPanel3.setLayout(sPanel3Layout);
        sPanel3Layout.setHorizontalGroup(
            sPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );
        sPanel3Layout.setVerticalGroup(
            sPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        sLabel7.setForeground(new java.awt.Color(56, 56, 56));
        sLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sLabel7.setText(String.valueOf(data.getFloor()));
        sLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sLabel7.setPreferredSize(new java.awt.Dimension(75, 16));

        sLabel8.setForeground(new java.awt.Color(56, 56, 56));
        sLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sLabel8.setText(String.valueOf(data.getUnit()));
        sLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sLabel8.setPreferredSize(new java.awt.Dimension(75, 16));

        javax.swing.GroupLayout sPanelHighlight1Layout = new javax.swing.GroupLayout(sPanelHighlight1);
        sPanelHighlight1.setLayout(sPanelHighlight1Layout);
        sPanelHighlight1Layout.setHorizontalGroup(
            sPanelHighlight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sPanelHighlight1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(sLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(sPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        sPanelHighlight1Layout.setVerticalGroup(
            sPanelHighlight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(sPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(sPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sPanelHighlight1, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sPanelHighlight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private FrameSystem.SLibrary.SComponents.SLabel sLabel1;
    private FrameSystem.SLibrary.SComponents.SLabel sLabel10;
    private FrameSystem.SLibrary.SComponents.SLabel sLabel2;
    private FrameSystem.SLibrary.SComponents.SLabel sLabel4;
    private FrameSystem.SLibrary.SComponents.SLabel sLabel7;
    private FrameSystem.SLibrary.SComponents.SLabel sLabel8;
    private FrameSystem.SLibrary.SComponents.SLabel sLabel9;
    private FrameSystem.SLibrary.SComponents.SPanel sPanel1;
    private FrameSystem.SLibrary.SComponents.SPanel sPanel3;
    private FrameSystem.SLibrary.SComponents.SPanelHighlight sPanelHighlight1;
    // End of variables declaration//GEN-END:variables
}
