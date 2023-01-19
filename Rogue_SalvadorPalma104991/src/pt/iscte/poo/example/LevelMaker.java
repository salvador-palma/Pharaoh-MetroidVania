package pt.iscte.poo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.utils.Point2D;

public class LevelMaker {
	
	public static void WriteLevel(String lvl , HashMap<Point2D,GameElement> map, ArrayList<GameElement> entities) {
		File f = new File("rooms//CostumRooms//"+lvl+"Saved.txt");
//		File f = new File("rooms//"+lvl+"Saved.txt");
		if(f.exists()) {
			
			f.delete();
		}
		
		try{
			PrintWriter w = new PrintWriter(new File("rooms//CostumRooms//"+lvl+"Saved.txt"));
//			PrintWriter w = new PrintWriter(new File("rooms//"+lvl+"Saved.txt"));
			for(int i =0; i!= 10; i++) {
				for(int j =0; j!= 10; j++) {
					if(Engine.getInstance().isWall(new Point2D(j,i))) {
						w.print("#");
						
					}else {
						w.print(" ");
					}
					
				}
				w.println("");
			}
			
			w.println(" ");
			
			for(GameElement e : entities) {
				if(e.getPosition().getX() < 10 && e.getPosition().getY() < 10) {
					
						w.println(e.InfoLine());
					
				
				}
				
			}
			w.close();
			System.out.println("Wrote file " + lvl + "Saved.txt");
		}catch(FileNotFoundException e){
			System.err.println("couldnt read file " + lvl);
		}	
	}
	public static HashMap<Point2D,GameElement> ReadLevel(String str){
		HashMap<Point2D,GameElement> map = new HashMap<Point2D,GameElement>();
		Scanner s = pickScanner(str);
		if(s!=null){
			for( int i = 0; i!= 10; i++){
				char[] line = s.nextLine().toCharArray();
				for( int j = 0; j!= 10; j++){
					if(line[j] == '#'){ 
						Point2D p = new Point2D(j,i);
						if(i==0){
							map.put(p, new Wall(p,-2));
						}
						else if(i==9){
							map.put(p, new Wall(p,-4));
						}
						else if(j==9){
							map.put(p, new Wall(p,-3));
						}
						else if(j==0){
							map.put(p, new Wall(p,-1));
						}
						else{
							map.put(p, new Wall(p,-9));
						}		
					}
				}
			
			}
		}
		map.replace(new Point2D(0,0), new Wall(new Point2D(0,0),-5));
		map.replace(new Point2D(0,9), new Wall(new Point2D(0,9),-7));
		map.replace(new Point2D(9,0), new Wall(new Point2D(9,0),-8));
		map.replace(new Point2D(9,9), new Wall(new Point2D(9,9),-6));	
		s.close();
		return map;
	}
	
	private static Scanner pickScanner(String str) {
		
		try {
			File f = new File("rooms//CostumRooms//"+str+"Saved.txt");
//			File f = new File("rooms//"+str+"Saved.txt");
			if(f.exists()) {
				System.out.println("Reading " + str +" from Saved");
				return new Scanner (f);
			
			}
			
			return new Scanner(new File("rooms//CostumRooms//"+str+".txt"));
		} catch (FileNotFoundException e) {
			
		}
		return null;
	}
	public static ArrayList<GameElement> readObjects(String str){
		ArrayList<GameElement> entities = new ArrayList<GameElement>();
		ObjectReader reader = new ObjectReader();
		
			Scanner s = pickScanner(str);
			if(s != null) {
				while (s.hasNextLine()) {
					   String line = s.nextLine();
					   if(line.contains(",")){	 
						   entities.add(reader.lineToObject(line));
					   }
					}
			}
			s.close();
		return entities;
	}
	
	public static void DeleteProgress() {
		File dir = new File("rooms//CostumRooms");
		//File dir = new File("rooms");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
		    for (File f : directoryListing) {
		      if(f.getName().contains("Saved")){
		    	  System.out.println("LevelMaker: Deleting File " + f.getName());
		    	  f.delete();
		      }
		    }
		} 
	}

	public static String  GetScores(){
		
		Scanner sc;
		String res = "";
		try {
			sc = new Scanner(new File("HighScores.txt"));
		
			
		for(int i = 0; i!= 5 && sc.hasNextLine(); i++){
			res+= sc.nextLine()+"\n";
		}
		sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static void RegisterScore(String nick, int n){
		List<Player> players = new ArrayList<>();
		Scanner sc;
		File f = new File("HighScores.txt");
		boolean existsPlayer = false;
		try {
			sc = new Scanner(f);
		while(sc.hasNextLine()){
			String[] str = sc.nextLine().split("-");
			if(str.length == 2){
				if(str[0].equals(nick)){
					existsPlayer = true;
					if(n > Integer.parseInt(str[1])){
						players.add(new Player(str[0], n));
					}else{
						players.add(new Player(str[0], Integer.parseInt(str[1])));
					}
				}else { //Sem ser MiniBird 
					players.add(new Player(str[0], Integer.parseInt(str[1])));
				}
			}
		}
		sc.close();
		if(!existsPlayer){
			players.add(new Player(nick, n));
		}
		players.sort((p1,p2)->p2.getPoints()-p1.getPoints());
		f.delete();
		PrintWriter pw = new PrintWriter(new File("HighScores.txt"));
		for(Player p : players){
			pw.println(p.toString());
		}
		pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String checkScore(String nick) {
		try {
			Scanner sc = new Scanner(new File("HighScores.txt"));
			while(sc.hasNextLine()){
				String[] args = sc.nextLine().split("-");
				if(args.length == 2){
					if(args[0].equals(nick)){
						return args[1];
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static class Player{
		private String name; private int points;
		public Player(String name, int points){
			this.name=name;
			this.points=points;
		}
		@Override
		public String toString(){
			return this.name+"-"+this.points;
		}
		public int getPoints(){return points;}
	}

	
}

