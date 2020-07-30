package features;

import java.util.Date;

/**
 * 
 * @author ishaqkhan
 *
 */

public class Order { 
	
	private int id;
	private String userName;
	private Double amount;
	private Date date;
	private Date deliveryDate;
	public Order(int id) { 
		this.id = id;
	}
	public int getId() { 
		return id; 
	}
	
	public void setId(int id) { 
		this.id = id; 
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@Override
	public String toString() {
		return "Order {id=" + id + ", userName=" + userName + ", amount=" + amount + ", date=" + date
				+ ", deliveryDate=" + deliveryDate + "}";
	}
	
	
	
	
}