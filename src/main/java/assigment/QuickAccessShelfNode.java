package assigment;

public class QuickAccessShelfNode {
    public Integer key;             
    public int usageCount; 
    public long lastAccessTime;  
    public QuickAccessShelfNode left;
    public QuickAccessShelfNode right;
    public QuickAccessShelfNode parent;
    
    public QuickAccessShelfNode(Integer key) {
        //intitialisation
this.key = key;
this.usageCount = 0;
this.lastAccessTime = System.nanoTime();
this.left = null;
this.right = null;
this.parent = null;




    }

    /*
    ========================================================
                        Helper Functions
    ========================================================
    */
    
    public String toString() {
        return key + "(" + usageCount + ")";
    }
}
