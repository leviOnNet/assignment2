package assigment;

public class ShelfNode {
    public Comparable<Integer>[] nodeData;          
    public ShelfNode[] nodeChildren;                
    public ShelfNode parent;
    public int size;                                
    public int numberOfKeys;                        
    public int minDegree;   

    @SuppressWarnings("unchecked")
    public ShelfNode(int size) {
        this.size = size;
        this.nodeData = new Comparable[size];
 this.nodeChildren = new ShelfNode[size + 1];
 this.parent       = null;
        this.numberOfKeys = 0;
        this.minDegree    = size / 2;

    }

    public Comparable<Integer> getIndex(int i) {
         if (i < 0 || i >= numberOfKeys) return null;
        return nodeData[i];
    }
 
    // Returns the parent node
    public ShelfNode ascend() {
        return parent;
    }

//returns the child node at index i or null if invalid
    public ShelfNode descend(int i) {
         if (i < 0 || i > numberOfKeys) return null;
        return nodeChildren[i];
    }

    /*
    ========================================================
                        Helper Functions
    ========================================================
    */

    public String toString() {
        String out = "|";
        for (int i = 0; i < nodeData.length; i++) {
            if (nodeData[i] == null) {
                out += "null|";
            } else {
                out += nodeData[i].toString() + "|";
            }
        }
        return out;
    }
}