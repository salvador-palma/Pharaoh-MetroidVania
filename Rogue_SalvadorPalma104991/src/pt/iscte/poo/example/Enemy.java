package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

//========== ENEMY INTERFACE ========== \\

public interface Enemy{
	
	//[  getPosition & setName & setLayer  ]\\
	//--Will automatically reference the super type methods
	public abstract Point2D getPosition();
	public abstract void setName(String string);
	public abstract void setLayer(int i);
	
	//[  move & canMoveTo  ]\\
	//--Will return a Point in the map adjacent to the current position that is approved by canMoveTo()
	//--The function will prioritize moving according to if Δx or Δy is greater
	//--If Δx = Δy then it will prioritize vertical movement
	//--If Δx = 0 or Δy = 0 and the next logical Point to move is blocked then Point returned will be the same
	default Point2D move(Point2D p) {
		int x_diff = p.getX() - getPosition().getX();
		int y_diff = p.getY() - getPosition().getY();
		Point2D dest = getPosition().plus(Utils.Normalize(x_diff, y_diff));
		if(dest.equals(Hero.getHero().getPosition())){
			return dest;
		}
		if(!canMoveTo(dest)){
			dest = getPosition().plus(Utils.ReverseNormalize(x_diff, y_diff));
			if(!canMoveTo(dest)){
				dest = getPosition().plus(new Vector2D(0,0));	
			}
		}
		return dest;
	}
	default boolean canMoveTo(Point2D dest){
		Engine eng = Engine.getInstance();
		GameElement e = eng.ElementAt(dest.getX(), dest.getY());
		return !(eng.isWall(dest) || (e!=null && e.getEnemy()!=null)|| (e!= null &&  e.getInteractable() !=null && !e.getInteractable().EnemyInteractable()));
	}
	
	//[  takeDmg  ]\\
	//--Default function for an enemy to take Damage
	default public void takeDmg(int dmg){
		setHealth(-dmg);
		if(getHealth() <= 0) {
			((GameElement)this).Delete();
			Engine.getInstance().addPoints(40);
			setLayer(1);
			setName("Blood");
		}
	}
	
	//[  getHealth & setHealth  ]\\
	//--functions to manipulate the health value
	abstract public int getHealth();
	abstract public void setHealth(int n);
	
	//[  attack  ]\\
	//--returns true if the attack was successful  
	//--calls an Animate function to enhance visual effects
	default boolean attack(int dmg, float accuracy) {
		Hero target = Hero.getHero();
		if(Math.random() < accuracy){
			target.takeDmg(dmg);
			String str = target.getName();
			target.setName(str+ "Hitted");
			((GameElement)this).Animate("Hit", 250);
			target.setName(str);
			return true;
		}
		return false;
		
	}
	
	//[  Flip  ]\\
	//--Called when the object moves in a horizontal direction so the sprite can update the facing direction
	default void Flip(int x, String name) {
		if(x>0){setName(name);}else if(x<0){setName(name +"Flip");}
	}
	
	//[  addPoints  ]\\
	//--Called when the Enemy dies so the constant POINTS can be added;
	abstract void addPoints();


	
	
}