package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Hero_Inventory  {

	private GameElement[] list = new GameElement[3];
	private Hero owner;
	private Engine INSTANCE;
	
	public Hero_Inventory (Hero h, Engine ins){
		INSTANCE = ins;
		owner = h;
		addUI();
		
	}
	public void addUI() {
		List<GameElement> set = new ArrayList<GameElement>();
		for(int i = 0 ; i<5; i++) {
			set.add(new UI_Block("Slot", new Point2D(i+5,10), 0));
			set.add(new UI_Block("Index"+i, new Point2D(i+5,10), 0));
		}
		set.add(new UI_Block("Icon"+0, new Point2D(5,10), 0));
		set.add(new UI_Block("Icon"+4, new Point2D(9,10), 0));
		INSTANCE.addImages(set);
		
	}
	
	public void updateUI() {
		addUI();
		ArrayList<GameElement> set = new ArrayList<GameElement>();
		for(int i = 0 ; i<list.length; i++) {
			if(list[i] != null) {
				set.add(list[i]);
			}
		}
		INSTANCE.addImages(set);
	}
	public void add(GameElement obj){
		for( int i=0; i!= list.length; i++){
			if(list[i] == null){
				list[i] = obj;
				list[i].setPosition(new Point2D(6+i, 10));
				if(obj.getItem().getType().equals("StatBoost")){
					obj.getItem().Buff();
				}
				return;
			}
		}
	}
	
	public int hasKey(String code){
		for( int i=0; i!= list.length; i++){
			if(list[i] != null){
				if(list[i].getItem()!=null){
					if(list[i].getItem().getType().equals("Key")){
						if(list[i].getInfo()[0].equals(code)){
							return i;
						}
					}
				}
			}
		}
		System.out.println("Inventory doesnt have key: " + code );
		return -1;
		
	}
	public boolean isFull(){
		for( int i=0; i!= list.length; i++){
			if(list[i] == null){
				return false;
			}
		}
		return true;
	}
	public boolean isEmpty(){
		for( int i=0; i!= list.length; i++){
			if(list[i] != null){
				return false;
			}
		}
		return true;
	}
	

	public Item getSlot(int n){
		if(list[n] != null){
			
		return list[n].getItem();
		
		}
		return null;
	}
	
	public void removeSlot(int n){
		
		INSTANCE.removeImage(list[n]);
		if(list[n].getItem().getType().equals("StatBoost")){
			list[n].getItem().Debuff();
		}
		list[n] = null;
	}
	
	public void drop(int n){
		GameElement e = INSTANCE.ElementAt(owner.getPosition().getX(),owner.getPosition().getY());
		if(e==null || e.tag().equals("Item")) {
			list[n].setPosition(owner.getPosition());
			INSTANCE.addElement(list[n]);
			((Item)(list[n])).Debuff();
			list[n] = null;
		}
	}
	
	public int getRandomSlot() {
		if(!isEmpty()) {
			int r = (int)(Math.random()*3);
			while(getSlot(r)==null) {
				r=(int)(Math.random()*3);
			}
			return r;
		}
		return -1;
		
		
	}
}
