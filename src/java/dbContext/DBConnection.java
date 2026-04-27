package dbContext;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String Url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String User = "postgres";
    private static final String Password = "Theany2712$$";
    
    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(Url, User, Password);
    }
}
