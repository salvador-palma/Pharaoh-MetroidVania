package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Thief extends GameElement implements Enemy{
	
	//[[========= S E T T I N G S  =========]]\\
	private static final float ACCURACY = 1.0f;
	private static String NAME = "Thief";
	private static final int MAX_HEALTH= 5;
	private static final int DAMAGE = 0;
	private static final int POINTS = 60;
	
	//[[== E X T R A == V A R I A B L E S ==]]\\
	private int Health;
	private boolean Stole = false;
	private String items="";
	
	//[[====== C O N S T R U C T O R  ======]]\\
	public Thief(Point2D position,String Health, String it) {
		super(NAME, position, 2);
		this.Health = Health!=null ? Integer.parseInt(Health):MAX_HEALTH;
		
		//INVENTORY SETUP
		String item_list[] = it.split(";");
		ObjectReader r = new ObjectReader();
		for(int i = 1; i!= item_list.length ; i++) {
			items+= r.lineToObject(item_list[i]).InfoLine() + ";";
			Stole = true;
			NAME="ThiefFull";
			setName(NAME);
		}
		System.out.println("Items Start: " + items);
	}

	//[[= D I S T I N C T == M E T H O D S =]]\\
	//[  Thief  ]\\
	//--When he attacks the enemy, he steals an item from the Inventory
	//--When he already stole an item he will move in the opposite direction
	//--When he dies he will drop all his stolen items
	@Override public void takeDmg(int dmg){
		setHealth(-dmg);
		if(getHealth() <= 0) {
			((GameElement)this).Delete();
			Engine INSTANCE = Engine.getInstance();
			INSTANCE.updateDeleted();
			INSTANCE.addPoints(40);
			setLayer(1);
			setName("Blood");
			String aux[] = items.split(";");
			if(aux[0].contains(",")) {
				for(int i = 0; i != aux.length; i++) {
					ObjectReader r = new ObjectReader();
					GameElement e = r.lineToObject(aux[i]);
					e.setPosition(getPosition());
					INSTANCE.addElement(e);
					INSTANCE.addImage(e);
				}
			}
		}
	}
	@Override public void takeTurn() {
		Point2D dest;
		Point2D p = Hero.getHero().getPosition();
		if(Stole) {
			Point2D e = getPosition();		
			dest = move(e.plus(Utils.VectorDistance(p,e)));
		}else {
			dest = move(p);
		}
		if(dest.equals(p)){
			if(attack(DAMAGE, ACCURACY)) {
				Hero_Inventory i = Hero.getHero().getInventory();
				int r = i.getRandomSlot();
				if(r!=-1) {
					GameElement e = (GameElement)i.getSlot(r);
					e.Delete();
					items+=e.InfoLine()+";";
					i.removeSlot(r);
					Stole=true;
					NAME = "ThiefFull";
					setName(NAME);
				}
			}
			return;
		}
		GameElement e = Engine.getInstance().ElementAt(dest.getX(),dest.getY());
		if(e!=null && e.tag().equals("Item")) {
			e.Delete();
			items+=e.InfoLine()+";";
			Engine.getInstance().removeImage(e);
			NAME = "ThiefFull";
			setName(NAME);
		}
		else if(e!=null && e.getInteractable() != null && e.getInteractable().EnemyInteractable()) {
			e.getInteractable().Interact(this);
			if(e.getInteractable().Collidable()) {
				Flip(dest.getX(), NAME);
				return;
			}
		}
		Flip(dest.getX()-getPosition().getX(), NAME);
		setPosition(dest);
	}
	
	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public void addPoints() {Engine.getInstance().addPoints(POINTS);}
	@Override public int getHealth(){return Health;}
	@Override public void setHealth(int n){Health=Math.min(Health+n, MAX_HEALTH);}
	@Override public Enemy getEnemy(){return this;}
	@Override public String InfoLine() {return NAME +","+ getPosition().getX() +"," + getPosition().getY()+ ","+ Health+ ";" + items;}
	@Override public String tag() {return "Enemy";}
}
