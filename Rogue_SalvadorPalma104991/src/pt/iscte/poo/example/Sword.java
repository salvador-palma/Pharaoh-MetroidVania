package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameElement implements Item {

	//[[====== C O N S T R U C T O R  ======]]\\
	public Sword(Point2D position) {
		super("Sword", position, 1);
	}

	//[[= D I S T I N C T == M E T H O D S =]]\\
	@Override public void Buff() {Hero.getHero().Attack += 1;}
	@Override public void Debuff() {Hero.getHero().Attack -= 1;}
	
	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public Item getItem() {return this;}
	@Override public String InfoLine() { return getName() + "," + getPosition().getX() +"," + getPosition().getY();}
	@Override public String tag() {return "Item";}
	@Override public String getType() {return "StatBoost";}
}
