package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Key  extends GameElement implements Item{

	//[[====== C O N S T R U C T O R  ======]]\\
	public Key(Point2D position, String serial) {
		super("Key", position, 1);
		String[] ExtraInfo = new String [1];
		ExtraInfo[0] = serial;
		setInfo(ExtraInfo);
	}
	
	//[[== D E F A U L T == M E T H O D S ==]]\\
	@Override public Item getItem() {return this;}
	@Override public String getType() {return "Key";}
	@Override public String InfoLine() {return "Key,"+ getPosition().getX() +"," + getPosition().getY()+","+getInfo()[0];}
	@Override public String tag() {return "Item";}



}
