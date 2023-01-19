package pt.iscte.poo.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;
import java.awt.event.KeyEvent;

public class Engine implements Observer {
	
	//[[===== ENGINE  =====]]\\
	private static Engine INSTANCE = null;
	public ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	
	//[[====== USER  ======]]\\
	private Hero hero;
	private String NickName;
	private int points;
	private Hero_Map map;
	private int turns;
	private String current_level;
	
	//[[======= MAP =======]]\\
	private ArrayList<GameElement> entities = new ArrayList<GameElement>();
	private HashMap <Point2D,GameElement> grid_map = new HashMap<Point2D,GameElement>();
	
	//[[=== SINGLETON CONSTRUCTOR ===]]\\
	public static Engine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Engine(10, 10);
		return INSTANCE;
	}
	private Engine(int width, int height) {	
		gui.registerObserver(this);
		gui.setSize(width, height+1);
		gui.go();
	}
	
	//[[=== USER MANIPULATION ===]]\\
	public void setNickName(String str){NickName= str;}
	public void addPoints(int n){points = Math.max(points+n,0);}
	
	//[[=== UI AND MAP MANIPULATION ===]]\\
	private void addBackground() {
		List<ImageTile> tileList = new ArrayList<>();
		for (int x=0; x!=10; x++) {
			for (int y=0; y!=10; y++) {
				tileList.add(new Floor(new Point2D(x,y)));
			}
		}
		gui.addImages(tileList);
	}
	private void addWalls() {
		for(Point2D p : grid_map.keySet()){
			gui.addImage(grid_map.get(p));
		}
	}
	public void addImages(List<GameElement> set){
		for( GameElement e : set){
			if(e != null) {
				gui.addImage(e);
			}
		}	
	}
	public void addImage(GameElement e){gui.addImage(e);}
	public void addElement(GameElement obj){entities.add(obj);}
	public void updateDeleted() {entities.removeIf(e -> e.isToRemove());}
	public void addWall(Point2D p, Wall w){grid_map.put(p,w);gui.addImage(w);}
	public void removeWall(Point2D p){removeImage(grid_map.get(p));grid_map.remove(p);}
	public void removeImage(ImageTile t){gui.removeImage(t);}
	public void removeImages(List<GameElement> set){
		for( GameElement e : set){
			if(e != null) {
				gui.removeImage(e);
			}
		}	
	}

	//[[=== MAP GETTERS ===]]\\
	public GameElement ElementAt(int x, int y){
		GameElement res=null;
		for( GameElement e : entities){
			if(e.getPosition().getX() == x &&  e.getPosition().getY()==y){
				if(e.getInteractable() != null) {
					res = e;
				}else {
				return e;
				}
			}
		}
		return res;
	}
	public boolean isWall(Point2D p){
		return grid_map.containsKey(p);
	}

	@Override
	public void update(Observed source) {
		if(gui.wasWindowClosed()){
			Close();
			return;
		}
		int key = ((ImageMatrixGUI) source).keyPressed();
		int turns_prev = turns;
		switch(key){
			case KeyEvent.VK_W:case KeyEvent.VK_A:case KeyEvent.VK_S:case KeyEvent.VK_D: //WASD
				if(hero.input(Utils.WASD_to_Arrows(key))) {	turns++;}
				break;
			case KeyEvent.VK_1:case KeyEvent.VK_2:case KeyEvent.VK_3: //123
				if(hero.consume(key-49)) {turns++;}
				break;
			case KeyEvent.VK_P: //Pause
				EscMenu();
				break;
			case KeyEvent.VK_M: //Map
				if(map.isVisible()) {
					map.hide();
				}else { map.show();}
				
				break;
		}
		entities.removeIf(e -> e.isToRemove());
		if(turns_prev != turns) {
			hero.applyEffects();
			for (GameElement e : entities){
				e.takeTurn();
			}
			gui.setStatusMessage("Nickname: " + NickName + " - Turns:" + turns + " - Points:" + points);
			gui.update();
		}
	}
	
	//[[=== LEVEL MANIPULATION ===]]\\
	public void LoadLevel(String level, Point2D spawn, boolean toSave){
		if(toSave){LevelMaker.WriteLevel(current_level, grid_map, entities);}
		grid_map.clear();
		map.setRoom(Integer.parseInt(level.substring(4)));
		current_level=level;
		gui.clearImages();
		hero.setPosition(spawn);
		hero.getHealth().addUI();
		hero.getInventory().updateUI();
		gui.addImage(hero);
		grid_map = LevelMaker.ReadLevel(level);
		entities = LevelMaker.readObjects(level);
		addWalls();
		addBackground();
		addImages(entities);
		gui.setStatusMessage("Nickname: " + NickName + " - Turns:" + turns + " - Points:" + points);
		gui.update();
	}
	public void startLevel(String lvl) {
		map = new Hero_Map(2,3);
		map.setRoom(0);
		hero = Hero.getHero();
		LoadLevel("room0", new Point2D(2,4), false);
	}
	
	//[[====== MENUS ======]]\\
	public void GameOver(){
		String[] options = {"Try Again", "Exit"};
		switch(JOptionPane.showOptionDialog(null,"You've scored: " + points + " in " + turns + " turns\n\n", "Game Over", 0, 3,  null, options, "Try Again")){
			case 0:
				Reset();
				break;
			case 1:
				Close();
				break;
		}	
	}
	private void EscMenu(){
		String[] options = {"Continue", "Reset","Exit"};
		switch(JOptionPane.showOptionDialog(null,"What do you wish to do?", "Pause Menu", 0, 3,  null, options, "Continue")){
			case 1:
				Reset();
				break;
			case 2:
				Close();
				break;
		}	
	}
	
	//[[====== GAME OPERATIONS ======]]\\
	private void Reset() {
		turns=0;
		points=0;
		LevelMaker.DeleteProgress();
		hero = Hero.getNewHero();
		LoadLevel("room0", new Point2D(4,4),false);
	}
	private void Close(){
		gui.dispose();
		System.exit(0);
	}
	public void Win(){
		LevelMaker.DeleteProgress();
		LevelMaker.RegisterScore(NickName, points); 
		gui.setMessage("--HIGHSCORES--\n\n" + LevelMaker.GetScores() + "\nYour score: " + points);
		Close();
	}
}