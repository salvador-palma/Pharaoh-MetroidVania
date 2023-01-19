package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Skeleton extends GameElement implements Enemy{
	
	//[[========= S E T T I N G S  =========]]\\
	private static final float ACCURACY = 1.0f;
	private static final String NAME = "Skeleton";
	private static final int MAX_HEALTH= 5;
	private static final int DAMAGE = 1;
	private static final int POINTS = 40;
	
	//[[== E X T R A == V A R I A B L E S ==]]\\
	private int Health;
	private int turn_counter=2;

	//[[====== C O N S T R U C T O R  ======]]\\
	public Skeleton(Point2D position,String Health) {
		super(NAME, position, 2);
		this.Health = Health!=null ? Integer.parseInt(Health):MAX_HEALTH;
	}

	//[[= D I S T I N C T == M E T H O D S =]]\\
	//[  Skeleton  ]\\
	//--Only moves each two turns
	@Override public void takeTurn() {
		turn_counter--;
		if(turn_counter<=0){
			Point2D p = Hero.getHero().getPosition();
			turn_counter=2;
			Point2D dest = move(p);
			if(dest.equals(p)){
				attack(DAMAGE, ACCURACY);
				return;
			}
			Flip(dest.getX()-getPosition().getX(), NAME);
			setPosition(dest);
		}
	}
	
	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public void addPoints() {Engine.getInstance().addPoints(POINTS);}
	@Override public int getHealth(){return Health;}
	@Override public void setHealth(int n){Health=Math.min(Health+n, MAX_HEALTH);}
	@Override public Enemy getEnemy(){return this;}
	@Override public String InfoLine() {return NAME +","+ getPosition().getX() +"," + getPosition().getY()+ ","+ Health;}
	@Override public String tag() {return "Enemy";}

	
}
