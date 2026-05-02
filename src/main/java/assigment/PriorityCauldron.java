package assigment;

public class PriorityCauldron {
    private PotionOrder[] heap;
    private int size;
    private OrderEntry[] orderMap;
    private int mapSize;

    // ── CONSTRUCTOR ──────────────────────────────────────
    public PriorityCauldron() {
        heap = new PotionOrder[100]; // 1-based, index 0 unused
        size = 0;
        orderMap = new OrderEntry[100];
        mapSize = 0;
    }

    // ── ADD ORDER ────────────────────────────────────────
    public void addOrder(PotionOrder order) {
        if (order.getGoldReward() <= 0) return;

        // Expand if full
        if (size + 1 >= heap.length) {
            PotionOrder[] newHeap = new PotionOrder[heap.length * 2];
            for (int i = 0; i <= size; i++) newHeap[i] = heap[i];
            heap = newHeap;
        }

        // Place at end and bubble up
        size++;
        heap[size] = order;
        bubbleUp(size);

        // Store in orderMap for cancelOrder lookups
        orderMap[mapSize] = new OrderEntry(order.getOrderId(), order);
        mapSize++;

        System.out.println("Order #" + order.getOrderId() +
            " (" + order.getItemType() + ", " + order.getGoldReward() + " gold) added to cauldron");
    }

    // ── BREW NEXT ────────────────────────────────────────
    public PotionOrder brewNext() {
        if (isEmpty()) {
            System.out.println("No orders in cauldron!");
            return null;
        }

        PotionOrder top = heap[1]; // max always at index 1

        // Move last to root, shrink, fix heap
        heap[1] = heap[size];
        heap[size] = null;
        size--;
        if (size > 0) bubbleDown(1);

        System.out.println("Brewing: Order #" + top.getOrderId() +
            " (" + top.getItemType() + ", " + top.getGoldReward() + " gold)");
        return top;
    }

    // ── CANCEL ORDER ─────────────────────────────────────
    public boolean cancelOrder(int orderId) {
        // Scan heap for the order
        int index = -1;
        for (int i = 1; i <= size; i++) {
            if (heap[i].getOrderId() == orderId) {
                index = i;
                break;
            }
        }

        if (index == -1) return false;

        // Replace with last element and shrink
        heap[index] = heap[size];
        heap[size] = null;
        size--;

        // Fix heap in both directions (we don't know which way is needed)
        if (index <= size) {
            bubbleUp(index);
            bubbleDown(index);
        }

        System.out.println("Order #" + orderId + " cancelled");
        return true;
    }

    // ── PEEK NEXT ────────────────────────────────────────
    public PotionOrder peekNext() {
        if (isEmpty()) return null;
        return heap[1];
    }

    // ── IS EMPTY ─────────────────────────────────────────
    public boolean isEmpty() {
        return size == 0;
    }

    // ── GET SIZE ─────────────────────────────────────────
    public int getSize() {
        return size;
    }

    // ── PRIVATE HELPERS ──────────────────────────────────

    // Swap node up until parent has higher gold reward
    private void bubbleUp(int i) {
        while (i > 1) {
            int parent = i / 2;
            if (heap[parent].getGoldReward() < heap[i].getGoldReward()) {
                PotionOrder temp = heap[parent];
                heap[parent]    = heap[i];
                heap[i]         = temp;
                i = parent;
            } else {
                break;
            }
        }
    }

    // Swap node down until both children have lower gold reward
    private void bubbleDown(int i) {
        while (true) {
            int left    = i * 2;
            int right   = i * 2 + 1;
            int largest = i;

            if (left  <= size && heap[left].getGoldReward()  > heap[largest].getGoldReward()) largest = left;
            if (right <= size && heap[right].getGoldReward() > heap[largest].getGoldReward()) largest = right;

            if (largest != i) {
                PotionOrder temp = heap[i];
                heap[i]         = heap[largest];
                heap[largest]   = temp;
                i = largest;
            } else {
                break;
            }
        }
    }

    // ── GIVEN — DO NOT CHANGE ────────────────────────────
    public String printCauldron() {
        StringBuilder result = new StringBuilder();
        result.append("\n=== PRIORITY CAULDRON ===\n");
        if (isEmpty()) {
            result.append("The cauldron is empty!\n");
        } else {
            result.append("Next to brew: " + peekNext().getItemType() +
                        " (" + peekNext().getGoldReward() + " gold)\n");
            result.append("\nFull queue (by priority):\n");
            PotionOrder[] temp = new PotionOrder[size];
            for (int i = 0; i < size; i++) temp[i] = heap[i + 1];
            for (int i = 0; i < size - 1; i++) {
                for (int j = 0; j < size - i - 1; j++) {
                    if (temp[j].getGoldReward() < temp[j + 1].getGoldReward()) {
                        PotionOrder t = temp[j]; temp[j] = temp[j+1]; temp[j+1] = t;
                    }
                }
            }
            for (int i = 0; i < size; i++) {
                PotionOrder order = temp[i];
                result.append("  " + (i+1) + ". Order #" + order.getOrderId() +
                            " | " + order.getItemType() +
                            " | " + order.getGoldReward() + " gold" +
                            " | Due: " + order.getDueDateString() + "\n");
            }
        }
        result.append("===========================\n");
        return result.toString();
    }
}





























































































































/*public class PriorityCauldron {
    private PotionOrder[] heap;  
    private int size; 
    private OrderEntry[] orderMap; 
    private int mapSize; 
    
    public PriorityCauldron() {
    heap = new PotionOrder[100];
   size = 0;
   orderMap = new OrderEntry[100];
   mapSize = 0;
    }   
    
    public void addOrder(PotionOrder order) {
        //if there is no gold reward , ignore the order 
   if(order.getGoldReward() <= 0) return; // since it is a void function
//expand the heap is it is full
if(size + 1 >= heap.length){
    PotionOrder[] newHeap = new PotionOrder[heap.length * 2];
    for(int i = 0; i < size; i++ ){
        newHeap[i] = heap[i];
    }
    heap = newHeap;
    //add to end of the heap then bubble up
    size++;
    heap[size] = order;
    bubbleUp(size);

}


    }

    public PotionOrder brewNext() {
        if(isEmpty()){
            System.out.println("No orders in cauldron");
            return null;

        }
        //the a=max is always at index 1;
        PotionOrder top = heap[1];
        //move the last to root;

heap[1] = heap[size];
heap[size] = null;
size--;   // decreases since we got rid of the element at the last index
//or the first index tis gon and stored in array
if(size > 0) bubbleDown(1);
System.out.println("Brewing: Order#" + top.getOrderId() +
" (" + top.getItemType() + ", " + top.getGoldReward() + " gold)");
   return top;


    }
    
    public boolean cancelOrder(int orderId) {
        //scan heap for order 
        int index = -1;
        for(int i = 1; i <= size ; i++){
      if(heap[i].getOrderId() == orderId){
        index = i;
        break;
      }
        }
        if(index == -1) return false;
        heap[index] = heap[size];
        heap[size] = null;
        size--;
        if(index <= size){
            bubbleUp(index);
            bubbleDown(index);

        }
        System.out.println("Order #" + orderId + " cancelled");
        return true;
    }

    public PotionOrder peekNext() {
        if(isEmpty()) return null;
        return heap[i];

    }
    
    public boolean isEmpty() {
        return size == 0;

    }
    
    public int getSize() {
        return size;
    }
    //private helsper 
    private void bubbleUp(int i){
     while(i > 1){
int parent = i/2;
if(heap[parent].getGoldReward() < heap[i].getGoldReward()){

//perform traditional swapping of parent and child
PotionOrder temp = heap[parent];
heap[parent] = heap[i];
heap[i]   = temp;
i = parent;}
else{break;

}
}
}
private void bubbleDown(int i){
    while(true){
//this is for heaps and arrays
        int left = i*2;
        int right = i*2 + 1;
        int largest = i;
if(left <= size && heap[left].getGoldReward() > heap[largest].getGoldReward()) 
    largest = left;
if(right <= size && heap[right].getGoldReward() > heap[largest].getGoldReward())
    largest = right;
if(largest != i){
    PotionOrder temp = heap[i];
    heap[i] = heap[largest];
    heap[largest] = temp;
    i = largest;

}else {
    break;
      }
    }
}


    ========================================================
                        Helper Functions
    ========================================================
    
    
    public String printCauldron() {
        StringBuilder result = new StringBuilder();
        result.append("\n=== PRIORITY CAULDRON ===\n");
        
        if (isEmpty()) {
            result.append("The cauldron is empty!\n");
        } else {
            result.append("Next to brew: " + peekNext().getItemType() + 
                        " (" + peekNext().getGoldReward() + " gold)\n");
            result.append("\nFull queue (by priority):\n");
            
            PotionOrder[] temp = new PotionOrder[size];
            for (int i = 0; i < size; i++) {
                temp[i] = heap[i + 1];
            }
            
            for (int i = 0; i < size - 1; i++) {
                for (int j = 0; j < size - i - 1; j++) {
                    if (temp[j].getGoldReward() < temp[j + 1].getGoldReward()) {
                        PotionOrder t = temp[j];
                        temp[j] = temp[j + 1];
                        temp[j + 1] = t;
                    }
                }
            }
            
            for (int i = 0; i < size; i++) {
                PotionOrder order = temp[i];
                result.append("  " + (i+1) + ". Order #" + order.getOrderId() + 
                            " | " + order.getItemType() + 
                            " | " + order.getGoldReward() + " gold" +
                            " | Due: " + order.getDueDateString() + "\n");
            }
        }
        
        result.append("===========================\n");
        return result.toString();
    }
}



*/