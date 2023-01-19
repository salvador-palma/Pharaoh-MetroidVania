package pt.iscte.poo.example;


import pt.iscte.poo.utils.Point2D;

public class Wall extends GameElement {

	
	
	public Wall(Point2D position, int type) {
		super("Wall"+type,position,5 );
	}

	@Override
	public String tag() {
		// TODO Auto-generated method stub
		return "Wall";
	}
	
	

	

	

}