package tbca.ui;

import tbca.combatant.Combatant;
import tbca.combatant.enemy.EnemyType;
import tbca.combatant.player.Player;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.effect.ArcaneBlastBuff;
import tbca.effect.AttackBuffEffect;
import tbca.effect.DefendEffect;
import tbca.effect.SmokeBombInvulnerability;
import tbca.effect.StatusEffect;
import tbca.effect.StunEffect;
import tbca.engine.difficulty.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.SpecialSkillType;
import tbca.engine.action.parameters.*;
import tbca.engine.difficulty.EnemyBlueprint;
import tbca.engine.difficulty.WaveBlueprint;
import tbca.item.Item;
import tbca.item.ItemType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Selection {
    DisplayOnly displayOnly;
    InputValidator inputValidator;

    public Selection() {
        this.displayOnly = new DisplayOnly();
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }

    public void startingMenu()
    {
        DisplayOnly.displayMenu();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, 2);
        switch(choice){
            case 1:
                return;
            case 2:
                showDetails();
                break;
        }
    }

    private void showDetails(){
        boolean viewing = true;
        printDetailsHeader();
        while (viewing) {
            System.out.println();
            System.out.println("1. Player Stats");
            System.out.println("2. Enemy Stats");
            System.out.println("3. Debuffs/Status Effects");
            System.out.println("4. Item Details");
            System.out.println("5. Difficulty Info");
            System.out.println("6. Back to Main Menu");
            System.out.println();

            int choice = inputValidator.getIntInput("Enter choice: ", 1, 6);
            System.out.println();

            switch (choice) {
                case 1 -> showPlayerStatsDetails();
                case 2 -> showEnemyStatsDetails();
                case 3 -> showStatusEffectDetails();
                case 4 -> showItemDetails();
                case 5 -> showDifficultyDetails();
                case 6 -> viewing = false;
                default -> throw new IllegalStateException("Unexpected choice: " + choice);
            }
        }
    }

    private void printDetailsHeader() {
        System.out.println("\n=========================================");
        System.out.println("              INFORMATION                ");
        System.out.println("=========================================");
    }

    private void showPlayerStatsDetails() {
        System.out.println("--- Player Stats ---");
        for (PlayerClass playerClass : PlayerClass.values()) {
            System.out.println(playerClass.getLabel());
            System.out.printf("  HP      : %d\n", playerClass.getMaxHp());
            System.out.printf("  Attack  : %d\n", playerClass.getAttack());
            System.out.printf("  Defense : %d\n", playerClass.getDefense());
            System.out.printf("  Speed   : %d\n", playerClass.getSpeed());
            System.out.println();
        }

        System.out.println("Special Skills:");
        for (SpecialSkillType skillType : SpecialSkillType.values()) {
            if (skillType == SpecialSkillType.NONE) {
                continue;
            }
            System.out.printf("- %s: %s\n",
                    skillType.getDisplayName(),
                    skillType.getDescription().replace("\n", " "));
        }
        System.out.println();
    }

    private void showEnemyStatsDetails() {
        System.out.println("--- Enemy Stats ---");
        for (EnemyType enemyType : EnemyType.values()) {
            System.out.println(enemyType.getDisplayName());
            System.out.printf("  HP      : %d\n", enemyType.getMaxHp());
            System.out.printf("  Attack  : %d\n", enemyType.getAttack());
            System.out.printf("  Defense : %d\n", enemyType.getDefense());
            System.out.printf("  Speed   : %d\n", enemyType.getSpeed());
            System.out.println();
        }
    }

    private void showStatusEffectDetails() {
        System.out.println("--- Debuffs / Status Effects ---");
        List<StatusEffect> effects = List.of(
                new DefendEffect(),
                new StunEffect(),
                new SmokeBombInvulnerability(),
                new ArcaneBlastBuff(),
                new AttackBuffEffect(2.0, 1)
        );

        System.out.printf("%-20s %-12s %s\n", "Effect", "Duration", "Description");
        for (StatusEffect effect : effects) {
            String duration = formatEffectDuration(effect.getRemainingTurns());
            System.out.printf("%-20s %-12s %s\n", effect.getName(), duration, effect.getDescription());
        }
        System.out.println();
    }
    private String formatEffectDuration(int turns) {
        if (turns <= 0) {
            return "Permanent";
        }
        if (turns == 1) {
            return "1 turn";
        }
        return turns + " turns";
    }

    private void showItemDetails() {
        System.out.println("--- Item Details ---");
        for (ItemType itemType : STARTING_ITEM_OPTIONS) {
            System.out.printf("- %-12s : %s\n",
                    itemType.getDisplayName(),
                    itemType.getDescription());
        }
        System.out.println();
    }

    private void showDifficultyDetails() {
        System.out.println("--- Difficulty Info ---");
        for (GameDifficulty difficulty : DIFFICULTY_OPTIONS) {
            List<WaveBlueprint> waves = difficulty.getEnemySpawnList();
            System.out.printf("%s (%d wave(s))\n", difficulty.getLabel(), waves.size());
            System.out.printf("  %s\n", difficulty.getDescription());

            for (int waveIndex = 0; waveIndex < waves.size(); waveIndex++) {
                Map<String, Integer> counts = new LinkedHashMap<>();
                for (EnemyBlueprint enemyBlueprint : waves.get(waveIndex).enemies()) {
                    String enemyName = enemyBlueprint.enemyType().getDisplayName();
                    counts.put(enemyName, counts.getOrDefault(enemyName, 0) + 1);
                }

                StringBuilder line = new StringBuilder();
                for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                    if (!line.isEmpty()) {
                        line.append(", ");
                    }
                    line.append(entry.getValue()).append("x ").append(entry.getKey());
                }

                System.out.printf("  Wave %d: %s\n", waveIndex + 1, line);
            }
            System.out.println();
        }
    }


    private static final List<GameDifficulty> DIFFICULTY_OPTIONS = List.of(GameDifficulty.values());

    // List of starting item options for selection
    private static final List<ItemType> STARTING_ITEM_OPTIONS = List.of(
            ItemType.POTION,
            ItemType.POWER_STONE,
            ItemType.SMOKE_BOMB
    );

    public GameDifficulty promptDifficulty() {
        displayDifficultyOptions();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, DIFFICULTY_OPTIONS.size());
        return DIFFICULTY_OPTIONS.get(choice - 1);
    }

    private void displayDifficultyOptions() {
        System.out.println("\nSelect game difficulty:");
        for (int i = 0; i < DIFFICULTY_OPTIONS.size(); i++) {
            GameDifficulty difficulty = DIFFICULTY_OPTIONS.get(i);
            System.out.println((i + 1) + ". " + difficulty.getLabel());
            System.out.println("   " + difficulty.getDescription());
        }
    }

    public List<ItemType> itemSelection()
    {
        List<ItemType> selectedItems = new ArrayList<>();
        System.out.println("Choose 2 starting items (duplicates allowed):\n");
        for (int i = 1; i <= 2; i++) {
            System.out.println("Item choice " + i + ":");
            printItem();
            int choice = inputValidator.getIntInput("Enter 1-" + STARTING_ITEM_OPTIONS.size() + ": ", 1, STARTING_ITEM_OPTIONS.size());
            selectedItems.add(mapChoiceToItemType(choice));
            System.out.println();
        }
        return selectedItems;
    }

    private ItemType mapChoiceToItemType(int choice) {
        return STARTING_ITEM_OPTIONS.get(choice - 1);
    }

    private void printItem()
    {
        for (int i = 0; i < STARTING_ITEM_OPTIONS.size(); i++) {
            printItemOption(i + 1, STARTING_ITEM_OPTIONS.get(i));
        }
    }

    private void printItemOption(int optionNumber, ItemType itemType) {
        System.out.printf("%d: %-12s -- %s\n",
                optionNumber,
                itemType.getDisplayName(),
                itemType.getDescription());
    }

    public PlayerClass classChoice()
    {
        displayClassOptions();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, 2);
        System.out.println("\n");
        return switch (choice) {
            case 1 -> PlayerClass.WARRIOR;
            case 2 -> PlayerClass.WIZARD;
            default -> null;
        };
    }


    private void displayClassOptions() {
        System.out.println("Select your class:");
        printClassOption(1, PlayerClass.WARRIOR, SpecialSkillType.SHIELD_BASH);
        printClassOption(2, PlayerClass.WIZARD, SpecialSkillType.ARCANE_BLAST);
    }

    // Use printAlignedLine to ensure proper alignment of class attributes and special skill description
    private void printClassOption(int optionNumber, PlayerClass playerClass, SpecialSkillType skillType) {
        System.out.println(optionNumber + ": " + playerClass.getLabel());
        printAlignedLine("HP", String.valueOf(playerClass.getMaxHp()));
        printAlignedLine("Attack", String.valueOf(playerClass.getAttack()));
        printAlignedLine("Defense", String.valueOf(playerClass.getDefense()));
        printAlignedLine("Speed", String.valueOf(playerClass.getSpeed()));

        String skillText = "[" + skillType.getDisplayName() + "] " + skillType.getDescription();
        String[] lines = skillText.split("\n");
        System.out.printf("  %-15s: %s\n", "Special Skill", lines[0]);
        for (int i = 1; i < lines.length; i++) {
            System.out.printf("%19s%s\n", "", lines[i]);
        }
        System.out.println();
    }

    // Helper method to print aligned lines for class attributes and special skill description
    private void printAlignedLine(String label, String value) {
        System.out.printf("  %-14s : %s\n", label, value);
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
                showDetails();
                displayOnly.displayTurnStart(gameState);
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
            System.out.printf("%d: %-12s -- %s\n", //make it align
                    i + 1,
                    itemType.getDisplayName(),
                    itemType.getDescription());
        }
        int itemChoice = inputValidator.getIntInput("Enter 1-" + inventory.size() + ": ", 1, inventory.size());
        return inventory.get(itemChoice - 1).getType();
    }

    public EndingScreenOptions promptEndingScreenChoice() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Replay with Same Settings");
        System.out.println("2. Start New Game");
        System.out.println("3. Exit Game");
        System.out.println();

        int choice = inputValidator.getIntInput("Enter choice: ", 1, 3);
        return switch (choice) {
            case 1 -> EndingScreenOptions.REPLAY_SAME_SETTINGS;
            case 2 -> EndingScreenOptions.START_NEW;
            case 3 -> EndingScreenOptions.EXIT;
            default -> null;
        };
    }
}