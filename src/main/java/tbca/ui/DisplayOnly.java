package tbca.ui;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.engine.GameStateReadOnly;
import tbca.item.Item;
import tbca.item.ItemType;

import java.util.List;

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
            System.out.println("Inventory is empty!");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                System.out.printf("%s: %d|", item.getName(), item.getAmount());
            }
        }
        System.out.printf("Special Skills Cooldown: Round %d",
                gameState.getPlayer().getSpecialSkillCooldown());
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

    public void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, List<Integer> target, List<Integer> dmg) {
        System.out.println(actor.getName() + " performs a Basic Attack!");

        for (int i = 0; i < target.size(); i++) {
            int targetIndex = target.get(i);
            Combatant victim = gameState.getCurrEnemies().get(targetIndex);

            int damage = dmg.get(i);
            System.out.println(victim.getName() + " takes " + damage + " damage!");
        }
    }
    public void displayDefend(GameStateReadOnly gameState, Combatant actor) {
        System.out.println(actor.getName() + " Defends");
    }
    public void displayItem(GameStateReadOnly gameState,Combatant actor, ItemType item) {
        System.out.println(actor.getName() + " uses " + item.getDisplayName());
    }

}