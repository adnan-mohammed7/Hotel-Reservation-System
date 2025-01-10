package application.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.models.AdminUsers;
import application.models.Bill;
import application.models.Deluxe;
import application.models.Double;
import application.models.Guest;
import application.models.PentHouse;
import application.models.Reservation;
import application.models.Room;
import application.models.RoomDetails;
import application.models.Single;
import javafx.collections.ObservableList;

public class Database {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/project?useSSL=false";
	private static final String DB_USERNAME = "Adnan";
	private static final String DB_PASSWORD = "Seneca@123";
	
	private static final String ADMINUSERS_SELECT_ALL_QRY = "SELECT * FROM adminusers";
	
	private static final String ROOMS_SELECT_ALL_QRY = "SELECT * FROM rooms";
	private static final String ROOMS_SELECT_BY_ID_QRY = "SELECT * FROM rooms WHERE roomID = ?";
	
	private static final String GUESTS_INSERT_QRY = "INSERT INTO guests (title, firstName, lastName, address, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GUESTS_GET_BY_ID_QRY = "SELECT * FROM guests WHERE guestID = ?";
	private static final String GUESTS_GET_BY_NAME_OR_PHONE_QRY = "SELECT * FROM guests WHERE firstName LIKE ? OR lastName LIKE ? OR phone LIKE ?";
	private static final String GUESTS_GET_BY_PHONE_QRY = "SELECT * FROM guests WHERE phone = ?";
	private static final String GUESTS_GET_BY_EMAIL_QRY = "SELECT * FROM guests WHERE email = ?";
	
	private static final String BILL_INSERT_QRY = "INSERT INTO bills (ratePerNight, totalAmount, discount, amountAfterDiscount, amountAfterTax, numOfDays) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String BILL_GET_BY_ID_QRY = "SELECT * FROM bills WHERE billID = ?";
	private static final String BILL_UPDATE_QRY = "UPDATE bills SET ratePerNight = ?, totalAmount = ?, discount = ?, " +
             "amountAfterDiscount = ?, amountAfterTax = ?, numOfDays = ? WHERE billID = ?";
	
	private static final String RESERVATION_DETAILS_INSERT_QRY = "INSERT INTO reservationdetails (roomID, reservationID, numOfGuest) VALUES (?, ?, ?)";
	private static final String RESERVATION_DETAILS_GET_BY_ID_QRY = "SELECT * FROM reservationdetails WHERE reservationID = ?";
	
	private static final String RESERVATIONS_INSERT_QRY = "INSERT INTO reservations (guestID, numOfRooms, numOfDays, typesOfRooms, bookingDate, checkInDate, checkOutDate, status, billID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String RESERVATIONS_GET_ALL_QRY = "SELECT * FROM reservations";
	private static final String RESERVATIONS_GET_BY_DATE_QRY = "SELECT * FROM reservations WHERE" +
            "(checkInDate <= ? AND checkOutDate > ?) OR " +
            "(checkInDate < ? AND checkOutDate >= ?) OR " +
            "(checkInDate >= ? AND checkOutDate <= ?)";
	
	private static final String RESERVATIONS_UPDATE_STATUS_QRY = "UPDATE reservations SET status = ? WHERE reservationID = ?";
	
	public List<AdminUsers> getAdmins() {
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
			PreparedStatement ps = conn.prepareStatement(ADMINUSERS_SELECT_ALL_QRY);
			ResultSet res =  ps.executeQuery();
			List<AdminUsers> list = new ArrayList<AdminUsers>();
			while(res.next()) {
				list.add(new AdminUsers(res.getInt("adminID"),
						res.getString("userName"),
						res.getString("password")));
			}
			return list;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<Room> getAllRooms(){
		List<Room> allRooms = null;
		
		allRooms = new ArrayList<Room>();
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
			PreparedStatement ps = conn.prepareStatement(ROOMS_SELECT_ALL_QRY);
			ResultSet res =  ps.executeQuery();
			Room room = null;
			while(res.next()) {
				if(res.getString("roomType") != null) {
					if(res.getString("roomType").equals("Single Room")) {
						room = new Single(res.getInt("roomID"),
								res.getDouble("rate"));
					}else if(res.getString("roomType").equals("Double Room")) {
						room = new Double(res.getInt("roomID"),
								res.getDouble("rate"));
					}else if(res.getString("roomType").equals("Deluxe Room")) {
						room = new Deluxe(res.getInt("roomID"),
								res.getDouble("rate"));
					}else if(res.getString("roomType").equals("Pent House")) {
						room = new PentHouse(res.getInt("roomID"),
								res.getDouble("rate"));
					}
					allRooms.add(room);
				}
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return allRooms;
	}
	
	public Room getRoomByID(int id) {
		Room room = null;
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
			PreparedStatement ps = conn.prepareStatement(ROOMS_SELECT_BY_ID_QRY);
			ps.setInt(1, id);
			
			ResultSet res =  ps.executeQuery();
			while(res.next()) {
				if(res.getString("roomType") != null) {
					if(res.getString("roomType").equals("Single Room")) {
						room = new Single(res.getInt("roomID"),
								res.getDouble("rate"));
					}else if(res.getString("roomType").equals("Double Room")) {
						room = new Double(res.getInt("roomID"),
								res.getDouble("rate"));
					}else if(res.getString("roomType").equals("Deluxe Room")) {
						room = new Deluxe(res.getInt("roomID"),
								res.getDouble("rate"));
					}else if(res.getString("roomType").equals("Pent House")) {
						room = new PentHouse(res.getInt("roomID"),
								res.getDouble("rate"));
					}
				}
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return room;
	}
	
	public void addGuest(Reservation reservation) {
		Guest guest = reservation.getGuest();
		if(guest.getGuestID() == 0) {
			try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
					PreparedStatement ps = conn.prepareStatement(GUESTS_INSERT_QRY, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, guest.getTitle());
				ps.setString(2, guest.getFirstName());
				ps.setString(3, guest.getLastName());
				ps.setString(4, guest.getAddress());
				ps.setLong(5, guest.getPhone());
				ps.setString(6, guest.getEmail());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int generatedGuestID = rs.getInt(1);
					reservation.getGuest().setGuestID(generatedGuestID);
				}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public List<Guest> getGuestsByNameOrPhone(String search) {
	    List<Guest> guests = new ArrayList<>();
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	         PreparedStatement ps = conn.prepareStatement(GUESTS_GET_BY_NAME_OR_PHONE_QRY)) {
	         
	        ps.setString(1, "%" + search + "%");
	        ps.setString(2, "%" + search + "%");
	        ps.setString(3, "%" + search + "%");
	        
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            Guest guest = new Guest(
	                rs.getString("title"),
	                rs.getString("firstName"),
	                rs.getString("lastName"),
	                rs.getString("address"),
	                rs.getLong("phone"),
	                rs.getString("email")
	            );
	            guest.setGuestID(rs.getInt("guestID"));
	            guests.add(guest);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return guests;
	}
	
	public Guest getGuestsByPhone(Long num) {
	    Guest guest = null;
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	         PreparedStatement ps = conn.prepareStatement(GUESTS_GET_BY_PHONE_QRY)) {
	         
	        ps.setLong(1, num);
	        
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            guest = new Guest(
	                rs.getString("title"),
	                rs.getString("firstName"),
	                rs.getString("lastName"),
	                rs.getString("address"),
	                rs.getLong("phone"),
	                rs.getString("email")
	            );
	            guest.setGuestID(rs.getInt("guestID"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return guest;
	}
	
	public Guest getGuestsByEmail(String email) {
	    Guest guest = null;
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	         PreparedStatement ps = conn.prepareStatement(GUESTS_GET_BY_EMAIL_QRY)) {
	         
	        ps.setString(1, email);
	        
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            guest = new Guest(
	                rs.getString("title"),
	                rs.getString("firstName"),
	                rs.getString("lastName"),
	                rs.getString("address"),
	                rs.getLong("phone"),
	                rs.getString("email")
	            );
	            guest.setGuestID(rs.getInt("guestID"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return guest;
	}

	
	public void addBill(Reservation reservation) {
		Bill bill = reservation.getBill();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			     PreparedStatement ps = conn.prepareStatement(BILL_INSERT_QRY, Statement.RETURN_GENERATED_KEYS)) {
			ps.setDouble(1, bill.getRatePerNight());
			ps.setDouble(2, bill.getTotalAmount());
			ps.setDouble(3, bill.getDiscount());
			ps.setDouble(4, bill.getAmountAfterDiscount());
			ps.setDouble(5, bill.getAmountAfterTax());
			ps.setInt(6, bill.getNumOfDays());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedBillID = rs.getInt(1);
				reservation.getBill().setBillID(generatedBillID);
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void addReservationDetails(Reservation reservation) {
		addReservation(reservation);
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			     PreparedStatement ps = conn.prepareStatement(RESERVATION_DETAILS_INSERT_QRY)) {
			for (RoomDetails rd : reservation.getRoomDetails()) {
				ps.setInt(1, rd.getRoom().getRoomID());
				ps.setInt(2, reservation.getReservationID());
		        ps.setInt(3, rd.getNumOfGuest());
		        ps.execute();
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public void addReservation(Reservation reservation) {
		Integer guestId = reservation.getGuest().getGuestID();
		addGuest(reservation);
		addBill(reservation);
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			    PreparedStatement ps = conn.prepareStatement(RESERVATIONS_INSERT_QRY, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, reservation.getGuest().getGuestID());
			ps.setInt(2, reservation.getNumOfRooms());
		    ps.setInt(3, reservation.getNumOfDays());
		    ps.setString(4, reservation.getTypesOfRooms());
		    ps.setDate(5, Date.valueOf(reservation.getBookingDate()));
		    ps.setDate(6, Date.valueOf(reservation.getCheckInDate()));
		    ps.setDate(7, Date.valueOf(reservation.getCheckOutDate()));
		    ps.setString(8, reservation.getStatus());
		    ps.setInt(9, reservation.getBill().getBillID());
		    ps.executeUpdate();
		    
		    ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        int generatedReservationID = rs.getInt(1);
		        reservation.setReservationID(generatedReservationID);
		    }
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public List<Reservation> getAllReservations(){
		List<Reservation> result = new ArrayList<Reservation>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			     PreparedStatement ps = conn.prepareStatement(RESERVATIONS_GET_ALL_QRY)) {

		    ResultSet rs = ps.executeQuery();

		    while (rs.next()) {
		        result.add(new Reservation(rs.getInt("reservationID"),
		        		getGuestById(rs.getInt("guestID")),
		        		getRoomDetailByReservationId(rs.getInt("reservationID")),
		        		getBillById(rs.getInt("billID")),
		        		(rs.getDate("bookingDate")).toLocalDate(),
		        		(rs.getDate("checkInDate")).toLocalDate(),
		        		(rs.getDate("checkOutDate")).toLocalDate(),
		        		rs.getString("status")
		        		));
		    }
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
		return result;
	}
	
	public List<Reservation> getReservationByDate(Date in, Date out){
		List<Reservation> result = new ArrayList<Reservation>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			     PreparedStatement ps = conn.prepareStatement(RESERVATIONS_GET_BY_DATE_QRY)) {
			ps.setDate(1, in);
		    ps.setDate(2, in);
		    ps.setDate(3, out);
		    ps.setDate(4, out);
		    ps.setDate(5, in);
		    ps.setDate(6, out);

		    ResultSet rs = ps.executeQuery();

		    while (rs.next()) {
		        result.add(new Reservation(rs.getInt("reservationID"),
		        		getGuestById(rs.getInt("guestID")),
		        		getRoomDetailByReservationId(rs.getInt("reservationID")),
		        		getBillById(rs.getInt("billID")),
		        		(rs.getDate("bookingDate")).toLocalDate(),
		        		(rs.getDate("checkInDate")).toLocalDate(),
		        		(rs.getDate("checkOutDate")).toLocalDate(),
		        		rs.getString("status")
		        		));
		    }
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
		return result;
	}
	
	public void updateReservationStatus(int id, String status) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		         PreparedStatement ps = conn.prepareStatement(RESERVATIONS_UPDATE_STATUS_QRY)) {
			ps.setString(1, status);
	        ps.setInt(2, id);
	        ps.executeUpdate();
		        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Guest getGuestById(int id) {
		Guest guest = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(GUESTS_GET_BY_ID_QRY)) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				guest = new Guest(rs.getInt("guestID"),
						rs.getString("title"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("address"),
						rs.getLong("phone"),
						rs.getString("email")
						);
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return guest;
	}
	
	public List<RoomDetails> getRoomDetailByReservationId(int id){
		List<RoomDetails> result = new ArrayList<RoomDetails>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(RESERVATION_DETAILS_GET_BY_ID_QRY)) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RoomDetails detail = new RoomDetails();
				detail.setRoom(getRoomByID(rs.getInt("roomID")));
				detail.setNumOfGuest(rs.getInt("numOfGuest"));
				result.add(detail);
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public Bill getBillById(int id) {
		Bill bill = new Bill();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(BILL_GET_BY_ID_QRY)) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bill.setBillID(rs.getInt("billID"));
				bill.setRatePerNight(rs.getDouble("ratePerNight"));
				bill.setTotalAmount(rs.getDouble("totalAmount"));
				bill.setDiscount(rs.getDouble("discount"));
				bill.setAmountAfterDiscount(rs.getDouble("amountAfterDiscount"));
				bill.setAmountAfterTax(rs.getDouble("amountAfterTax"));
				bill.setNumOfDays(rs.getInt("numOfDays"));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return bill;
	}
	
	public void updateBill(Bill bill) {
		 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		         PreparedStatement ps = conn.prepareStatement(BILL_UPDATE_QRY)) {
			 ps.setDouble(1, bill.getRatePerNight());
			 ps.setDouble(2, bill.getTotalAmount());
			 ps.setDouble(3, bill.getDiscount());
			 ps.setDouble(4, bill.getAmountAfterDiscount());
			 ps.setDouble(5, bill.getAmountAfterDiscount());
			 ps.setInt(6, bill.getNumOfDays());
			 ps.setInt(7, bill.getBillID());
			 
			 ps.executeUpdate();
		        
		 } catch (SQLException e) {
			 e.printStackTrace();
		}
	}
}