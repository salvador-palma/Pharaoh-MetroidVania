package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Boulder extends GameElement implements Interactable{

	private Point2D defaultSpawn;

	//[[====== C O N S T R U C T O R  ======]]\\
	public Boulder(Point2D position) {
		super("Boulder", position, 4);
		defaultSpawn = position;
	}

	//[[========= I N T E R A C T  =========]]\\
	@Override public boolean Interact(GameElement g) {
		Vector2D v = Vector2D.movementVector(g.getPosition(), getPosition());
		Point2D p = getPosition().plus(v);
		if(canMove(p)){
			setPosition(p);
			return true;
		}
		return false;	
	}
	public boolean canMove(Point2D p){
		Engine e = Engine.getInstance();
		GameElement ge = e.ElementAt(p.getX(), p.getY());
		if(ge!=null && ge.getInteractable()!=null && ge.getInteractable().EnemyInteractable()){
			ge.getInteractable().Interact(Hero.getHero());
			return true;
		}
		return !e.isWall(p) && ge==null;
	}

	//[[========= S E T T I N G S  =========]]\\
	@Override public boolean Collidable() {return true;}
	@Override public boolean EnemyInteractable() {return false;}

	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public String tag() {return "Interactable";}
	@Override public String InfoLine(){return "Boulder," + defaultSpawn.getX() + "," + defaultSpawn.getY();}
	@Override public Interactable getInteractable() {return this;}
}
