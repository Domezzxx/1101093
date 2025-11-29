package org.mavensample;
import org.apache.commons.lang3.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class MavenDemo {
    private static final String URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String USER = "root";
    private static final String PASSWord = "DomeDome55&55";

    public static void main(String[] args) {
        Connection con = null;
        Statement stat = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWord);
            System.out.println("Connect to the DB");

            stat = con.createStatement();
            String SQL = "SELECT * FROM actor";
            ResultSet result = stat.executeQuery(SQL);

            while (result.next()) {
                int actor_id = result.getInt("actor_id");
                String first_name = result.getString("first_name");
                System.out.println("actor_id: " + actor_id + ", first_name: " + first_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) stat.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}