package tbca.ui.BattleScreen;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.player.Player;
import tbca.domain.gamestate.GameStateReadOnly;
import tbca.domain.item.Item;
import tbca.domain.item.ItemType;
import tbca.engine.action.parameters.*;
import tbca.ui.UiUtility.InputValidator;
import tbca.ui.StartingScreen.ManualScreen;
import tbca.ui.StartingScreen.StartingScreen;

import java.util.List;
import java.util.Scanner;

public class BattleScreenInputHandler {
    InputValidator inputValidator;
    StartingScreen loadingScreen;
    ManualScreen manualScreen;

    private static final int TURN_HEADER_WIDTH = 41;

    public BattleScreenInputHandler()
    {
        this.inputValidator = new InputValidator(new Scanner(System.in));
        this.manualScreen = new ManualScreen();
    }

    public ActionParameters playerAction(GameStateReadOnly gameState) {
        int choice;
        Player player = (Player) gameState.getPlayer();
        List<Item> inventory = player.getInventory();

        while(true) {
            System.out.println("\nChoose your action:");
            System.out.println("1. Basic Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            System.out.printf("4. Special Skill (%s)%n", gameState.getPlayer().getSpecialSkillType().getDisplayName());
            System.out.println("5. Read Manual");

            choice = inputValidator.getIntInput("Pick choice 1-5: ", 1, 5);

            if (choice == 5) {
                manualScreen.showDetails();
                BattleScreenDisplayRenderer.displayTurnStart(gameState);
                continue;
            }

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
        int targetChoice;
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
            System.out.printf("%d: %-12s -- %s\n",
                    i + 1,
                    itemType.getDisplayName(),
                    itemType.getDescription());
        }
        int itemChoice = inputValidator.getIntInput("Enter 1-" + inventory.size() + ": ", 1, inventory.size());
        return inventory.get(itemChoice - 1).getType();
    }
}
