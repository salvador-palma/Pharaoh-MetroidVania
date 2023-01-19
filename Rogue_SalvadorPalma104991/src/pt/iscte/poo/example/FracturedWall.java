package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;


public class FracturedWall extends GameElement implements Enemy {
	//[[========= S E T T I N G S  =========]]\\
	private static final String NAME = "FracturedWall";
	private static final int MAX_HEALTH= 5;
	private static final int POINTS = 20;

	//[[== E X T R A == V A R I A B L E S ==]]\\
	private int Health;

	//[[====== C O N S T R U C T O R  ======]]\\
	public FracturedWall(Point2D position,String Health) {
		super(NAME, position, 2);
		this.Health = Health!=null ? Integer.parseInt(Health):MAX_HEALTH;
	}
	
	//[[= D I S T I N C T == M E T H O D S =]]\\
	@Override public void takeDmg(int dmg){
		setHealth(-dmg);
		if(getHealth() <= 0) {
			((GameElement)this).Delete();
			Engine.getInstance().addPoints(40);
			setLayer(-1);
			
		}
	}

	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public void addPoints() {Engine.getInstance().addPoints(POINTS);}
	@Override public int getHealth(){return Health;}
	@Override public void setHealth(int n){Health=Math.min(Health+n, MAX_HEALTH);}
	@Override public Enemy getEnemy(){return this;}
	@Override public String InfoLine() {return "Fractured Wall" +","+ getPosition().getX() +"," + getPosition().getY()+ ","+ Health;}
	@Override public String tag() {return "Enemy";}
}
