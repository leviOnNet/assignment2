package assigment;

public class Material {
    private int ID;
    private String name;
    private int quantity;
    private float magicalPotency;

    public Material(int ID, String name, int quantity, int materialQuality, int powerLevel) {
    // since the memebers are pprivate it means we access from outside the class
     this.ID = ID;
     this.name = name;
     this.quantity = quantity;
     this.magicalPotency = (materialQuality / 100.0f) * (powerLevel / 100);
// we use 0f to preserve the decimal


    }

    public int getID() {
return ID;
    }

    public String getName() {
        return name;  
    }

    public int getQuantity() {
        return quantity;
    }

    public float getMagicalPotency() {
          return magicalPotency;

    }
    //the only setter
    public void setQuantity(int quantity) {
          this.quantity = quantity;
    }
}