package pt.iscte.poo.example;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends GameElement implements Enemy{

	//[[========= S E T T I N G S  =========]]\\
	private static final float ACCURACY = 0.5f;
	private static final String NAME = "Bat";
	private static final int MAX_HEALTH= 3;
	private static final int DAMAGE = 1;
	private static final int POINTS = 50;
	
	//[[== E X T R A == V A R I A B L E S ==]]\\
	private int Health;
	private int Max_Random_Tries=10;
	private int Random_Tries=Max_Random_Tries;

	//[[====== C O N S T R U C T O R  ======]]\\
	public Bat(Point2D position,String Health) {
		super(NAME, position, 2);
		this.Health = Health!=null ? Integer.parseInt(Health):MAX_HEALTH;
	}

	//[[= D I S T I N C T == M E T H O D S =]]\\
	//[  Bat  ]\\
	//--50% chance to move to Hero
	//--50% chance to move in a random direction
	//--when an attack lands it regenerates 1 Health
	@Override public void takeTurn() {
		Point2D dest;
		Point2D p = Hero.getHero().getPosition();
		if(Math.random() < 0.5f) {
			dest = move(p);
		}else {
			dest = getPosition().plus(Direction.random().asVector());
			while(!canMoveTo(dest) && Random_Tries>0) {
				dest = getPosition().plus(Direction.random().asVector());
				Random_Tries--;
			}
			if(Random_Tries<=0) {
				dest = getPosition().plus(new Vector2D(0,0));	
			}
			Random_Tries=Max_Random_Tries;
		}
		if(dest.equals(p)){
			if(attack(DAMAGE, ACCURACY)) {
				
			    Health = Math.min(MAX_HEALTH, Health+1);
			}
			return;
		}
		Flip(dest.getX()-getPosition().getX(), NAME);
		setPosition(dest);
	}
	
	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public void addPoints() {Engine.getInstance().addPoints(POINTS);}
	@Override public int getHealth(){return Health;}
	@Override public void setHealth(int n){Health=Math.min(Health+n, MAX_HEALTH);}
	@Override public Enemy getEnemy(){return this;}
	@Override public String InfoLine() {return NAME +","+ getPosition().getX() +"," + getPosition().getY()+ ","+ Health;}
	@Override public String tag() {return "Enemy";}


}
