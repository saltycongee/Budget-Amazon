package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
	//private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	
	private Connection connection = null;
	
	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void deleteBranch(int branchId) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
			ps.setInt(1, branchId);
			
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
			}
			
			connection.commit();
	
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	
	public void insertBranch(BranchModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
			ps.setInt(1, model.getId());
			ps.setString(2, model.getName());
			ps.setString(3, model.getAddress());
			ps.setString(4, model.getCity());
			if (model.getPhoneNumber() == 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, model.getPhoneNumber());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	
	public BranchModel[] getBranchInfo() {
		ArrayList<BranchModel> result = new ArrayList<BranchModel>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");
		
//    		// get info on ResultSet
//    		ResultSetMetaData rsmd = rs.getMetaData();
//
//    		System.out.println(" ");
//
//    		// display column names;
//    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
//    			// get column name and print it
//    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//    		}
			
			while(rs.next()) {
				BranchModel model = new BranchModel(rs.getString("branch_addr"),
													rs.getString("branch_city"),
													rs.getInt("branch_id"),
													rs.getString("branch_name"),
													rs.getInt("branch_phone"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}	
		
		return result.toArray(new BranchModel[result.size()]);
	}
	
	public void updateBranch(int id, String name) {
		try {
		  PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
		  ps.setString(1, name);
		  ps.setInt(2, id);
		
		  int rowCount = ps.executeUpdate();
		  if (rowCount == 0) {
		      System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
		  }
	
		  connection.commit();
		  
		  ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}	
	}
	
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}
	
			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(true);
			databaseSetup();
			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}

	}

	private void rollbackConnection() {
		try  {
			connection.rollback();	
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void databaseSetup() {
		Setup.addData(connection);
	}

	public Object[][] itemList(){
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
				out[index][4] = rs.getDouble(5);
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
	public Customer customerLogin(String email_address, String pass){
		try {
			Statement stmt = connection.createStatement();
			String query = "select * from customer_accounts where email like '"+email_address+"' and password like '" + pass + "'";
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
	public SellerAccount sellerLogin(String email_address, String pass){
		try {
			Statement stmt = connection.createStatement();
			String query = "select * from seller_account where email like '"+email_address+"' and password like '" + pass + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				int seller_id = rs.getInt(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String credit_card_info = rs.getString(4);
				long phone_number = rs.getLong(5);
				return new SellerAccount(seller_id,email,password,credit_card_info,phone_number);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return null;
	}
	public void modifyItem(Item item) {
		try {
			PreparedStatement ps = connection.prepareStatement("update item set count = count + ? where item_id = ?");
			ps.setInt(1, item.getCount());
			ps.setInt(2, item.getItemID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public void modifyItem(int itemID, int itemCount) {
		try {
			PreparedStatement ps = connection.prepareStatement("update item set count = count - ? where item_id = ?");
			ps.setInt(1, itemCount);
			ps.setInt(2, itemID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	public int getItemCount(Item item){
		try {
			Statement stmt = connection.createStatement();
			ResultSet count = stmt.executeQuery("SELECT COUNT(*) from item where Item_ID = "+item.getItemID());
			count.next();
			int out = count.getInt(1);

			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return -1;
	}

	public int getItemCount(int pitemID) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet count = stmt.executeQuery("SELECT count from item where Item_ID = " + pitemID);
			count.next();
			int out = count.getInt(1);

			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return -1;
	}

	public void addItem(Item item) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, item.getItem_Name());
			ps.setInt(2, item.getItemID());
			ps.setInt(3, item.getCount());
			ps.setInt(4, item.getWeight());
			ps.setDouble(5, item.getPrice());
			ps.setInt(6, item.getSellerID());
			ps.setInt(7, item.getWarehouseID());
			ps.executeUpdate();
			System.out.println("Added "+ ps.toString());
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	public void removeItem(int pitemID) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeQuery("DELETE FROM item where Item_ID = " + pitemID);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	public Integer[] getWarehouseID(){
		try {
			Statement stmt = connection.createStatement();
			ResultSet count = stmt.executeQuery("SELECT COUNT(*) from item");
			count.next();
			int len = count.getInt(1);
			String query = "select Warehouse_ID from Warehouse";
			ResultSet rs = stmt.executeQuery(query);
			Integer[] out = new Integer[len];
			int index = 0;
			while (rs.next()){
				out[index] = rs.getInt(1);
				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return null;
	}
	public Integer[] getItemID(){
		try {
			Statement stmt = connection.createStatement();
			ResultSet count = stmt.executeQuery("SELECT COUNT(*) from item");
			count.next();
			int len = count.getInt(1);
			String query = "select Item_ID from Item";
			ResultSet rs = stmt.executeQuery(query);
			Integer[] out = new Integer[len];
			int index = 0;
			while (rs.next()){
				out[index] = rs.getInt(1);
				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return null;
	}
	public Object[][] getManages(int managerID) {
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM (SELECT w.* FROM (Workers w LEFT JOIN Workers m ON w.Manager_ID= m.worker_ID) where m.worker_ID = " +managerID + ")";
			ResultSet count = stmt.executeQuery("SELECT COUNT(*) FROM (SELECT * FROM (SELECT w.* FROM (Workers w LEFT JOIN Workers m ON w.Manager_ID= m.worker_ID) where m.worker_ID = " +managerID+"))");
			count.next();
			int len = count.getInt(1);
			ResultSet rs = stmt.executeQuery(query);

			Object[][] out = new Object[len][5];
			int index = 0;
			while (rs.next()) {
				out[index][0] = rs.getInt(1);
				out[index][1] = rs.getInt(2);
				out[index][2] = rs.getString(3);
				out[index][3] = rs.getLong(4);
				out[index][4] = rs.getInt(5);

				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	public Object[][] getWorkers() {
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * from workers";
			ResultSet count = stmt.executeQuery("SELECT COUNT(*) from Workers");
			count.next();
			int len = count.getInt(1);
			ResultSet rs = stmt.executeQuery(query);

			Object[][] out = new Object[len][5];
			int index = 0;
			while (rs.next()){
				out[index][0] = rs.getInt(1);
				out[index][1] = rs.getInt(2);
				out[index][2] = rs.getString(3);
				out[index][3] = rs.getLong(4);
				out[index][4] = rs.getInt(5);
				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	public CreditCardName getCreditCardName(String creditCardInfo){
		try {
			Statement stmt = connection.createStatement();
			String query = "select * from Credit_Card_Name where Credit_Card_Information like '" + creditCardInfo +"'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				String creditCardInf = rs.getString(1);
				String name = rs.getString(2);
				String lname = rs.getString(3);
				return new CreditCardName(creditCardInf,name,lname);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	public SellerAccount getSellerAccount(Item item){
		try {
			Statement stmt = connection.createStatement();
			String query = "select * from seller_account where seller_id = " + item.getSellerID() ;
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				int id = rs.getInt(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String credit_card_info = rs.getString(4);
				long phone_number = rs.getLong(5);
				return new SellerAccount(id,email,password,credit_card_info,phone_number);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	public Warehouse getWareHouse(Item item) {
		try {
			Statement stmt = connection.createStatement();
			String query = "select * from Warehouse where Warehouse_ID = " + item.getWarehouseID() ;
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				int warehouseID = rs.getInt(1);
				String postalCode = rs.getString(2);
				int buildingNumber = rs.getInt(3);
				return new Warehouse(warehouseID,postalCode,buildingNumber);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	public PostalCodeLocation getPostalCodeLocation(String postalCode){
		try {
			Statement stmt = connection.createStatement();
			String query = "select * from Postal_Code_Locations where Postal_Code like '" + postalCode +"'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				String city = rs.getString(2);
				String street = rs.getString(3);
				return new PostalCodeLocation(postalCode,city,street);

			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		System.out.println(postalCode);
		return null;
	}
	public Object[][] getManageCounts(){
		try {
			Statement stmt = connection.createStatement();
			String query = "Select Manager_ID, COUNT(*) FROM Workers GROUP BY Manager_ID";
			ResultSet count = stmt.executeQuery("Select count(*) FROM (Select Manager_ID, COUNT(*) FROM Workers GROUP BY Manager_ID)");
			count.next();
			int len = count.getInt(1);
			ResultSet rs = stmt.executeQuery(query);
			Object[][] out = new Object[len][2];
			int index = 0;
			while (rs.next()){
				out[index][0] = rs.getString(1);
				out[index][1] = rs.getInt(2);
				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	public Object[][] getDivision(){
		try {
			Statement stmt = connection.createStatement();
			String query = "Select wow.seller_ID, c.Name, c.Last_name from Credit_card_name c, (Select s.seller_ID, s.Credit_Card_Information from Seller_Account s WHERE NOT EXISTS (SELECT * from Warehouse w WHERE NOT EXISTS (SELECT i.seller_ID FROM item i WHERE s.seller_ID = i.seller_ID and w.warehouse_ID = i.warehouse_ID))) wow where wow.Credit_Card_Information = c.Credit_Card_Information";
			ResultSet count = stmt.executeQuery("Select count(*) from (Select s.seller_ID from Seller_Account s WHERE NOT EXISTS (SELECT * from Warehouse w WHERE NOT EXISTS (SELECT i.seller_ID FROM item i WHERE s.seller_ID = i.seller_ID and w.warehouse_ID = i.warehouse_ID)))");
			count.next();
			int len = count.getInt(1);
			ResultSet rs = stmt.executeQuery(query);
			Object[][] out = new Object[len][3];
			int index = 0;
			while (rs.next()){
				out[index][0] = rs.getInt(1);
				out[index][1] = rs.getString(2);
				out[index][2] = rs.getString(3);
				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	public Object[][] getWarehouses() {
		try {
			Statement stmt = connection.createStatement();
			String query = "select * from warehouse";
			ResultSet count = stmt.executeQuery("SELECT COUNT(*) from warehouse");
			count.next();
			int len = count.getInt(1);
			ResultSet rs = stmt.executeQuery(query);

			Object[][] out = new Object[len][3];
			int index = 0;
			while (rs.next()) {
				out[index][0] = rs.getInt(1);
				out[index][1] = rs.getString(2);
				out[index][2] = rs.getInt(3);
				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	public Object[][] filteredItemList(String str, ArrayList<Integer> colNumbers) {
		try {
			Statement stmt = connection.createStatement();
			String query = "select " + str + " from item";
			ResultSet count = stmt.executeQuery("SELECT COUNT(*) from item");
			count.next();
			int len = count.getInt(1);
			ResultSet rs = stmt.executeQuery(query);

			Object[][] out = new Object[len][colNumbers.size()];
			int index = 0;
			while (rs.next()) {
				for (int k = 0; k < colNumbers.size(); k++) {
					if (colNumbers.get(k) == 1) {
						out[index][k] = rs.getString(k + 1);
					} else if (colNumbers.get(k) == 5) {
						out[index][k] = rs.getDouble( k + 1);
					} else {
						out[index][k] = rs.getInt(k + 1);
					}
				}
				index++;
			}
			return out;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
}
