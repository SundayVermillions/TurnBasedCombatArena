package tbca.ui.Battle;

import java.util.Scanner;

import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.parameters.BasicAttackParameters;
import tbca.engine.action.parameters.DefendParameters;
import tbca.engine.action.parameters.SpecialSkillParameters;
import tbca.engine.action.parameters.UseItemParameters;
import tbca.item.ItemType;
import tbca.ui.Input.InputValidator;

public class GetPlayerAction {
    InputValidator inputValidator;

    public GetPlayerAction() {
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
                return new BasicAttackParameters(gameState.getPlayer(), promptTargetEnemyIndex(gameState));
            case 2:
                return new DefendParameters(gameState.getPlayer());
            case 3:
                return new UseItemParameters(gameState.getPlayer(), promptItemType());
            case 4:
                return new SpecialSkillParameters(gameState.getPlayer(), promptTargetEnemyIndex(gameState));
        }
        return null;
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

        switch (itemChoice) {
            case 1:
                return ItemType.POTION;
            case 2:
                return ItemType.POWER_STONE;
            case 3:
                return ItemType.SMOKE_BOMB;
        }
        return null;
    }
}
