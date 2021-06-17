package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CreditCardName{
    private final String creditCardInfo;
    private final String name;
    private final String lName;

    public CreditCardName(String creditCardInfo, String name, String lName) {
        this.creditCardInfo = creditCardInfo;
        this.name = name;
        this.lName = lName;
    }

    public String getCreditCardInfo() {
        return this.creditCardInfo;
    }

    public String getName() {
        return this.name;
    }


    public String getlName() {
        return lName;
    }
}
