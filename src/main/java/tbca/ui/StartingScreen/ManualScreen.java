package tbca.ui.StartingScreen;

import tbca.domain.combatant.enemy.EnemyType;
import tbca.domain.combatant.player.playerclass.PlayerClass;
import tbca.domain.effect.*;
import tbca.engine.action.SpecialSkillType;
import tbca.domain.gamestate.difficulty.EnemyBlueprint;
import tbca.domain.gamestate.difficulty.GameDifficulty;
import tbca.domain.gamestate.difficulty.WaveBlueprint;
import tbca.domain.item.ItemType;
import tbca.ui.UiUtlity.InputValidator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ManualScreen {
    InputValidator inputValidator;
    public ManualScreen(){
        inputValidator = new InputValidator(new Scanner(System.in));
    }

    private static final List<GameDifficulty> DIFFICULTY_OPTIONS = List.of(GameDifficulty.values());


    public void showDetails(){
        boolean viewing = true;
        printDetailsHeader();
        while (viewing) {
            System.out.println();
            System.out.println("1. Staring Player Stats");
            System.out.println("2. Starting Enemy Stats");
            System.out.println("3. Debuffs/Status Effects");
            System.out.println("4. Item Details");
            System.out.println("5. Difficulty Info");
            System.out.println("6. Back to previous");
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
        System.out.println("--- Starting Player Stats ---");
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
        System.out.println("--- Starting Enemy Stats ---");
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
                new ArcaneBlastBuff(),
                new AttackBuffEffect(2.0, 1)
        );

        System.out.printf("%-20s %-12s %s\n", "Effect", "Duration", "Description");
        for (StatusEffect effect : effects) {
            String duration = formatEffectDuration(effect.getRemainingTurns());
            System.out.printf("%-20s %-12s %s\n", effect.getName(), duration, effect.getDescription());
        }
        System.out.println("\n-- Field Effects ---");
        SmokeBombEffect smokebombitem = new SmokeBombEffect(true);
        System.out.printf("%-20s %-12s %s\n", smokebombitem.getName(), "2 turns", smokebombitem.getDescription());


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

    private static final List<ItemType> STARTING_ITEM_OPTIONS = List.of(
            ItemType.POTION,
            ItemType.POWER_STONE,
            ItemType.SMOKE_BOMB
    );

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


}
