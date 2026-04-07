package tbca.item;
import tbca.combatant.Combatant;
import tbca.engine.GameState;
import tbca.engine.action.results.SpecialSkillResults;

public class Potion implements Item{
    public static final int HEAL_AMOUNT = 100;
    @Override
    public SpecialSkillResults use(Combatant user, GameState gameState, int targetIndex){

        int newHp = Math.min(user.getCurrHp() + HEAL_AMOUNT, user.getMaxHp());
        user.setHp(newHp);
        return null;
    }

    @Override
    public String getName(){
        return "Potion";
    }

    public ItemType getType(){
        return ItemType.POTION;
    }
}