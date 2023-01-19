package pt.iscte.poo.example;

public class VenomEffect implements StatusEffect{
	
	int intensity;
	public VenomEffect(int intensity) {
		this.intensity = intensity;
	}
	@Override
	public void apply() {
		Hero h=Hero.getHero();
		h.takeDmg(intensity);
	}

}
