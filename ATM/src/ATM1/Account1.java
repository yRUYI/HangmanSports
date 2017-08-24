package ATM1;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Account1 implements Serializable{
	// 操作成功
	public static final int SUCCESS = 0;
	// 存款数为负数
	public static final int NEGATIVE_DEPOSITE = 1;
	// 取款数大于余额
	public static final int BEYOND_BALANCE = 2;
	// 取款金额为负数
	public static final int NEGATIVE_WITHDRAW = 3;

	private int id;// 用户id
	private double balance;// 用户存款
	private double annualInterestRate;// 年利率
	private Date dateCreated;// 创建日期

	// 构造方法
	public Account1() {

	}

	public Account1(int newId, double newBalance) {
		setId(newId);
		setBalance(newBalance);
	}

	// id访问器与修改器
	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id >= 0)
			this.id = id;
	}

	// balance访问器与修改器
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		if (balance >= 0)
			this.balance = new Double(String.format("%.2f", balance));
	}

	// annualInterestRate访问器与修改器
	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	// dateCreated访问器
	public Date getDateCreated() {
		return dateCreated;
	}

	// 获取月利率
	public double getMonthlyInterestRate() {
		return getAnnualInterestRate() / 12;
	}

	/**
	 * 取款 根据不同的条件返回不同的值
	 * 
	 * @param amount
	 * @return
	 */
	public int withdraw(double amount) {
		if (amount < 0)// 取款数小于0
			return NEGATIVE_WITHDRAW;
		if (amount > this.balance)// 取款数大于余额
			return BEYOND_BALANCE;

		this.balance -= amount;// 操作成功
		return SUCCESS;
	}

	/**
	 * 存款 根据不同的条件返回不同的值
	 * 
	 * @param amount
	 * @return
	 */
	public int deposit(double amount) {
		if (amount < 0)// 存款数小于0
			return NEGATIVE_DEPOSITE;

		this.balance += amount;// 操作成功
		return SUCCESS;
	}

}
