package pt.iscte.poo.example;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Hero extends GameElement {
	
	private Engine INSTANCE;
	private static Hero HERO;
	public float DodgeFactor = 0f;
	public int Attack = 1;
	
	
	private Hero_Health HealthBar = new Hero_Health(10);
	private Hero_Inventory InventoryBar ;
	public String doorEntered="default";
	
	private List<StatusEffect> status = new ArrayList<>();
	
	//[[====== SINGLETON CONSTRUCTOR ======]]\\
	public static Hero getHero() {
		if (HERO == null)
			HERO = new Hero(new Point2D(1,1));
		return HERO;
	}
	public static Hero getNewHero() {
		HERO = new Hero(new Point2D(1,1));
		return HERO;
	}
	public Hero(Point2D position) {
		super("Hero",position, 5);
		INSTANCE = Engine.getInstance();
		InventoryBar =  new Hero_Inventory(this, INSTANCE);
		HealthBar.addUI();
		InventoryBar.updateUI();
	}
	
	
	
	//[[======= HIT AND GETTING HIT =======]]\\
	public boolean hit(GameElement target){
		if(target.getEnemy() != null){
			String interactable_name = target.getName();
			target.setName(target.getName() + "Hitted");
			target.getEnemy().takeDmg(Attack);
			super.Animate("Hit", 250);
			if(target != null && target.getEnemy().getHealth()>0){target.setName(interactable_name);}
			return true;
		}
		return false;
		
	}
	public void takeDmg(int dmg) {
		if(Math.random() >DodgeFactor){
			HealthBar.remove(dmg);
		}else {
			System.out.println("Dodged!");
		}
	}

	//[[=========== INPUT CHECK ===========]]\\
	public boolean input(int dir) {
		Vector2D vec = Direction.directionFor(dir).asVector();
		Point2D p = new Point2D(getPosition().getX() + vec.getX(),getPosition().getY() + vec.getY());
		if(!INSTANCE.isWall(p)){
			GameElement destin = INSTANCE.ElementAt(p.getX(), p.getY());
			String str="";
			if(destin== null){
				str= "Empty";
			}
			else{
				str = destin.tag();
			}
			Flip(dir);
			switch(str){
				case "Enemy":
					return hit(destin);
				case "Item":
					if(!InventoryBar.isFull()){
						InventoryBar.add(destin);
						destin.getItem().Picked();
						move(dir);
						return true;
					}
					return false;
				case "Interactable":
					Interactable i =  destin.getInteractable();
					if(!i.Collidable()){
						move(dir);
					}
					return (i.Interact(this));
				case "Empty":
					move(dir);
					return true;
				default: return false;
			}
		}
		return false;
	}
	
	//[[============= ACTIONS =============]]\\
	public void move(int dir){
		setPosition(getPosition().plus(Direction.directionFor(dir).asVector()));
	}
	public boolean consume(int n){
		if(InventoryBar.getSlot(n)!= null){
			if(InventoryBar.getSlot(n).getType().equals("Consumable")){
				InventoryBar.getSlot(n).Consume();
				InventoryBar.removeSlot(n);
				return true;
			}else{
				InventoryBar.drop(n);
				return true;
			}
		}
		return false;
	}
	
	//[[========== STATUS EFFECTS =========]]\\
	public void applyEffects() {for(StatusEffect s : status) {s.apply();}}
	public void addEffect(StatusEffect s) {status.add(s);}
	public void clearEffect() {status.clear();}
	
	//[[======= INVENTORY AND HEALTH ======]]\\
	public Hero_Inventory getInventory() {return InventoryBar;}
	public Hero_Health getHealth() {return HealthBar;}
	
	public void Flip(int x){if(x==39){setName("Hero");}else if(x==37){setName("HeroFlip");}}
	@Override public String tag() {return "Hero";}
}
