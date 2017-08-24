package ATM1;

import java.util.Scanner;

public class ATMMachine1 {
	// 创建账号
	private static Account1[] account1 = setAccount(100);
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
//		// 创建账号
//		Account1[] account1 = setAccount(100);

		String strToInt;
		while(true){
			System.out.print("请输入账号id(00~99):");
			//判断输入是否为整数
			strToInt = input.nextLine();
			if(isInt(strToInt)){
				break;
			}
			System.out.println("输入错误，当前输入非整数!\n请重新输入:");
		}		
		int ID = Integer.valueOf(strToInt);

		// 开始菜单
		while (true) {
			System.out.println("\nMain menu");
			System.out.println("1:check balance");
			System.out.println("2:withdraw");
			System.out.println("3:deposit");
			System.out.println("4:exit");

			// 输入数字进行操作
			String strToSelect;
			while(true){
				//判断输入是否为整数
				System.out.print("\n请输入操作号:");
				strToSelect = input.nextLine();
				if(isInt(strToSelect))
					break;
				System.out.println("输入错误,当前输入非整数!");
			}
			int select = Integer.valueOf(strToSelect);
			switch (select) {
			case 1:
				checkBalance(account1[ID]);// 查看账户余额
				break;
			case 2:
				// 取款
				String strToWithdraw;
					System.out.println("请输入取款金额:");
					//判断输入是否为双精度浮点数
					strToWithdraw = input.nextLine();
				double amountOFWithdraw = Double.parseDouble(strToWithdraw);
				withdraw(account1[ID], amountOFWithdraw);
				break;
			case 3://存款
				String strToDeposit;
					System.out.println("请输入存款金额:");
					//判断输入是否为双精度浮点数
					strToDeposit = input.nextLine();
				double amountOfDeposit = Double.parseDouble(strToDeposit);
				Deposit(account1[ID], amountOfDeposit);
				break;
			case 4:
				System.exit(0);// 退出
			default:
				System.out.println("操作号错误！请输入正确的操作号");
			}
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}

	}

	// 定义并初始化类数组
	public static Account1[] setAccount(int numberOfId) {
		Account1[] account1 = new Account1[numberOfId];
		for (int i = 0; i < numberOfId; i++) {
			account1[i] = new Account1(i, 1000);
		}

		return account1;
	}

	// checkBalance
	public static void checkBalance(Account1 account) {
		System.out.println("账户余额为: " + account.getBalance());
	}

	// withdraw
	public static void withdraw(Account1 account, double amount) {
		int result = account.withdraw(amount);
		if (result == 2)
			System.out.println("取款金额大于账户余额,取款失败");
		else if (result == 3)
			System.out.println("取款金额不能小于0,取款失败");
		else {
			System.out.println("取款操作成功,已取出 $" + amount);
			System.out.println("账户余额为: $" + account.getBalance());
		}

	}

	// deposit
	public static void Deposit(Account1 account, double amount) {
		int result = account.deposit(amount);
		if (result == 1)
			System.out.println("存入金额不能小于0,存入失败");
		else {
			System.out.print("操作成功,已存入 $" + amount + ", ");
			System.out.println("账户余额为: $" + account.getBalance());
		}
	}
	
	//用正则表达式判断字符串是否为数字字符串
	public static boolean isInt(String str){
		if(str.matches("[\\d]+"))
			return true;
		else
			return false;		
	}
	//用正则表达式判断字符串是否为双精度浮点数
	public static boolean isDouble(String str){
		if(str.matches("[\\d]+.[\\d]+"))
			return true;
		else
			return false;		
	}

}
