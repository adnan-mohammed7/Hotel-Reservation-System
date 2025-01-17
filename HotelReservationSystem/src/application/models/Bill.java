package application.models;

public class Bill {
	int billID;
	double ratePerNight;
	double totalAmount;
	double discount = 0.0;
	double amountAfterDiscount;
	double amountAfterTax;
	int numOfDays;
	
	public Bill() {
	}
	
	public Bill(double rate, int days) {
		this.ratePerNight = rate;
		this.numOfDays = days;
		calculate();
	}
	
	public Bill(double rate, int days, double dis) {
		this.ratePerNight = rate;
		this.numOfDays = days;
		this.discount = dis;
		calculate();
	}
	
	public int getBillID() {
		return billID;
	}
	public void setBillID(int billID) {
		this.billID = billID;
	}
	public double getRatePerNight() {
		return ratePerNight;
	}
	public void setRatePerNight(double ratePerNight) {
		this.ratePerNight = ratePerNight;
	}
	public double getTotalAmount() {
		calculateTotalAmount();
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
		calculate();
	}
	public double getAmountAfterDiscount() {
		if(discount != 0.0) {
			return amountAfterDiscount;
		}
		return getTotalAmount();
	}
	public void setAmountAfterDiscount(double amountAfterDiscount) {
		this.amountAfterDiscount = amountAfterDiscount;
	}
	public double getAmountAfterTax() {
		return amountAfterTax;
	}
	public void setAmountAfterTax(double amountAfterTax) {
		this.amountAfterTax = amountAfterTax;
	}
	public int getNumOfDays() {
		return this.numOfDays;
	}
	public void setNumOfDays(int days) {
		this.numOfDays = days;
	}
	void calculateTotalAmount() {
		totalAmount = ratePerNight * numOfDays;
	}
	void calculateAmountAfterDiscount() {
		if (discount != 0.0)
			amountAfterDiscount = totalAmount - (totalAmount * discount / 100.0);
	}
	void calculateAmountAfterTax() {
			amountAfterTax = (discount != 0 ? amountAfterDiscount : totalAmount) * 1.13;
	}
	void calculate() {
		calculateTotalAmount();
		calculateAmountAfterDiscount();
		calculateAmountAfterTax();
	}
}
