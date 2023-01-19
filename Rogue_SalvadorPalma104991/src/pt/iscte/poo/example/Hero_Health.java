package pt.iscte.poo.example;
import java.util.ArrayList;
import java.util.List;
import pt.iscte.poo.utils.Point2D;



public class Hero_Health{
	
	private List<GameElement> HealthBlocks = new ArrayList<GameElement>();
	private int Health;
	private int MaxHealth;
	private Engine INSTANCE;
	public Hero_Health(int Health) {
		this.Health = Health;
		MaxHealth = Health;
		INSTANCE = Engine.getInstance();
	}

	
	public void updateHealth(){
		int n =Health;
		for(GameElement b : HealthBlocks){
			
				if(n >= 2) {
					n-=2;
					b.setName("Green");
				}else if(n>=1) {
					b.setName("RedGreen");
					n-=1;
				}else if(n<=0) {
					b.setName("Red");
				}
			
		}
	}
	
	public void addUI() {
		HealthBlocks = new ArrayList<GameElement>();
		for(int i = 0 ; i<5; i++) {
			HealthBlocks.add(new UI_Block("Green", new Point2D(i,10), 1));
		}
		List<GameElement> set = new ArrayList<GameElement>();
		
		for(int i = 1 ; i<4; i++) {
			set.add(new UI_Block("UI_Life2", new Point2D(i,10), 2));
		}
		set.add(new UI_Block("UI_Life0", new Point2D(0,10), 2));
		set.add(new UI_Block("UI_Life1", new Point2D(4,10), 2));
		INSTANCE.addImages(HealthBlocks);
		INSTANCE.addImages(set);
		updateHealth();
		
	}
	
	public void remove(int n){
		Health -= n;
		if(Health <= 0) {
			INSTANCE.GameOver();
		}
		updateHealth();
	}
	
	public void add(int n){
		Health = Math.min(MaxHealth, Health + n);
		updateHealth();
	}
	
}
