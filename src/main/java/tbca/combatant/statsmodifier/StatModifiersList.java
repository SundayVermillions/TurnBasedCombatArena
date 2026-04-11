package tbca.combatant.statsmodifier;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// class representing a list of stat modifiers
// abstracts out the application of stat into effectiveStat
public class StatModifiersList {

    private final Map<String, StatModifier> modifiers = new HashMap<>();

    public void add(String effectId, StatModifier modifier) {
        modifiers.put(effectId, modifier);
    }

    public void remove(String effectId) {
        modifiers.remove(effectId);
    }

    public int applyAllModifiers(int baseStat) {
        int effectiveStat = baseStat;
        List<Map.Entry<String, StatModifier>> sorted = modifiers.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<String, StatModifier> e) -> e.getValue().getPriority().getPriority())
                        .thenComparing(Map.Entry::getKey))
                .toList();
        for (Map.Entry<String, StatModifier> entry : sorted) {
            effectiveStat = entry.getValue().apply(effectiveStat);
        }
        return effectiveStat;
    }
}
