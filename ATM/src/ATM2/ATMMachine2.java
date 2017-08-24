package ATM2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ATMMachine2{
	public Scanner input = new Scanner(System.in);
	private ArrayList<Integer> unusedID = null;
	private HashMap<Integer, Account2> accounts = null;
	private Account2 account = null;
	
	/**
	 * 读取文件，将文件中的账户信息保存至accounts中
	 * @param fileName
	 */
	public void readFile(String fileName){
		try{
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(fileName));
			accounts = (HashMap<Integer, Account2>)ois.readObject();
			if(accounts == null){
				System.out.println("read null");
			}
			ois.close();
		}catch(Exception e){
			accounts = null;
		}
	}
	
	/**
	 * 向文件中写入账号信息
	 * @param fileName
	 */
	public void writeFile(String fileName){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(fileName));
			if(accounts == null)
				System.out.println("write null");
			oos.writeObject(accounts);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 构造方法
	 */
	public ATMMachine2() {
		readFile("accounts.dat");
		
		//账户不存在，创建新的账户
		if(accounts == null){
			accounts = new HashMap<Integer, Account2>();
		}

		if(unusedID == null){
			unusedID = new ArrayList<Integer>();
			for(int i = 100000; i <= 999999; i++){
				if(!accounts.keySet().contains(i))
					unusedID.add(i);
			}
		}
	}
	
	/**
	 * 登陆
	 */
	public int login(){
		System.out.println("请输入账号: ");
		String ID = input.nextLine();
		
		//账号ID不是6为数字
		if(!ID.matches("\\d{6}")){
			System.out.println("账号错误,应为6位数字!");
			return 0;
		}
		Account2 account = accounts.get(Integer.valueOf(ID));
		
		if(account == null){
			System.out.println("账号不存在");
			return 0;
		}
		
		System.out.println("请输入密码: ");
		String password = input.nextLine();
		
		//密码不正确
		if(!account.getPassword().equals(password)){
			System.out.println("密码错误");
			return 0;
		}

		this.account = account;
		return 1;
	}
	
	/**
	 * 获取账号
	 * @return
	 */
	public Account2 getAccount(){
		return this.account;
	}
	
	/**
	 * 创建账号
	 */
	public void CreatAccount(){
		System.out.println("请输入名字: ");
		String name = input.nextLine();
		System.out.println("请输入密码: ");
		String password = input.nextLine();
		System.out.println("请输入初始余额: ");
		int balance = input.nextInt();
		System.out.println("请输入年利率: ");
		double annualInterestRate = input.nextDouble();
		
		try{
			Account2 account = new Account2(name, password, 
					balance, annualInterestRate, unusedID);
			
			accounts.put(account.getId(), account);
			System.out.println("您的账号为: " + account.getId());
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * 查看账户余额
	 */
	public void checkBalance(){
		Account2 account = getAccount();
		
		if(account != null){
			System.out.println("您的账户余额为: " + account.getBalance());
		}else{
			System.out.println("++++++");
		}
	}
	
	/**
	 * 取款
	 */
	public void withdraw(){
		Account2 account = getAccount();
		
		if(account != null){
			System.out.println("请输入取款金额: ");
			double amount = input.nextDouble();
			
			int op = account.withdraw(amount);
			if(op == 0){
				System.out.println("取款成功!");
			}else if(op == 2){
				System.out.println("账户余额不足,取款失败!");
			}else if(op == 3){
				System.out.println("取款金额输入非法,取款失败!");
			}else if(op == 4){
				System.out.println("取款金额应为100的倍数,取款失败!");
			}else if(op == 5){
				System.out.println("当日取款金额已经达5000上限,取款失败!");
			}
		}		
	}
	
	/**
	 * 存款
	 */
	public void deposit(){
		Account2 account = getAccount();
		
		if(account != null){
			System.out.println("请输入存款金额: ");
			double amount = input.nextDouble();
			
			int op = account.deposit(amount);
			if(op == 0){
				System.out.println("存款成功!");
			}else if(op == 1){
				System.out.println("存款金额输入非法，存款失败!");
			}
		}
	}
	
	/**
	 * 打印交易记录
	 */
	public void detailOfTransaction(){
		Account2 account = getAccount();
		
		if(account != null){
			for(Transaction t: account.getTransactions()){
				System.out.println(t.getDescription());
			}
		}
	}
	
	/**
	 * 修改密码
	 */
	public void changePassword(){
		Account2 account = getAccount();
		
		if(account != null){
			System.out.println("请输入旧密码: ");
			String oldPassword = input.nextLine();
			System.out.println("请输入新密码: ");
			String newPassword1 = input.nextLine();
			System.out.println("请再次输入新密码: ");
			String newPassword2 = input.nextLine();
			
			int op = account.changePassword(oldPassword, newPassword1, newPassword2);
			if(op == 0){
				System.out.println("修改成功!");
			}else if(op == 6){
				System.out.println("旧密码不正确,修改失败!");
			}else if(op == 7){
				System.out.println("两次密码不相同,修改失败!");
			}else if(op == 8){
				System.out.println("密码不规范,修改失败!");
			}
		}
	}
	
	/**
	 * 菜单
	 */
	public void menu(){
		System.out.println("Main menu");
//		System.out.println("0：create a account");
		System.out.println("1: check balance");
		System.out.println("2: withdraw");
		System.out.println("3: deposit");
		System.out.println("4：details of the transaction");
		System.out.println("5: change password");
		System.out.println("6：exit");
		System.out.println("请输入编号: ");
	}
	
	/**
	 * 开始
	 */
	public void start(){
		while(true){
			System.out.println("1: 登陆");
			System.out.println("2: 注册");
			System.out.println("0: 退出");
			System.out.println("请输入编号:");
			String select = input.nextLine();
			while(true){
				if(select.matches("[0-2]")){
					break;
				}
				select = input.nextLine();
			}
			
			if(Integer.valueOf(select) == 1){
				if(login() == 1){
					while(true){
						menu();
						String option = input.nextLine();
						while (!isNumber(option)){
							option = input.nextLine();
						}
						int op = Integer.valueOf(option);
//						input.nextLine();
						
						switch(op){
						case 1: checkBalance();break;
						case 2: withdraw();break;
						case 3: deposit();break;
						case 4: detailOfTransaction();break;
						case 5: changePassword();break;
						case 6: writeFile("accounts.dat");System.exit(0);break;
						default: System.out.println("操作错误!");
						}
					}
				}
			}else if(Integer.valueOf(select) == 2){
				CreatAccount();
			}else if(Integer.valueOf(select) == 0){
				System.exit(1);
			}
			
			
		}
	}
	
	public boolean isNumber(String num){
		return num.matches("\\d+");
	}
	
	public static void main(String[] args) throws InterruptedException{
		ATMMachine2 atm = new ATMMachine2();
		atm.start();
	}
}