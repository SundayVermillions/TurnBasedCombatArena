package tbca.effect;

import tbca.combatant.Combatant;
public abstract class StatusEffect {

	protected String name;
	protected int duration;
	
	public StatusEffect(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}
	
	public abstract void applyEffect(Combatant target);
	public abstract void removeEffect(Combatant target);
	
	public void tick(Combatant target) {
		if (duration > 0) {
			duration--;
		}
		if (isExpired()) {
			removeEffect(target);
		}
	}
	
	public boolean isExpired() {
		return duration == 0;
	}
	
	public String getName() {
		return name;
	}
}
