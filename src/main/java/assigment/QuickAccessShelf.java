package assigment;

public class QuickAccessShelf {
    public QuickAccessShelfNode root;
    private MaterialEntry[] materialMap; 
    private int materialCount; 
    private long accessCounter; 
    
    public QuickAccessShelf() {
      root = null;
      materialMap = new MaterialEntry[10];
      materialCount = 0;
      accessCounter = 0;
    }

    public void insert(Material material) {
        int id = material.getID();
        if(id < 0) return;

        if(contains(id)){
            System.out.println("Material ID " + id + " already exists");
            return;
        }
 // Store in materialMap
        storeMaterial(id, material);
 
        // Insert into splay tree as a standard BST insert
        QuickAccessShelfNode newNode = new QuickAccessShelfNode(id);
        if (root == null) {
            root = newNode;
        } else {
            QuickAccessShelfNode current = root;
            while (true) {
                if (id < current.key) {
                    if (current.left == null) {
                        current.left = newNode;
                        newNode.parent = current;
                        break;
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        current.right = newNode;
                        newNode.parent = current;
                        break;
                    } else {
                        current = current.right;
                    }
                }
            }
        }
        System.out.println("Added: " + material.getName() + " (ID: " + id + ")");
    }

public Material useMaterial(int id, int amount){
   QuickAccessShelfNode node = findNode(id);
   
   if(node == null){
    System.out.println("Material ID " + id + " not found");
    return null;
   }

   Material mat = findMaterial(id);

   if(mat == null) return null;
    if(mat.getQuantity() < amount){  

    System.out.println("Not enough " + mat.getName() + " (Have: " + 
     mat.getQuantity() + ", Need: " + amount + ")");
     node.usageCount++;
    node.lastAccessTime = ++accessCounter;
    splay(node);
     return null;
}
 // Take the material
        mat.setQuantity(mat.getQuantity() - amount);
        System.out.println("Took " + amount + " of " + mat.getName() +
                " (Remaining: " + mat.getQuantity() + ")");
 
        // Update usage and splay
        node.usageCount++;
        node.lastAccessTime = ++accessCounter;
        splay(node);
 
        return mat;
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


    //height
    public int getHeight() {
        return heightHelper(root);
    }
    
    //height helper function
    private int heightHelper(QuickAccessShelfNode node) {
        if (node == null) return 0;
        int left  = heightHelper(node.left);
        int right = heightHelper(node.right);
        return 1 + Math.max(left, right);
    }

    public Integer getMostUsed() {
         if (root == null) return null;
        return mostUsedHelper(root, null);
    }
     private Integer mostUsedHelper(QuickAccessShelfNode node, QuickAccessShelfNode best) {
        if (node == null) return best == null ? null : best.key;
 
        if (best == null ||
            node.usageCount > best.usageCount ||
            (node.usageCount == best.usageCount && node.lastAccessTime > best.lastAccessTime)) {
            best = node;
        }
 
        Integer leftBest  = mostUsedHelper(node.left,  best);
        Integer rightBest = mostUsedHelper(node.right, best);
 
        // Compare left and right subtree results with current best
        QuickAccessShelfNode leftNode  = leftBest  != null ? findNode(leftBest)  : null;
        QuickAccessShelfNode rightNode = rightBest != null ? findNode(rightBest) : null;
 
        Integer result = best.key;
 
        if (leftNode != null) {
            QuickAccessShelfNode cur = findNode(result);
            if (leftNode.usageCount > cur.usageCount ||
                (leftNode.usageCount == cur.usageCount && leftNode.lastAccessTime > cur.lastAccessTime)) {
                result = leftNode.key;
            }
        }
 
        if (rightNode != null) {
            QuickAccessShelfNode cur = findNode(result);
            if (rightNode.usageCount > cur.usageCount ||
                (rightNode.usageCount == cur.usageCount && rightNode.lastAccessTime > cur.lastAccessTime)) {
                result = rightNode.key;
            }
        }
 
        return result;
    }

    //contains 
    public boolean contains(int id) {
        return findNode(id) != null; 
    }
     // peak not ree change
    public Material peek(int id) {
        return findMaterial(id);
    }

    public int getUsageCount(int id) {
         QuickAccessShelfNode node = findNode(id);
        if (node == null) return 0;
        return node.usageCount;
    }

    public Material search(int id) {
         QuickAccessShelfNode node = findNode(id);
        if (node == null) return null;
        splay(node);
        return findMaterial(id);
    }
    //fin material helper function
       private Material findMaterial(int id) {
        for (int i = 0; i < materialCount; i++) {
            if (materialMap[i] != null && materialMap[i].id == id) {
                return materialMap[i].material;
            }
        }
        return null;
    }

    //find node in splay tree 
     private QuickAccessShelfNode findNode(int id) {
        QuickAccessShelfNode current = root;
        while (current != null) {
            if (id == current.key) return current;
            else if (id < current.key) current = current.left;
            else current = current.right;
        }
        return null;
    }

    //store in material map

    private void storeMaterial(int id, Material material) {
        // Update if exists
        for (int i = 0; i < materialCount; i++) {
            if (materialMap[i] != null && materialMap[i].id == id) {
                materialMap[i].material = material;
                return;
            }
        }
 
        // Expand if full
        if (materialCount >= materialMap.length) {
            MaterialEntry[] newMap = new MaterialEntry[materialMap.length * 2];
            for (int i = 0; i < materialCount; i++) {
                newMap[i] = materialMap[i];
            }
            materialMap = newMap;
        }
 
        // Insert sorted by ID
        int pos = materialCount;
        for (int i = 0; i < materialCount; i++) {
            if (materialMap[i].id > id) {
                pos = i;
                break;
            }
        }
 
        // Shift elements right
        for (int i = materialCount; i > pos; i--) {
            materialMap[i] = materialMap[i - 1];
        }
 
        materialMap[pos] = new MaterialEntry(id, material);
        materialCount++;
    }
     // Rotate node x up, making its parent go down
    private void rotateUp(QuickAccessShelfNode x) {
        QuickAccessShelfNode p = x.parent;
        QuickAccessShelfNode g = p.parent;
 
        if (p.left == x) {
            // x is left child — right rotation
            p.left = x.right;
            if (x.right != null) x.right.parent = p;
            x.right = p;
        } else {
            // x is right child — left rotation
            p.right = x.left;
            if (x.left != null) x.left.parent = p;
            x.left = p;
        }
 
        p.parent = x;
        x.parent = g;
 
        if (g == null) {
            root = x; // x is now root
        } else if (g.left == p) {
            g.left = x;
        } else {
            g.right = x;
        }
    }
 
    // Splay: bring node x to the root using zig, zig-zig, zig-zag
    private void splay(QuickAccessShelfNode x) {
        while (x.parent != null) {
            QuickAccessShelfNode p = x.parent;
            QuickAccessShelfNode g = p.parent;
 
            if (g == null) {
                // Zig step — x's parent is root
                rotateUp(x);
            } else if ((g.left == p && p.left == x) ||
                       (g.right == p && p.right == x)) {
                // Zig-zig — same direction twice
                rotateUp(p); // rotate parent first
                rotateUp(x);
            } else {
                // Zig-zag — different directions
                rotateUp(x);
                rotateUp(x);
            }
        }
    }
    
    /*
    ========================================================
                        Helper Functions
    ========================================================
    */

    public String printShelf() {
        StringBuilder result = new StringBuilder();
        result.append("\n=== QUICK ACCESS SHELF STRUCTURE ===\n");
        if (root == null) {
            result.append("The shelf is empty!\n");
        } else {
            buildShelfString(root, "", true, result);
        }
        result.append("====================================\n");
        return result.toString();
    }

    private void buildShelfString(QuickAccessShelfNode node, String prefix, boolean isTail, StringBuilder result) {
        if (node == null) return;

        result.append(prefix + (isTail ? "└── " : "├── ") + node.toString() + "\n");

        if (node.left != null || node.right != null) {
            if (node.left != null) {
                buildShelfString(node.left, prefix + (isTail ? "    " : "│   "), node.right == null, result);
            }
            if (node.right != null) {
                buildShelfString(node.right, prefix + (isTail ? "    " : "│   "), true, result);
            }
        }
    }

    public String printInventory() {
        StringBuilder result = new StringBuilder();
        result.append("=== INVENTORY CONTENTS ===\n");
        
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
        result.append("====================================\n");
        return result.toString();
    }
 public int get(int id) {
        QuickAccessShelfNode node = findNode(id);
        return node == null ? -1 : node.key;
    }



}