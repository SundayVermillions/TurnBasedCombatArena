package tbca.domain.combatant.statsmodifier;

// has the final and highest priority. completely overrides
// all other stat modifiers with its own absolute value
public final class FinalOverrideModifier extends StatModifier {
    private final int finalOverrideValue;

    public FinalOverrideModifier(int finalOverrideValue) {
        this.finalOverrideValue = finalOverrideValue;
    }

    @Override
    public Priority getPriority() {
        return Priority.FINAL_OVERRIDE;
    }

    @Override
    public int apply(int currentValue) {
        return finalOverrideValue;
    }
}

