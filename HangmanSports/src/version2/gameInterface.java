package version2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class gameInterface extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		
		Arc arc = new Arc(100, 400 + 200, 50, 30, 0, 180);
		arc.setFill(Color.WHITE);
		arc.setType(ArcType.OPEN);
		arc.setStroke(Color.BLACK);
		
		Line line1 = new Line(100, 570, 100, 20);
		Line line2 = new Line(100, 20, 300, 20);
		Line line3 = new Line(300, 20, 300, 80);

		int radius = 30;
		Circle circle = new Circle(300, 110, radius);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);

		Line line4 = new Line(300, 140, 300, 250);
		//左手
		Line line5 = new Line(300 - radius * Math.cos(Math.toRadians(45)),
								110 + radius * Math.sin(Math.toRadians(45)), 200, 200);
		//右手
		Line line6 = new Line(300 + radius * Math.cos(Math.toRadians(45)),
								110 + radius * Math.sin(Math.toRadians(45)), 400, 200);
		Line line7 = new Line(300, 250, 200, 350);
		Line line8 = new Line(300, 250, 400, 350);

//		circle.setVisible(false);

        pane.getChildren().addAll(arc, line1, line2, line3, line4, circle, line5, line6, line7, line8);
		Scene scene = new Scene(pane);
		primaryStage.setTitle("刽子手游戏面板");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
