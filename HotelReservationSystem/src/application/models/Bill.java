package application.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Bill {
	static final AtomicInteger counter = new AtomicInteger(0);
	int billID;
	double ratePerNight;
	double totalAmount;
	double discount = 0.0;
	double amountAfterDiscount;
	double amountAfterTax;
	int NumOfDays;
	
	public Bill(double rate, int days) {
		this.ratePerNight = rate;
		this.NumOfDays = days;
		this.billID = counter.incrementAndGet();
		calculate();
	}
	
	public Bill(double rate, int days, double dis) {
		this.ratePerNight = rate;
		this.NumOfDays = days;
		this.discount = dis;
		this.billID = counter.incrementAndGet();
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
		return amountAfterDiscount;
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
	void calculateTotalAmount() {
		totalAmount = ratePerNight * NumOfDays;
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