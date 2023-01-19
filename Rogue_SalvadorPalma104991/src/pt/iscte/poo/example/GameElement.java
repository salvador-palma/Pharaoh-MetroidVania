package pt.iscte.poo.example;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

// ========== GAME ELEMENT ABSTRACT CLASS ========== \\

public abstract class GameElement implements ImageTile{
	
	private int layer;
	private Point2D position;
	private String name;
	private String[] ExtraInfo;
	private boolean remove;
	
	//[  Constructor  ]\\
	public GameElement(String name, Point2D position, int layer){
		this.name = name;
		this.position = position;
		this.layer = layer;
	}
	
	//[  get/set  ]\\
	public void setLayer(int l){layer = l;}
	public void setPosition(Point2D p){position = p;}
	public void setName(String n){name = n;}
	@Override public int getLayer(){return layer;}
	@Override public Point2D getPosition(){return position;}
	@Override public String getName(){return name;}
	
	//[  takeTurn  ]\\
	//--updates the object once a turn
	public void takeTurn(){};
	
	//[  get/set Info  ]\\
	//--setup and return useful info like door codes
	public String[] getInfo(){return ExtraInfo;}
	public void setInfo(String[] arr){
		ExtraInfo = new String[arr.length];
		for(int i = 0; i != arr.length; i++){
			ExtraInfo[i] = arr[i];
		}
	}
	
	//[  get Interfaces  ]\\
	//--returns the Interface of Instances (needs to be Overwritten)
	public Enemy getEnemy(){return null;}
	public Item getItem(){return null;}
	public Interactable getInteractable(){return null;}
	
	//[  Animate  ]\\
	//--will change the sprite of this GameElement and change it back after a given time
	public void Animate(String anim, int time){
		String name = getName();
		setName(name+anim);
		Utils.Wait(time);
		setName(name);
	}
	
	//[  InfoLine  ]\\
	//--equivalent to toString method, useful to save information
	public String InfoLine(){return null;}
	
	//[  tag  ]\\
	//--Identifies which Interface the GameElement Implements in a faster manner
	abstract public String tag();
	
	//[  Delete  ]\\
	//--When Delete is called, the GameElement will be removed the next turn
	public boolean isToRemove(){return remove;}
	public void Delete(){remove = true;}
	
}
