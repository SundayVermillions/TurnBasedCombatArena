package tbca.ui;

import tbca.combatant.Combatant;
import tbca.combatant.player.Player;
import tbca.effect.FieldEffect;
import tbca.effect.StatusEffect;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.ActionType;
import tbca.engine.action.parameters.*;
import tbca.engine.action.results.*;
import tbca.item.Item;
import tbca.item.ItemType;

import javax.swing.plaf.ColorChooserUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameplayScreen {
    InputValidator inputValidator;
    LoadingScreen loadingScreen;

    private static final int TURN_HEADER_WIDTH = 41;

    public GameplayScreen()
    {
        this.loadingScreen = new LoadingScreen();
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }
    //
    private static void displayTurnStartFormat(Combatant actor, GameStateReadOnly gameState)
    {
        System.out.printf("%-12s: %-20s (%3d/%3d)",
                actor.getName(),
                healthBar(actor.getCurrHp(), actor.getMaxHp()),
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

    private static String healthBar(int hp, int maxHp) {
        int totalBars = 22;
        int filledBars = (int) ((hp / (double) maxHp) * totalBars);
        StringBuilder bar = new StringBuilder("[");
        bar.append(Color.RED);
        for (int i = 0; i < totalBars; i++) {
            if (i < filledBars) bar.append("=");
            else bar.append(" ");
        }
        bar.append(Color.RESET);
        bar.append("]");
        return bar.toString();
    }

    public void displayTurnStart(GameStateReadOnly gameState) {
        String header = "--- Wave " + gameState.currWave() + "/" + gameState.getTotalWaves() +  " | Turn " + gameState.getCurrTurn() +" ---";
        System.out.println("\n" + centerText(header, TURN_HEADER_WIDTH));

        displayTurnStartFormat(gameState.getPlayer(),gameState);

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


    public void displayTurnEnd(GameStateReadOnly gameState) {
        System.out.println();
        System.out.printf("End of Turn: %d\n", gameState.getCurrTurn());

    }


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
                loadingScreen.showDetails();
                displayTurnStart(gameState);
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
            System.out.println(combatant.getName() + " is unable to move!");
        }
    }

}