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
	String status;
	
	public Reservation() {
		roomDetails = new ArrayList<>();
		bill = new Bill();
		status = "Reserved";
	}
	
	public Reservation(int id, Guest guest, List<RoomDetails> roomDetails, Bill bill, LocalDate date, LocalDate in, LocalDate out, String status) {
		this.reservationID = id;
		this.guest = guest;
		this.bill = bill;
		this.setRoomDetails(roomDetails);
		this.bookingDate = date;
		this.setCheckInDate(in);
		this.setCheckOutDate(out);
		this.status = status;
	}
	
	public void setReservationID(int id) {
		this.reservationID = id;
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

//	public void setDiscount(double discount) {
//		this.discount = discount;
//		bill.setDiscount(discount);
//	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		typesOfRooms = "";
		for(int i = 0; i < roomDetails.size(); i++) {
			if(!typesOfRooms.contains(roomDetails.get(i).getRoom().getRoomType())) {
				typesOfRooms += roomDetails.get(i).getRoom().getRoomType();
				if(i != roomDetails.size() - 1)
					typesOfRooms += " ";
			}
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
