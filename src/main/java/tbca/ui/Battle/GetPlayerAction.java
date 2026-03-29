package tbca.ui.Battle;

import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.ActionType;
import tbca.ui.Input.InputValidator;

import java.util.Scanner;

public class GetPlayerAction {
    InputValidator inputValidator;
    public GetPlayerAction()
    {
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }
    public ActionParameters playerAction(GameStateReadOnly gameState) {
        System.out.println("\nChoose your action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill");


        int choice = inputValidator.getIntInput("Enter 1-4: ", 1, 4);
        switch (choice) {
            case 1:
                int index = 1;
                int no_of_enemies = gameState.getCurrEnemies().size();
                if (no_of_enemies > 1) {
                    index = inputValidator.getIntInput("Select target", 1, no_of_enemies);
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
}
