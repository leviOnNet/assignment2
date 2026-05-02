package assigment;

public class PotionOrder {
    private int orderId;
    private String itemType;
    private int goldReward;
    private String dueDate; 
    private MaterialRequirement[] requiredMaterials; 
    private int materialCount;
    
    public PotionOrder(int orderId, String itemType, int goldReward, String dueDate) {      
        this.orderId = orderId;
        this.itemType = itemType;
        this.goldReward = goldReward;
        this.dueDate = dueDate;                                     
        this.requiredMaterials = new MaterialRequirement[10];       
        this.materialCount = 0;
    }
    
    public void addMaterial(int materialId, int quantity) {
        if (materialCount >= requiredMaterials.length) {
            MaterialRequirement[] newReq = new MaterialRequirement[requiredMaterials.length * 2];
            for (int i = 0; i < materialCount; i++) {
                newReq[i] = requiredMaterials[i];
            }
            requiredMaterials = newReq;
        }

        for (int i = 0; i < materialCount; i++) {
            if (requiredMaterials[i] != null && requiredMaterials[i].materialId == materialId) {
                requiredMaterials[i].quantity = quantity; 
                return;
            }
        }

        requiredMaterials[materialCount] = new MaterialRequirement(materialId, quantity);
        materialCount++;
    }
    
    public int getMaterialQuantity(int materialId) {
        for (int i = 0; i < materialCount; i++) {
            if (requiredMaterials[i] != null && requiredMaterials[i].materialId == materialId) {
                return requiredMaterials[i].quantity;
            }
        }
        return 0;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public String getItemType() {
        return itemType;
    }
    
    public int getGoldReward() {
        return goldReward;
    }
    
    public String getDueDateString() {
        return dueDate;
    }
    
    public MaterialRequirement[] getRequiredMaterials() {
        MaterialRequirement[] copy = new MaterialRequirement[materialCount];
        for (int i = 0; i < materialCount; i++) {
            copy[i] = requiredMaterials[i];
        }
        return copy;
    }
    
    @Override
    public String toString() {
        return "Order #" + orderId + ": " + itemType + " (" + goldReward + " gold)";
    }
}