import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import model.CreditCardName;
import model.Customer;

public class Test {
    private static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static Map<String, String> env = System.getenv();
    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection(ORACLE_URL, env.get("ora_login"), env.get("ora_password"));
        test2(connection);
        Object[][] test = itemList();
        int a= 0;
    }
    public static Object[][] itemList(){
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from item";
            ResultSet count = stmt.executeQuery("SELECT COUNT(*) from item");
            count.next();
            int len = count.getInt(1);
            ResultSet rs = stmt.executeQuery(query);

            Object[][] out = new Object[len][7];
            int index = 0;
            while (rs.next()){
                out[index][0] = rs.getInt(2);
                out[index][1] = rs.getString(1);
                out[index][2] = rs.getInt(3);
                out[index][3] = rs.getInt(4);
                out[index][4] = rs.getInt(5);
                out[index][5] = rs.getInt(6);
                out[index][6] = rs.getInt(7);
                index++;
            }
            return out;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static Customer test3(Connection connection){
        Statement stmt = null;
        String email0 = "aaa@gmail.com";
        String pass0 = "aaa";
        try {
            stmt = connection.createStatement();
            String query = "select * from customer_accounts where email like '"+email0+"' and password like '" + pass0 + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                String email = rs.getString(1);
                String password = rs.getString(2);
                long phone_number = rs.getLong(3);
                String credit_card_info = rs.getString(4);
                int customerID = rs.getInt(5);
                int order_listID = rs.getInt(6);
                String postal_code = rs.getString(7);
                int building_number = rs.getInt(8);
                return new Customer(email,password,phone_number,credit_card_info,customerID,order_listID,postal_code,building_number);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
    public static void dropTables(Connection connection){
        String temp = "";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            ArrayList<String> list = new ArrayList<>();
            while (rs.next())
                list.add(rs.getString(1).toLowerCase());
            while (!list.isEmpty()) {
                temp = list.remove(0);
                stmt.execute("DROP TABLE " + temp + " cascade constraints ");
            }
        } catch (SQLException var7) {
            System.out.println("failed at " + temp);
            System.out.println(var7.getMessage());
        }

    }
    public static void test2(Connection connection) {
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
                command = command.replaceAll("(    |\s+|\t|\n)", " ");
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



}