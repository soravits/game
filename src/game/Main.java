package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	public static Stage currStage;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 550;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	private GameFrame Game;

	@Override
	public void start (Stage startStage) throws Exception {
		// TODO Auto-generated method stub
		
		currStage = startStage;
		Game = new GameFrame();
		startStage.setTitle(Game.getTitle());
		startStage.setResizable(false);
		startStage.sizeToScene();
		Scene startScene = Game.initializeSplashScreen(WIDTH, HEIGHT);
		startStage.setScene(startScene);
		startStage.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> Game.step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public static void main (String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
