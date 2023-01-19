package pt.iscte.poo.example;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Note extends GameElement implements Item {
	String speach;
	
	//[[====== C O N S T R U C T O R  ======]]\\
	public Note(Point2D position,  String speach) {
		super("Note", position, 1);
		this.speach = speach;
	}

	//[[= D I S T I N C T == M E T H O D S =]]\\
	@Override public void Consume() {ImageMatrixGUI.getInstance().setMessage(speach);}
	@Override public void Picked() {
		Engine.getInstance().addPoints(100);
	}
	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public Item getItem() {return this;}
	@Override public String tag() {return "Item";}
	@Override public String getType() {return "Consumable";}
	@Override public String InfoLine() {return getName() + "," + getPosition().getX() +"," + getPosition().getY()+","+speach;}
}
