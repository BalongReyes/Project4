package DatabaseSystem.UnitsData;

import ConsoleSystem.Console;
import ConsoleSystem.ConsoleColors;
import DatabaseSystem.DataTable.DataTableOrder;
import DatabaseSystem.Database;
import MainSystem.Main;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnitsDataHandler {

    private static ArrayList<UnitsDataTable> array = new ArrayList<>();
    
    public static void refreshData() throws SQLException {
        array.clear();
        
        // SECURED: Now uses executePreparedQuery
        Database.executePreparedQuery("SELECT * FROM units", (result) -> {
            while (result.next()) {
                UnitsDataTable data = new UnitsDataTable(result);
                if (!data.isError()) array.add(data);
            }
        });
        
        if (Main.debugDataHandlerRefresh) Console.out("UnitsDataHandler refreshed", ConsoleColors.YELLOW);
    }
    
    public static UnitsDataTable[] getAllData(boolean refresh) throws SQLException {
        if (refresh || array.isEmpty()) refreshData();
        return array.toArray(UnitsDataTable[]::new);
    }
    
// Search & Sort Methods =====================================================================================

    // REPLACED: Now uses high-speed SQL sorting instead of Java QuickSort
    public static UnitsDataTable[] getAllDataSorted(boolean refresh, int dataIndex, DataTableOrder order) throws SQLException {
        String columnName = getColumnName(dataIndex);
        String orderString = (order == DataTableOrder.Asc) ? "ASC" : "DESC";
        String query = "SELECT * FROM units ORDER BY " + columnName + " " + orderString;
        
        ArrayList<UnitsDataTable> sortedArray = new ArrayList<>();
        Database.executePreparedQuery(query, (result) -> {
            while (result.next()) {
                UnitsDataTable data = new UnitsDataTable(result);
                if (!data.isError()) sortedArray.add(data);
            }
        });
        
        return sortedArray.toArray(UnitsDataTable[]::new);
    }
    
    // REPLACED: Purged the old Java BinarySearch and renamed findDataByIdSecure to standard findDataById
    public static UnitsDataTable findDataById(int id) throws SQLException {
        UnitsDataTable[] resultHolder = new UnitsDataTable[1];
        
        Database.executePreparedQuery("SELECT * FROM units WHERE id = ?", (result) -> {
            if (result.next()) {
                resultHolder[0] = new UnitsDataTable(result);
            }
        }, id);
        
        return resultHolder[0];
    }

    // Helper method to safely translate DataTable indexes to SQL columns to prevent injection
    private static String getColumnName(int dataIndex) {
        return switch (dataIndex) {
            case UnitsDataTable.ID -> "id";
            case UnitsDataTable.TOWER -> "tower";
            case UnitsDataTable.FLOOR -> "floor";
            case UnitsDataTable.UNIT -> "unit";
            case UnitsDataTable.MODEL -> "model";
            case UnitsDataTable.BALCONY -> "balcony";
            case UnitsDataTable.FLOOR_AREA -> "floorarea";
            default -> "id";
        };
    }

// CRUD Operations ===========================================================================================
    
    public static void deleteData(int id) {
        try {
            Database.executePrepared("DELETE FROM units WHERE id = ?", id);
        } catch (SQLException e) {
            Console.errorOut("Deleting data from table units error", e);
        }
    }
    
    public static void insertData(UnitsDataTable data) {
        try {
            String query = "INSERT INTO units (tower, floor, unit, model, balcony, floorarea) VALUES (?, ?, ?, ?, ?, ?)";
            Database.executePrepared(query, 
                data.getTower(), data.getFloor(), data.getUnit(), 
                data.getModel(), data.getBalcony(), data.getFloorArea()
            );
        } catch (SQLException e) {
            Console.errorOut("Inserting data from table units error", e);
        }
    }
    
    public static void updateData(UnitsDataTable data, int id) {
        try {
            String query = "UPDATE units SET tower = ?, floor = ?, unit = ?, model = ?, balcony = ?, floorarea = ? WHERE id = ?";
            Database.executePrepared(query, 
                data.getTower(), data.getFloor(), data.getUnit(), 
                data.getModel(), data.getBalcony(), data.getFloorArea(), id 
            );
        } catch (SQLException e) {
            Console.errorOut("Updating data from table units error", e);
        }
    }
}