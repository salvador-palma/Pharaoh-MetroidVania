package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Hero_Map {
	
	private int[] RoomToGrid = new int[20];
	private int room;
	private int posX;
	private int posY;
	private Engine INSTANCE;
	private boolean isOn=false;;
	private List<GameElement> map = new ArrayList<GameElement>();
	
	public Hero_Map(int x, int y) {
		
		posX = x;
		posY = y;
		INSTANCE = Engine.getInstance();
		for(int i=0; i!= 4; i++) {
			for(int j=0; j!= 6; j++) {
				
				map.add( new UI_Block("map" + ((i*6)+j), new Point2D(posX + j, posY+i), 10) );
				
			}
		}
		map.add(new UI_Block("frame0", new Point2D(posX , posY), 11));
		map.add(new UI_Block("frame1", new Point2D(posX+1 , posY), 11));
		initConversion();
		
		
	}
	public void setRoom(int n) {	
	 room=n;
	 update();
	}
	public void update() {
		int r = RoomToGrid[room];
		map.get(24).setPosition(new Point2D(posX + r%5, posY + r/5));
		map.get(25).setPosition(new Point2D(posX + (r%5)+1,posY + r/5));
	
	}
	public void show() {
		isOn=true;
		INSTANCE.addImages(map);
		
	}
	
	public void hide() {
		isOn=false;
		INSTANCE.removeImages(map);
	}
	public boolean isVisible() {
		
		return isOn;
	}
	
	public void initConversion() {
		RoomToGrid[0]= 16;
		RoomToGrid[1]= 17;
		RoomToGrid[2]= 18;
		RoomToGrid[3]= 19;
		RoomToGrid[4]= 12;
		RoomToGrid[5]= 13;
		RoomToGrid[6]= 8;
		RoomToGrid[7]= 7;
		RoomToGrid[8]= 14;
		RoomToGrid[9]= 11;
		RoomToGrid[10]= 10;
		RoomToGrid[11]= 5;
		RoomToGrid[12]= 0;
		RoomToGrid[13]= 6;
		RoomToGrid[14]= 1;
		RoomToGrid[15]= 2;
		RoomToGrid[16]= 3;
		RoomToGrid[17]= 4;
		RoomToGrid[18]= 9;
		RoomToGrid[19]= 15;
	}

	
	
}
