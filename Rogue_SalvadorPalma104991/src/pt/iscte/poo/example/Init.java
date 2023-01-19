package pt.iscte.poo.example;
import javax.swing.JOptionPane;


import pt.iscte.poo.gui.ImageMatrixGUI;

public class Init {
	
	public static void main(String[] args) {
		LevelMaker.DeleteProgress();
		String nick = Login();
		MainMenu(nick);
	}
	
	public static String Login(){
		return ImageMatrixGUI.getInstance().askUser("Nickname");
	}
	public static void startEngine(String level_to_start, String nick){
		Engine e = Engine.getInstance();
		e.setNickName(nick);
		e.startLevel(level_to_start);
	}
	
	public static void MainMenu(String nick){
		String[] options = {"Play", "Check HighScore","Exit Game"};
		
		switch(JOptionPane.showOptionDialog(null,"What do you wish to do?", "Main Menu", 0, 3,  null, options, "Continue")){
			case 0:
				startEngine("room0", nick);
				break;
			case 1:
				String res = LevelMaker.checkScore(nick);
				if(res==null){
					ImageMatrixGUI.getInstance().setMessage("No recordings for the Player: " + nick);
				}else{
					ImageMatrixGUI.getInstance().setMessage(nick + "'s highest score: " + res);
				}
				MainMenu(nick);
				break;
			case 2:
				ImageMatrixGUI.getInstance().dispose();
				System.exit(0);;
				break;
				
		}	

		
	}
}
