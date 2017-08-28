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
    //���������
    private virtualKeyboard keyboard = new virtualKeyboard();
    //����������
    private BorderPane borderPaneOfKeyBoard = keyboard.getPane();
    //��̨������
    private ATMService service = new ATMService();

    //�����
    private BorderPane pane = new BorderPane();
    //��½���
    private BorderPane loginPane = new BorderPane();
    //ע�����
    private BorderPane registerPane = new BorderPane();
    //�����������
    private BorderPane changePasswordPane = new BorderPane();
    //ȡ�����
    private BorderPane withdrawPane = new BorderPane();
    //������
    private BorderPane depositPane = new BorderPane();
    //�鿴���׼�¼���
    private BorderPane detailOfTransactionPane = new BorderPane();
    //�˳���ť
    private Button btOfExit = new Button("�˳�");
    //����������
    private HBox hBoxOfKeyBoard = new HBox();
    //��ഹֱ���
    private VBox vBoxOfRight = new VBox(20);
    //�Ҳഹֱ���;
    private VBox vBoxOfLeft = new VBox(20);
    //������
    private BorderPane borderPaneOfRight = new BorderPane();
    //�Ҳ����;
    private BorderPane borderPaneOfLeft = new BorderPane();
    //�����ı������
    private VBox vBoxOfTextFiled = new VBox();

    //���ù���UI
    public void setMainUI(){
        //�����˳���ť
        setButton(btOfExit);

        //�˳���ť�¼�
        btOfExit.setOnAction(e ->{
            service.exit();
        });

        //�������������Ϊ������
        hBoxOfKeyBoard.setAlignment(Pos.CENTER);
        hBoxOfKeyBoard.getChildren().add(borderPaneOfKeyBoard);

        //����������Ӵ�ֱ���
        borderPaneOfLeft.setBottom(vBoxOfLeft);
        borderPaneOfRight.setBottom(vBoxOfRight);

        //���ı�������Ϊ��ֱ�ı����������
        vBoxOfTextFiled.setAlignment(Pos.CENTER);
    }

    //���ð�ť��С
    public void setButton(Button bt){
        bt.setMaxWidth(100);
        bt.setMinWidth(100);
        bt.setPrefWidth(100);
        bt.setMaxHeight(40);
        bt.setMinHeight(40);
        bt.setPrefHeight(40);
    }

    //�����ı����С
    public void setTextFiled(TextField textFiled){
        textFiled.setMaxWidth(250);
        textFiled.setMinWidth(250);
        textFiled.setPrefWidth(250);
        textFiled.setMaxHeight(30);
        textFiled.setMinHeight(30);
        textFiled.setPrefHeight(30);
    }

    //������������
    public void clearPane(){
        pane.getChildren().clear();
        vBoxOfLeft.getChildren().clear();
        vBoxOfRight.getChildren().clear();
        vBoxOfTextFiled.getChildren().clear();
    }

    //ϵͳ��ʾ��
    public void alertInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ϵͳ��ʾ");
        alert.setContentText(message);

        alert.show();
    }

    //��¼��
    class LoginPane{
        //�����˺ŵ��ı���
        protected TextField textFieldOfId = new TextField();
        //����������ı���
        protected TextField textFieldOfPassword = new TextField();
        protected Text textOfId = new Text("�������˺�");
        protected Text textOfPassword = new Text("����������");
        //��½��ť
        protected Button btOfLogin = new Button("��¼");
        //ע�ᰴť
        protected Button btOfRegister = new Button("ע��");
        //�������
        protected BorderPane borderPane = new BorderPane();
        //����˺�
        private String id = null;
        //�������
        private String password = null;

        /**
         * ���캯��
         */
        public LoginPane(){
            clearPane();//��������
            setUI();//�������
            solveEvent();//��������¼�
        }

        /**
         * �������
         */
        protected void setUI(){
            //�����ı����Ⱥͳ���
            setTextFiled(textFieldOfId);
            setTextFiled(textFieldOfPassword);

            //��ʾ�ı������־���
            textOfId.setTextAlignment(TextAlignment.CENTER);
            textOfPassword.setTextAlignment(TextAlignment.CENTER);

            //���ð�ť��С
            setButton(btOfLogin);
            setButton(btOfRegister);

            //�������������Ӱ�ť
            vBoxOfLeft.getChildren().addAll(btOfRegister, btOfExit);
            vBoxOfRight.getChildren().addAll(btOfLogin);

            //���ı������������ı������ʾ�ı�
            vBoxOfTextFiled.getChildren().addAll(textOfId, textFieldOfId, textOfPassword, textFieldOfPassword);

            //������������������
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //���������������������
            pane.setCenter(borderPane);
        }

        /**
         * ��������¼��͵���¼�
         */
        public void solveEvent(){
            //����Ĭ������ı���
            keyboard.setControl(textFieldOfId);

            //����¼���������ı���
            textFieldOfId.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfId);
            });
            textFieldOfPassword.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfPassword);
            });

            //��½��ť����¼�����
            btOfLogin.setOnAction(e ->{
                //��ȡ�˺ź�����
                id = textFieldOfId.getText();
                password = textFieldOfPassword.getText();

                //������Ϣ����ȡ����ֵ
                int value = service.login(id, password);

                if(value == 0){
                    alertInformation("�˺�ӦΪ6λ����");
                }else if(value == 1){
                    alertInformation("���˺���Ϣ");
                }else if(value == 2){
                    alertInformation("�������");
                }else if(value == 3){//��½�ɹ�
                    new MenuPane();//����������
                }
            });

            //���ע�ᰴť����ע�����
            btOfRegister.setOnAction(e ->{
                new RegisterPane();
            });
        }
    }

    /**
     * ע����
     */
    class RegisterPane{
        //�����û�������ʼ�������ʺ�������ı���
        protected TextField textFieldOfName = new TextField();
        protected TextField textFieldOfBalance = new TextField();
        protected TextField textFieldOfAnual = new TextField();
        protected TextField textFieldOfPassword = new TextField();

        //��ʾ�����Ϣ���ı�
        protected Text textOfName = new Text("�������û���");
        protected Text textOfBalance = new Text("�������ʼ���");
        protected Text textOfAnual = new Text("������������");
        protected Text textOfPassword = new Text("����������");

        //ע��ͷ��ذ�ť
        protected Button btOfReturn = new Button("������һ��");
        protected Button btOfRegister = new Button("ע��");

        //�������
        protected BorderPane borderPane = new BorderPane();

        //���캯��
        public RegisterPane(){
            clearPane();//���������
            setUI();//�������
            solveEvent();//�����¼�
        }

        /**
         * �������
         */
        public void setUI(){
            //���������ı����С
            setTextFiled(textFieldOfName);
            setTextFiled(textFieldOfBalance);
            setTextFiled(textFieldOfAnual);
            setTextFiled(textFieldOfPassword);

            //�����ı�����ʾ��Ϣ
            textFieldOfName.setPromptText("����������");
            textFieldOfBalance.setPromptText("�������˻����");
            textFieldOfAnual.setPromptText("������������(����:0.02)");
            textFieldOfPassword.setPromptText("������6λ����ĸ��������ɵ�����");

            //���ð�ť��С
            setButton(btOfRegister);
            setButton(btOfReturn);

            //���ı��������������
            vBoxOfTextFiled.getChildren().addAll(textOfName, textFieldOfName, textOfBalance, textFieldOfBalance,
                    textOfAnual, textFieldOfAnual, textOfPassword, textFieldOfPassword);

            //�����������Ӱ�ť
            vBoxOfLeft.getChildren().addAll(btOfReturn, btOfExit);
            vBoxOfRight.getChildren().addAll(btOfRegister);

            //�������������
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //���������Ӹ������
            pane.setCenter(borderPane);
        }

        /**
         * �¼�������
         */
        public void solveEvent(){
            //����Ĭ�������ı���
            keyboard.setControl(textFieldOfName);

            //���������ı���
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

            //ע�ᰴť�������
            btOfRegister.setOnAction(e ->{
                //����ע�ᷬ����ȡ����ֵ
                int value = service.register(textFieldOfName.getText(), textFieldOfBalance.getText(),
                                textFieldOfAnual.getText(), textFieldOfPassword.getText());

                //�жϲ�������ʾ��Ϣ
                if(value == 0){
                    alertInformation("���ֲ���Ϊ��");
                }else if(value == 1){
                    alertInformation("��ʼ���ӦΪ����");
                }else if(value == 2){
                    alertInformation("������ӦΪС��");
                }else if(value == 3){
                    alertInformation("����Ӧ��6-10λ���ֻ���ĸ���");
                }else if(value == 4){
                    alertInformation("ע��ʧ��,����ϵ�ͷ�");
                }else {
                    alertInformation("���ס�����˺� " + value);//����˺�
                }
            });

            //���ص�¼���水ť�������
            btOfReturn.setOnAction(e ->{
                new LoginPane();
            });
        }
    }

    /**
     * ȡ����
     */
    class WithdrawPane{
        //����ȡ����
        protected TextField textFieldOfWithdraw = new TextField();
        //��ʾ�ı�
        protected Text textOfWithdraw = new Text("������ȡ����");
        //ָ�����ȡ�ť
        protected Button btOf100 = new Button("100");
        protected Button btOf200 = new Button("200");
        protected Button btOf500 = new Button("500");
        protected Button btOf1000 = new Button("1000");
        protected Button btOf1500 = new Button("1500");
        protected Button btOf2000 = new Button("2000");
        //ȡ�ť
        protected Button btOfWithdraw = new Button("ȡ��");
        //���������水ť
        protected Button btOfReturn = new Button("������һ��");
        //�������
        protected BorderPane borderPane = new BorderPane();

        /**
         * ���캯��
         */
        public WithdrawPane(){
            clearPane();//������������
            setUI();//�������
            solveEvent();//�¼�������
        }

        /**
         * �����������
         */
        protected void setUI(){
            //�����ı����С
            setTextFiled(textFieldOfWithdraw);
            //�����ı�����ʾ��Ϣ
            textFieldOfWithdraw.setPromptText("������ȡ������");
            //���ð�ť��С
            setButton(btOf100);
            setButton(btOf200);
            setButton(btOf500);
            setButton(btOf1000);
            setButton(btOf1500);
            setButton(btOf2000);
            setButton(btOfReturn);
            setButton(btOfWithdraw);
            //���ı���ֱ����������
            vBoxOfTextFiled.getChildren().addAll(textOfWithdraw, textFieldOfWithdraw);
            //�����Ҵ�ֱ���������
            vBoxOfLeft.getChildren().addAll(btOf100, btOf200, btOf500, btOfReturn);
            vBoxOfRight.getChildren().addAll(btOf1000, btOf1500, btOf2000, btOfWithdraw);
            //���������������
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);
            //�����������Ӹ������
            pane.setCenter(borderPane);
        }

        /**
         * �¼�������
         */
        private void solveEvent(){
            //����Ĭ�������ı���
            keyboard.setControl(textFieldOfWithdraw);

            //ָ�����ȡ�ť����¼�����
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

            //ȡ�ť����¼�����
            btOfWithdraw.setOnAction(e ->{
                //����ȡ�������ȡ����ֵ
                int op = service.withdraw(textFieldOfWithdraw.getText());
                //���ô�����ֵ����
                option(op);
            });
            //���ذ�ť�¼�����
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });

        }

        /**
         * ������ֵ����
         * @param op
         */
        public void option(int op){
            if(op == 0){
                alertInformation("ȡ����ӦΪ100�ı�����Ϊ����");
            }else if (op == 1){
                alertInformation("�˻�����");
            }else if (op == 2){
                alertInformation("����ȡ�����Ѵ�5000����");
            }else if (op == 3){
                alertInformation("ȡ��ɹ�");
            }
        }
    }

    /**
     * ������
     */
    class DepositPane{
        //�����������ı���
        protected TextField textFieldOfDeposit = new TextField();
        //��ʾ�ı�
        protected Text textOfDeposit = new Text("�����������");
        //ָ�������밢ţ
        protected Button btOf100 = new Button("100");
        protected Button btOf200 = new Button("200");
        protected Button btOf500 = new Button("500");
        protected Button btOf1000 = new Button("1000");
        protected Button btOf1500 = new Button("1500");
        protected Button btOf2000 = new Button("2000");
        //���밴ť
        protected Button btOfDeposit = new Button("����");
        //���ذ�ť
        protected Button btOfReturn = new Button("������һ��");
        //�������
        protected BorderPane borderPane = new BorderPane();

        /**
         * ���캯��
         */
        public DepositPane(){
            clearPane();//������������
            setUI();//�������
            solveEvent();//�����¼�����
        }

        /**
         * �������
         */
        protected void setUI(){
            //�����ı����С
            setTextFiled(textFieldOfDeposit);

            //���ð�ť��С
            setButton(btOf100);
            setButton(btOf200);
            setButton(btOf500);
            setButton(btOf1000);
            setButton(btOf1500);
            setButton(btOf2000);
            setButton(btOfDeposit);
            setButton(btOfReturn);

            //�������������Ӱ�ť
            vBoxOfLeft.getChildren().addAll(btOf100, btOf200, btOf500, btOfReturn);
            vBoxOfRight.getChildren().addAll(btOf1000, btOf1500, btOf2000,btOfDeposit);

            //��ֱ�ı����������ı���
            vBoxOfTextFiled.getChildren().addAll(textOfDeposit, textFieldOfDeposit);

            //�������������
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //���������Ӹ������
            pane.setCenter(borderPane);
        }

        /**
         * �¼�������
         */
        protected void solveEvent(){
            //����Ĭ�������ı���
            keyboard.setControl(textFieldOfDeposit);

            //ָ�������밴ť����¼�����
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

            //���밴ť����¼�����
            btOfDeposit.setOnAction(e ->{
                int op = service.deposit(textFieldOfDeposit.getText());
                option(op);
            });

            //���������水ť����¼�����
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });
        }

        /**
         * ������ֵ����
         * @param op
         */
        protected void option(int op){
            if(op == 0){
                alertInformation("������ӦΪ������С���Ҵ�����");
            }else if (op == 1){
                alertInformation("������Ӧ������");
            }else {
                alertInformation("����ɹ�");
            }
        }

    }

    /**
     * �鿴�˻������
     */
    class CheckBalancePane{
        //��ʾ�˻�����ı�
        protected Text textOfCheckBalance = new Text();
        //���ذ�ť
        protected Button btOfReturn = new Button("������һ��");
        //�������
        protected BorderPane borderPane = new BorderPane();

        /**
         * ���캯��
         */
        public CheckBalancePane(){
            clearPane();//������������
            setUI();//�������
            solveEvent();//�¼�������
        }

        /**
         * �����������
         */
        protected void setUI(){
            //������ʾ����λ���ı�����
            textOfCheckBalance.setTextAlignment(TextAlignment.CENTER);

            //���÷��ذ�ť��С
            setButton(btOfReturn);

            //���������ӷ��ذ�ť
            vBoxOfLeft.getChildren().addAll(btOfReturn);

            //�����Ĵ�ֱ�ı�����������ʾ�ı�
            vBoxOfTextFiled.getChildren().addAll(textOfCheckBalance);

            //�������������
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);

            //���������Ӹ������
            pane.setCenter(borderPane);
        }

        /**
         * �¼�������
         */
        protected void solveEvent(){
            //��ʾ�˻����
            textOfCheckBalance.setText("�����˻����Ϊ: " + service.checkBalance());

            //���ذ�ť����¼�����
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });
        }
    }

    /**
     * �޸�������
     */
    class ChangePasswordPane{
        //�����������
        protected TextField textFieldOfOld = new TextField();
        //�����������
        protected TextField textFieldOfNew1 = new TextField();
        //�ٴ����������
        protected TextField textFieldOfNew2 = new TextField();
        //��ʾ�ı�
        protected Text textOfOld = new Text("�����������");
        protected Text textOfNew1 = new Text("������������");
        protected Text textOfNew2 = new Text("���ٴ�����������");
        //���ذ�ť���޸İ�ť
        protected Button btOfReturn = new Button("������һ��");
        protected Button btOfChange = new Button("�޸�");
        //�������
        protected BorderPane borderPane = new BorderPane();

        /**
         * ���캯��
         */
        public ChangePasswordPane(){
            clearPane();//���������
            setUI();//�������
            solveEvent();//�¼�����
        }

        /**
         * �������
         */
        protected void setUI(){
            //����������С
            setTextFiled(textFieldOfOld);
            setTextFiled(textFieldOfNew1);
            setTextFiled(textFieldOfNew2);

            //�����������ʾ��Ϣ
            textFieldOfOld.setPromptText("�����������");
            textFieldOfNew1.setPromptText("������������");
            textFieldOfNew2.setPromptText("���ٴ���������");

            //���ð�ť��С
            setButton(btOfReturn);
            setButton(btOfChange);

            //�������ֱ�����Ӱ�ť
            vBoxOfTextFiled.getChildren().addAll(textOfOld, textFieldOfOld, textOfNew1,
                                                textFieldOfNew1,textOfNew2, textFieldOfNew2);

            //�����������Ӱ�ť
            vBoxOfLeft.getChildren().addAll(btOfReturn);
            vBoxOfRight.getChildren().addAll(btOfChange);

            //�������������
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);
            borderPane.setBottom(hBoxOfKeyBoard);

            //���������Ӹ������
            pane.setCenter(borderPane);
        }

        /**
         * �¼�������
         */
        protected void solveEvent(){
            //����Ĭ�������
            keyboard.setControl(textFieldOfOld);

            //��������¼����õ�ǰ�����
            textFieldOfOld.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfOld);
            });
            textFieldOfNew1.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfNew1);
            });
            textFieldOfNew2.setOnMouseClicked(e ->{
                keyboard.setControl(textFieldOfNew2);
            });

            //���ذ�ť����¼�����
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });

            //�޸İ�ť����¼�����
            btOfChange.setOnAction(e ->{
                //�����޸����뺯������ȡ����ֵ
                int op = service.changePassword(textFieldOfOld.getText(),
                                                textFieldOfNew1.getText(), textFieldOfNew2.getText());
                //���÷���ֵ������
                option(op);
            });
        }

        /**
         * ����ֵ������
         * @param op
         */
        protected void option(int op){

            if (op == 0){
                alertInformation("�������");
            }else if (op == 1){
                alertInformation("���1����������벻ͬ");
            }else if (op == 2){
                alertInformation("����ӦΪ6-10λ���ֻ���ĸ");
            }else if (op == 3){
                alertInformation("�޸ĳɹ�");
            }
        }
    }

    /**
     * �鿴���׼�¼��
     */
    class CheckTransactionPane{
        //��ʾ���׼�¼�ı�����
        protected TextArea textArea = new TextArea();
        //���ذ�ť
        protected Button btOfReturn = new Button("������һ��");
        //����֧�����
        protected ScrollPane scrollPane = new ScrollPane(textArea);
        //�������
        protected BorderPane borderPane = new BorderPane();
        //���׼�¼����
        protected ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();

        /**
         * ���캯��
         */
        public CheckTransactionPane(){
            clearPane();//���������
            setUI();//�������
            solveEvent();//�¼�����
        }

        /**
         * �����������
         */
        protected void setUI(){
            //�����������Ϊ40��20�в��ҿɻ���
            textArea.setPrefColumnCount(35);
            textArea.setPrefRowCount(20);
            textArea.setWrapText(true);

            //���ð�ť��С
            setButton(btOfReturn);

            //������
            vBoxOfLeft.getChildren().addAll(btOfReturn);
            vBoxOfTextFiled.getChildren().addAll(scrollPane);
            borderPane.setCenter(vBoxOfTextFiled);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            pane.setCenter(borderPane);
        }

        /**
         * �¼�������
         */
        protected void solveEvent(){
            //��ȡ���׼�¼
            transactionArrayList = service.detailOfTransaction();
            StringBuilder string = new StringBuilder();
            for(int i = 0; i < transactionArrayList.size(); i++){
                string.append(transactionArrayList.get(i) + "\n");
            }
            textArea.setText(string.toString());
            textArea.setEditable(false);

            //���ذ�ť����¼�����
            btOfReturn.setOnAction(e ->{
                new MenuPane();
            });
        }

    }

    /**
     * ��������
     */
    class MenuPane{
        //ѡ�ť
        protected Button btOfWithdraw = new Button("ȡ��");
        protected Button btOfDeposit = new Button("���");
        protected Button btOfTransaction = new Button("�鿴���׼�¼");
        protected Button btOfCheckBalance = new Button("�鿴���");
        protected Button btOfChangePassword = new Button("�޸�����");
        protected Button btOfReturn = new Button("���ص�¼");
        //�������
        protected BorderPane borderPane = new BorderPane();

        /**
         * ���캯��
         */
        public MenuPane(){
            clearPane();//���������
            setUI();//�������
            solveEvent();//�¼�����
        }

        /**
         * �����������
         */
        private void setUI(){
            //���ð�ť��С
            setButton(btOfCheckBalance);
            setButton(btOfWithdraw);
            setButton(btOfDeposit);
            setButton(btOfTransaction);
            setButton(btOfChangePassword);
            setButton(btOfReturn);

            //������
            vBoxOfLeft.getChildren().addAll(btOfTransaction, btOfChangePassword, btOfReturn);
            vBoxOfRight.getChildren().addAll(btOfCheckBalance, btOfWithdraw, btOfDeposit);
            borderPane.setBottom(hBoxOfKeyBoard);
            borderPane.setLeft(borderPaneOfLeft);
            borderPane.setRight(borderPaneOfRight);
            pane.setCenter(borderPane);
        }

        /**
         * �¼�������
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
            
            btOfReturn.setOnAction(e ->{
            	new LoginPane();
            });
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setMainUI();//������������
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
