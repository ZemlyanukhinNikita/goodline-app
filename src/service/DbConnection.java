package service;


import org.flywaydb.core.Flyway;

import java.sql.*;

public class DbConnection {
        public static void getDbConnection(){
            System.out.println("Testing DB...");
            try {
                Class.forName("org.h2.Driver");
                String url = "jdbc:h2:file:./aaa;mv_store=false";
                String login = "admin";
                String password = "db_h2_17";
                System.out.println("Getting connection...");
                Connection con = DriverManager.getConnection(url, login, password);


                System.out.println("OK");
                try
                {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM \"USER\"");
                    while (rs.next()) {
                        String str = rs.getString("ID") + " " + rs.getString("LOGIN");
                        System.out.println(str);
                    }
                    rs.close();
                    stmt.close();
                } finally {
                    con.close();
                }

            }
            catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        public static void doMigration () {
            // Create the Flyway instance
            Flyway flyway = new Flyway();

            // Point it to the database
            flyway.setDataSource("jdbc:h2:file:./aaa;mv_store=false", "admin", "db_h2_17");

            // Start the migration
            flyway.migrate();


        }

}
