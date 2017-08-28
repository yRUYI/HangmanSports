package version3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class countLetterFromFile extends Application{
    //定义一个字符数组存储26个英文字母
    final String[] letters = {"A", "B", "C", "D", "E", "F",	"G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    //定义X轴
    final CategoryAxis xAxis = new CategoryAxis();
    //定义Y轴
    final NumberAxis yAxis = new NumberAxis();
    //创建柱状图
    final BarChart<String,Number> bc =
            new BarChart<String,Number>(xAxis,yAxis);
    //从文件读取数据
    private String data = null;

    //目标文件
    private String targetFile = null;
    private BorderPane borderPane = new BorderPane();
    private TextField textField = new TextField();
    private TextArea textArea = new TextArea();

    private VBox vBox = new VBox();
    private HBox hBox = new HBox();

    private Button buttonOfRead = new Button("读取");
    private Button buttonOfWrite = new Button("写入");

    public void start(Stage primaryStage) throws Exception {
        //设置统计图标题，X轴名称，Y轴名称
        bc.setTitle("字符统计个数");
        xAxis.setLabel("字母");
        yAxis.setLabel("个数");

        //设置统计图最大高度
        bc.setMaxHeight(300);

        textField.setPromptText("请输入文件名（例如: test）");
        textArea.setWrapText(true);
        textArea.setPrefRowCount(5);
        textArea.setPrefColumnCount(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(textField, textArea, hBox);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(buttonOfRead, buttonOfWrite);

        //点击事件处理
        handler();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(bc);
        borderPane.setBottom(vBox);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void handler(){
        buttonOfRead.setOnAction(event -> {
            //打印统计结果
            printData();
        });

        //存入文件
        buttonOfWrite.setOnAction(event -> {
            targetFile = textField.getText();
            data = textArea.getText();
            writeByte(data, targetFile);
            alertInformation("写入成功");
            printData();
        });
    }

    //打印结果
    public void printData(){
        textArea.setText("");
        bc.getData().clear();
        targetFile = textField.getText();//获取目标文件
        data = readByte(targetFile);//读取文件

        //获取用户输入的字符串统计结果
        int[] numberOfLetters = new int[0];
        try {
            numberOfLetters = countLetters(data);
        } catch (Exception e) {
            alertInformation("未找到文件, 读取失败");//弹出提示信息
        }

        //添加字符统计结果
        XYChart.Series series = new XYChart.Series();
        for(int i = 0; i < numberOfLetters.length; i++){
            series.getData().add(new XYChart.Data(letters[i], numberOfLetters[i]));
        }
        bc.getData().add(series);
    }

    //系统提示框
    public void alertInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("系统提示");
        alert.setContentText(message);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int[] countLetters(String string) throws Exception{
        //创建一个整型数组并初始化
        int[] numberOfLetters = new int[26];
        for(int i = 0; i < numberOfLetters.length; i++){
            numberOfLetters[i] = 0;
        }

        //将字符串转换为大写
        string = string.toUpperCase();

        //循环判断并各个字符个数
        for(int i = 0; i < string.length(); i++){
            switch(string.charAt(i)){
                case 'A': numberOfLetters[0]++;break;
                case 'B': numberOfLetters[1]++;break;
                case 'C': numberOfLetters[2]++;break;
                case 'D': numberOfLetters[3]++;break;
                case 'E': numberOfLetters[4]++;break;
                case 'F': numberOfLetters[5]++;break;
                case 'G': numberOfLetters[6]++;break;
                case 'H': numberOfLetters[7]++;break;
                case 'I': numberOfLetters[8]++;break;
                case 'J': numberOfLetters[9]++;break;
                case 'K': numberOfLetters[10]++;break;
                case 'L': numberOfLetters[11]++;break;
                case 'M': numberOfLetters[12]++;break;
                case 'N': numberOfLetters[13]++;break;
                case 'O': numberOfLetters[14]++;break;
                case 'P': numberOfLetters[15]++;break;
                case 'Q': numberOfLetters[16]++;break;
                case 'R': numberOfLetters[17]++;break;
                case 'S': numberOfLetters[18]++;break;
                case 'T': numberOfLetters[19]++;break;
                case 'U': numberOfLetters[20]++;break;
                case 'V': numberOfLetters[21]++;break;
                case 'W': numberOfLetters[22]++;break;
                case 'X': numberOfLetters[23]++;break;
                case 'Y': numberOfLetters[24]++;break;
                case 'Z': numberOfLetters[25]++;break;
            }
        }

        //返回结果
        return numberOfLetters;

    }

    //向文件写入数据
    public int writeByte(String str, String targetFileName){
        try{
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(targetFileName + ".dat"));
            bos.write(str.getBytes(), 0, str.length());
            bos.flush();
            bos.close();
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("写入失败");
            return -1;
        }
    }

    //从文件读出数据
    public String readByte(String sourceFileName){
        try{
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(sourceFileName + ".dat"));

            String content=null;
            //定义一个缓冲区
            byte[] buffer=new byte[10240];
            int flag=0;
            while((flag=bis.read(buffer))!=-1){
                content+=new String(buffer, 0, flag);
            }

            return content;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
