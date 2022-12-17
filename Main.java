import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BillingSystem bs = new BillingSystem();
        bs.getConnection();
    }
}




