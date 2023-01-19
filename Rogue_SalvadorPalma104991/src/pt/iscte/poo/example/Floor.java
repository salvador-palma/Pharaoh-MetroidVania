package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Floor implements ImageTile {

	private Point2D position;
	private String sprite;
	
	
	public Floor(Point2D position) {
		this.position = position;
		int rnd = (int)(Math.random()*6);
		sprite = "Floor" + rnd;
	}

	@Override
	public String getName() {
		return sprite;
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}

}