package application.models;

public abstract class Room {
	int roomID;
	String roomType;
	double rate;
	
	public Room(int id, String type, double price) {
		this.roomID = id;
		this.roomType = type;
		this.rate = price;
	}
	
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Room room = (Room) obj;
	    return roomID == room.roomID;
	}

	@Override
	public int hashCode() {
	    return Integer.hashCode(roomID);
	}
}
