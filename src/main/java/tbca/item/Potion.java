package tbca.item;
import tbca.combatant.Combatant;
import tbca.engine.GameState;
import tbca.engine.action.results.SpecialSkillResults;
import tbca.engine.action.results.UseItemResults;

public class Potion implements Item{
    public static final int HEAL_AMOUNT = 100;
    @Override
    public UseItemResults use(Combatant user, GameState gameState, int targetIndex){
        int oldHp = user.getCurrHp();
        int newHp = Math.min(user.getCurrHp() + HEAL_AMOUNT, user.getMaxHp());
        user.setHp(newHp);
        int actualHeal = newHp - oldHp;
        return new UseItemResults(user, ItemType.POTION, actualHeal);
    }

    @Override
    public String getName(){
        return "Potion";
    }

    public ItemType getType(){
        return ItemType.POTION;
    }
}