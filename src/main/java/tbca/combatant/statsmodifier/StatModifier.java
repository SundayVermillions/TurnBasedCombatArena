package tbca.combatant.statsmodifier;

public abstract sealed class StatModifier
        permits AdditiveModifier, MultiplicativeModifier, FinalOverrideModifier {

    // defines possible priority levels discretely.
    // higher = last to be applied.
    public enum Priority {
        ADDITIVE(10),
        MULTIPLICATIVE(20),
        FINAL_OVERRIDE(30);

        private final int priorityValue;

        Priority(int priorityValue) {
            this.priorityValue = priorityValue;
        }

        public int getValue() {
            return priorityValue;
        }
    }

    StatModifier() {}

    public abstract Priority getPriority();
    public abstract int apply(int currentValue);

    public static StatModifier additive(int additiveValue) {
        return new AdditiveModifier(additiveValue);
    }

    public static StatModifier multiplicative(double multiplicativeFactor) {
        return new MultiplicativeModifier(multiplicativeFactor);
    }

    public static StatModifier finalOverride(int finalOverrideValue) {
        return new FinalOverrideModifier(finalOverrideValue);
    }
}
