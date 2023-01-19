package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Throne extends GameElement implements Interactable{

	//[[====== C O N S T R U C T O R  ======]]\\
	public Throne(Point2D position) {
		super("Throne", position, 10);
	}

	//[[========= I N T E R A C T  =========]]\\
	@Override public boolean Interact(GameElement g) {	
		Engine.getInstance().Win();
		return false;
	}
	
	//[[========= S E T T I N G S  =========]]\\
	@Override public boolean Collidable() {return true;}
	@Override public boolean EnemyInteractable() {return false;}

	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public String tag() {return "Interactable";}
	@Override public String InfoLine(){return "Treasure," + getPosition().getX() + "," + getPosition().getY();}
	@Override public Interactable getInteractable() {return this;}
}
