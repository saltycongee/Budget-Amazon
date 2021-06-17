package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class Setup {
    private static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static Map<String, String> env = System.getenv();
    public static void addData(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            dropTables(connection);
            FileReader fr = new FileReader("src/scripts/warehouseDatabaseSetup.sql") ;
//            FileReader fr = new FileReader("src/scripts/test.sql") ;
            BufferedReader br = new BufferedReader(fr) ;
            while (true){

                String command = "";
                String next = null;
                while(!command.contains(";")){
                    next = br.readLine();
                    if (next == null)
                        break;
                    command = command + next;
                }
                command = command.strip();
                command = command.replaceAll("(    |\s|\t|\n)+", " ");
                command = command.replaceAll(";", "");
                command = command.replaceFirst("VALUES",  " VALUES");
                if (next == null)
                    break;
                System.out.println(command);
                stmt.execute(command);

            }
            stmt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    private static void dropTables(Connection connection){
        String temp = "";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            ArrayList<String> list = new ArrayList<>();
            while (rs.next())
                list.add(rs.getString(1).toLowerCase());
            while (!list.isEmpty()) {
                temp = list.remove(0);
                stmt.executeUpdate("DROP TABLE " + temp + " cascade constraints ");
            }
        } catch (SQLException var7) {
            System.out.println("failed at " + temp);
            System.out.println(var7.getMessage());
        }

    }
}
