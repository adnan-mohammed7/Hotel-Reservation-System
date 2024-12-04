package application.models;

public class Double extends Room {
	
	public Double(int id, double price) {
		super(id, "Double Room", price);
	}

	public Double(int id, String type, double price) {
		super(id, type, price);
	}
	
}
