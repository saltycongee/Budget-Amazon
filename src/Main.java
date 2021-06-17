import database.DatabaseConnectionHandler;
import ui.*;
import delegates.*;
import model.*;

import java.util.ArrayList;
import java.util.Map;

public class Main implements LoginWindowDelegate, TerminalTransactionsDelegate, FirstLoginWindowDelegate,
        BuyerUIDelegate, PuchaseUIDelegate, CreateAccountDelegate, SellerUIDelegate, WarehouseManagerDelegate{
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;
    private FirstLoginWindow firstLoginWindow = null;
    private BuyerUI buyerUI = null;
    private CreateAccount createAccount = null;
    private SellerUI sellerUI = null;
    private WarehouseManager warehouseManager = null;
    private WarehouseInfo warehouseInfo = null;
    private WorkersUI workersUI = null;
    private ItemsUI itemsUI = null;
    private PurchaseUI purchaseUI = null;
    private BuyerFilterUI buyerFilterUI = null;
    boolean didConnect;
    private String buyerUser = "buyer";
    private String buyerPassword = "bbb";
    private String sellerUser = "seller";
    private String sellerPassword = "sss";


    public Main() {
        dbHandler = new DatabaseConnectionHandler();
        Map<String,String> env = System.getenv();
        didConnect  = dbHandler.login(env.get("ora_login"),env.get("ora_password"));
        firstLoginWindow = new FirstLoginWindow();
        firstLoginWindow.showFrame(this);


    }

    public static void main(String[] args) {
        Main main = new Main();
    }

    public void buyerLogin(String username, String password) {
        boolean validLogin = dbHandler.customerLogin(username, password) != null;

        if (didConnect && validLogin) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();
            buyerUI = new BuyerUI(dbHandler, dbHandler.customerLogin(username, password));
            buyerUI.showFrame(this, this);

        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }
    public void sellerLogin(String username, String password) {
        boolean validLogin = dbHandler.sellerLogin(username, password) != null;
        if (didConnect && validLogin) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();
            sellerUI = new SellerUI(dbHandler, dbHandler.sellerLogin(username, password));
            sellerUI.showFrame(this, this);

        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    @Override
    public void items() {
        warehouseManager.dispose();
        itemsUI = new ItemsUI(dbHandler);
        itemsUI.showFrame(this);
    }

    @Override
    public void workers() {
        warehouseManager.dispose();
        workersUI = new WorkersUI(dbHandler);
        workersUI.showFrame(this);
    }

    @Override
    public void warehouses() {
        warehouseManager.dispose();
        warehouseInfo = new WarehouseInfo(dbHandler);
        warehouseInfo.showFrame(this);
    }

    @Override
    public void addListing() {

    }


    @Override
    public void createBuyerAccount(Integer id, String fname, String lname, String email, Integer phone, String creditCard,
                              String postalCode, Integer buildingNum, String password) {
        createAccount.dispose();
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this, this);
    }

    @Override
    public void createSellerAccount(Integer id, String fname, String lname, String email, Integer phone, String creditCard,
                                   String postalCode, Integer buildingNum, String password) {
        createAccount.dispose();
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this, this);
    }

    @Override
    public void create() {
        loginWindow.dispose();
        createAccount = new CreateAccount();
        createAccount.showFrame(this, this);
    }
    @Override
    public void whichUser(String user){
        firstLoginWindow.dispose();
        if (user == "B/S") {
            loginWindow = new LoginWindow();
            loginWindow.showFrame(this,this );
        } else if (user == "Warehouse") {
            warehouseManager = new WarehouseManager();
            warehouseManager.showFrame(this, this);
        } else if (user == "ItemsUI") {
            itemsUI = new ItemsUI(dbHandler);
            itemsUI.showFrame(this);
        } else if (user == "Reset") {
            firstLoginWindow = new FirstLoginWindow();
            firstLoginWindow.showFrame(this);
        }
    }

    @Override
    public void databaseSetup() {
        dbHandler.databaseSetup();;
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Insert a branch with the given info
     */
    public void insertBranch(BranchModel model) {
        dbHandler.insertBranch(model);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Delete branch with given branch ID.
     */
    public void deleteBranch(int branchId) {
        dbHandler.deleteBranch(branchId);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Update the branch name for a specific ID
     */

    public void updateBranch(int branchId, String name) {
        dbHandler.updateBranch(branchId, name);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Displays information about varies bank branches.
     */
    public void showBranch() {
        BranchModel[] models = dbHandler.getBranchInfo();

        for (int i = 0; i < models.length; i++) {
            BranchModel model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getId());
            System.out.printf("%-20.20s", model.getName());
            if (model.getAddress() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getAddress());
            }
            System.out.printf("%-15.15s", model.getCity());
            if (model.getPhoneNumber() == 0) {
                System.out.printf("%-15.15s", " ");
            } else {
                System.out.printf("%-15.15s", model.getPhoneNumber());
            }

            System.out.println();
        }
    }

    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }


    @Override
    public void purchase() {

    }

    @Override
    public void filter(String str, String[] filtered, ArrayList<Integer> colNumbers) {
        //buyerUI.dispose();
        buyerFilterUI = new BuyerFilterUI(dbHandler, str, filtered, colNumbers);
        buyerFilterUI.showFrame(this, this);
    }

    @Override
    public void purchaseItems() {

    }
}
