package ATM2;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{
	//存入
	public static final char DEPOSIT = 'D';
	//取出
	public static final char WITHDRAW = 'W';
	//操作时间
	private Date date;
	//操作类型
	private char type;
	//操作金额
	private double amount;
	//当前余额
	private double balance;
	//操作描述
	private String description;
	
	public Transaction(char type, double amount, double balance, String description) {
		this.date = new Date();
		this.type = type;
		this.amount = amount;
		this.balance = balance;
		this.description = date.toString() + " " + 
							(getType() == DEPOSIT ? "存入: " : "取出: ") +
							getAmount() + " 当前余额: " + getBalance();
	}

	/**
	 * 获取操作类型
	 * @return
	 */
	public char getType() {
		return type;
	}

	/**
	 * 获取操作金额
	 * @return
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * 获取当前余额
	 * @return
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * 获取描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 获取操作时间
	 * @return
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * 判断是否为同一天
	 * @param newDate
	 * @return
	 */
	public boolean sameDay(Date newDate){
		return getDate().getTime() /24/3600/1000 == newDate.getTime() /24/3600/1000;
	}
	
	/**
	 * 返回操作类型
	 */
	@Override
	public String toString(){
		return description;
	}
}