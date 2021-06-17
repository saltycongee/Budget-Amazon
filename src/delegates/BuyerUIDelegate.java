package delegates;

import java.util.ArrayList;

public interface BuyerUIDelegate {
    public void purchase();

    public void filter(String str, String [] filtered, ArrayList<Integer> colNumbers);
}
