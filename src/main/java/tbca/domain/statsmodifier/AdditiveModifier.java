package tbca.domain.statsmodifier;

// adds additiveValue to to current stat
// is always applied first, before any multiplicative modifiers
public final class AdditiveModifier extends StatModifier {
    private final int additiveValue;

    public AdditiveModifier(int additiveValue) {
        this.additiveValue = additiveValue;
    }

    @Override
    public Priority getPriority() {
        return Priority.ADDITIVE;
    }

    @Override
    public int apply(int currentValue) {
        return currentValue + additiveValue;
    }
}
