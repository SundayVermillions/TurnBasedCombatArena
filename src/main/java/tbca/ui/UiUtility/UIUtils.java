package tbca.ui.UiUtility;

public class UIUtils {
    public static String healthBar(int hp, int maxHp) {
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
    public static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int leftPadding = (width - text.length()) / 2;
        return " ".repeat(leftPadding) + text;
    }
}
