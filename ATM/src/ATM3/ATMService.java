package ATM3;

import ATM2.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ATMService {
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
    public ATMService() {
        //读取文件中的账户信息
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
     * 登录
     * @param id
     * @param password
     * @return
     */
    public int login(String id, String password){
        //账号不是6为数字
        if(!id.matches("\\d{6}")){
            return 0;
        }

        Account2 account2 = accounts.get(Integer.valueOf(id));

        //没有账号信息
        if(account2 == null){
            return 1;
        }

        //密码错误
        if(!password.equals(account2.getPassword())){
            return 2;
        }

        this.account = account2;

        //成功
        return 3;
    }

    /**
     * 注册
     * @param name
     * @param balance
     * @param annualInterestRate
     * @param password
     * @return
     */
    public int register(String name, String balance, String annualInterestRate, String password){
        //用户名不是字母
        if(!name.matches("[a-zA-Z]+")){
            return 0;
        }

        //初始余额不是整数
        if(!balance.matches("[\\d]+")){
            return 1;
        }

        //年利率不是小于零的小数
        if(!annualInterestRate.matches("0.[\\d]+")){
            return 2;
        }

        //密码不是6-10位数字或字母
        if(!password.matches("[a-zA-Z0-9]{6,10}")){
            return 3;
        }

        try {
            Account2 account2 = new Account2(name, password, Double.valueOf(balance),
                    Double.valueOf(annualInterestRate), unusedID);

            accounts.put(account2.getId(), account2);
            return account2.getId();//返回账号
        }catch(Exception e){
            e.printStackTrace();
            return 4;//注册失败
        }
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword1
     * @param newPassword2
     * @return
     */
    public int changePassword(String oldPassword, String newPassword1, String newPassword2){
        int value = account.changePassword(oldPassword, newPassword1, newPassword2);

        //旧密码输入错误
        if (value == 6){
            return 0;
        }else if (value == 7){//两次密码输入不相同
            return 1;
        }else if (value == 8){//新密码不是6-10位数字或字母
            return 2;
        }

        //修改成功
        return 3;
    }

    /**
     * 获取交易记录
     * @return
     */
    public ArrayList<Transaction> detailOfTransaction(){
        return account.getTransactions();
    }

    /**
     * 取款
     * @param amount
     * @return
     */
    public int withdraw(String amount){
        //取款金额不s100的整数
        if(!amount.matches("\\d+00")){
            return 0;
        }

        int op = account.withdraw(Double.valueOf(amount));

        //取款金额大于账户余额
        if(op == 2){
            return 1;
        }
        //当日取款金额达到5000
        if (op == 5) {
            return 2;
        }
        //成功
        return 3;

    }

    /**
     * 存款
     * @param amount
     * @return
     */
    public int deposit(String amount){
        //存入金额非实数
        if(!amount.matches("\\d+") && !amount.matches("[\\d]+.[\\d]+")){
            return 0;
        }

        int value = account.deposit(Double.valueOf(amount));

        //存款金额小于零
        if(value == 1){
            return 1;
        }else{//存款成功
            return 2;
        }
    }

    public double checkBalance(){
        return account.getBalance();
    }

    /**
     * 退出
     */
    public void exit(){
        //向文件中写入账户信息
        writeFile("accounts.dat");
        System.exit(1);
    }
}
