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
    public void displayTurnStart(GameStateReadOnly gameState) {
        System.out.println("\n--- Wave " + gameState.currWave() + " | Turn " + gameState.getCurrTurn() + " ---");
        System.out.println("Player HP: " + gameState.getPlayer().getCurrHp() + "/" + gameState.getPlayer().getMaxHp());

        for (int i = 0; i < gameState.getCurrEnemies().size(); i++) {
            Combatant enemy = gameState.getCurrEnemies().get(i);
            if (enemy.getCurrHp() <= 0) {
                System.out.println((i + 1) + ". " + enemy.getName() + ": DEAD");
            } else {
                System.out.println((i + 1) + ". " + enemy.getName() + ": " + enemy.getCurrHp() + "/" + enemy.getMaxHp() + " HP");
            }
        }
        System.out.println();

    }
    public void displayTurnEnd(GameStateReadOnly gameState) {
        System.out.println();
        System.out.printf("End of Turn: %d\n",gameState.getCurrTurn());

        displayPlayerAndEnemyStats(gameState);
        displayItemsAndCooldown(gameState);
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
            System.out.printf("%s HP: %d",
                    enemy.getName(),
                    enemy.getCurrHp()
            );
            if (!enemy.canAct()) {
                System.out.printf("[Stunned for %d turns]", enemy.getRemainingEffectTurn());
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
        if (inventory.isEmpty()) {
            System.out.print("Inventory is empty!|");
        } else {
            Map<String, Integer> groupedItems = new HashMap<>();
            for (Item item : inventory) {
                String itemName = item.getName();
                groupedItems.put(itemName, groupedItems.getOrDefault(itemName, 0) + 1);
            }
            System.out.print(" | ");
            for (Map.Entry<String, Integer> entry : groupedItems.entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + " ");
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

                if(itemResults.item() == ItemType.POWER_STONE && itemResults.specialSkillResults() != null){
                    SpecialSkillResults specialSkills = itemResults.specialSkillResults();

                    if(!specialSkills.targets().isEmpty()){
                        System.out.println("(Power Stone) " + itemResults.actor().getName() + " activated a bonus skill!");
                        displaySpecialSkill(gameState, specialSkills.actor(), specialSkills.targets(),specialSkills.dmg(), specialSkills.statusEffects());
                    }

                }
            }
            case SPECIAL_SKILL -> {
                SpecialSkillResults skillResults = (SpecialSkillResults) actionResults;
                if(skillResults.targets().isEmpty()){
                    System.out.println(skillResults.actor().getName() + "'s skill is still charging!");
                }
                else {
                    displaySpecialSkill(gameState, skillResults.actor(),
                            skillResults.targets(), skillResults.dmg(),
                            skillResults.statusEffects());
                }
            }

        }
    }

    private void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, int targetEnemyIndex, int damage) {
        System.out.print(actor.getName() + " performs a Basic Attack! → ");

        if (actor.isPlayer()) {
            Combatant victim = gameState.getCurrEnemies().get(targetEnemyIndex);
            System.out.println(victim.getName() + " takes " + damage + " damage!");
        }
        else{
            System.out.println(gameState.getPlayer().getName() + " takes " + damage + " damage!");
        }
    }

    private void displayDefend(GameStateReadOnly gameState, Combatant actor) {
        System.out.println(actor.getName() + " Defends");
    }

    private void displayItem(GameStateReadOnly gameState, Combatant actor, ItemType item) {
        System.out.println(actor.getName() + " uses " + item.getDisplayName());
    }

    private void displaySpecialSkill(GameStateReadOnly gameState, Combatant actor,
                                     List<Integer> targets, List<Integer> damage,
                                     List<StatusEffect> statusEffects) {
        String skillName = actor.getClass().getSimpleName().equals("Warrior") ? "Shield Bash" : "Arcane Blast";
        System.out.print(actor.getName() + " uses " + skillName + "! -> ");
        for (int i = 0; i < targets.size(); i++) {
            int targetIndex = targets.get(i);
            Combatant victim = gameState.getCurrEnemies().get(targetIndex);
            int dmgAmount = damage.get(i);
            System.out.print(victim.getName() + " takes " + dmgAmount + " damage!");
            if(i < targets.size() - 1) System.out.print(", ");
            else{
                System.out.println();
            }
        }

        if (statusEffects != null && !statusEffects.isEmpty()) {
            System.out.print(" → Inflicts: ");
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

    public void displayEnemyDefeated(GameStateReadOnly gameState, int enemyIndex) {
        List<Combatant> enemies = gameState.getCurrEnemies();
        if (enemyIndex >= 0 && enemyIndex < enemies.size()) {
            Combatant defeatedEnemy = enemies.get(enemyIndex);
            System.out.println(defeatedEnemy.getName() + " has been defeated!");
        } else {
            System.out.println("Enemy at index " + enemyIndex + " not found!");
        }
    }
}