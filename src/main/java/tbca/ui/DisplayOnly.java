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
    private static final int TURN_HEADER_WIDTH = 41;


    public static void displayMenu() {
        System.out.println("=========================================");
        System.out.println("       TURN-BASED COMBAT ARENA           ");
        System.out.println("=========================================\n");
        System.out.println();
        System.out.println("1. Start Game");
        System.out.println("2. View Stats/Details");
        System.out.println();
    }

    private static void displayTurnStartFormat(Combatant actor)
    {
        System.out.printf("%-12s: %-20s (%3d/%3d)",
                actor.getName(),
                healthBar(actor.getCurrHp(), actor.getMaxHp()),
                actor.getCurrHp(),
                actor.getMaxHp());
        if(!actor.getEffects().isEmpty())
        {
            System.out.print("[");
            for(int j = 0; j < actor.getEffects().size(); j++) {
                System.out.print(actor.getEffects().get(j).getName());
                if(j < actor.getEffects().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
        }
        System.out.println();
    }

    public void displayTurnStart(GameStateReadOnly gameState) {
        String header = "--- Wave " + gameState.currWave() + "/" + gameState.getTotalWaves() +  " | Turn " + gameState.getCurrTurn() +" ---";
        System.out.println("\n" + centerText(header, TURN_HEADER_WIDTH));//Center header based on health bar width

        displayTurnStartFormat(gameState.getPlayer());

        for (int i = 0; i < gameState.getCurrEnemies().size(); i++) {
            Combatant enemy = gameState.getCurrEnemies().get(i);
            if (enemy.getCurrHp() <= 0) {
                System.out.println(String.format("%-12s: DEAD", enemy.getName()));
            } else {
                displayTurnStartFormat(enemy);
                }
        }
        displayItemsAndCooldown(gameState);
    }


    public void displayTurnEnd(GameStateReadOnly gameState) {
        System.out.println();
        System.out.printf("End of Turn: %d\n", gameState.getCurrTurn());

        //displayPlayerAndEnemyStats(gameState);
    }
    /*
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
            for (StatusEffect effect : enemy.getEffects()) {
                System.out.printf("[%s: %d turns] ", effect.getName(), effect.getRemainingTurns());
            }

            if (i < enemies.size() - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }*/

    private void displayItemsAndCooldown(GameStateReadOnly gameState) {
        Player player = (Player) gameState.getPlayer();
        List<Item> inventory = player.getInventory();
        System.out.print("Inventory: ");
        if (inventory.isEmpty()) {
            System.out.print("Inventory is empty!|");
        } else {
            Map<String, Integer> groupedItems = new HashMap<>();
            for (Item item : inventory) {
                String itemName = item.getName();
                groupedItems.put(itemName, groupedItems.getOrDefault(itemName, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : groupedItems.entrySet()) {
                System.out.print(entry.getKey() + " x" + entry.getValue() + " ");
            }
        }
        System.out.printf("\nSpecial Skill (%s) Cooldown: %d",
                gameState.getPlayer().getSpecialSkillType().getDisplayName(),gameState.getPlayer().getSpecialSkillCooldown());
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
        System.out.println("=========================================");
        System.out.println("              YOU LOST!                  ");
        System.out.println("         Better luck next time!          ");
        System.out.println("=========================================\n");
    }

    private void displayVictoryMessage() {
        System.out.println("=========================================");
        System.out.println("              YOU WON!                   ");
        System.out.println("       Congratulations on your victory!  ");
        System.out.println("=========================================\n");


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

                if (itemResults.item() == ItemType.POWER_STONE && itemResults.specialSkillResults() != null) {
                    SpecialSkillResults specialSkills = itemResults.specialSkillResults();

                    if (!specialSkills.targets().isEmpty()) {
                        System.out.println("(Power Stone) " + itemResults.actor().getName() + " activated a bonus skill!");
                        displaySpecialSkill(gameState, specialSkills.actor(), specialSkills.targets(), specialSkills.dmg(), specialSkills.statusEffects());
                    }

                }
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
        System.out.print(actor.getName() + " performs a Basic Attack! → ");

        if (actor.isPlayer()) {
            Combatant victim = gameState.getCurrEnemies().get(targetEnemyIndex);
            System.out.println(victim.getName() + " takes " + damage + " damage!");
        } else {
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
        if (actor.isPlayer()) {
            for (int i = 0; i < targets.size(); i++) {
                int targetIndex = targets.get(i);
                Combatant victim = gameState.getCurrEnemies().get(targetIndex);
                int dmgAmount = damage.get(i);
                String skillName = actor.getSpecialSkillType().getDisplayName();
                System.out.print(actor.getName() + " uses " + skillName + "! -> ");
                System.out.print(victim.getName() + " takes " + dmgAmount + " damage!");
                if (statusEffects != null && !statusEffects.isEmpty()) {
                    StatusEffect effect = statusEffects.get(i);
                    if (effect != null) {
                        System.out.print(" -> inflicted with " + effect.getName());
                    }
                }
                System.out.println();
            }
        } else {
            Combatant victim = gameState.getPlayer();
            int dmgAmount = damage.isEmpty() ? 0 : damage.get(0);
            String skillName = actor.getSpecialSkillType().getDisplayName();

            System.out.print(actor.getName() + " uses " + skillName + "! -> ");
            if (dmgAmount > 0) {
                System.out.print(victim.getName() + " takes " + dmgAmount + " damage!");
            }
            if (statusEffects != null && !statusEffects.isEmpty()) {
                StatusEffect effect = statusEffects.get(0);
                if (effect != null) {
                    /*
                    if(effect.isBuff())
                    {
                        System.out.print(actor.getName() + " buffed with " + effect.getName());
                    }
                    else
                    {
                        System.out.print(victim.getName() + " inflicted with " + effect.getName());
                    }

                     */
                }
            }
        }
            System.out.println();
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

    private static String healthBar(int hp, int maxHp) {
        int totalBars = 20;
        int filledBars = (int) ((hp / (double) maxHp) * totalBars);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < totalBars; i++) {
            if (i < filledBars) bar.append("=");
            else bar.append(" ");
        }
        bar.append("]");
        return bar.toString();
    }

    //Function to center header based on health bar width
    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int leftPadding = (width - text.length()) / 2;
        return " ".repeat(leftPadding) + text;
    }
    public void displayIncapacitated(Combatant combatant)
    {
        if(!combatant.canAct())
        {
            System.out.println(combatant.getName() + "is unable to move!");
        }
    }

}