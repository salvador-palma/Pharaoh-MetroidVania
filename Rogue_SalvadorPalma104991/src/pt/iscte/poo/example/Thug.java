package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Thug extends GameElement implements Enemy{

	//[[========= S E T T I N G S  =========]]\\
	private static final float ACCURACY = 0.3f;
	private static final String NAME = "Thug";
	private static final int MAX_HEALTH= 10;
	private static final int DAMAGE = 3;
	private static final int POINTS = 100;
	
	//[[== E X T R A == V A R I A B L E S ==]]\\
	private int Health;

	//[[====== C O N S T R U C T O R  ======]]\\
	public Thug(Point2D position,String Health) {
		super(NAME, position, 2);
		this.Health = Health!=null ? Integer.parseInt(Health):MAX_HEALTH;
	}
	
	//[[= D I S T I N C T == M E T H O D S =]]\\
	//[  Thug  ]\\
	//--When he steps onto an Interactable that is possible for enemies to interact with he will activate it
	@Override public void takeTurn() {
		Point2D p = Hero.getHero().getPosition();
		Point2D dest = move(p);
		if(dest.equals(p)){
			attack(DAMAGE, ACCURACY);
			return;
		}
		GameElement e = Engine.getInstance().ElementAt(dest.getX(),dest.getY());
		if(e!=null && e.getInteractable() != null && e.getInteractable().EnemyInteractable()) {
			e.getInteractable().Interact(this);
			if(e.getInteractable().Collidable()) {
				Flip(dest.getX(), NAME);
				return;
			}
		}
		Flip(dest.getX(), NAME);
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
