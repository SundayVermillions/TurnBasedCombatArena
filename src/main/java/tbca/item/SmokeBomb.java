package tbca.item;

import tbca.combatant.Combatant;
import tbca.effect.SmokeBombInvulnerability;

public class SmokeBomb implements Item{
	public void use(Combatant user) {
		System.out.println(user.getName() + " threw a Smoke Bomb!");
		user.addStatusEffect(new SmokeBombInvulnerability());
	}
	
	public String getName() {
		return "Smoke Bomb";
	}

}
