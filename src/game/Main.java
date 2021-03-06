package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class contains the main method and sets up the game's graphics and
 * constant variables
 * 
 * @author Soravit Sophastienphong, credit to Robert C. Duvall for template code
 */
public class Main extends Application {
    private static Stage currStage;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 550;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private GameFrame Game;

    /**
     * Sets up the stage, scene, and graphics for the game
     * 
     * @param startStage The stage that the game is played on
     * @throws Exception Accounts for numerous types of exceptions
     * that could crash the game
     */
    @Override
    public void start (Stage startStage) throws Exception {
        // TODO Auto-generated method stub
        setCurrStage(startStage);
        Game = new GameFrame();
        startStage.setTitle(Game.getTitle());
        startStage.setResizable(false);
        startStage.sizeToScene();
        Scene startScene = Game.initializeSplashScreen(WIDTH, HEIGHT);
        startStage.setScene(startScene);
        startStage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> Game.step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * This is the main method
     */
    public static void main (String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    /**
     * Returns the stage
     */
    public static Stage getCurrStage () {
        return currStage;
    }

    /**
     * Sets the stage
     */
    public static void setCurrStage (Stage currStage) {
        Main.currStage = currStage;
    }
}
