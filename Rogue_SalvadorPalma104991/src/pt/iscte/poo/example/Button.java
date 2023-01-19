package pt.iscte.poo.example;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Button extends GameElement implements Interactable {
	
	List<Point2D> points;
	Boolean Activated;

	//[[====== C O N S T R U C T O R  ======]]\\
	public Button(Point2D position, Boolean OnOff, List<Point2D> points) {
		super("Button", position, 1);
		this.points = points;
		Activated = OnOff;
		if(Activated) {setName("ButtonOff");}
	}

	//[[========= I N T E R A C T  =========]]\\
	@Override public boolean Interact(GameElement g) {
		if(!Activated){
			Activated = true;
			setName("ButtonOff");
			Engine INSTANCE = Engine.getInstance();
			List<GameElement> blocks = new ArrayList<GameElement>();
			for(Point2D p : points){
				GameElement e = INSTANCE.ElementAt(p.getX(), p.getY());
				if(e!=null && e.getEnemy()!= null){
					e.getEnemy().takeDmg(100);
				}
				if(INSTANCE.isWall(p)){

					INSTANCE.removeWall(p);
				}else{
					INSTANCE.addWall(p, new Wall(p,-9));
				}
			}
			INSTANCE.addImages(blocks);
		}
		return false;
	}

	//[[========= S E T T I N G S  =========]]\\
	@Override public boolean Collidable() {return false;}
	@Override public boolean EnemyInteractable() {return true;}

	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public String tag() {return "Interactable";}
	@Override public String InfoLine(){
		String str = "Button,"+ getPosition().getX() + "," + getPosition().getY() + "," + Activated;
		for(Point2D p : points){
			str+=","+p.getX()+","+p.getY();
		}
		return str;
	}
	@Override public Interactable getInteractable() {return this;}
}
