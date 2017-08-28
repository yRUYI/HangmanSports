package version3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShowFrequency extends Application{
	//����һ���ַ�����洢26��Ӣ����ĸ
	final String[] letters = {"A", "B", "C", "D", "E", "F",	"G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	//����X��
	final CategoryAxis xAxis = new CategoryAxis();
	//����Y��
	final NumberAxis yAxis = new NumberAxis();
	//������״ͼ
	final BarChart<String,Number> bc =
			new BarChart<String,Number>(xAxis,yAxis);
	//���ļ���ȡ����
	private String data = null;

	public void start(Stage primaryStage) throws Exception {
		Scanner input = new Scanner(System.in);

        //����ͳ��ͼ���⣬X�����ƣ�Y������
        bc.setTitle("�ַ�����ͳ��");
        xAxis.setLabel("��ĸ");
        yAxis.setLabel("����");

        //����ͳ��ͼ���߶�
        bc.setMaxHeight(300);

		data = readByte("test1");

        //��ȡ�û�������ַ���ͳ�ƽ��
        int[] numberOfLetters = countLetters(data);

        //����ַ�ͳ�ƽ��
        XYChart.Series series = new XYChart.Series();
        for(int i = 0; i < 26; i++){
        	series.getData().add(new XYChart.Data(letters[i], numberOfLetters[i]));
        }

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(bc);

        Scene scene = new Scene(borderPane);
        bc.getData().add(series);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static int[] countLetters(String string){    	
    	//����һ���������鲢��ʼ��
		int[] numberOfLetters = new int[26];
		for(int i = 0; i < numberOfLetters.length; i++){
			numberOfLetters[i] = 0;
		}
		
		//���ַ���ת��Ϊ��д
		string = string.toUpperCase();
		
		//ѭ���жϲ������ַ�����
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
    	
    	//���ؽ��
    	return numberOfLetters;
    	
    }
    
    //���ļ�д������
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
    		System.out.println("д��ʧ��");
    		return -1;
    	}
    }
    
    //���ļ���������
    public String readByte(String sourceFileName){
    	try{
    		BufferedInputStream bis = new BufferedInputStream(
    				new FileInputStream(sourceFileName + ".dat"));
    		
    		String content=null;
            //����һ��������
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
