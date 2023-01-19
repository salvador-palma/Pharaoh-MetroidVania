package pt.iscte.poo.example;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class ObjectReader {
	
	private Hero h;
	
	private String DoorToUnlock;
	public ObjectReader() {
		
		h =Hero.getHero();
		DoorToUnlock = h.doorEntered;
		h.doorEntered="default";
		
	}
	
	public String checkHealthState(String[] args){
		if(args.length>=4){
			return args[3];
		}
		return null;
	}
	public GameElement lineToObject(String str){
		String args[] = str.split("[,;]");
		switch(args[0]){
		
		//============== E N E M I E S ===============
		case "Skeleton":
			return new Skeleton(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), checkHealthState(args));
		case "Scorpion":
			return new Scorpion(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), checkHealthState(args));
		case "Thug":
			return new Thug(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), checkHealthState(args));
		case "Thief":
		case "ThiefFull":
			return new Thief(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), checkHealthState(args), str);
		case "Bat":
			return new Bat(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), checkHealthState(args));
		case "Fractured Wall":
			return new FracturedWall(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), checkHealthState(args));
			
		//============ I N T E R A C T A B L E S =================
		case "Door":
			String[] filter = new String[7];
			for(int i = 0; i!= args.length; i++){
				filter[i] = args[i];
			}
			Point2D p = new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			Point2D s = new Point2D(Integer.parseInt(args[4]), Integer.parseInt(args[5]));
			
			if(filter[6] != null && filter[6].equals(DoorToUnlock)) {
				return new Door(p, filter[3], s, null);
			}
			return new Door(p, filter[3], s, filter[6]);
		
		case "Treasure":	
			return new Throne(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
		case "Button":
			List<Point2D> points = new ArrayList<Point2D>();
			
			for(int i=4; i!= args.length; i+=2){
				points.add(new Point2D(Integer.parseInt(args[i]),Integer.parseInt(args[i+1])));
			}
			return new Button(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), args[3].equals("true") ?  true:false, points);
		case "Teleporter":
			return new Teleporter(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])),new Point2D(Integer.parseInt(args[3]), Integer.parseInt(args[4])));
		case "Boulder":	
			return new Boulder(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])));	
		case "Note":	
			return new Note(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), args[3]);	

		//========== I T E M S ===============
		case "Key":
			return new Key(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])), args[3]);
			
		case "Armor":
			return new Armor(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
			
		case "Sword":
			return new Sword(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
	
		case "HealingPotion":
			return new HealthPotion(new Point2D(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
				
		}
		return null;
	}
}	
	

