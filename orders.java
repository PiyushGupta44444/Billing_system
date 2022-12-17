import java.util.HashMap;

public class orders {

    private int price, orderId;
    private String itemName;

    private HashMap <String,Integer> map;

    public orders(){
        this.map = new HashMap<>();
    }
    public HashMap<String,Integer> getMap(){
         return this.map;
    }

    public void push(String ItemId,Integer Quantity){
       this.map.put(ItemId,Quantity);
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    private int totalBill;

}
