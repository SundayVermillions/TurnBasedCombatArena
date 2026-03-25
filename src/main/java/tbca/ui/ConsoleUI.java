package tbca.ui;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.ActionParameters;
import tbca.engine.action.ActionType;
import tbca.item.Item;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
//asd
public class ConsoleUI implements Ui{
    private Scanner scanner = new Scanner(System.in);

    private int checkErrorInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.println(prompt);
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
    @Override
    public void displayMenu() {
        System.out.println("=== Game Menu ===");
        System.out.println("1. Start Game");
        System.out.println("2. Options");
        System.out.println("3. Exit");
    }

    @Override
    public GameDifficulty promptDifficulty() {
        System.out.println("\nSelect game difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");

        int choice = checkErrorInt("Enter choice: ", 1, 3); //check if input value is between 1 and 3
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

    @Override
    public PlayerClass promptClassSelection() {

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
        System.out.println("Each enemy defeated by Arcane Blast adds 10 to the Wizard’s Attack, lasting until end of the level.");
        System.out.print("Enter 1 or 2: ");
        int choice = checkErrorInt("Enter choice: ", 1, 2);
        return switch (choice) {
            case 1 -> PlayerClass.WARRIOR;
            case 2 -> PlayerClass.WIZARD;
            default -> null;
        };
    }

    @Override
    public List<Item> promptItemSelection() {
        List<Item> selectedItems = new ArrayList<>();

        System.out.println("Choose 2 starting items (duplicates allowed):\n");

        for (int i = 1; i <= 2; i++) {

            System.out.println("Item choice " + i + ":");
            System.out.println("1. Potion - Heal 100 HP");
            System.out.println("2. Power Stone - Free extra use of Special Skill (no cooldown change)");
            System.out.println("3. Smoke Bomb - Enemy attacks deal 0 damage for this and next turn");

            int choice = checkErrorInt("Enter 1-3: ", 1, 3);
            /*
            switch (choice) {
                case 1:
                    selectedItems.add(new Item(ItemType.POTION));
                    break;

                case 2:
                    selectedItems.add(new Item(ItemType.POWER_STONE));
                    break;

                case 3:
                    selectedItems.add(new Item(ItemType.SMOKE_BOMB));
                    break;
            }
            */
            System.out.println();
        }

        System.out.println("Items selected: " + selectedItems);
        return selectedItems;
    }

    @Override
    public void displayTurnStart(GameStateReadOnly gameStateReadOnly) {

    }

    @Override
    public ActionParameters getPlayerAction(GameStateReadOnly gameState) {
        System.out.println("\nChoose your action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill");

        int choice = checkErrorInt("Enter 1-4: ", 1, 4);
        switch (choice) {
            case 1:
                int index = 1;
                int no_of_enemies = gameState.getCurrEnemies().size();
                if (no_of_enemies > 1) {
                    index = checkErrorInt("Select target", 1, no_of_enemies);
                }
                return new ActionParameters(ActionType.BASIC_ATTACK, gameState.getPlayer(), index - 1, null);
            /*case 2: // DEFEND
                return new ActionParameters(
                        ActionType.DEFEND,
                        gameState.getPlayer(),
                        -1,
                        null
                );*/
                /*
            case 3: // USE ITEM/
                System.out.println("=== PLAYER INVENTORY ===\n");

                // Table header
                System.out.printf("%-4s %-20s %-12s %-8s\n", "No.", "Item Name", "Type", "Qty");
                System.out.println("------------------------------------------------");

                // Table rows
                for (int i = 0; i < gameState..length; i++) {
                    System.out.printf("%-4d %-20s %-12s %-8d\n",
                            i + 1,                 // item number
                            list[i].getName(),    // item name
                            list[i].getType(),    // item type
                            list[i].getQuantity() // quantity
                    );
                }

                System.out.println("------------------------------------------------");

                // Ask user to choose item
                int choice = CheckErrorINT("Enter choice: ", 1, list.length);

                // Return the selected index (0-based if needed)
                return 1;*//*
            case 4: // SPECIAL SKILL
                int target = 1;
                int enemies = gameState.getCurrEnemies().size();

                if (enemies > 1 && gameState.getPlayer().getClass() == "Warrior") {
                    target = CheckErrorINT("Select target enemy (1-" + enemies + "): ", 1, enemies);
                }

                return new ActionParameters(
                        ActionType.SPECIAL_SKILL,
                        gameState.getPlayer(),
                        target,
                        null
                );*/
            default:
                return null;
        }
    }

    @Override
    public void showEndingScreen(GameStateReadOnly gameState) {
        if(!gameState.isPlayerAlive())
        {
            System.out.println("You Lost!");
        }
        else {
            System.out.println("You Won!");
        }
    }

    @Override
    public void displayTurnEnd(GameStateReadOnly gameState) {

    }
}
