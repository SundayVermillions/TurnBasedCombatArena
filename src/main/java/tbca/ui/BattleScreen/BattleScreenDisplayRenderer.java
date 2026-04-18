package tbca.ui.BattleScreen;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.player.Player;
import tbca.domain.effect.FieldEffect;
import tbca.domain.effect.StatusEffect;
import tbca.domain.gamestate.GameStateReadOnly;
import tbca.domain.item.Item;
import tbca.domain.item.ItemType;
import tbca.engine.action.ActionType;
import tbca.engine.action.results.*;
import tbca.ui.UiUtlity.Color;
import tbca.ui.StartingScreen.StartingScreen;
import tbca.ui.UiUtlity.UIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleScreenDisplayRenderer {
    StartingScreen loadingScreen;

    private static final int TURN_HEADER_WIDTH = 41;

    public BattleScreenDisplayRenderer()
    {
        this.loadingScreen = new StartingScreen();
    }

    public static void displayTurnStart(GameStateReadOnly gameState) {
        String header = "--- Wave " + gameState.currWave() + "/" + gameState.getTotalWaves() +  " | Turn " + gameState.getCurrTurn() +" ---";
        System.out.println("\n" + UIUtils.centerText(header, TURN_HEADER_WIDTH));
        if (gameState.getPlayer().getCurrHp() <= 0) {
            System.out.println(String.format("%-12s: DEAD", gameState.getPlayer().getName()));
        } else {
            displayTurnStartFormat(gameState.getPlayer(),gameState);
        }

        for (int i = 0; i < gameState.getCurrEnemies().size(); i++) {
            Combatant enemy = gameState.getCurrEnemies().get(i);
            if (enemy.getCurrHp() <= 0) {
                System.out.println(String.format("%-12s: DEAD", enemy.getName()));
            } else {
                displayTurnStartFormat(enemy,gameState);
            }
        }
        displayItemsAndCooldown(gameState);

        if(!gameState.getActiveFieldEffects().isEmpty())
        {
            System.out.print("\nActive Field Effect: ");
            for(int i = 0; i < gameState.getActiveFieldEffects().size(); i++) {
                FieldEffect effect = gameState.getActiveFieldEffects().get(i);
                System.out.print(Color.YELLOW + effect.getName() +Color.RESET+ "(" + effect.getTurnsRemaining() + " turn)");
                if(i < gameState.getActiveFieldEffects().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

    }

    private static void displayTurnStartFormat(Combatant actor, GameStateReadOnly gameState)
    {
        System.out.printf("%-12s: %-20s (%3d/%3d)",
                actor.getName(),
                UIUtils.healthBar(actor.getCurrHp(), actor.getMaxHp()),
                actor.getCurrHp(),
                actor.getMaxHp());

        //status effect
        if(!actor.getEffects().isEmpty())
        {

            System.out.print("[");
            for(int j = 0; j < actor.getEffects().size(); j++) {
                if(actor.getEffects().get(j).isBuff())
                {
                    System.out.print(Color.GREEN);
                }
                else{
                    System.out.print(Color.RED);
                }
                System.out.print(actor.getEffects().get(j).getName() + Color.RESET);

                if(j < actor.getEffects().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
        }
        //field Effect
        if(!gameState.getActiveFieldEffects().isEmpty())
        {
            for(FieldEffect fieldEffect : gameState.getActiveFieldEffects())
            {
                if(fieldEffect.appliesTo(actor))
                {
                    System.out.print("["+Color.YELLOW+fieldEffect.getName()+Color.RESET+"]");
                }
            }
        }


        System.out.println();
    }

    private static void displayItemsAndCooldown(GameStateReadOnly gameState) {
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

    public void displayTurnEnd(GameStateReadOnly gameState) {
        System.out.println();
        System.out.printf("End of Turn: %d\n", gameState.getCurrTurn());

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
                        displaySpecialSkill(gameState, specialSkills.actor(), specialSkills.targets(), specialSkills.hpChanges(), specialSkills.statusEffects());
                    }

                }
            }
            case SPECIAL_SKILL -> {
                SpecialSkillResults skillResults = (SpecialSkillResults) actionResults;
                displaySpecialSkill(gameState, skillResults.actor(),
                        skillResults.targets(), skillResults.hpChanges(),
                        skillResults.statusEffects());

            }

        }
    }

    private void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, int targetEnemyIndex, int damage) {
        System.out.print(actor.getName() + " performs a Basic Attack! → ");

        if (actor.isPlayer()) {
            Combatant victim = gameState.getCurrEnemies().get(targetEnemyIndex);
            System.out.println(victim.getName() + " takes " + Color.RED + damage + Color.RESET + " damage!");
        } else {
            System.out.println(gameState.getPlayer().getName() + " takes " + Color.RED + damage + Color.RESET + " damage!");
        }
    }

    private void displayDefend(GameStateReadOnly gameState, Combatant actor) {
        System.out.println(actor.getName() + " Defends");
    }

    private void displayItem(GameStateReadOnly gameState, Combatant actor, ItemType item) {
        System.out.println( actor.getName() + " uses " + Color.GREEN + item.getDisplayName() + Color.RESET);
    }

    private void displaySpecialSkill(GameStateReadOnly gameState, Combatant actor,
                                     List<Combatant> targets, List<Integer> damage,
                                     List<StatusEffect> statusEffects) {
        if (actor.isPlayer()) {
            for (int i = 0; i < targets.size(); i++) {
                Combatant victim = targets.get(i);
                int dmgAmount = Math.abs(damage.get(i));
                String skillName = actor.getSpecialSkillType().getDisplayName();
                System.out.print(actor.getName() + " uses " + skillName + "! -> ");
                if (dmgAmount > 0) {
                    System.out.print(victim.getName() + " takes " + Color.RED + dmgAmount + Color.RESET + " damage!");
                }
                if (statusEffects != null && !statusEffects.isEmpty()) {
                    StatusEffect effect = statusEffects.get(i);
                    if (effect != null) {
                        if (effect.isBuff()) {
                            System.out.print(actor.getName() + " buffed with " + Color.GREEN +effect.getName() + Color.RESET);
                        } else {
                            System.out.print(victim.getName() + " inflicted with " +Color.RED+ effect.getName() + Color.RESET);
                        }
                    }
                }
                System.out.println();
            }
        } else {
            Combatant victim = gameState.getPlayer();
            int dmgAmount = Math.abs(damage.isEmpty() ? 0 : damage.get(0));
            String skillName = actor.getSpecialSkillType().getDisplayName();

            System.out.print(actor.getName() + " uses " + skillName + "! -> ");
            if (dmgAmount > 0) {
                System.out.print(victim.getName() + " takes " + Color.RED + dmgAmount + Color.RESET + " damage!");
            }
            if (statusEffects != null && !statusEffects.isEmpty()) {
                StatusEffect effect = statusEffects.get(0);
                if (effect != null) {
                    if (effect.isBuff()) {
                        System.out.print(actor.getName() + " buffed with " + Color.GREEN +effect.getName() + Color.RESET);
                    } else {
                        System.out.print(victim.getName() + " inflicted with " +Color.RED+ effect.getName() + Color.RESET);
                    }
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
    public void displayIncapacitated(Combatant combatant)
    {
        if(!combatant.canAct())
        {
            System.out.println(combatant.getName() + " is unable to move!");
        }
    }
}
