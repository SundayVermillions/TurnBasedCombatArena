package tbca.ui;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.difficulty.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.SpecialSkillType;
import tbca.engine.action.parameters.*;
import tbca.item.Item;
import tbca.item.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Selection {
    // List of starting item options for selection
    private static final List<ItemType> STARTING_ITEM_OPTIONS = List.of(
            ItemType.POTION,
            ItemType.POWER_STONE,
            ItemType.SMOKE_BOMB
    );

    // Map to hold item effects for display purposes
    private static final Map<ItemType, String> ITEM_EFFECTS = Map.of(
            ItemType.POTION, "Heal 100 HP",
            ItemType.POWER_STONE, "Free extra use of Special Skill (no cooldown change)",
            ItemType.SMOKE_BOMB, "Enemy attacks deal 0 damage for this and next turn"
    );

    InputValidator inputValidator;
    public Selection() {
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }

    public GameDifficulty promptDifficulty() {
        displayDifficultyOptions();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, 3);
        return switch (choice) {
            case 1 -> GameDifficulty.EASY;
            case 2 -> GameDifficulty.MEDIUM;
            case 3 -> GameDifficulty.HARD;
            default -> {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                yield GameDifficulty.MEDIUM;
            }
        };
    }

    private void displayDifficultyOptions() {
        System.out.println("\nSelect game difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
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

        System.out.println("Items selected: " + selectedItems);
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
                ITEM_EFFECTS.getOrDefault(itemType, "No effect description."));
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
        String skillEffect = skillType.getDescription();
        printAlignedLine("Special Skill", skillType.getDisplayName() + " - " + skillEffect);
        System.out.println();
    }

    // Helper method to print aligned lines for class attributes and special skill description
    private void printAlignedLine(String label, String value) {
        System.out.printf("  %-14s : %s\n", label, value);
    }


    public ActionParameters playerAction(GameStateReadOnly gameState) {
        System.out.println("\nChoose your action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill");
        System.out.println();

        int choice;
        Player player = (Player) gameState.getPlayer();
        List<Item> inventory = player.getInventory();


        while(true)
        {
            choice = inputValidator.getIntInput("Enter 1-4: ", 1, 4);
            if(gameState.getPlayer().getSpecialSkillCooldown() != 0 && choice == 4)
            {
                System.out.println("Cannot use Special Skill yet");
            }

            else if (inventory.isEmpty() && choice == 3){
                System.out.println("Inventory is Empty");
            }
            else{
                break;
            }
        }
        System.out.println();
        return switch (choice) {
            case 1 -> new BasicAttackParameters(gameState.getPlayer(), promptTargetEnemyIndex(gameState));
            case 2 -> new DefendParameters(gameState.getPlayer());
            case 3 -> {
                ItemType selectedType = promptItemType(inventory);
                int targetIndex = -1;

                if (selectedType == ItemType.POWER_STONE && player.specialSkillNeedsTarget()){
                    System.out.println("Select a target for your bonus skill:");
                    targetIndex = promptTargetEnemyIndex(gameState);
                }
                yield new UseItemParameters(gameState.getPlayer(), selectedType, targetIndex);
            }
            case 4 -> {

                int targetIndex = -1;
                if (player.specialSkillNeedsTarget()){
                    targetIndex = promptTargetEnemyIndex(gameState);
                }
                yield new SpecialSkillParameters(player, targetIndex);
            }
            default -> null;
        };
    }

    private int promptTargetEnemyIndex(GameStateReadOnly gameState) {
        List<Combatant> enemies = gameState.getCurrEnemies();
        System.out.println("\nAvailable Targets:");
       for(int i = 0; i < enemies.size(); i++) {
           Combatant enemy = enemies.get(i);
           String status = enemy.isDead() ? "[DEAD]" : "(" + enemy.getCurrHp() + "/" + enemy.getMaxHp() + " HP)";
           System.out.println((i + 1) + ". " + enemy.getName() + " " + status);
       }
           int targetChoice = 0;
           while(true){
               targetChoice = inputValidator.getIntInput("Select target enemy (1-" + enemies.size() + "):", 1, enemies.size());
               if(gameState.getCurrEnemies().get(targetChoice - 1).isDead()){
                   System.out.println("Target is Dead. Pick a living enemy!");
               } else{
                   break;
               }
           }
           return targetChoice - 1;

        }


    private ItemType promptItemType(List<Item> inventory) {
        System.out.println("\nChoose item to use:");
        for(int i = 0; i < inventory.size(); i++)
        {
            ItemType itemType = inventory.get(i).getType();
            System.out.printf("%d: %-12s -- %s\n", //make it align
                    i + 1,
                    itemType.getDisplayName(),
                    ITEM_EFFECTS.getOrDefault(itemType, "No effect description."));
        }
        int itemChoice = inputValidator.getIntInput("Enter 1-" + inventory.size() + ": ", 1, inventory.size());
        return inventory.get(itemChoice - 1).getType();

    }
}