package assigment;

public class Main {

    public static void main(String[] args) {

        Shelf shelf = new Shelf(3);

        // 1. INSERT BASIC
        shelf.insert(new Material(1001, "Dragon Scales", 60, 80, 90));
        shelf.insert(new Material(1002, "Unicorn Hair", 20, 70, 85));
        shelf.insert(new Material(1003, "Phoenix Feather", 40, 90, 95));

        // 2. DUPLICATE INSERT (UPDATE)
        shelf.insert(new Material(1002, "Unicorn Hair", 100, 90, 90));

        // 3. SEARCH TESTS
        Material found = shelf.search(1001);
        if (found != null) {
            System.out.println("Search OK: " + found.getName());
        }

        Material notFound = shelf.search(9999);
        if (notFound == null) {
            System.out.println("Search NULL OK");
        }

        // 4. CONTAINS TESTS
        System.out.println("Contains 1001: " + shelf.contains(1001));
        System.out.println("Contains 9999: " + shelf.contains(9999));

        // 5. TAKE MATERIAL TESTS
        shelf.takeMaterial(1001, 10);  // success
        shelf.takeMaterial(1002, 1000); // not enough
        shelf.takeMaterial(9999, 10);  // not found

        // 6. FORCE SPLITS - This will trigger multiple splits
        shelf.insert(new Material(1004, "Goblin Dust", 10, 50, 60));
        shelf.insert(new Material(1005, "Elf Root", 25, 60, 70));
        shelf.insert(new Material(1006, "Troll Fat", 15, 40, 50));
        shelf.insert(new Material(1007, "Mermaid Tears", 30, 85, 88));
        shelf.insert(new Material(1008, "Vampire Fang", 5, 95, 99));
        shelf.insert(new Material(1009, "Werewolf Claw", 12, 65, 75));

        // 7. SEARCH AFTER SPLITS
        System.out.println("Post-split search: " + shelf.search(1008).getName());

        // 8. EDGE CASES
        // Negative ID (should do nothing)
        shelf.insert(new Material(-1, "Invalid", 10, 10, 10));

        // Zero quantity take
        shelf.takeMaterial(1003, 0);

        // Take exact quantity
        shelf.takeMaterial(1003, 40);

        // Take again (should fail)
        shelf.takeMaterial(1003, 1);


        // 9. STRESS TEST FOR ARRAY RESIZE
        for (int i = 1010; i < 1030; i++) {
            shelf.insert(new Material(i, "Material" + i, i, 50, 50));
        }

        // 10. FINAL CHECKS
        System.out.println("Final contains check: " + shelf.contains(1025));
        System.out.println("Final search check: " + shelf.search(1025).getName());
        System.out.println("Testing complete.");
    }
}