package application.models;

public class Single extends Room{

	public Single(int id, double price) {
		super(id, "Single", price);
	}
	
	public Single(int id, String type,double price) {
		super(id, type, price);
	}
}
