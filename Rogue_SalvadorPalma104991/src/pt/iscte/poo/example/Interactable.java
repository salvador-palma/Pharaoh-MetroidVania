package pt.iscte.poo.example;

//========== INTERACTABLE INTERFACE ========== \\

public interface Interactable {

	//[  Interact  ]\\
	//--Void called when an Enemy that can Interact or a Hero goes to the Interactable
	abstract public boolean Interact(GameElement h);
	
	//[  Collidable  ]\\
	//--Checks if the Interactable can be stepped onto
	abstract public boolean Collidable();
	
	//[  EnemyInteractable  ]\\
	//--Checks if the Interactable can be activated through enemies
	abstract public boolean EnemyInteractable();
}
