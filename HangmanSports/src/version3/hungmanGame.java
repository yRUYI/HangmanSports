package version3;

import java.util.ArrayList;

import javafx.animation.PathTransition;
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
	final String[] words = {"words", "that", "menu", "like", "life", "program"
			, "java", "free", "world", "center", "top", "button", "application"};
	
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
	private Line[] list = {line3, line4, line5, line6, line7, line8};
	
	private Text text1 = new Text(200, 400, "");
	private Text text2 = new Text(200, 450, "Miss letters: ");
	
	private Arc pathOfCircle = new Arc(300, 20, 80, 90, 210, 125);
	private Arc pathOfLine3 = new Arc(300, 20, 80, 30, 210, 125);
	private Arc pathOfLine4 = new Arc(300, 20, 80, 90, 210, 125);
	private Arc pathOfLine5 = new Arc(300, 20, 80, 90, 210, 125);
	private Arc pathOfLine6 = new Arc(300, 20, 80, 90, 210, 125);
	private Arc pathOfLine7 = new Arc(300, 20, 80, 90, 210, 125);
	private Arc pathOfLine8 = new Arc(300, 20, 80, 90, 210, 125);
	private Arc[] pathOfSwing = {pathOfLine3, pathOfLine4, pathOfLine5, 
									pathOfLine6, pathOfLine7, pathOfLine8};
	
//	private PathTransition[] pt = new PathTransition[8];
	
	private String word = null;
	private StringBuilder replacedWord = new StringBuilder();
	private int wrongTimes = 0;
	private int correct = 0;

    @Override
	public void start(Stage stage) throws Exception {
    	reStart();
    	Pane pane = new Pane();
    	pane.getChildren().addAll(arc,line1,line2,line3,line4,circle,line5,
    			line6,line7,line8,text1,text2);
    	
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
    
    //随机获取单词并设置为****格式
    public void setWord(){
    	replacedWord.delete(0, replacedWord.length());
    	word = words[((int)(Math.random() * words.length))];
    	for(int i=0; i<word.length(); i++){
    		replacedWord.append('*');
    	}
    }
    
    public void guessWord(KeyEvent e){
    	char key = e.getText().charAt(0);
    	
    	if(wrongTimes < 7 && correct < word.length()){
    		if(replacedWord.indexOf(key + "") >= 0){
    			
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
	    	
	    	if(wrongTimes < 7 && correct < word.length()){
		    	text1.setText("Guess a word > " + replacedWord);
		    	
		    	if(existed(key) < 0){
		    		text2.setText(text2.getText() + e.getText().charAt(0));
	    		}
	    	}else{
//	    		startSwing();
	    		text1.setText("The word is: " + word);
	        	text2.setText("To continiu the game, press ENTER");
	    	}
	    	showUI();
    	}else{
//    		setPathOfSwing();
    		switch(e.getCode()){
    		case ENTER:reStart();hideUI();break;
    		}
    	}    	
    }
    
    //返回text2中出现输入字母的下标
    public int existed(char ch){
    	return text2.getText().indexOf(ch,12);
    }
    
    //重新开始游戏
    public void reStart(){
    	setUI();
    	hideUI();
    	setWord();
    	correct = 0;
    	wrongTimes = 0;
    	text1.setText("Guess a word > " + replacedWord);
    	text2.setText("Miss letters: ");
    }
    
    //隐藏小人
    public void hideUI(){
    	text2.setVisible(false);
    	line3.setVisible(false);
    	line4.setVisible(false);
    	line5.setVisible(false);
    	line6.setVisible(false);
    	line7.setVisible(false);
    	line8.setVisible(false);
    	circle.setVisible(false);
    }
    
    //展示小人
    public void showUI(){
    	if(correct == word.length()){
    		text2.setVisible(true);
    	}
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
    
//    //错误次数为7,开始摇摆
//    public void setPathOfSwing(){
////    	PathTransition[] ptOfCircle = new PathTransition[8];
//    	for(int i=0; i<pathOfSwing.length; i++){
//    		pt[i].setDuration(Duration.millis(500));    	
//	    	pt[i].setPath(pathOfSwing[i]);
//	    	pt[i].setNode(list[i]);
//	    	pt[i].setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//	    	pt[i].setCycleCount(Timeline.INDEFINITE);
//	    	pt[i].setAutoReverse(true);
//    	}
//    	pt[7].setPath(pathOfCircle);
//    	pt[7].setNode(circle);
//    	pt[7].setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//    	pt[7].setCycleCount(Timeline.INDEFINITE);
//    	pt[7].setAutoReverse(true);
//    }
//    
//    //开始摇摆
//    public void startSwing(){
//    	for(int i = 0; i < pt.length; i++){
//    		pt[i].play();
//    	}
//    }
//    
//    //停止摇摆
//    public void stopSwing(){
//    	for(int i = 0; i < pt.length; i++){
//    		pt[i].pause();
//    	}
//    }
    
    public static void main(String[] args) {
        launch(args);
    }

}

