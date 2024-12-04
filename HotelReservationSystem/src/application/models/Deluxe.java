package application.models;

public class Deluxe extends Room {
	
	public Deluxe(int id, double price) {
		super(id, "Deluxe Room", price);
	}

	public Deluxe(int id, String type, double price) {
		super(id, type, price);
	}
	
}
