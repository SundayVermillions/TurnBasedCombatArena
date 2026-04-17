package tbca.ui;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.ActionParameters;
import tbca.item.Item;
import tbca.ui.inputcheck.InputCheck;

import java.util.ArrayList;
import java.util.List;

public class Console implements Ui {

    private InputCheck checker;

    public Console() {
        this.checker = new InputCheck();
    }

    @Override
    public void displayMenu() {
        System.out.println("=========================================");
        System.out.println("       TURN-BASED COMBAT ARENA           ");
        System.out.println("=========================================\n");
    }

    @Override
    public GameDifficulty promptDifficulty() {
        System.out.println("Select Difficulty:");
        System.out.println("1. Easy (3 Goblins)");
        System.out.println("2. Medium (1 Goblin, 1 Wolf + Backups)");
        System.out.println("3. Hard (2 Goblins + Backups)");

        int choice = checker.getInt(1, 3, "Enter choice: ");
        return switch (choice) {
            case 1 -> GameDifficulty.EASY;
            case 2 -> GameDifficulty.MEDIUM;
            case 3 -> GameDifficulty.HARD;
            default -> GameDifficulty.EASY;
        };
    }

    @Override
    public PlayerClass promptClassSelection() {
        System.out.println("Select your hero:");
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
        int choice = checker.getInt(1, 2, "Enter choice: ");
        return (choice == 1) ? PlayerClass.WARRIOR : PlayerClass.WIZARD;
    }

    @Override
    public List<Item> promptItemSelection() {
        List<Item> selectedItems = new ArrayList<>();
        System.out.println("\nSelect 2 Items (Duplicates allowed):");
        System.out.println("1. Potion");
        System.out.println("2. Power Stone");
        System.out.println("3. Smoke Bomb");

        for (int i = 1; i <= 2; i++) {
            int choice = checker.getInt(1, 3, "Select Item " + i + ": ");
        }
        return selectedItems;
    }

    @Override
    public void displayTurnStart(GameStateReadOnly gameStateReadOnly) {
        System.out.println("\n--- Turn Start ---");
    }

    @Override
    public ActionParameters getPlayerAction(GameStateReadOnly gameState) {
        System.out.println("\nChoose your action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill");
        int actionType = checker.getInt(1, 4, "Enter action: ");
        int targetId = -1;
        if (actionType == 1) {
            System.out.println("\nSelect a target:");
            var enemies = gameState.getCurrEnemies();
        }

//            for (int i = 0; i < enemies.size(); i++) {
//                System.out.println((i + 1) + ". " + enemies.get(i).getName() +
//                        " (HP: " + enemies.get(i).getCurrHp() + ")");
//            }
//
//            int targetChoice = checker.getInt(1, enemies.size(), "Enter target: ");
//            targetId = enemies.get(targetChoice - 1).getId();
//        } else if (actionType == 3) {
//            System.out.println("\nSelect an item to use:");
//
//            var player = gameState.getPlayer();
//            var items = player.getItems();
//
//            if (items.isEmpty()) {
//                System.out.println("You have no items left!");
//            } else {
//                for (int i = 0; i < items.size(); i++) {
//                    System.out.println((i + 1) + ". " + items.get(i).getName());
//                }
//                int itemChoice = checker.getInt(1, items.size(), "Enter item: ");
//
//                targetId = items.get(itemChoice - 1).getId();
//            }
//        }
//        return new ActionParameters(actionType, targetId);
        return null;
    }

    @Override
    public void showEndingScreen(GameStateReadOnly gameState) {
        if (!gameState.isPlayerAlive()) {
            System.out.println("Defeated. Don't give up, try again!");
        } else {
            System.out.println("Congratulations, you have defeated all your enemies.");
        }
    }

    @Override
    public void displayTurnEnd(GameStateReadOnly gameState) {
        System.out.println("\n--- Turn End ---");
    }
}
