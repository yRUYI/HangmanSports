package version3;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class hungmanGame extends Application{
	final String[] words = {"words", "that", "menu", "like", "life", "program",
							"java", "free", "world", "center", "top", "button", "application"};
	//圆直径
	final int radius = 30;
	private Arc arc = new Arc(100, 400 + 200, 50, 30, 0, 180);	
	private Line line1 = new Line(100, 570, 100, 20);
	private Line line2 = new Line(100, 20, 300, 20);
	private Line line3 = new Line(300, 20, 300, 80);	
	private Circle circle = new Circle(300, 110, radius);
	private Line line4 = new Line(300, 140, 300, 250);
	//左手
	private Line line5 = new Line(300 - radius * Math.cos(Math.toRadians(45)),
							110 + radius * Math.sin(Math.toRadians(45)), 200, 200);
	//右手
	private Line line6 = new Line(300 + radius * Math.cos(Math.toRadians(45)),
							110 + radius * Math.sin(Math.toRadians(45)), 400, 200);
	private Line line7 = new Line(300, 250, 200, 350);
	private Line line8 = new Line(300, 250, 400, 350);
	
	//提示框
	private Text text1 = new Text(200, 400, "");
	private Text text2 = new Text(200, 450, "Miss letters: ");
	
	//摇摆设定
	private double leftY = 110 + radius * Math.sin(Math.toRadians(45));
	private double line3Radius = 60;
	private double circleRadius = 90;
	private double line4StartRadius = 120;
	private double line4Radius = 230;
	private double line5StartRadius = leftY - 20;
	private double line6StartRadius = line5StartRadius;
	private double line7StartRadius = line4Radius;
	private double line8StartRadius = line4Radius;
	private double leftAngle = 120;
	private double rightAngle = 60;
	private double angle = leftAngle; // 从左边开始
	private double angleDelta = 5; // 摇摆角度差
	
	private Timeline animation;
	
	//选取的单词
	private String word = null;
	//显示的结果
	private StringBuilder replacedWord = new StringBuilder();
	//错误次数
	private int wrongTimes = 0;
	//正确次数
	private int correct = 0;

    @Override
	public void start(Stage stage) throws Exception {
    	//设置UI
    	setUI();
    	//开始
    	reStart();
    	Pane pane = new Pane();
    	pane.getChildren().addAll(arc,line1,line2,line3,line4,circle,line5,
    			line6,line7,line8,text1,text2);
    	
    	//设置猜测字母
    	setWord();
    	
    	text1.setText("Guess a word > " + replacedWord);
    	
    	pane.setOnKeyPressed(e ->{
        	guessWord(e);
    	});    	
    	
    	stage.setScene(new Scene(pane, 600, 600));
    	stage.show();
    	pane.requestFocus();
    }
    
    //设置面板组件
    public void setUI(){
    	arc.setFill(Color.WHITE);
		arc.setType(ArcType.OPEN);
		arc.setStroke(Color.BLACK);
		
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		
		text1.setFont(Font.font("Courier", FontWeight.BLACK, FontPosture.REGULAR, 20));
		text2.setFont(Font.font("Courier", FontWeight.BLACK, FontPosture.REGULAR, 20));
    }
    
    //摇摆设置
    public void swing(){
    	animation = new Timeline(
				new KeyFrame(Duration.millis(50), e ->move()));
		animation.setCycleCount(Timeline.INDEFINITE);
		play();
	}
    
    //随机获取单词并设置为****格式
    public void setWord(){
    	replacedWord.delete(0, replacedWord.length());
    	word = words[((int)(Math.random() * words.length))];
    	for(int i=0; i<word.length(); i++){
    		replacedWord.append('*');
    	}
    }
    
    public void guessWord(KeyEvent e){
    	//获取猜测字母
    	char key = e.getText().charAt(0);
    	
    	if(wrongTimes < 7 && correct < word.length()){
    		if(replacedWord.indexOf(key + "") >= 0){//猜测字母已经猜过
    			
    		}else if(word.indexOf(key) < 0){//单词中没有用户输入字母
				wrongTimes++;
			}else{
				int index = word.indexOf(key);//获取正确字母下标
				while(index >= 0){
					replacedWord.setCharAt(index, key);//用正确字母替换*
					correct++;//正确次数加1
					index = word.indexOf(key, index + 1);//寻找下一个正确字母下标
				}
			}
	    	
    		//打印当前猜测结果
	    	if(wrongTimes < 7 && correct < word.length()){
		    	text1.setText("Guess a word > " + replacedWord);
		    	
		    	if(existed(key) < 0){
		    		//输出猜错字母
		    		text2.setText(text2.getText() + e.getText().charAt(0));
	    		}
	    	}else{
	    		text1.setText("The word is: " + word);
	        	text2.setText("To continiu the game, press ENTER");
	        	//开始摇摆
	        	swing();
	    	}
	    	
	    	//展示人物
	    	showUI();
    	}else{//继续下一轮游戏
    		switch(e.getCode()){
    		case ENTER:reStart();hideUI();pause();break;
    		}
    	}    	
    }
    
    //返回text2中出现输入字母的下标
    public int existed(char ch){
    	return text2.getText().indexOf(ch,12);
    }
    
    //重新开始游戏
    public void reStart(){
    	//隐藏人物
    	hideUI();
    	//设置下一个单词
    	setWord();
    	correct = 0;
    	wrongTimes = 0;
    	text1.setText("Guess a word > " + replacedWord);
    	text2.setVisible(false);//隐藏提示框
    	text2.setText("Miss letters: ");
    }
    
    //隐藏小人
    public void hideUI(){
    	line3.setVisible(false);
    	line4.setVisible(false);
    	line5.setVisible(false);
    	line6.setVisible(false);
    	line7.setVisible(false);
    	line8.setVisible(false);
    	circle.setVisible(false);
    }
    
    //展示人物
    public void showUI(){
    	//猜单词成功显示继续游戏提示
    	if(correct == word.length()){
    		text2.setVisible(true);
    	}
    	
    	//猜错单词显示人物并打印出错误字母信息
    	switch(wrongTimes){
    	case 1: line3.setVisible(true);text2.setVisible(true);break;
    	case 2: circle.setVisible(true);break;
    	case 3: line5.setVisible(true);break;
    	case 4: line6.setVisible(true);break;
    	case 5: line4.setVisible(true);break;
    	case 6: line7.setVisible(true);break;
    	case 7: line8.setVisible(true);break;
    	}
    }
    
    //开始
    public void play(){
		animation.play();
	}
	
    //暂停
	public void pause(){
		animation.pause();
	}
	
	//人物摇摆
	public void move(){
		//摇摆中心
		double x1 = 300;
	    double y1 = 20;

	    if (angle < rightAngle)
	      angleDelta = 5; // 向左摇
	    else if (angle > leftAngle)
	      angleDelta = -5; // 向右摇

	    //下一个位置
	    angle += angleDelta;
	    double x = x1 + line3Radius * Math.cos(Math.toRadians(angle));
	    double y = y1 + line3Radius * Math.sin(Math.toRadians(angle));
	    
	    double x2 = x1 + circleRadius * Math.cos(Math.toRadians(angle));
	    double y2 = y1 + circleRadius * Math.sin(Math.toRadians(angle));
	    
	    double startX3 = x1 + line4StartRadius * Math.cos(Math.toRadians(angle));
	    double startY3 = y1 + line4StartRadius * Math.sin(Math.toRadians(angle));
	    double x3 = x1 + line4Radius * Math.cos(Math.toRadians(angle));
	    double y3 = y1 + line4Radius * Math.sin(Math.toRadians(angle));
	    
	    double startX5 = x1 + line5StartRadius * Math.cos(Math.toRadians(angle + 10));
	    double startY5 = y1 + line5StartRadius * Math.sin(Math.toRadians(angle + 10));
	    double endX5 = x1 + 220 * Math.cos(Math.toRadians(angle + 30));
	    double endY5 = y1 + 220 * Math.sin(Math.toRadians(angle + 30));
	    
	    double startX6 = x1 + line6StartRadius * Math.cos(Math.toRadians(angle - 10));
	    double startY6 = y1 + line6StartRadius * Math.sin(Math.toRadians(angle - 10));
	    double endX6 = x1 + 220 * Math.cos(Math.toRadians(angle - 30));
	    double endY6 = y1 + 220 * Math.sin(Math.toRadians(angle - 30));
	    
	    double startX7 = x1 + line7StartRadius * Math.cos(Math.toRadians(angle));
	    double startY7 = y1 + line7StartRadius * Math.sin(Math.toRadians(angle));
	    double endX7 = x1 + 330 * Math.cos(Math.toRadians(angle + 15));
	    double endY7 = y1 + 330 * Math.sin(Math.toRadians(angle + 15));
	    
	    double startX8 = x1 + line8StartRadius * Math.cos(Math.toRadians(angle));
	    double startY8 = y1 + line8StartRadius * Math.sin(Math.toRadians(angle));
	    double endX8 = x1 + 330 * Math.cos(Math.toRadians(angle - 15));
	    double endY8 = y1 + 330 * Math.sin(Math.toRadians(angle - 15));

	    //设置下一个位置
	    line3.setStartX(300);line3.setStartY(20);
	    line3.setEndX(x);line3.setEndY(y);
	    circle.setCenterX(x2);circle.setCenterY(y2);
	    line4.setStartX(startX3);line4.setStartY(startY3);
	    line4.setEndX(x3);line4.setEndY(y3);
	    line5.setStartX(startX5);line5.setStartY(startY5);
	    line5.setEndX(endX5);line5.setEndY(endY5);
	    line6.setStartX(startX6);line6.setStartY(startY6);
	    line6.setEndX(endX6);line6.setEndY(endY6);
	    line7.setStartX(startX7);line7.setStartY(startY7);
	    line7.setEndX(endX7);line7.setEndY(endY7);
	    line8.setStartX(startX8);line8.setStartY(startY8);
	    line8.setEndX(endX8);line8.setEndY(endY8);	    
	}
    
    public static void main(String[] args) {
        launch(args);
    }

}

