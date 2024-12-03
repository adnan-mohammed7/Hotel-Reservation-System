package application.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Reservation {
	static final AtomicInteger counter = new AtomicInteger(0);
	int reservationID;
	Guest guest;
	List<RoomDetails> roomDetails;
	int numOfRooms;
	int numOfDays;
	String typesOfRooms;
	Date bookingDate;
	Date checkInDate;
	Date checkOutDate;
	Bill bill;
	double discount;
	String status;
	
	public Reservation() {
		this.reservationID = counter.incrementAndGet();
		roomDetails = new ArrayList<>();
		bill = new Bill();
	}
	
	public Reservation(Guest guest, RoomDetails[] roomDetails, double dis, Date date, Date in, Date out) {
		this.reservationID = counter.incrementAndGet();
		this.guest = guest;
		this.roomDetails = new ArrayList<>(Arrays.asList(roomDetails));
		this.discount = dis;
		this.bookingDate = date;
		this.checkInDate = in;
		this.checkOutDate = out;
		calculateDays();
		bill = new Bill(calculateRate(), numOfDays, discount);
	}
	
	void calculateDays() { 
	}
	
	double calculateRate() {
		double total = 0.0;
		for (RoomDetails e : roomDetails) {
			total += e.getRoom().getRate();
		}
		return total;
	}
	
	void calculateNumOfRooms() {
		numOfRooms = roomDetails.size();
	}
	
	void calculateTypeOfRooms() {
		for(int i = 0; i < roomDetails.size(); i++) {
			typesOfRooms += roomDetails.get(i).getRoom().getRoomType();
			if(i != roomDetails.size() - 1)
				typesOfRooms += " ";
		}
	}

	public int getReservationID() {
		return reservationID;
	}
	
	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public List<RoomDetails> getRoomDetails() {
		return roomDetails;
	}

	public void setRoomDetails(List<RoomDetails> rooms) {
		this.roomDetails = rooms;
		calculateNumOfRooms();
		calculateRate();
		calculateTypeOfRooms();
	}

	public int getNumOfRooms() {
		return numOfRooms;
	}

	public void setNumOfRooms(int numOfRooms) {
		this.numOfRooms = numOfRooms;
	}

	public int getNumOfDays() {
		return numOfDays;
	}

	public void setNumOfDays(int numOfDays) {
		this.numOfDays = numOfDays;
	}

	public String getTypesOfRooms() {
		return typesOfRooms;
	}

	public void setTypesOfRooms(String typesOfRooms) {
		this.typesOfRooms = typesOfRooms;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
		calculateDays();
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
		calculateDays();
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
		bill.setDiscount(discount);
	}
}
