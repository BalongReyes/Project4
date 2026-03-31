package DatabaseSystem.AccountsData;

import ConsoleSystem.Console;
import ConsoleSystem.ConsoleColors;
import DatabaseSystem.Database;
import MainSystem.Main;
import MainSystem.Managers.ManagerLogin;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AccountsDataHandler {
    
    private static ArrayList<AccountsDataTable> array = new ArrayList<>();
    
    public static void refreshData() throws SQLException {
        array.clear();
        
        Database.executePreparedQuery("SELECT * FROM accounts", (result) -> {
            while (result.next()) {
                AccountsDataTable data = new AccountsDataTable(result);
                if (ManagerLogin.isLoggedIn() && ManagerLogin.getAccountLoggedIn().idEquals(data.getId())) {
                    ManagerLogin.updateAccountLoggedIn(data);
                }
                if (!data.isError()) array.add(data);
            }
        });
        
        // --- THIS IS THE FIXED BLOCK ---
        if (array.isEmpty()) {
            Console.out("No accounts found, inserting default account");
            
            // 1. Create the default account object. We leave password and salt blank ("") 
            // because our new insertData method handles generating them securely!
            AccountsDataTable defaultAccount = new AccountsDataTable(
                null, "SuperAdmin", 1, "SuperAdmin", "", "", 1, java.sql.Date.valueOf(LocalDate.now())
            );
            
            // 2. Call our updated insertData, passing the object AND the raw password "SuperAdmin"
            insertData(defaultAccount, "SuperAdmin");
            
            refreshData();
            return;
        }
        // -------------------------------
        
        if (Main.debugDataHandlerRefresh) Console.out("AccountsDataHandler refreshed", ConsoleColors.YELLOW);
    }
    
    public static AccountsDataTable[] getAllData(boolean refresh) throws SQLException {
        if (refresh || array.isEmpty()) refreshData();
        return array.toArray(AccountsDataTable[]::new);
    }
    
// Find ======================================================================================================
    
    public static AccountsDataTable findDataById(int id) throws SQLException {
        AccountsDataTable[] resultHolder = new AccountsDataTable[1];
        Database.executePreparedQuery("SELECT * FROM accounts WHERE id = ?", (result) -> {
            if (result.next()) resultHolder[0] = new AccountsDataTable(result);
        }, id);
        return resultHolder[0];
    }
    
    public static AccountsDataTable findDataByUserId(int userId) throws SQLException {
        AccountsDataTable[] resultHolder = new AccountsDataTable[1];
        Database.executePreparedQuery("SELECT * FROM accounts WHERE userID = ?", (result) -> {
            if (result.next()) resultHolder[0] = new AccountsDataTable(result);
        }, userId);
        return resultHolder[0];
    }
    
    public static AccountsDataTable findDataByUsername(String username) throws SQLException {
        AccountsDataTable[] resultHolder = new AccountsDataTable[1];
        Database.executePreparedQuery("SELECT * FROM accounts WHERE username = ?", (result) -> {
            if (result.next()) resultHolder[0] = new AccountsDataTable(result);
        }, username);
        return resultHolder[0];
    }
    
// Check Duplicate ===========================================================================================
    
    public static boolean checkDuplicateUserId(int oldUserId, int newUserId) {
        if (oldUserId == newUserId) return false;
        try {
            return findDataByUserId(newUserId) != null;
        } catch (SQLException e) {
            Console.errorOut("Check duplicate userId error", e);
        }
        return true;
    }
    
    public static boolean checkDuplicateUsername(String oldUsername, String newUsername) {
        if (oldUsername.equals(newUsername)) return false;
        try {
            return findDataByUsername(newUsername) != null;
        } catch (SQLException e) {
            Console.errorOut("Check duplicate username error", e);
        }
        return true;
    }
    
// ===========================================================================================================
    
    private static MessageDigest md;
    
    public static AccountsDataTable loginAccount(String usernameOrId, char[] charPassword) throws SQLException {
        String password = String.valueOf(charPassword);
        
        AccountsDataTable account = null;
        try {
            account = findDataByUserId(Integer.parseInt(usernameOrId));
        } catch (NumberFormatException e) {}
        
        if (account == null) account = findDataByUsername(usernameOrId);
        if (account == null) return null;
        
        // Hash the inputted password USING the account's unique salt
        String hashedInput = hashPassword(password, account.getSalt());
        
        if (account.checkPassword(hashedInput)) return account;
        return null;
    }

    public static boolean verifyLogin(AccountsDataTable data, String password) {
        return data.checkPassword(hashPassword(password, data.getSalt()));
    }
    
    protected static String hashPassword(String password) {
        // I added the '16' parameter here to ensure it correctly generates Hexadecimal output!
        return new BigInteger(1, md.digest(password.getBytes())).toString(16);
    }
    
// Security Methods ==========================================================================================
    
    // Generates a random 16-byte string to use as the salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hashes the password using PBKDF2 and the unique salt
    public static String hashPassword(String password, String salt) {
        try {
            // 65536 iterations slows down hackers significantly
            KeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), 65536, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            Console.errorOut("Password Hashing Error", e);
            return null;
        }
    }
    
// CRUD ======================================================================================================
    
    public static void deleteData(int id) {
        try {
            Database.executePrepared("DELETE FROM accounts WHERE id = ?", id);
        } catch (SQLException e) {
            Console.errorOut("Deleting data from table accounts error", e);
        }
    }
    
    public static void insertData(AccountsDataTable data, String rawPassword) {
        // Generate a fresh salt for the new user
        String newSalt = generateSalt();
        String hashedPassword = hashPassword(rawPassword, newSalt);

        // Make sure to include "salt" in your SQL query!
        String insertAccountSql = "INSERT INTO accounts (name, userID, username, password, salt, role, lastChange) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertSettingsSql = "INSERT INTO settings (accountId, autoLogoutTime, preferredDate) VALUES (?, '120', '1')";
        
        Connection conn = Database.getConnection();
        if (conn == null) return;

        try {
            conn.setAutoCommit(false); 
            try (PreparedStatement accStmt = conn.prepareStatement(insertAccountSql, Statement.RETURN_GENERATED_KEYS)) {
                accStmt.setString(1, data.getName());
                accStmt.setInt(2, data.getUserId());
                accStmt.setString(3, data.getUsername());
                accStmt.setString(4, hashedPassword); // Save the secure hash
                accStmt.setString(5, newSalt);        // Save the unique salt
                accStmt.setInt(6, data.getRole());
                accStmt.setDate(7, data.getLastChange());
                accStmt.executeUpdate();
                
                try (ResultSet generatedKeys = accStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newAccountId = generatedKeys.getInt(1);
                        try (PreparedStatement setStmt = conn.prepareStatement(insertSettingsSql)) {
                            setStmt.setInt(1, newAccountId);
                            setStmt.executeUpdate();
                        }
                    }
                }
            }
            conn.commit(); 
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { } 
            Console.errorOut("Inserting data from table accounts error", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { }
        }
    }

    public static void updatePassword(String rawPassword, int id) {
        try {
            // If they change their password, give them a fresh salt too!
            String newSalt = generateSalt();
            String hashedPassword = hashPassword(rawPassword, newSalt);

            String query = "UPDATE accounts SET password = ?, salt = ?, lastChange = ? WHERE id = ?";
            Database.executePrepared(query, 
                hashedPassword, newSalt, java.sql.Date.valueOf(LocalDate.now()), id
            );
        } catch (SQLException e) {
            Console.errorOut("Updating password data error", e);
        }
    }
    
    public static void updateData(AccountsDataTable data, int id) {
        try {
            String query = "UPDATE accounts SET name = ?, userId = ?, username = ?, password = ?, role = ?, lastChange = ? WHERE id = ?";
            Database.executePrepared(query, 
                data.getName(), data.getUserId(), data.getUsername(), 
                data.getPassword(), data.getRole(), data.getLastChange(), id
            );
        } catch (SQLException e) {
            Console.errorOut("Updating data from table accounts error", e);
        }
    }
    
// Static Initialization Block ===============================================================================

    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Console.errorOut("Message Digest Error", e);
        }
    }
}