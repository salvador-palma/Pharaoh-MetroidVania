package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class HealthPotion extends GameElement implements Item {
	private int HealthRegen=5;

	//[[====== C O N S T R U C T O R  ======]]\\
	public HealthPotion(Point2D position) {
		super("HealingPotion", position, 1);	
	}

	//[[= D I S T I N C T == M E T H O D S =]]\\
	@Override public void Consume() {
		Hero.getHero().getHealth().add(HealthRegen);
		Hero.getHero().clearEffect();
	}
	@Override public void Picked() {
		Engine.getInstance().addPoints(30);
	}

	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public String InfoLine() {return getName() + "," + getPosition().getX() +"," + getPosition().getY();}
	@Override public String getType() {return "Consumable" ;}
	@Override public Item getItem() {return this;}
	@Override public String tag() {return "Item";}
	

	
}
