package pt.iscte.poo.example;

// ========== ITEM INTERFACE ========== \\

public interface Item {
	
	//[  getType  ]\\
	//--returns the Item type {Consumable, StatBoost}
	abstract public String getType();
	
	//[  Buff & Debuff  ]\\
	//--Called whenever an item is added or dropped from the Inventory
	default public void Buff() {}
	default void Debuff() {}
	
	//[  Consume  ]\\
	//--Called when a Consumable type is Activated
	default public void Consume() {}
	
	//[  Picked  ]\\
	//--Called whenever an item is Added to the inventory (Useful to add Points)
	default public void Picked() {};
	
	
}
