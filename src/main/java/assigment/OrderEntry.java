package assigment;

public class OrderEntry {
    protected int id;
    protected PotionOrder order;
    
    OrderEntry(int id, PotionOrder order) {
        this.id = id;
        this.order = order;
    }
}