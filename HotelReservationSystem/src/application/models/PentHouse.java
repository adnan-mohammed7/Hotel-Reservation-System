package application.models;

public class PentHouse extends Room {
	
	public PentHouse(int id, double price) {
		super(id, "Pent House", price);
	}

	public PentHouse(int id, String type, double price) {
		super(id, type, price);
	}
	
}
