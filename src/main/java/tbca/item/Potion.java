package tbca.item;
import tbca.combatant.Combatant;
import tbca.engine.GameState;
import tbca.engine.action.results.SpecialSkillResults;

public class Potion implements Item{
    @Override
    public SpecialSkillResults use(Combatant user, GameState gameState, int targetIndex){
        int healAmount = 100;
        int newHp = Math.min(user.getCurrHp() + healAmount, user.getMaxHp());
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