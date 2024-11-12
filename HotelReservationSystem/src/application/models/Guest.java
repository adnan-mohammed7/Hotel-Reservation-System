package application.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Guest {
	static final AtomicInteger counter = new AtomicInteger(0);
	int guestID;
	String title;
	String firstName;
	String lastName;
	String address;
	Long phone;
	String email;
	
	public Guest(String title, String fName, String lName, String address, Long phone, String email){
		this.title = title;
		this.firstName = fName;
		this.lastName = lName;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.guestID = counter.incrementAndGet();
	}
	
	public int getGuestID() {
		return guestID;
	}
	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}