package application.models;

public class AdminUsers {
	int adminId;
	String userName;
	String password;
	
	public AdminUsers(int id, String name, String pwd){
		//No user validation is done as these are hard coded
		this.adminId = id;
		this.userName = name;
		this.password = pwd;
	}
	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
}
