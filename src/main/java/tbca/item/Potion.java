package tbca.item;
import tbca.combatant.Combatant;

public class Potion implements Item{
    @Override
    public void use(Combatant user, Combatant target){
        int healAmount = 100;
        int newHp = Math.min(user.getCurrHp() + healAmount, user.getMaxHp());
        user.setHp(newHp);
    }

    @Override
    public String getName(){
        return "Potion";
    }

    public ItemType getType(){
        return ItemType.POTION;
    }
}