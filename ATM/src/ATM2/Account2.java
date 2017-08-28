package ATM2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import ATM1.Account1;

public class Account2 extends Account1 implements Serializable{
	//不是100的倍数
	public static final int NOT_MULTIPLE_100 = 4;
	//当日取款金额达到5000
	public static final int WITHDRAW_BEYOUND_5000 = 5;
	//旧密码错误
	public static final int OLDPASSWRD_WRONG = 6;
	//两次密码输入不相同
	public static final int TWO_PASSWORD_DIFFERENT = 7;
	//密码不规范
	public static final int PASSWORD_NOT_STANDARDIZED = 8;
	private String password;
	private String name;
	private ArrayList<Transaction> transactions;
	
	/**
	 * 返回账号注册的ID
	 * 若没有未使用的ID抛出错误
	 * @param unusedID
	 * @return
	 * @throws Exception
	 */
	private static int randomID(ArrayList<Integer> unusedID) throws Exception{
		int length = unusedID.size();
		if(length == 0){
			throw new Exception("注册人数已满");
		}
		return unusedID.remove((int)(Math.random() * length));
	}
	
	/**
	 * 检查密码是否符合规范
	 * @return
	 */
	public boolean checkPassword(String password){
		return password.matches("[a-zA-Z0-9]{6,10}");
	}

	/**
	 * 判断当日取款数额是否大于或等于5000
	 * @param amount
	 * @return
	 */
	public boolean withdrawExcess(double amount){
		double withdrawedAmont = 0;//已经取款的数额
		Date newDate = new Date();

		for(int i=0; i<transactions.size() ; i++){
			Transaction t = transactions.get(i);
			
			if(!t.sameDay(newDate)){//是否为同一天操作
				break;
			}
			
			if(t.getType() == Transaction.WITHDRAW){//操作类型是否相同
				withdrawedAmont += t.getAmount();
				if(withdrawedAmont > 5000){//已经取款数额大于5000
					return true;
				}
			}
		}
		
		return (withdrawedAmont + amount) > 5000;
	}

	/**
	 * 无参构造函数
	 */
	public Account2(){

	}

	/**
	 * 带初始余额的构造函数
	 * @param name
	 * @param password
	 * @param balance
	 * @param annualInterestRate
	 * @param unusedID
	 * @throws Exception
	 */
	public Account2(String name, String password, double balance, 
					double annualInterestRate, 
					ArrayList<Integer> unusedID) throws Exception{
		try{
		//密码不规范
		if(!checkPassword(password)){
			throw new Exception("密码应为6-10位的数字或字母");
		}
		//初始余额小于零
		if(balance < 0){
			throw new Exception("初始余额不能为小于零");
		}
		
		this.name = name;
		this.password = password;
		setBalance(balance);
		setAnnualInterestRate(annualInterestRate);

			setId(randomID(unusedID));
		}catch(Exception e){
			System.out.println(e.toString());
		}
		transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * 重写取钱方法
	 * @param amount
	 * @return
	 */
	@Override
	public int withdraw(double amount){
		//取款金额大于当前余额
		if(amount > getBalance()){
			return BEYOND_BALANCE;
		}
		//取款金额为负数
		if(amount < 0){
			return NEGATIVE_WITHDRAW;
		}
		//取款金额不是100的倍数
		if(amount%100 != 0){
			return NOT_MULTIPLE_100;
		}
		//当日取款金额大于或等于5000
		if(withdrawExcess(amount)){
			return WITHDRAW_BEYOUND_5000;
		}
		
		this.setBalance(getBalance() - amount);
		transactions.add(
				new Transaction(Transaction.WITHDRAW, amount, getBalance(), ""));
		
		return SUCCESS;
	}
	
	/**
	 * 重写存钱函数
	 */
	@Override
	public int deposit(double amount){
		//存款金额小于零
		if(amount < 0){
			return NEGATIVE_DEPOSITE;
		}
		
		setBalance(getBalance() + amount);
		transactions.add(
				new Transaction(Transaction.DEPOSIT, amount, getBalance(), ""));
		return SUCCESS;
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword1
	 * @param newPassword2
	 * @return
	 */
	public int changePassword(String oldPassword, String newPassword1, String newPassword2){
		//输入旧密码不相同
		if(!oldPassword.equals(password)){
			return OLDPASSWRD_WRONG;
		}
		//两次密码输入不相同
		if(!newPassword1.equals(newPassword2)){
			return TWO_PASSWORD_DIFFERENT;
		}
		//新密码不规范
		if(!checkPassword(newPassword1)){
			return PASSWORD_NOT_STANDARDIZED;
		}
		
		this.password = newPassword1;
		return SUCCESS;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	
	
}