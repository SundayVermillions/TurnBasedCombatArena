package tbca.ui;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.combatant.player.playerclass.Wizard;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.*;
import tbca.item.Item;
import tbca.item.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Selection {
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
            int choice = inputValidator.getIntInput("Enter 1-3: ", 1, 3);
            selectedItems.add(mapChoiceToItemType(choice));
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
        //wizard.ToString();
        //warrior.ToString();
        System.out.println("Select your class:");
        System.out.println("1: Warrior");
        System.out.println("HP: 260");
        System.out.println("Attack: 40");
        System.out.println("Defense: 20");
        System.out.println("Speed: 30");
        System.out.println("Special Skill: Shield Bash");
        System.out.println("Effect: Deal BasicAttack damage to selected enemy.");
        System.out.println("Selected enemy is unable to take actions for the current turn and the next turn.\n");

        System.out.println("2: Wizard");
        System.out.println("HP: 200");
        System.out.println("Attack: 50");
        System.out.println("Defense: 10");
        System.out.println("Speed: 20");
        System.out.println("Special Skill: Arcane Blast");
        System.out.println("Effect: Deal BasicAttack damage to all enemies currently in combat.");
        System.out.println("Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until end of the level.");
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
            case 3 -> new UseItemParameters(gameState.getPlayer(), promptItemType(inventory));
            case 4 -> new SpecialSkillParameters(gameState.getPlayer(), promptTargetEnemyIndex(gameState));
            default -> null;
        };
    }

    private int promptTargetEnemyIndex(GameStateReadOnly gameState) {
        int enemies = gameState.getCurrEnemies().size();
        int targetChoice = 0;
        while(true)
        {
            targetChoice = inputValidator.getIntInput("Select target enemy (1-" + enemies + "): ", 1, enemies);
            if(gameState.getCurrEnemies().get(targetChoice - 1).isDead()){
                System.out.println("Target is Dead");
            }
            else{
                break;
            }
        }
        return targetChoice - 1;
    }

    private ItemType promptItemType(List<Item> inventory) {
        System.out.println("\nChoose item to use:");
        for(int i = 0; i < inventory.size(); i++)
        {
            System.out.println(i + 1 + ". " + inventory.get(i).getType());
        }
        int itemChoice = inputValidator.getIntInput("Enter 1-" + inventory.size() + ": ", 1, inventory.size());
        return inventory.get(itemChoice - 1).getType();

    }
}