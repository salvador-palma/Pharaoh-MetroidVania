package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Teleporter extends GameElement implements Interactable{

	Point2D destination;

	//[[====== C O N S T R U C T O R  ======]]\\
	public Teleporter(Point2D position,Point2D destination) {
		super("Teleporter", position, 1);
		this.destination = destination;
	}

	//[[========= I N T E R A C T  =========]]\\
	@Override public boolean Interact(GameElement g) {	
		GameElement e = Engine.getInstance().ElementAt(destination.getX(), destination.getY());
		if(e!=null && e.getEnemy() == null){
			g.setPosition(destination);
			return true;
		}
		return false;
	}

	//[[========= S E T T I N G S  =========]]\\
	@Override public boolean Collidable() {
		GameElement e = Engine.getInstance().ElementAt(destination.getX(), destination.getY());
		return !(e!=null && e.getEnemy() == null);
	}
	@Override public boolean EnemyInteractable() {return true;}

	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public String tag() {return "Interactable";}
	@Override public String InfoLine(){return "Teleporter," + getPosition().getX() + "," + getPosition().getY()+","+ destination.getX() + "," + destination.getY();}
	@Override public Interactable getInteractable() {return this;}
}
