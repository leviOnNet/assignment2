package assigment;

public class Shelf {
    public ShelfNode root;
    public int m;                          
    private MaterialEntry[] materialMap;   
    private int materialCount;              
    
    public Shelf(int m) {
        this.m = m;
        this.root = null;
        this.materialMap = new MaterialEntry[10];
        this.materialCount = 0;
    }
    
    public void insert(Material material) {
     int id = material.getID();
        if (id < 0) return;
 
        // If already exists, update and print Updated message
        if (contains(id)) {
            storeMaterial(id, material);
            System.out.println("Updated: " + material.getName() + " (ID: " + id + ")");
            return;
        }
 
        // Store in materialMap
        storeMaterial(id, material);
 
        // Insert ID into B-tree
        if (root == null) {
            root = new ShelfNode(m);
            root.nodeData[0] = id;
            root.numberOfKeys = 1;
        } else {
            // If root is full, split it first
            if (root.numberOfKeys == m) {
                ShelfNode newRoot = new ShelfNode(m);
                newRoot.nodeChildren[0] = root;
                root.parent = newRoot;
                splitChild(newRoot, 0, root);
                root = newRoot;
            }
            insertNonFull(root, id);
        }
 
        System.out.println("Added: " + material.getName() + " (ID: " + id + ")");}

          private void insertNonFull(ShelfNode node, int id) {
        int i = node.numberOfKeys - 1;
 
        if (node.nodeChildren[0] == null) {
            // Leaf node — shift keys right and insert
            while (i >= 0 && (Integer) node.nodeData[i] > id) {
                node.nodeData[i + 1] = node.nodeData[i];
                i--;
            }
            node.nodeData[i + 1] = id;
            node.numberOfKeys++;
        } else {
            // Internal node — find correct child
            while (i >= 0 && (Integer) node.nodeData[i] > id) {
                i--;
            }
            i++;
            ShelfNode child = node.nodeChildren[i];
 
            // Split child if full
            if (child.numberOfKeys == m) {
                splitChild(node, i, child);
                // After split, decide which of the two children to descend into
                if ((Integer) node.nodeData[i] < id) {
                    i++;
                }
            }
            insertNonFull(node.nodeChildren[i], id);
        }
    }


 // Split child node at index childIndex of parent
    private void splitChild(ShelfNode parent, int childIndex, ShelfNode child) {
        int mid = m / 2; // index of median key
 
        ShelfNode rightNode = new ShelfNode(m);
        rightNode.parent = parent;
 
        // Copy right half of child's keys into rightNode
        rightNode.numberOfKeys = child.numberOfKeys - mid - 1;
        for (int i = 0; i < rightNode.numberOfKeys; i++) {
            rightNode.nodeData[i] = child.nodeData[mid + 1 + i];
            child.nodeData[mid + 1 + i] = null;
        }
 
        // Copy right half of child's children into rightNode
        if (child.nodeChildren[0] != null) {
            for (int i = 0; i <= rightNode.numberOfKeys; i++) {
                rightNode.nodeChildren[i] = child.nodeChildren[mid + 1 + i];
                if (rightNode.nodeChildren[i] != null) {
                    rightNode.nodeChildren[i].parent = rightNode;
                }
                child.nodeChildren[mid + 1 + i] = null;
            }
        }
 
        // The median key moves up to the parent
        Comparable<Integer> medianKey = child.nodeData[mid];
        child.nodeData[mid] = null;
        child.numberOfKeys = mid;
 
        // Shift parent's children right to make room for rightNode
        for (int i = parent.numberOfKeys; i > childIndex; i--) {
            parent.nodeChildren[i + 1] = parent.nodeChildren[i];
        }
        parent.nodeChildren[childIndex + 1] = rightNode;
 
        // Shift parent's keys right and insert median
        for (int i = parent.numberOfKeys - 1; i >= childIndex; i--) {
            parent.nodeData[i + 1] = parent.nodeData[i];
        }
        parent.nodeData[childIndex] = medianKey;
        parent.numberOfKeys++;
    }

























































































    public Material search(int id) {
         ShelfNode node = searchNode(root, id);
        if (node == null) return null;
        return findMaterial(id);
    }
  // Find the node containing the given id
    private ShelfNode searchNode(ShelfNode node, int id) {
        if (node == null) return null;
 
        int i = 0;
        // Find first key >= id
        while (i < node.numberOfKeys && (Integer) node.nodeData[i] < id) {
            i++;
        }
 
        if (i < node.numberOfKeys && (Integer) node.nodeData[i] == id) {
            return node; // found
        }
 
        if (node.nodeChildren[0] == null) {
            return null; // leaf, not found
        }
 
        return searchNode(node.nodeChildren[i], id); // recurse into child
    }
 




    public boolean contains(int id) {
        return searchNode(root, id) != null;
    }

    public Material takeMaterial(int id, int amount) {
          Material mat = findMaterial(id);
 
        if (mat == null) {
            System.out.println("Material ID " + id + " not found");
            return null;
        }
 
        if (mat.getQuantity() < amount) {
            System.out.println("Not enough " + mat.getName() +
                    " (Have: " + mat.getQuantity() + ", Need: " + amount + ")");
            return null;
        }
 
        mat.setQuantity(mat.getQuantity() - amount);
        System.out.println("Took " + amount + " of " + mat.getName() +
                " (Remaining: " + mat.getQuantity() + ")");
        return mat;
    }

    public Material findMaterial(int id) {
      for (int i = 0; i < materialCount; i++) {
            if (materialMap[i] != null && materialMap[i].id == id) {
                return materialMap[i].material;
            }
        }
        return null;  
    }

    private void storeMaterial(int id, Material material) {
// Update if already exists
        for (int i = 0; i < materialCount; i++) {
            if (materialMap[i] != null && materialMap[i].id == id) {
                materialMap[i].material = material;
                return;
            }
        }
 
        // Expand array if full
        if (materialCount >= materialMap.length) {
            MaterialEntry[] newMap = new MaterialEntry[materialMap.length * 2];
            for (int i = 0; i < materialCount; i++) {
                newMap[i] = materialMap[i];
            }
            materialMap = newMap;
        }
 
        // Find sorted insertion position
        int pos = materialCount;
        for (int i = 0; i < materialCount; i++) {
            if (materialMap[i].id > id) {
                pos = i;
                break;
            }
        }
 
        // Shift right to make room
        for (int i = materialCount; i > pos; i--) {
            materialMap[i] = materialMap[i - 1];
        }
 
        materialMap[pos] = new MaterialEntry(id, material);
        materialCount++;

    }

    /*
    ========================================================
                        Helper Functions
    ========================================================
    */

    public String printShelf() {
        StringBuilder result = new StringBuilder();
        result.append("\n=== SHELF STRUCTURE ===\n");
        if (root == null) {
            result.append("The shelf is empty!\n");
        } else {
            buildShelfString(root, "", true, result);
        }
        result.append("===========================\n");
        return result.toString();
    }

    private void buildShelfString(ShelfNode node, String prefix, boolean isTail, StringBuilder result) {
        if (node == null) return;

        result.append(prefix + (isTail ? "└── " : "├── "));
        for (int i = 0; i < node.nodeData.length; i++) {
            if (node.nodeData[i] != null) {
                result.append(node.nodeData[i]);
                if (i < node.nodeData.length - 1 && node.nodeData[i + 1] != null) {
                    result.append(", ");
                }
            }
        }

        if (node.parent != null) {
            result.append(" (parent: ");
            for (int i = 0; i < node.parent.nodeData.length; i++) {
                if (node.parent.nodeData[i] != null) {
                    result.append(node.parent.nodeData[i] + " ");
                    break;
                }
            }
            result.append(")");
        }
        result.append("\n");

        for (int i = 0; i < node.nodeChildren.length; i++) {
            if (node.nodeChildren[i] != null) {
                buildShelfString(node.nodeChildren[i], 
                                prefix + (isTail ? "    " : "│   "), 
                                i == node.nodeChildren.length - 1, 
                                result);
            }
        }
    }

    public String printInventory() {
        StringBuilder result = new StringBuilder();
        result.append("\n=== INVENTORY CONTENTS ===\n");
        if (materialCount == 0) {
            result.append("The shelf is empty!\n");
        } else {
            for (int i = 0; i < materialCount; i++) {
                if (materialMap[i] != null) {
                    Material m = materialMap[i].material;
                    result.append("ID: " + materialMap[i].id + 
                                " | " + m.getName() + 
                                " | Qty: " + m.getQuantity() + 
                                " | Potency: " + String.format("%.3f", m.getMagicalPotency()) + "\n");
                }
            }
        }
        result.append("============================\n");
        return result.toString();
    }
}


