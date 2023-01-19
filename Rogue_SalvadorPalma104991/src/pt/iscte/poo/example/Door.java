package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement implements Interactable{

	private String code=null;
	private String level_to_go;
	private Point2D spawn_coords;
	private Engine INSTANCE;

	//[[====== C O N S T R U C T O R  ======]]\\
	public Door(Point2D p, String destination, Point2D spawn, String code){
		super("DoorLocked", p, 1);
		if(code!= null){setName("DoorLocked");}else{setName("DoorUnlocked");}

		INSTANCE = Engine.getInstance();
		this.code = code;
		level_to_go = destination;
		spawn_coords = spawn;
		String[] aux = new String[1]; aux[0] = code;
		setInfo(aux);
	}

	//[[========= I N T E R A C T  =========]]\\
	@Override public boolean Interact(GameElement g) {
		Hero h = (Hero)g;
		if(code!=null) {
			int t =h.getInventory().hasKey(code);
			if(t!=-1){
				h.doorEntered=code;
				h.getInventory().removeSlot(t);
				setName("DoorUnlocked");
				code=null;
				System.out.println(spawn_coords.toString());
				INSTANCE.LoadLevel(level_to_go, spawn_coords,true);
				return true;
			}
		}else {
			System.out.println(spawn_coords.toString());
			INSTANCE.LoadLevel(level_to_go, spawn_coords,true);
			return true;
		}
		return false;
	}

	//[[========= S E T T I N G S  =========]]\\
	@Override public boolean Collidable() {return true;}
	@Override public boolean EnemyInteractable() {return false;}

	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public String tag() {return "Interactable";}
	@Override public String InfoLine() {
		Point2D aux = getPosition();
		if(code ==null) {
			return "Door" +","+ aux.getX() +"," + aux.getY() +","+level_to_go+","+spawn_coords.getX()+","+spawn_coords.getY()+",";
		}return "Door" +","+ aux.getX() +"," + aux.getY() +","+level_to_go+","+spawn_coords.getX()+","+spawn_coords.getY()+","+code;
	}
	@Override public Interactable getInteractable() {return this;}
}
