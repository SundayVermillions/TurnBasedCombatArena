package tbca.ui;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.action.SpecialSkillType;
import tbca.engine.difficulty.GameDifficulty;
import tbca.item.ItemType;

import java.util.*;

public class StartingScreen {
    private final InputValidator inputValidator;
    private ManualScreen manualScreen;

    public StartingScreen(){
        this.inputValidator = new InputValidator(new Scanner(System.in));
        this.manualScreen = new ManualScreen();

    }

    public void startingMenu()
    {
        while(true)
        {
            displayMenu();
            int choice = inputValidator.getIntInput("Enter choice: ", 1, 2);
            switch(choice){
                case 1:
                    return;
                case 2:
                    manualScreen.showDetails();
            }
        }
    }
    private static void displayMenu() {
        System.out.println("=========================================");
        System.out.println("       TURN-BASED COMBAT ARENA           ");
        System.out.println("=========================================\n");
        System.out.println();
        System.out.println("1. Start Game");
        System.out.println("2. View Stats/Details");
        System.out.println();
    }

    private static final List<GameDifficulty> DIFFICULTY_OPTIONS = List.of(GameDifficulty.values());

    // List of starting item options for selection
    private static final List<ItemType> STARTING_ITEM_OPTIONS = List.of(
            ItemType.POTION,
            ItemType.POWER_STONE,
            ItemType.SMOKE_BOMB
    );

    public GameDifficulty promptDifficulty() {
        displayDifficultyOptions();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, DIFFICULTY_OPTIONS.size());
        return DIFFICULTY_OPTIONS.get(choice - 1);
    }

    private void displayDifficultyOptions() {
        System.out.println("\nSelect game difficulty:");
        for (int i = 0; i < DIFFICULTY_OPTIONS.size(); i++) {
            GameDifficulty difficulty = DIFFICULTY_OPTIONS.get(i);
            System.out.println((i + 1) + ". " + difficulty.getLabel());
            System.out.println("   " + difficulty.getDescription());
        }
    }

    public List<ItemType> itemSelection()
    {
        List<ItemType> selectedItems = new ArrayList<>();
        System.out.println("Choose 2 starting items (duplicates allowed):\n");
        for (int i = 1; i <= 2; i++) {
            System.out.println("Item choice " + i + ":");
            printItem();
            int choice = inputValidator.getIntInput("Enter 1-" + STARTING_ITEM_OPTIONS.size() + ": ", 1, STARTING_ITEM_OPTIONS.size());
            selectedItems.add(mapChoiceToItemType(choice));
            System.out.println();
        }
        return selectedItems;
    }

    private ItemType mapChoiceToItemType(int choice) {
        return STARTING_ITEM_OPTIONS.get(choice - 1);
    }

    private void printItem()
    {
        for (int i = 0; i < STARTING_ITEM_OPTIONS.size(); i++) {
            printItemOption(i + 1, STARTING_ITEM_OPTIONS.get(i));
        }
    }

    private void printItemOption(int optionNumber, ItemType itemType) {
        System.out.printf("%d: %-12s -- %s\n",
                optionNumber,
                itemType.getDisplayName(),
                itemType.getDescription());
    }

    public PlayerClass classChoice()
    {
        displayClassOptions();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, 2);
        System.out.println("\n");
        return switch (choice) {
            case 1 -> PlayerClass.WARRIOR;
            case 2 -> PlayerClass.WIZARD;
            default -> null;
        };
    }


    private void displayClassOptions() {
        System.out.println("Select your class:");
        printClassOption(1, PlayerClass.WARRIOR, SpecialSkillType.SHIELD_BASH);
        printClassOption(2, PlayerClass.WIZARD, SpecialSkillType.ARCANE_BLAST);
    }

    // Use printAlignedLine to ensure proper alignment of class attributes and special skill description
    private void printClassOption(int optionNumber, PlayerClass playerClass, SpecialSkillType skillType) {
        System.out.println(optionNumber + ": " + playerClass.getLabel());
        printAlignedLine("HP", String.valueOf(playerClass.getMaxHp()));
        printAlignedLine("Attack", String.valueOf(playerClass.getAttack()));
        printAlignedLine("Defense", String.valueOf(playerClass.getDefense()));
        printAlignedLine("Speed", String.valueOf(playerClass.getSpeed()));

        String skillText = "[" + skillType.getDisplayName() + "] " + skillType.getDescription();
        String[] lines = skillText.split("\n");
        System.out.printf("  %-15s: %s\n", "Special Skill", lines[0]);
        for (int i = 1; i < lines.length; i++) {
            System.out.printf("%19s%s\n", "", lines[i]);
        }
        System.out.println();
    }

    // Helper method to print aligned lines for class attributes and special skill description
    private void printAlignedLine(String label, String value) {
        System.out.printf("  %-14s : %s\n", label, value);
    }


}
