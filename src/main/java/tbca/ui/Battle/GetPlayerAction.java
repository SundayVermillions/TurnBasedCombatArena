package tbca.ui.Battle;

import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.parameters.BasicAttackParameters;
import tbca.engine.action.parameters.DefendParameters;
import tbca.engine.action.parameters.SpecialSkillParameters;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.item.ItemType;
import tbca.ui.Input.InputValidator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Scanner;

public class GetPlayerAction {
    InputValidator inputValidator;

    public GetPlayerAction() {
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }

    public ActionParameters playerAction(GameStateReadOnly gameState) {
        while (true) {
            System.out.println("\nChoose your action:");
            System.out.println("1. Basic Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            System.out.println("4. Special Skill");

            int choice = inputValidator.getIntInput("Enter 1-4: ", 1, 4);

            switch (choice) {
                case 1:
                    return new BasicAttackParameters(gameState.getPlayer(), promptTargetEnemyIndex(gameState));
                case 2:
                    return new DefendParameters(gameState.getPlayer());
                case 3:
                    if (!hasUsableItems(gameState)) {
                        System.out.println("No usable items available. Choose another action.");
                        continue;
                    }
                    return new UseItemParameters(gameState.getPlayer(), promptItemType());
                case 4:
                    return new SpecialSkillParameters(gameState.getPlayer(), promptTargetEnemyIndex(gameState));
                default:
                    throw new IllegalStateException("Unexpected action choice: " + choice);
            }
        }
    }

    private int promptTargetEnemyIndex(GameStateReadOnly gameState) {
        int enemies = gameState.getCurrEnemies().size();
        if (enemies <= 1) {
            return 0;
        }
        int targetChoice = inputValidator.getIntInput("Select target enemy (1-" + enemies + "): ", 1, enemies);
        return targetChoice - 1;
    }

    private ItemType promptItemType() {
        System.out.println("\nChoose item to use:");
        System.out.println("1. Potion");
        System.out.println("2. Power Stone");
        System.out.println("3. Smoke Bomb");

        int itemChoice = inputValidator.getIntInput("Enter 1-3: ", 1, 3);

        return switch (itemChoice) {
            case 1 -> ItemType.POTION;
            case 2 -> ItemType.POWER_STONE;
            case 3 -> ItemType.SMOKE_BOMB;
            default -> throw new IllegalStateException("Unexpected item choice: " + itemChoice);
        };
    }

    private boolean hasUsableItems(GameStateReadOnly gameState) {
        Object player = gameState.getPlayer();

        try {
            Method getItemCount = player.getClass().getMethod("getItemCount", ItemType.class);
            for (ItemType itemType : ItemType.values()) {
                Object countObj = getItemCount.invoke(player, itemType);
                if (countObj instanceof Number count && count.intValue() > 0) {
                    return true;
                }
            }
            return false;
        } catch (ReflectiveOperationException ignored) {
            // Fall through to alternate API checks.
        }

        try {
            Method getItems = player.getClass().getMethod("getItems");
            Object itemsObj = getItems.invoke(player);
            if (itemsObj instanceof Collection<?> items) {
                return !items.isEmpty();
            }
        } catch (ReflectiveOperationException ignored) {
            // Fall through to permissive default.
        }

        // Inventory API is not available yet, so allow item selection for now.
        return true;
    }
}
