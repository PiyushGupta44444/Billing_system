
import java.sql.*;
import java.util.Scanner;

public class BillingSystem {
    private Connection connection;
    private final Scanner sc = new Scanner(System.in);
    private Customers customer;
    private orders order;
    private Invoices invoice;
    private Payments payment;
    public void customerInfo(){
        this.customer = new Customers();
        System.out.println("Your First Name");
        this.customer.setFirst(sc.nextLine());
        System.out.println("Your Last Name");
        this.customer.setLast(sc.nextLine());
        System.out.println("Your Mail");
        this.customer.setMail(sc.nextLine());
        System.out.println("Your Phone Number");
        this.customer.setPhoneNumber(sc.nextInt());
    }
    public  void InsertCustomerInfo() {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into customers(first_name,last_name,phone_number,email_id)values(?,?,?,?);");
            statement.setString(1, this.customer.getFirst());
            statement.setString(2, this.customer.getLast());
            statement.setString(3, String.valueOf(this.customer.getPhoneNumber()));
            statement.setString(4, this.customer.getMail());
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement("select* from customers where phone_number=?");
            statement1.setString(1,String.valueOf(this.customer.getPhoneNumber()));
            ResultSet r=statement1.executeQuery();
            while(r.next()) {
                this.customer.setCustomerId(r.getInt("customer_id"));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public  void menu(){
        try {
            Statement statement2 = connection.createStatement();
            ResultSet rs = statement2.executeQuery("Select* from menu;");
            while ((rs.next())) {
                System.out.print("Press" + " " + rs.getInt("item_Id") + " " + "for" + " ");
                System.out.print(rs.getString("item_name") + "->");
                System.out.println(rs.getInt("price"));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public  void selectFromMenu() {
        try {
            this.order = new orders();
            int bill=0;
            System.out.println("Select your Item");
            int itemId = sc.nextInt();
            while (itemId != -1) {
                PreparedStatement statement3 = connection.prepareStatement("Select* from menu WHERE item_Id = ?");
                statement3.setString(1, String.valueOf(itemId));
                ResultSet rs2 = statement3.executeQuery();
                System.out.println("select Quantity");
                int quantity = sc.nextInt();
                sc.nextLine();
                while (rs2.next()) {
                    this.order.push(rs2.getString("item_name"),quantity);
                    bill += rs2.getInt("price") * quantity;
                }
                System.out.println("Select -1 to exit or Select any number greater than 0 to continue");
                itemId = sc.nextInt();
                sc.nextLine();
            }
            this.order.setTotalBill(bill);
            System.out.println("totalBill = " + this.order.getTotalBill());
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public  void insertOrderInfo(){
        try {
            PreparedStatement statement4 = connection.prepareStatement("insert into orders(customer_id,order_date_time,items)" + "values(?,now(),?)");
            statement4.setString(1,String.valueOf(this.customer.getCustomerId()));
            statement4.setObject(2,this.order.getMap().toString());
            statement4.execute();

            PreparedStatement statement5=connection.prepareStatement("select * from orders where customer_id=?");
           statement5.setString(1,String.valueOf(this.customer.getCustomerId()));
            ResultSet rs3= statement5.executeQuery();
            while (rs3.next()){
                this.order.setOrderId(rs3.getInt("order_id"));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void insertIntoInvoice(){
        try {
            this.invoice = new Invoices();

            System.out.println("Please Enter the payment status");
            this.invoice.setPaymentStatus(sc.nextLine());
            PreparedStatement statement6 = connection.prepareStatement("insert into invoices(order_id,total_bill,payment_status)" + "values(?,?,?)");
            statement6.setString(1, String.valueOf(this.order.getOrderId()));
            statement6.setString(2, String.valueOf(this.order.getTotalBill()));
            statement6.setString(3, this.invoice.getPaymentStatus());
            statement6.execute();

            PreparedStatement statement7 = connection.prepareStatement("select* from invoices where order_id=?");
            statement7.setString(1, String.valueOf(this.order.getOrderId()));
            ResultSet rs4 = statement7.executeQuery();
            while (rs4.next()) {
                this.invoice.setInvoiceId(rs4.getInt("invoice_id"));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void insertPaymentInfo(){
        try{
            this.payment = new Payments();
            System.out.println("Please Enter the Payment Method");
            this.payment.setPaymentMethod(sc.nextLine());
            PreparedStatement statement8 = connection.prepareStatement("insert into payments(invoice_id,payment_method,amount)"+"values(?,?,?)");
            statement8.setString(1,String.valueOf(this.invoice.getInvoiceId()));
            statement8.setString(2,this.payment.getPaymentMethod());
            statement8.setString(3,String.valueOf(this.order.getTotalBill()));
            statement8.execute();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    void getConnection() {
        try {
            this.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/the_yummy_bites", "root", "Piyush@33");
            System.out.println("Welcome to The Yummy Bites");
            customerInfo();
            InsertCustomerInfo();

            menu();
            selectFromMenu();
            insertOrderInfo();

            insertIntoInvoice();
            insertPaymentInfo();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
