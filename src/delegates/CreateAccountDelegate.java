package delegates;

public interface CreateAccountDelegate {
    void createBuyerAccount(Integer id, String fname, String lname, String email, Integer phone, String creditCard, String postalCode,
                       Integer buildingNum, String password);

    void createSellerAccount(Integer id, String fname, String lname, String email, Integer phone, String creditCard, String postalCode,
                            Integer buildingNum, String password);
}
