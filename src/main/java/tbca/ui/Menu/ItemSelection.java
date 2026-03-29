package tbca.ui.Menu;

import tbca.item.Item;
import tbca.item.ItemType;
import tbca.ui.Input.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemSelection {
    InputValidator inputValidator;
    public ItemSelection() {
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }
    public List<Item> itemSelection()
    {
        List<Item> selectedItems = new ArrayList<>();
        System.out.println("Choose 2 starting items (duplicates allowed):\n");
        for (int i = 1; i <= 2; i++) {
            System.out.println("Item choice " + i + ":");
            printItem();
            int choice = inputValidator.getIntInput("Enter 1-3: ", 1, 3);
            selectedItems.add(new Item(mapChoiceToItemType(choice)));
            System.out.println();
        }

        System.out.println("Items selected: " + selectedItems);
        return selectedItems;
    }

    private ItemType mapChoiceToItemType(int choice) {
        return switch (choice) {
            case 1 -> ItemType.POTION;
            case 2 -> ItemType.POWER_STONE;
            case 3 -> ItemType.SMOKE_BOMB;
            default -> throw new IllegalStateException("Unexpected item choice: " + choice);
        };
    }

    private void printItem()
    {
        System.out.println("1. Potion - Heal 100 HP");
        System.out.println("2. Power Stone - Free extra use of Special Skill (no cooldown change)");
        System.out.println("3. Smoke Bomb - Enemy attacks deal 0 damage for this and next turn");
    }
}
