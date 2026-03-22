package tbca.item;
import tbca.combatant.Combatant;
public class PowerStone implements Item {
	public void use(Combatant user) {
		System.out.println(user.getName() + " used a Power Stone!");
		user.executeSpecialSkillFree();
	}
	
	public String getName() {
		return "Power Stone";
	}

}
