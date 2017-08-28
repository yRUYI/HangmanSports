package ATM2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import ATM1.Account1;

public class Account2 extends Account1 implements Serializable{
	//����100�ı���
	public static final int NOT_MULTIPLE_100 = 4;
	//����ȡ����ﵽ5000
	public static final int WITHDRAW_BEYOUND_5000 = 5;
	//���������
	public static final int OLDPASSWRD_WRONG = 6;
	//�����������벻��ͬ
	public static final int TWO_PASSWORD_DIFFERENT = 7;
	//���벻�淶
	public static final int PASSWORD_NOT_STANDARDIZED = 8;
	private String password;
	private String name;
	private ArrayList<Transaction> transactions;
	
	/**
	 * �����˺�ע���ID
	 * ��û��δʹ�õ�ID�׳�����
	 * @param unusedID
	 * @return
	 * @throws Exception
	 */
	private static int randomID(ArrayList<Integer> unusedID) throws Exception{
		int length = unusedID.size();
		if(length == 0){
			throw new Exception("ע����������");
		}
		return unusedID.remove((int)(Math.random() * length));
	}
	
	/**
	 * ��������Ƿ���Ϲ淶
	 * @return
	 */
	public boolean checkPassword(String password){
		return password.matches("[a-zA-Z0-9]{6,10}");
	}

	/**
	 * �жϵ���ȡ�������Ƿ���ڻ����5000
	 * @param amount
	 * @return
	 */
	public boolean withdrawExcess(double amount){
		double withdrawedAmont = 0;//�Ѿ�ȡ�������
		Date newDate = new Date();

		for(int i=0; i<transactions.size() ; i++){
			Transaction t = transactions.get(i);
			
			if(!t.sameDay(newDate)){//�Ƿ�Ϊͬһ�����
				break;
			}
			
			if(t.getType() == Transaction.WITHDRAW){//���������Ƿ���ͬ
				withdrawedAmont += t.getAmount();
				if(withdrawedAmont > 5000){//�Ѿ�ȡ���������5000
					return true;
				}
			}
		}
		
		return (withdrawedAmont + amount) > 5000;
	}

	/**
	 * �޲ι��캯��
	 */
	public Account2(){

	}

	/**
	 * ����ʼ���Ĺ��캯��
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
		//���벻�淶
		if(!checkPassword(password)){
			throw new Exception("����ӦΪ6-10λ�����ֻ���ĸ");
		}
		//��ʼ���С����
		if(balance < 0){
			throw new Exception("��ʼ����ΪС����");
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
	 * ��дȡǮ����
	 * @param amount
	 * @return
	 */
	@Override
	public int withdraw(double amount){
		//ȡ������ڵ�ǰ���
		if(amount > getBalance()){
			return BEYOND_BALANCE;
		}
		//ȡ����Ϊ����
		if(amount < 0){
			return NEGATIVE_WITHDRAW;
		}
		//ȡ�����100�ı���
		if(amount%100 != 0){
			return NOT_MULTIPLE_100;
		}
		//����ȡ������ڻ����5000
		if(withdrawExcess(amount)){
			return WITHDRAW_BEYOUND_5000;
		}
		
		this.setBalance(getBalance() - amount);
		transactions.add(
				new Transaction(Transaction.WITHDRAW, amount, getBalance(), ""));
		
		return SUCCESS;
	}
	
	/**
	 * ��д��Ǯ����
	 */
	@Override
	public int deposit(double amount){
		//�����С����
		if(amount < 0){
			return NEGATIVE_DEPOSITE;
		}
		
		setBalance(getBalance() + amount);
		transactions.add(
				new Transaction(Transaction.DEPOSIT, amount, getBalance(), ""));
		return SUCCESS;
	}
	
	/**
	 * �޸�����
	 * @param oldPassword
	 * @param newPassword1
	 * @param newPassword2
	 * @return
	 */
	public int changePassword(String oldPassword, String newPassword1, String newPassword2){
		//��������벻��ͬ
		if(!oldPassword.equals(password)){
			return OLDPASSWRD_WRONG;
		}
		//�����������벻��ͬ
		if(!newPassword1.equals(newPassword2)){
			return TWO_PASSWORD_DIFFERENT;
		}
		//�����벻�淶
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