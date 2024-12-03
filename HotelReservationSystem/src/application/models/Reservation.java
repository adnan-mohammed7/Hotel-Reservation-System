package application.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
	LocalDate bookingDate;
	LocalDate checkInDate;
	LocalDate checkOutDate;
	Bill bill;
	double discount;
	String status;
	
	public Reservation() {
		roomDetails = new ArrayList<>();
		bill = new Bill();
		status = "Reserved";
	}
	
//	public Reservation(Guest guest, RoomDetails[] roomDetails, double dis, LocalDate date, LocalDate in, LocalDate out) {
//		this.guest = guest;
//		this.roomDetails = new ArrayList<>(Arrays.asList(roomDetails));
//		this.discount = dis;
//		this.bookingDate = date;
//		this.checkInDate = in;
//		this.checkOutDate = out;
//		setDays();
//		bill = new Bill(calculateRate(), numOfDays, discount);
//		status = "Reserved";
//	}

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
		setNumOfRooms();
		setTypeOfRooms();
		generateBill();
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

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
		setDays();
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
		setDays();
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
	
	void setDays() {
		if(checkInDate != null && checkOutDate != null) {
			long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
			numOfDays = (int) days;
			generateBill();
		}
	}
	
	double calculateRate() {
		double total = 0.0;
		if(numOfRooms > 0) {
			for (RoomDetails e : roomDetails) {
				total += e.getRoom().getRate();
			}
		}
		return total;
	}
	
	void setNumOfRooms() {
		numOfRooms = roomDetails.size();
	}
	
	void setTypeOfRooms() {
		for(int i = 0; i < roomDetails.size(); i++) {
			typesOfRooms += roomDetails.get(i).getRoom().getRoomType();
			if(i != roomDetails.size() - 1)
				typesOfRooms += " ";
		}
	}
	
	void generateBill() {
		if(calculateRate() != 0 && numOfDays > 0) {
			bill.setRatePerNight(calculateRate());
			bill.setNumOfDays(numOfDays);
			bill.calculate();
		}
	}
}
