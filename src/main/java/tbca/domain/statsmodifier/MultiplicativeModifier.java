package tbca.domain.statsmodifier;

// multiplies stat by multiplicativeFactor
// applied after all additive modifiers have been applied
// but before any final overrides
public final class MultiplicativeModifier extends StatModifier {
    private final double multiplicativeFactor;

    public MultiplicativeModifier(double multiplicativeFactor) {
        this.multiplicativeFactor = multiplicativeFactor;
    }

    @Override
    public Priority getPriority() {
        return Priority.MULTIPLICATIVE;
    }

    @Override
    public int apply(int currentValue) {
        return (int) (currentValue * multiplicativeFactor);
    }
}

