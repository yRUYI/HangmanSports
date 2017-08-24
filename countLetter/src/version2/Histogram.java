package version2;

import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class Histogram extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
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
        
        //设置统计图标题，X轴名称，Y轴名称
        bc.setTitle("字符个数统计");
        xAxis.setLabel("字母");       
        yAxis.setLabel("个数");
        
        //获取用户输入的字符串统计结果
        int[] numberOfLetters = countLetters();
        
        //添加字符统计结果
        XYChart.Series series = new XYChart.Series();
        for(int i = 0; i < 26; i++){
        	series.getData().add(new XYChart.Data(letters[i], numberOfLetters[i]));
        }


        Scene scene = new Scene(bc);
        bc.getData().add(series);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static int[] countLetters(){
    	//提示用户输入字符串并接收
    	Scanner input = new Scanner(System.in);
    	System.out.println("请输入字符串:");
    	String string = input.nextLine();
    	
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
}
