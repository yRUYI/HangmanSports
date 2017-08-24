package ATM3;

import ATM2.Transaction;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;


public class AllPane extends virtualKeyboard{
    //虚拟键盘类
    private virtualKeyboard keyboard = new virtualKeyboard();
    //虚拟键盘面板
    private BorderPane borderPaneOfKeyBoard = keyboard.getPane();
    //后台处理类
    private ATMService service = new ATMService();

    //主面板
    private BorderPane pane = new BorderPane();
    //登陆面板
    private BorderPane loginPane = new BorderPane();
    //注册面板
    private BorderPane registerPane = new BorderPane();
    //更改密码面板
    private BorderPane changePasswordPane = new BorderPane();
    //取款面板
    private BorderPane withdrawPane = new BorderPane();
    //存款面板
    private BorderPane depositPane = new BorderPane();
    //查看交易记录面板
    private BorderPane detailOfTransactionPane = new BorderPane();
    //退出按钮
    private Button btOfExit = new Button("退出");
    //虚拟键盘面板
    private HBox hBoxOfKeyBoard = new HBox();
    //左侧垂直面板
    private VBox vBoxOfRight = new VBox(20);
    //右侧垂直面板;
    private VBox vBoxOfLeft = new VBox(20);
    //左侧面板
    private BorderPane borderPaneOfRight = new BorderPane();
    //右侧面板;
    private BorderPane borderPaneOfLeft = new BorderPane();
    //中心文本域面板
    private VBox vBoxOfTextFiled = new VBox();

    //设置公共UI
    public void setMainUI(){
        //设置退出按钮
        setButton(btOfExit);

        //退出按钮事件
        btOfExit.setOnAction(e ->{
            service.exit();
        });

        //将虚拟键盘设置为中心区
        hBoxOfKeyBoard.setAlignment(Pos.CENTER);
        hBoxOfKeyBoard.getChildren().add(borderPaneOfKeyBoard);

        //左右两边添加垂直面板
        borderPaneOfLeft.setBottom(vBoxOfLeft);
        borderPaneOfRight.setBottom(vBoxOfRight);

        //将文本域设置为垂直文本域面板中心
        vBoxOfTextFiled.setAlignment(Pos.CENTER);
    }

    //设置按钮大小
    public void setButton(Button bt){
        bt.setMaxWidth(100);
        bt.setMinWidth(100);
        bt.setPrefWidth(100);
        bt.setMaxHeight(40);
        bt.setMinHeight(40);
        bt.setPrefHeight(40);
    }

    //设置文本域大小
    public void setTextFiled(TextField textFiled){
        textFiled.setMaxWidth(250);
        textFiled.setMinWidth(250);
        textFiled.setPrefWidth(250);
        textFiled.setMaxHeight(30);
        textFiled.setMinHeight(30);
        textFiled.setPrefHeight(30);
    }

    //清空主面板的组件
    public void clearPane(){
        pane.getChildren().clear();
        vBoxOfLeft.getChildren().clear();
        vBoxOfRight.getChildren().clear();
        vBoxOfTextFiled.getChildren().clear();
    }

    //系统提示框
    public void alertInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("系统提示");
        alert.setContentText(message);

        alert.show();
    }

    //登录类
    class LoginPane{
        //接收账号的文本域
        protected TextField textFieldOfId = new TextField();
        //接收密码的文本域
        protected TextField textFieldOfPassword = new TextField();
        protected Text textOfId = new Text("请输入账号");
        protected Text textOfPassword = new Text("请输入密码");
        //登陆按钮
        protected Button btOfLogin = new Button("登录");
        //注册按钮
        protected Button btOfRegister = new Button("注册");
        //辅助面板
        protected BorderPane borderPane = new BorderPane();
        //存放账号
        private String id = null;
        //存放密码
        private String password = null;

        /**
         * 构造函数
         */
        public LoginPane(){
            clearPane();//清空主面板
            setUI();//设置组件
            solveEvent();//处理鼠标事件
        }

        /**
         * 设置组件
         */
        protected void setUI(){
            //设置文本域宽度和长度
            setTextFiled(textFieldOfId);
            setTextFiled(textFieldOfPassword);

            //提示文本的文字居中
            textOfId.setTextAlignment(TextAlignment.CENTER);
            textOfPassword.setTextAlignment(TextAlignment.CENTER);

            //设置按钮大小
            setButton(btOfLogin);
            setButton(btOfRegister);

            //向左右面板中添加按钮
            vBoxOfLeft.getChildren().addAll(btOfRegister, btOfExit);
            vBoxOfRight.getChildren().addAll(btOfLogin);

            //想文本域面板中添加文本域和提示文本
            vBoxOfTextFiled.getChildren().addAll(textOfId, textFieldOfId, textOfPassword, textFieldOfPassword);

            //向辅助面板中添加相关组件
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //将辅助面板添加至主面板中
            pane.setCenter(borderPane);
        }

        /**
         * 处理鼠标事件和点击事件
         */
        public void solveEvent(){
            //设置默认输出文本域
            keyboard.setControl(textFieldOfId);

            //点击事件设置输出文本域
            textFieldOfId.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfId);
            });
            textFieldOfPassword.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfPassword);
            });

            //登陆按钮点击事件处理
            btOfLogin.setOnAction(e ->{
                //获取账号和密码
                id = textFieldOfId.getText();
                password = textFieldOfPassword.getText();

                //处理信息并获取返回值
                int value = service.login(id, password);

                if(value == 0){
                    alertInformation("账号应为6位数字");
                }else if(value == 1){
                    alertInformation("无账号信息");
                }else if(value == 2){
                    alertInformation("密码错误");
                }else if(value == 3){//登陆成功
                    new MenuPane();//进入主界面
                }
            });

            //点击注册按钮进入注册界面
            btOfRegister.setOnAction(e ->{
                new RegisterPane();
            });
        }
    }

    /**
     * 注册类
     */
    class RegisterPane{
        //接收用户名、初始余额、年利率和密码的文本域
        protected TextField textFieldOfName = new TextField();
        protected TextField textFieldOfBalance = new TextField();
        protected TextField textFieldOfAnual = new TextField();
        protected TextField textFieldOfPassword = new TextField();

        //提示相关信息的文本
        protected Text textOfName = new Text("请输入用户名");
        protected Text textOfBalance = new Text("请输入初始余额");
        protected Text textOfAnual = new Text("请输入年利率");
        protected Text textOfPassword = new Text("请输入密码");

        //注册和返回按钮
        protected Button btOfReturn = new Button("返回上一层");
        protected Button btOfRegister = new Button("注册");

        //辅助面板
        protected BorderPane borderPane = new BorderPane();

        //构造函数
        public RegisterPane(){
            clearPane();//清理主面板
            setUI();//设置组件
            solveEvent();//处理事件
        }

        /**
         * 设置组件
         */
        public void setUI(){
            //设置输入文本域大小
            setTextFiled(textFieldOfName);
            setTextFiled(textFieldOfBalance);
            setTextFiled(textFieldOfAnual);
            setTextFiled(textFieldOfPassword);

            //设置文本域提示信息
            textFieldOfName.setPromptText("请输入姓名");
            textFieldOfBalance.setPromptText("请输入账户余额");
            textFieldOfAnual.setPromptText("请输入年利率(例如:0.02)");
            textFieldOfPassword.setPromptText("请输入6位由字母或数字组成的密码");

            //设置按钮大小
            setButton(btOfRegister);
            setButton(btOfReturn);

            //向文本域面板中添加组件
            vBoxOfTextFiled.getChildren().addAll(textOfName, textFieldOfName, textOfBalance, textFieldOfBalance,
                    textOfAnual, textFieldOfAnual, textOfPassword, textFieldOfPassword);

            //想左右面板添加按钮
            vBoxOfLeft.getChildren().addAll(btOfReturn, btOfExit);
            vBoxOfRight.getChildren().addAll(btOfRegister);

            //向辅助面板添加组件
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //向主面板添加辅助面板
            pane.setCenter(borderPane);
        }

        /**
         * 事件处理函数
         */
        public void solveEvent(){
            //设置默认输入文本域
            keyboard.setControl(textFieldOfName);

            //设置输入文本域
            textFieldOfName.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfName);
            });
            textFieldOfBalance.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfBalance);
            });
            textFieldOfAnual.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfAnual);
            });
            textFieldOfPassword.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfPassword);
            });

            //注册按钮点击处理
            btOfRegister.setOnAction(e ->{
                //调用注册番薯并获取返回值
                int value = service.register(textFieldOfName.getText(), textFieldOfBalance.getText(),
                                textFieldOfAnual.getText(), textFieldOfPassword.getText());

                //判断并设置提示信息
                if(value == 0){
                    alertInformation("名字不能为空");
                }else if(value == 1){
                    alertInformation("初始余额应为整数");
                }else if(value == 2){
                    alertInformation("年利率应为小数");
                }else if(value == 3){
                    alertInformation("密码应由6-10位数字或字母组成");
                }else if(value == 4){
                    alertInformation("注册失败,请联系客服");
                }else {
                    alertInformation("请记住您的账号 " + value);//输出账号
                }
            });

            //返回登录界面按钮点击处理
            btOfReturn.setOnAction(e ->{
                new LoginPane();
            });
        }
    }

    /**
     * 取款类
     */
    class WithdrawPane{
        //接收取款金额
        protected TextField textFieldOfWithdraw = new TextField();
        //提示文本
        protected Text textOfWithdraw = new Text("请输入取款金额");
        //指定金额取款按钮
        protected Button btOf100 = new Button("100");
        protected Button btOf200 = new Button("200");
        protected Button btOf500 = new Button("500");
        protected Button btOf1000 = new Button("1000");
        protected Button btOf1500 = new Button("1500");
        protected Button btOf2000 = new Button("2000");
        //取款按钮
        protected Button btOfWithdraw = new Button("取款");
        //返回主界面按钮
        protected Button btOfReturn = new Button("返回上一层");
        //辅助面板
        protected BorderPane borderPane = new BorderPane();

        /**
         * 构造函数
         */
        public WithdrawPane(){
            clearPane();//清理主面板组件
            setUI();//设置组件
            solveEvent();//事件处理函数
        }

        /**
         * 设置组件函数
         */
        protected void setUI(){
            //设置文本域大小
            setTextFiled(textFieldOfWithdraw);
            //设置文本域提示信息
            textFieldOfWithdraw.setPromptText("请输入取款数额");
            //设置按钮大小
            setButton(btOf100);
            setButton(btOf200);
            setButton(btOf500);
            setButton(btOf1000);
            setButton(btOf1500);
            setButton(btOf2000);
            setButton(btOfReturn);
            setButton(btOfWithdraw);
            //向文本域垂直面中添加组件
            vBoxOfTextFiled.getChildren().addAll(textOfWithdraw, textFieldOfWithdraw);
            //向左右垂直面板添加组件
            vBoxOfLeft.getChildren().addAll(btOf100, btOf200, btOf500, btOfReturn);
            vBoxOfRight.getChildren().addAll(btOf1000, btOf1500, btOf2000, btOfWithdraw);
            //向辅助面板中添加组件
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);
            //向主面板中添加辅助面板
            pane.setCenter(borderPane);
        }

        /**
         * 事件处理函数
         */
        private void solveEvent(){
            //设置默认输入文本域
            keyboard.setControl(textFieldOfWithdraw);

            //指定金额取款按钮点击事件处理
            btOf100.setOnAction(e ->{
                int op = service.withdraw("100");
                option(op);

            });
            btOf200.setOnAction(e ->{
                int op = service.withdraw("200");
                option(op);
            });
            btOf500.setOnAction(e ->{
                int op = service.withdraw("500");
                option(op);
            });
            btOf1000.setOnAction(e ->{
                int op = service.withdraw("1000");
                option(op);
            });
            btOf1500.setOnAction(e ->{
                int op = service.withdraw("1500");
                option(op);
            });
            btOf2000.setOnAction(e ->{
                int op = service.withdraw("2000");
                option(op);
            });

            //取款按钮点击事件处理
            btOfWithdraw.setOnAction(e ->{
                //调用取款函数并获取返回值
                int op = service.withdraw(textFieldOfWithdraw.getText());
                //调用处理返回值函数
                option(op);
            });
            //返回按钮事件处理
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });

        }

        /**
         * 处理返回值函数
         * @param op
         */
        public void option(int op){
            if(op == 0){
                alertInformation("取款金额应为100的倍数且为正数");
            }else if (op == 1){
                alertInformation("账户余额不足");
            }else if (op == 2){
                alertInformation("当日取款金额已达5000上限");
            }else if (op == 3){
                alertInformation("取款成功");
            }
        }
    }

    /**
     * 存入类
     */
    class DepositPane{
        //接收输入金额文本域
        protected TextField textFieldOfDeposit = new TextField();
        //提示文本
        protected Text textOfDeposit = new Text("请输入存入金额");
        //指定金额存入阿牛
        protected Button btOf100 = new Button("100");
        protected Button btOf200 = new Button("200");
        protected Button btOf500 = new Button("500");
        protected Button btOf1000 = new Button("1000");
        protected Button btOf1500 = new Button("1500");
        protected Button btOf2000 = new Button("2000");
        //存入按钮
        protected Button btOfDeposit = new Button("存入");
        //返回按钮
        protected Button btOfReturn = new Button("返回上一层");
        //辅助面板
        protected BorderPane borderPane = new BorderPane();

        /**
         * 构造函数
         */
        public DepositPane(){
            clearPane();//清理主面板组件
            setUI();//设置组件
            solveEvent();//处理事件函数
        }

        /**
         * 设置组件
         */
        protected void setUI(){
            //设置文本域大小
            setTextFiled(textFieldOfDeposit);

            //设置按钮大小
            setButton(btOf100);
            setButton(btOf200);
            setButton(btOf500);
            setButton(btOf1000);
            setButton(btOf1500);
            setButton(btOf2000);
            setButton(btOfDeposit);
            setButton(btOfReturn);

            //向左右面板中添加按钮
            vBoxOfLeft.getChildren().addAll(btOf100, btOf200, btOf500, btOfReturn);
            vBoxOfRight.getChildren().addAll(btOf1000, btOf1500, btOf2000,btOfDeposit);

            //向垂直文本域面板添加文本域
            vBoxOfTextFiled.getChildren().addAll(textOfDeposit, textFieldOfDeposit);

            //向辅助面板添加组件
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //向主面板添加辅助面板
            pane.setCenter(borderPane);
        }

        /**
         * 事件处理函数
         */
        protected void solveEvent(){
            //设置默认输入文本域
            keyboard.setControl(textFieldOfDeposit);

            //指定金额存入按钮点击事件处理
            btOf100.setOnAction(e ->{
                int op = service.deposit("100");
                option(op);
            });
            btOf200.setOnAction(e ->{
                int op = service.deposit("200");
                option(op);
            });
            btOf500.setOnAction(e ->{
                int op = service.deposit("500");
                option(op);
            });
            btOf1000.setOnAction(e ->{
                int op = service.deposit("1000");
                option(op);
            });
            btOf1500.setOnAction(e ->{
                int op = service.deposit("1500");
                option(op);
            });
            btOf2000.setOnAction(e ->{
                int op = service.deposit("2000");
                option(op);
            });

            //存入按钮点击事件处理
            btOfDeposit.setOnAction(e ->{
                int op = service.deposit(textFieldOfDeposit.getText());
                option(op);
            });

            //返回主界面按钮点击事件处理
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });
        }

        /**
         * 处理返回值函数
         * @param op
         */
        protected void option(int op){
            if(op == 0){
                alertInformation("存入金额应为整数或小数且大于零");
            }else if (op == 1){
                alertInformation("存入金额应大于零");
            }else {
                alertInformation("存入成功");
            }
        }

    }

    /**
     * 查看账户余额类
     */
    class CheckBalancePane{
        //显示账户余额文本
        protected Text textOfCheckBalance = new Text();
        //返回按钮
        protected Button btOfReturn = new Button("返回上一层");
        //辅助面板
        protected BorderPane borderPane = new BorderPane();

        /**
         * 构造函数
         */
        public CheckBalancePane(){
            clearPane();//清理主面板组件
            setUI();//设置组件
            solveEvent();//事件处理函数
        }

        /**
         * 设置组件函数
         */
        protected void setUI(){
            //设置显示文字位于文本中心
            textOfCheckBalance.setTextAlignment(TextAlignment.CENTER);

            //设置返回按钮大小
            setButton(btOfReturn);

            //向左面板添加返回按钮
            vBoxOfLeft.getChildren().addAll(btOfReturn);

            //向中心垂直文本域面板添加显示文本
            vBoxOfTextFiled.getChildren().addAll(textOfCheckBalance);

            //向辅助面板添加组件
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //向主面板添加辅助面板
            pane.setCenter(borderPane);
        }

        /**
         * 事件处理函数
         */
        protected void solveEvent(){
            //显示账户余额
            textOfCheckBalance.setText("您的账户余额为: " + service.checkBalance());

            //返回按钮点击事件处理
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });
        }
    }

    /**
     * 修改密码类
     */
    class ChangePasswordPane{
        //旧密码输入框
        protected TextField textFieldOfOld = new TextField();
        //新密码输入框
        protected TextField textFieldOfNew1 = new TextField();
        //再次输入密码框
        protected TextField textFieldOfNew2 = new TextField();
        //提示文本
        protected Text textOfOld = new Text("请输入旧密码");
        protected Text textOfNew1 = new Text("请输入新密码");
        protected Text textOfNew2 = new Text("请再次输入新密码");
        //返回按钮和修改按钮
        protected Button btOfReturn = new Button("返回上一层");
        protected Button btOfChange = new Button("修改");
        //辅助面板
        protected BorderPane borderPane = new BorderPane();

        /**
         * 构造函数
         */
        public ChangePasswordPane(){
            clearPane();//清理主面板
            setUI();//设置组件
            solveEvent();//事件处理
        }

        /**
         * 设置组件
         */
        protected void setUI(){
            //设置输入框大小
            setTextFiled(textFieldOfOld);
            setTextFiled(textFieldOfNew1);
            setTextFiled(textFieldOfNew2);

            //设置输入框提示信息
            textFieldOfOld.setPromptText("请输入旧密码");
            textFieldOfNew1.setPromptText("请输入新密码");
            textFieldOfNew2.setPromptText("请再次输入密码");

            //设置按钮大小
            setButton(btOfReturn);
            setButton(btOfChange);

            //向输入框垂直面板添加按钮
            vBoxOfTextFiled.getChildren().addAll(textOfOld, textFieldOfOld, textOfNew1,
                                                textFieldOfNew1,textOfNew2, textFieldOfNew2);

            //向左右面板添加按钮
            vBoxOfLeft.getChildren().addAll(btOfReturn);
            vBoxOfRight.getChildren().addAll(btOfChange);

            //向辅助面板添加组件
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);
            borderPane.setBottom(hBoxOfKeyBoard);

            //向主面板添加辅助面板
            pane.setCenter(borderPane);
        }

        /**
         * 事件处理函数
         */
        protected void solveEvent(){
            //设置默认输入框
            keyboard.setControl(textFieldOfOld);

            //输入框点击事件设置当前输入框
            textFieldOfOld.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfOld);
            });
            textFieldOfNew1.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfNew1);
            });
            textFieldOfNew2.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfNew2);
            });

            //返回按钮点击事件处理
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });

            //修改按钮点击事件处理
            btOfChange.setOnAction(e ->{
                //调用修改密码函数并获取返回值
                int op = service.changePassword(textFieldOfOld.getText(),
                                                textFieldOfNew1.getText(), textFieldOfNew2.getText());
                //调用返回值处理函数
                option(op);
            });
        }

        /**
         * 返回值处理函数
         * @param op
         */
        protected void option(int op){

            if (op == 0){
                alertInformation("密码错误");
            }else if (op == 1){
                alertInformation("与第1次输入的密码不同");
            }else if (op == 2){
                alertInformation("密码应为6-10位数字或字母");
                alertInformation("密码应为6-10位数字或字母");
            }else if (op == 3){
                alertInformation("修改成功");
            }
        }
    }

    /**
     * 查看交易记录类
     */
    class CheckTransactionPane{
        //显示交易记录文本区域
        protected TextArea textArea = new TextArea();
        //返回按钮
        protected Button btOfReturn = new Button("返回上一层");
        //滚动支持面板
        protected ScrollPane scrollPane = new ScrollPane(textArea);
        //辅助面板
        protected BorderPane borderPane = new BorderPane();
        //交易记录数组
        protected ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();

        /**
         * 构造函数
         */
        public CheckTransactionPane(){
            clearPane();//清理主面板
            setUI();//设置组件
            solveEvent();//事件处理
        }

        /**
         * 设置组件函数
         */
        protected void setUI(){
            //输出区域设置为40列20行并且可换行
            textArea.setPrefColumnCount(35);
            textArea.setPrefRowCount(20);
            textArea.setWrapText(true);

            //设置按钮大小
            setButton(btOfReturn);

            //添加组件
            vBoxOfLeft.getChildren().addAll(btOfReturn);
            vBoxOfTextFiled.getChildren().addAll(scrollPane);
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            pane.setCenter(borderPane);
        }

        /**
         * 事件处理函数
         */
        protected void solveEvent(){
            //获取交易记录
            transactionArrayList = service.detailOfTransaction();
            StringBuilder string = new StringBuilder();
            for(int i = 0; i < transactionArrayList.size(); i++){
                string.append(transactionArrayList.get(i) + "\n");
            }
            textArea.setText(string.toString());
            textArea.setEditable(false);

            //返回按钮点击事件处理
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });
        }

    }

    /**
     * 主界面类
     */
    class MenuPane{
        //选项按钮
        protected Button btOfWithdraw = new Button("取款");
        protected Button btOfDeposit = new Button("存款");
        protected Button btOfTransaction = new Button("查看交易记录");
        protected Button btOfCheckBalance = new Button("查看余额");
        protected Button btOfChangePassword = new Button("修改密码");
        //辅助面板
        protected BorderPane borderPane = new BorderPane();

        /**
         * 构造函数
         */
        public MenuPane(){
            clearPane();//清理主面板
            setUI();//设置组件
            solveEvent();//事件处理
        }

        /**
         * 设置组件函数
         */
        private void setUI(){
            //设置按钮大小
            setButton(btOfCheckBalance);
            setButton(btOfWithdraw);
            setButton(btOfDeposit);
            setButton(btOfTransaction);
            setButton(btOfChangePassword);;

            //添加组件
            vBoxOfLeft.getChildren().addAll(btOfTransaction, btOfChangePassword, btOfExit);
            vBoxOfRight.getChildren().addAll(btOfCheckBalance, btOfWithdraw, btOfDeposit);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);
            pane.setCenter(borderPane);
        }

        /**
         * 事件处理函数
         */
        private void solveEvent(){
            btOfCheckBalance.setOnAction(e ->{
                new CheckBalancePane();
            });

            btOfWithdraw.setOnAction(e ->{
                new WithdrawPane();
            });

            btOfDeposit.setOnAction(e ->{
                new DepositPane();
            });

            btOfTransaction.setOnAction(e ->{
                new CheckTransactionPane();
            });

            btOfChangePassword.setOnAction(e ->{
                new ChangePasswordPane();
            });
        }

    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        setMainUI();//设置主面板组件
        new LoginPane();
        Scene scene = new Scene(pane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ATM");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
