package pt.iscte.poo.example;



import java.util.HashMap;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Utils {
	static HashMap<Point2D, String> map = new HashMap<Point2D, String>();
	public static void main(String[] args){
		
		System.out.println(Integer.parseInt(null));
	}
	public static String ElementAt(int x, int y){
		Point2D p = new Point2D(x,y);
		return map.get(p);
		
	}
	public static void Wait(int time){
		try {
			  Thread.sleep(time);
		} catch (InterruptedException e) {
			  
		}
	}
	
	public static Vector2D Normalize(int x, int y){
		Vector2D v;
		if(x*x > y*y){
			v = new Vector2D(x/Math.abs(x), 0);
		}else {
			v = new Vector2D(0, y/Math.abs(y));
		}
		return v;
	}
	
	public static Vector2D ReverseNormalize(int x, int y){
		Vector2D v = new Vector2D(0,0);
		if(x*x <= y*y && x != 0){
			v = new Vector2D(x/Math.abs(x), 0);
		}else if ( y != 0){
			v = new Vector2D(0, y/Math.abs(y));
		}
		return v;
	}
	
	public static int WASD_to_Arrows(int key){
		switch(key){
		case 65:
			return 37;
		case 68:	
			return 39;
		case 87:	
			return 38;			
		case 83:
			return 40;
		}
		return 0;
		
	}
	
	public static Vector2D VectorDistance(Point2D p, Point2D q) {
		return new Vector2D(q.getX() - p.getX(), q.getY() - p.getY());
		
	}
}
