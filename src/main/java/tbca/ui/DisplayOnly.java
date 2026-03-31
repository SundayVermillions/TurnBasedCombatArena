package tbca.ui;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.effect.StatusEffect;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.ActionType;
import tbca.engine.action.results.*;
import tbca.item.Item;
import tbca.item.ItemType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayOnly {
    public void displayMenu() {
        System.out.println("=========================================");
        System.out.println("       TURN-BASED COMBAT ARENA           ");
        System.out.println("=========================================\n");
    }
    public void displayTurnEnd(GameStateReadOnly gameState) {
        displayPlayerAndEnemyStats(gameState);
        displayItemsAndCooldown(gameState);
    }

    public void displayTurnStart(GameStateReadOnly gameState) {
        System.out.printf("=== Round %d ===\n", gameState.currWave());
    }

    private void displayPlayerAndEnemyStats(GameStateReadOnly gameState) {
        // Player HP
        System.out.printf("%s HP: %d/%d | ",
                gameState.getPlayer().getClass().getSimpleName(),
                gameState.getPlayer().getCurrHp(),
                gameState.getPlayer().getMaxHp()
        );

        // Enemies HP
        List<Combatant> enemies = gameState.getCurrEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Combatant enemy = enemies.get(i);
            System.out.printf("%s %c HP: %d",
                    enemy.getName(),
                    (char)('A' + i),
                    enemy.getCurrHp()
            );

            if(!enemy.canAct())
            {
                System.out.print("[Stunned]");
            }

            if (i < enemies.size() - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    private void displayItemsAndCooldown(GameStateReadOnly gameState) {
        Player player = (Player) gameState.getPlayer();
        List<Item> inventory = player.getInventory();

        System.out.println("=== INVENTORY ===");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty!|");
        } else {
            Map<String, Integer> groupedItems = new HashMap<>();
            for (Item item : inventory) {
                String itemName = item.getName();
                groupedItems.put(itemName, groupedItems.getOrDefault(itemName, 0) + 1);
            }
            System.out.print(" | ");
            for (Map.Entry<String, Integer> entry : groupedItems.entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue());
            }
        }
        System.out.printf("|Special Skills Cooldown: Round %d",
                gameState.getPlayer().getSpecialSkillCooldown());
        System.out.println();
    }


    public void showEndingScreen(GameStateReadOnly gameState) {
        displayEndingHeader();

        if (!gameState.isPlayerAlive()) {
            displayLossMessage();
        } else {
            displayVictoryMessage();
        }
    }

    private void displayEndingHeader() {
        System.out.println("\n=========================================");
        System.out.println("              GAME OVER                 ");
        System.out.println("=========================================\n");
    }

    private void displayLossMessage() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║           YOU LOST!                   ║");
        System.out.println("║    Better luck next time!             ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
    }

    private void displayVictoryMessage() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║           YOU WON!                    ║");
        System.out.println("║    Congratulations on your victory!   ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
    }
    public void displayAction(GameStateReadOnly gameState, ActionResults actionResults) {
        ActionType actionType = actionResults.actionType();
        switch (actionType) {
            case BASIC_ATTACK -> {
                BasicAttackResults basicResults = (BasicAttackResults) actionResults;
                displayBasicAttack(gameState, basicResults.actor(),
                        basicResults.targetEnemyIndex(), basicResults.damage());
            }
            case DEFEND -> {
                DefendResults defendResults = (DefendResults) actionResults;
                displayDefend(gameState, defendResults.actor());
            }
            case USE_ITEM -> {
                UseItemResults itemResults = (UseItemResults) actionResults;
                displayItem(gameState, itemResults.actor(), itemResults.item());
            }
            case SPECIAL_SKILL -> {
                SpecialSkillResults skillResults = (SpecialSkillResults) actionResults;
                displaySpecialSkill(gameState, skillResults.actor(),
                        skillResults.targets(), skillResults.dmg(),
                        skillResults.statusEffects());
            }

        }
    }
    private void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, int targetEnemyIndex, int damage) {
        System.out.println(actor.getName() + " performs a Basic Attack!");

        Combatant victim = gameState.getCurrEnemies().get(targetEnemyIndex);
        System.out.println(victim.getName() + " takes " + damage + " damage!");
    }

    private void displayDefend(GameStateReadOnly gameState, Combatant actor) {
        System.out.println(actor.getName() + " Defends");
    }
    private void displayItem(GameStateReadOnly gameState,Combatant actor, ItemType item) {
        System.out.println(actor.getName() + " uses " + item.getDisplayName());
    }
    private void displaySpecialSkill(GameStateReadOnly gameState, Combatant actor,
                                     List<Integer> targets, List<Integer> damage,
                                     List<StatusEffect> statusEffects) {
        System.out.println(actor.getName() + " uses a Special Skill!");

        for (int i = 0; i < targets.size(); i++) {
            int targetIndex = targets.get(i);
            Combatant victim = gameState.getCurrEnemies().get(targetIndex);
            int dmgAmount = damage.get(i);
            System.out.println(victim.getName() + " takes " + dmgAmount + " damage!");
        }

        if (statusEffects != null && !statusEffects.isEmpty()) {
            System.out.print("Inflicts: ");
            for (int i = 0; i < statusEffects.size(); i++) {
                StatusEffect effect = statusEffects.get(i);
                System.out.print(effect.getName());
                if (i < statusEffects.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

}